#!/bin/bash

echo "Running project..."

java \
	--module-path "$PATH_TO_FX" \
	--add-modules javafx.controls,javafx.fxml \
	-cp out \
	mediavault.gui.Controller
