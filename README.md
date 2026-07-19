## CCPROG3-MCO1

Group 01
- ALIP, Rafael Prince Naif E.
- SY, Eryn Claire G.

Compile command:

```bash
javac -d out --source-path src src/mediavault/Main.java
java -cp out mediavault.Main
```

## GUI compile commands

**Windows**

```bash
set PATH_TO_FX="path\to\javafx-sdk-26.0.1\lib" # replace path with actual one

javac --module-path %PATH_TO_FX% --add-modules javafx.controls -d out --source-path src src/mediavault/Main.java
java --module-path %PATH_TO_FX% --add-modules javafx.controls -cp out mediavault.Main
```

**Bash**

```bash
export PATH_TO_FX=path/to/javafx-sdk-26.0.1/lib # replace path with actual one

javac --module-path $PATH_TO_FX --add-modules javafx.controls -d out --source-path src src/mediavault/Main.java
java --module-path $PATH_TO_FX --add-modules javafx.controls -cp out mediavault.Main
```

## Documentation

```bash
javadoc -d docs -sourcepath src -subpackages mediavault
```
