#!/usr/bin/env bash

docker-compose rm -f nginx app-1 app-2 && docker-compose up -d

