FROM postgres:12.3

COPY 01-init-user-db.sh /docker-entrypoint-initdb.d
COPY attendance.sql /payroll/scripts/
