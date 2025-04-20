package cvm.compiler;

import utils.BytesParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Compiler class that compiles source code into bytecode.
 */
public class Compiler {
    private static class Constant {
        enum Type {INTEGER, FLOAT, STRING}

        Type type;
        Object value;

        Constant(Type type, Object value) {
            this.type = type;
            this.value = value;
        }
    }

    private static class Instruction {
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

    /**
     * Compiles the source code from the specified path and writes the bytecode to the output path.
     *
     * @param sourcePath the path to the source code file
     * @param outputPath the path to the output bytecode file
     * @throws IOException if an I/O error occurs
     */
    public void compile(String sourcePath, String outputPath) throws IOException {
        String source = Files.readString(Path.of(sourcePath));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        writeHeader(output, 0, 0); // temporarily – will update later
        compileToBytecode(source, output);
        Files.write(Path.of(outputPath), output.toByteArray());
    }

    /**
     * Writes the header to the bytecode output stream.
     *
     * @param output        the output stream to write the header to
     * @param constantCount the number of constants in the constant table
     * @param functionCount the number of functions in the function table
     * @throws IOException if an I/O error occurs
     */
    private void writeHeader(ByteArrayOutputStream output, int constantCount, int functionCount) throws IOException {
        output.write(new byte[]{(byte) 0x83, 0x79, (byte) 0x83, 0x65});
        output.write(new byte[]{0x00, 0x01, 0x00, 0x00, 0x00, 0x00});
        output.write(BytesParser.toBytes(constantCount, 4));
        output.write(BytesParser.toBytes(functionCount, 4));
        output.write(new byte[20]);
    }

    /**
     * Writes the constant table to the bytecode output stream.
     *
     * @param output        the output stream to write the constant table to
     * @param constantTable the list of constants to write
     * @throws IOException if an I/O error occurs
     */
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
            }
        }
    }

    /**
     * Writes the function table to the bytecode output stream.
     *
     * @param output    the output stream to write the function table to
     * @param functions the list of functions to write
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * The main method that runs the compiler.
     *
     * @param args the command line arguments: sourcePath and outputPath
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
}