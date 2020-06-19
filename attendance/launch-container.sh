#!/bin/bash

YELLOW="\033[33m"
RESET="\033[0m"
DOCKER_EMOJI="\xf0\x9f\x90\xb3"

echo -e "${DOCKER_EMOJI} ${YELLOW}Build image${RESET}"
docker image build -t pg-payroll:1 .

echo -e "${DOCKER_EMOJI} ${YELLOW}Create volume${RESET}"
docker volume create payroll-data

CONTAINER_ID=$(docker container ls --filter name=pg-payroll -q)
if [[ -n "$CONTAINER_ID" ]]; then
  echo -e "${DOCKER_EMOJI} ${YELLOW}Stop and remove running container:${RESET} ${CONTAINER_ID}"
  docker container rm -f pg-payroll
fi

echo -e "${DOCKER_EMOJI} ${YELLOW}Create container${RESET}"
docker container run --name pg-payroll -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres -v payroll-data:/var/lib/postgresql/data pg-payroll:1
