#!/bin/bash

echo "Starting gui.."
cd client/src
chmod u+x build.sh clean.sh run_product_search_gui.sh
./build.sh
./run_product_search_gui.sh