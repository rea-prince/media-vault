#!/bin/bash

echo "Running project..."

java \
	--module-path lib \
	--add-modules javafx.controls,javafx.fxml \
	-cp out \
	mediavault.MainGUI
