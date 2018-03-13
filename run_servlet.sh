#!/bin/bash

echo "Starting server..."
cd server/src/systemet-api
chmod u+x build.sh build_jar.sh
cd ..
chmod u+x deploy_systemet_jar.sh run_servlet.sh
./deploy_systemet_jar.sh
./run_servlet.sh
