#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER payroll WITH PASSWORD 'payroll';
  CREATE DATABASE payroll;
  GRANT ALL PRIVILEGES ON DATABASE payroll TO payroll;
EOSQL

SCRIPT_HOME=/payroll/scripts

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "payroll" -a -f $SCRIPT_HOME/attendance.sql
