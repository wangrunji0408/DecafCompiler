#!/usr/bin/env bash
echo Testing S1+
cd TestCases/S1+/
python runAll.py
cd ../..

echo Testing S1-
cd TestCases/S1-/
python runAll.py
cd ../..
