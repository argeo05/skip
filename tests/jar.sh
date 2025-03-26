#!/bin/bash

# Очистка предыдущих сборок
rm -rf out/test out/jars
mkdir -p out/test out/jars

javac -d out/test --module-source-path src -m utils,cvm.core,cvm.bytecodeloader,App,disassembler,compiler

modules=("utils" "cvm.core" "cvm.bytecodeloader" "App" "disassembler" "compiler")
for mod in "${modules[@]}"; do
    jar --create --file=out/jars/$mod.jar -C out/test/$mod .
done

module_path="out/jars"
res_path="tests/resources"

java --module-path $module_path -m compiler/cvm.compiler.Compiler "$res_path/inputcomplier.txt" "$res_path/outputcomplier.sosa"

java --module-path $module_path -m disassembler/cvm.disassembler.Disassembler "$res_path/outputcomplier.sosa" "$res_path/outputdisassembler.txt"

output=$(java --module-path $module_path -m App/app.Main "$res_path/outputcomplier.sosa")

expected_value="12"

if [ "$output" == "$expected_value" ]; then
  echo "Test passed"
  exit 0
else
  echo "Test failed. Expected: $expected_value, got: $output"
  exit 1
fi
