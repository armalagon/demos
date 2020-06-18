#!/bin/bash

docker image build -t pg-payroll:1 .
docker volume create payroll-data
docker container rm pg-payroll
docker container run --name pg-payroll -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres -v payroll-data:/var/lib/postgresql/data pg-payroll:1
