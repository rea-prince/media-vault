#!/bin/bash

echo "Clearing previous build..."
rm -rf out

echo "Compiling MediaVault class files..."
mkdir -p out

javac \
	--module-path "$PATH_TO_FX" \
	--add-modules javafx.controls,javafx.fxml \
	--source-path src \
	-d out \
	src/mediavault/gui/Controller.java

cp -r resources/* out/

echo "Class files successfully compiled!"
