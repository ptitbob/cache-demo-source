#!/usr/bin/env bash

mvn clean install -f ./../app/pom.xml  -P prod

./up.sh

