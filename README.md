# Abramov-Georgii

## Name

VM for C

## Description

To be continued...

## DOCS

COCO bytecode has docs in gitlab pages.
Current version is 2.0.0. Last major update: added functions, constants, variables.

## USAGE

1. Run `mvn package` to build the project.
2. Use the following command to start the VM:
   ```bash
   java --module-path target/all-jars -m App/app.Main path_to_binarycode
      java --module-path target/all-jars -m App/app.Main .\compiler\src\test\resources\result1.sosa
3. Use the following command to get a binary code from C code:
   ```bash
   java --module-path target/all-jars -m compiler/cvm.compiler.Compiler input_file_path output_file_path
   java --module-path target/all-jars -m compiler/cvm.compiler.Compiler .\compiler\src\test\resources\inputcomplier1.txt .\compiler\src\test\resources\result1.sosa

4.
   ```bash
java --module-path target/all-jars -m disassembler/cvm.disassembler.Disassembler .\compiler\src\test\resources\result1.sosa          

