#!/bin/bash
javac -d out/utils utils/src/module-info.java utils/src/utils/*.java
javac --module-path out -d out/cvm.core cvm.core/src/module-info.java cvm.core/src/cvm/*.java cvm.core/src/cvm/instructions/*.java
javac --module-path out -d out/cvm.bytecodeloader cvm.bytecodeloader/src/module-info.java cvm.bytecodeloader/src/cvm/bytecodeloader/*.java cvm.bytecodeloader/src/cvm/bytecodeloader/instructions/*.java
javac --module-path out -d out/App App/src/module-info.java App/src/app/*.java

expected_value="12"
output=$(java --module-path out -m App/app.Main)

if [ "$output" == "$expected_value" ];
then
  echo "Test passed"
  exit 0
else
  echo "Test failed Expected: $expected_value Out: $output"
  exit 1
fi