#!/bin/bash
javac -d out/test --module-source-path src -m utils,cvm.core,cvm.bytecodeloader,cvm.exceptions,App,disassembler,compiler

module_path="out/test"
res_path="tests/resources"

java --module-path $module_path -m compiler/cvm.compiler.Compiler "$res_path/inputcomplier.txt" "$res_path/outputcomplier.sosa"
java --module-path $module_path -m disassembler/cvm.disassembler.Disassembler "$res_path/outputcomplier.sosa" "$res_path/outputdisassembler.txt"
output=$(java --module-path $module_path -m App/app.Main "$res_path/outputcomplier.sosa")

expected_value="12"

if [ "$output" == "$expected_value" ];
then
  echo "Test passed"
  exit 0
else
  echo "Test failed Expected: $expected_value Out: $output"
  exit 1
fi