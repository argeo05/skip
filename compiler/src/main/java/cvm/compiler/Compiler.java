package cvm.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import cvm.instructions.Instructions;
import utils.BytesParser;

/**
 * The Compiler class is responsible for parsing CVM assembly-like source code,
 * constructing constant and function tables, and generating the corresponding
 * bytecode output file.
 */
public class Compiler {

    /**
     * Reads the source assembly file from the given path, compiles it to bytecode,
     * and writes the resulting bytecode to the specified output path.
     *
     * @param sourcePath the file system path to the source assembly file
     * @param outputPath the file system path where the compiled bytecode will be written
     * @throws IOException if an I/O error occurs while reading or writing files
     */
    public void compile(String sourcePath, String outputPath) throws IOException {
        String source = Files.readString(Path.of(sourcePath));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        writeHeader(output, 0, 0);
        compileToBytecode(source, output);
        Files.write(Path.of(outputPath), output.toByteArray());
    }

    private void compileToBytecode(String source, ByteArrayOutputStream output) throws IOException {
        String[] lines = source.split("\n");
        int i = 0;
        List<Constant> constantTable = new ArrayList<>();
        List<CompiledFunction> functions = new ArrayList<>();

        while (i < lines.length) {
            String trimmed = lines[i].trim();
            if (trimmed.isEmpty() || trimmed.startsWith(";")) {
                i++;
                continue;
            }
            break;
        }

        // Parse constant block if present
        if (i < lines.length && lines[i].trim().toLowerCase().startsWith("const ")) {
            String[] parts = lines[i].trim().split("\\s+");
            if (parts.length < 2) {
                throw new RuntimeException("Incorrect constant block definition");
            }
            int constCount = Integer.parseInt(parts[1]);
            i++;
            for (int j = 0; j < constCount && i < lines.length; ) {
                String line = lines[i];
                if (line.startsWith(" ") || line.startsWith("\t")) {
                    String trimmed = line.trim();
                    int spaceIndex = trimmed.indexOf(' ');
                    if (spaceIndex < 0) {
                        throw new RuntimeException("Incorrect constant definition: " + trimmed);
                    }
                    String literal = trimmed.substring(spaceIndex + 1).trim();
                    if (literal.startsWith("\"") && literal.endsWith("\"") && literal.length() >= 2) {
                        literal = literal.substring(1, literal.length() - 1);
                        constantTable.add(new Constant(Constant.Type.STRING, literal));
                    } else if (literal.contains(".")) {
                        float f = Float.parseFloat(literal);
                        constantTable.add(new Constant(Constant.Type.FLOAT, f));
                    } else {
                        int val = Integer.parseInt(literal);
                        constantTable.add(new Constant(Constant.Type.INTEGER, val));
                    }
                    j++;
                }
                i++;
            }
        }

        // Parse function definitions
        while (i < lines.length) {
            String trimmed = lines[i].trim();
            if (trimmed.isEmpty() || trimmed.startsWith(";")) {
                i++;
                continue;
            }

            if (trimmed.toLowerCase().startsWith("fun ")) {
                String[] parts = trimmed.split("\\s+");
                if (parts.length < 4) {
                    throw new RuntimeException("Incorrect function declaration: " + trimmed);
                }

                String funName = parts[1];
                int argc = Integer.parseInt(parts[2]);
                int varCount = Integer.parseInt(parts[3]);
                if (findConstantIndex(constantTable, funName) < 0) {
                    constantTable.add(new Constant(Constant.Type.STRING, funName));
                }

                CompiledFunction func = new CompiledFunction(funName, argc, varCount);
                i++;

                while (i < lines.length && (lines[i].startsWith(" ") || lines[i].startsWith("\t"))) {
                    String instrLine = lines[i].replaceAll(";.*", "").trim();
                    if (instrLine.isEmpty()) {
                        i++;
                        continue;
                    }

                    Instruction instr = parseInstruction(instrLine, constantTable);
                    func.instructions.add(instr);
                    i++;
                }
                functions.add(func);
            } else {
                i++;
            }
        }

        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        writeHeader(temp, constantTable.size(), functions.size());
        byte[] headerBytes = temp.toByteArray();

        ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
        finalOutput.write(headerBytes);
        writeConstantTable(finalOutput, constantTable);
        writeFunctionTable(finalOutput, functions);

        output.reset();
        output.write(finalOutput.toByteArray());
    }

    private void writeHeader(ByteArrayOutputStream output, int constantCount, int functionCount) throws IOException {
        output.write(new byte[]{(byte) 0x83, 0x79, (byte) 0x83, 0x65});
        output.write(new byte[]{0x00, 0x01, 0x00, 0x00, 0x00, 0x00});
        output.write(BytesParser.toBytes(constantCount, 4));
        output.write(BytesParser.toBytes(functionCount, 4));
        output.write(new byte[20]);
    }

    private void writeConstantTable(ByteArrayOutputStream output, List<Constant> constantTable) throws IOException {
        for (Constant constant : constantTable) {
            switch (constant.type) {
                case INTEGER:
                    output.write(1); // INTEGER
                    int intVal = (Integer) constant.value;
                    output.write(BytesParser.toBytes(intVal, 4));
                    break;
                case FLOAT:
                    output.write(2); // FLOAT
                    int floatBits = Float.floatToIntBits((Float) constant.value);
                    output.write(BytesParser.toBytes(floatBits, 4));
                    break;
                case STRING:
                    output.write(3); // STRING
                    byte[] strBytes = ((String) constant.value).getBytes("UTF-8");
                    output.write(BytesParser.toBytes(strBytes.length, 4));
                    output.write(strBytes);
                    break;
                default:
            }
        }
    }

    private void writeFunctionTable(ByteArrayOutputStream output, List<CompiledFunction> functions) throws IOException {
        for (CompiledFunction func : functions) {
            byte[] nameBytes = func.name.getBytes("UTF-8");
            output.write(BytesParser.toBytes(nameBytes.length, 4));
            output.write(nameBytes);
            output.write(BytesParser.toBytes(func.argc, 4));
            output.write(BytesParser.toBytes(func.varCount, 4));
            output.write(BytesParser.toBytes(func.instructions.size(), 4));
            for (Instruction instr : func.instructions) {
                output.write(instr.opcode);
                output.write(instr.type);
                if (instr.argument != null) {
                    output.write(BytesParser.toBytes(instr.argument, 4));
                }
            }
        }
    }

    private Instruction parseInstruction(String line, List<Constant> constantTable) {
        String[] tokens = line.split("\\s+");
        if (tokens.length == 0) {
            throw new RuntimeException("Empty instruction");
        }

        String mnemonic = tokens[0];
        Instruction instr = new Instruction();
        if (mnemonic.equalsIgnoreCase("ldc")) {
            instr.opcode = (byte) Instructions.LD.getOpcode();
            instr.type = 1; // ldc – type 1
            if (tokens.length < 2) {
                throw new RuntimeException("Missing argument for ldc");
            }
            instr.argument = Integer.parseInt(tokens[1]);
        } else if (mnemonic.equalsIgnoreCase("ld")) {
            instr.opcode = (byte) Instructions.LD.getOpcode();
            instr.type = 0; // regular ld
            if (tokens.length < 2) {
                throw new RuntimeException("Missing argument for ld");
            }
            instr.argument = Integer.parseInt(tokens[1]);
        } else if (mnemonic.equalsIgnoreCase("get")) {
            instr.opcode = (byte) Instructions.GET.getOpcode();
            instr.type = 0;
            if (tokens.length < 2) {
                throw new RuntimeException("Missing argument for get");
            }
            instr.argument = Integer.parseInt(tokens[1]);
        } else if (mnemonic.equalsIgnoreCase("put")) {
            instr.opcode = (byte) Instructions.PUT.getOpcode();
            instr.type = 0;
            if (tokens.length < 2) {
                throw new RuntimeException("Missing argument for put");
            }
            instr.argument = Integer.parseInt(tokens[1]);
        } else if (mnemonic.equalsIgnoreCase("invoke")) {
            instr.opcode = (byte) Instructions.INVOKE.getOpcode();
            instr.type = 0;
            if (tokens.length < 2) {
                throw new RuntimeException("Missing argument for invoke");
            }
            String argStr = tokens[1];
            if (!argStr.startsWith("#")) {
                int idx = findConstantIndex(constantTable, argStr);
                if (idx < 0) {
                    idx = constantTable.size();
                    constantTable.add(new Constant(Constant.Type.STRING, argStr));
                }
                instr.argument = idx;
            } else {
                instr.argument = Integer.parseInt(argStr.substring(1));
            }
        } else {
            try {
                Instructions ins = Instructions.valueOf(mnemonic.toUpperCase());
                instr.opcode = (byte) ins.getOpcode();
                instr.type = 0;
                instr.argument = (tokens.length > 1) ? Integer.parseInt(tokens[1]) : null;
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Unknown instruction: " + mnemonic);
            }
        }
        return instr;
    }


    private int findConstantIndex(List<Constant> table, String value) {
        for (int i = 0; i < table.size(); i++) {
            Constant c = table.get(i);
            if (c.type == Constant.Type.STRING && c.value.equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The application entry point for the Compiler. Expects two arguments:
     * the source file path and the output file path.
     *
     * @param args an array of command-line arguments: [0]=sourcePath, [1]=outputPath
     */
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Must be 2 arguments");
            }
            new Compiler().compile(args[0], args[1]);
            System.out.println("Compilation successful");
        } catch (Exception e) {
            System.err.println("Compilation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class Constant {
        enum Type {INTEGER, FLOAT, STRING}

        Type type;
        Object value;

        Constant(Type type, Object value) {
            this.type = type;
            this.value = value;
        }
    }

    private static final class Instruction {
        byte opcode;
        byte type;
        Integer argument;
    }

    private static class CompiledFunction {
        String name;
        int argc;
        int varCount;
        List<Instruction> instructions = new ArrayList<>();

        CompiledFunction(String name, int argc, int varCount) {
            this.name = name;
            this.argc = argc;
            this.varCount = varCount;
        }
    }
}
