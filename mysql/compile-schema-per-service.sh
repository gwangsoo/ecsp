#! /bin/bash -e

for schema in board account;
do
  user=${schema}_user
  password=${schema}_password
  cat >> /docker-entrypoint-initdb.d/5.schema-per-service.sql <<END
  CREATE USER '${user}'@'%' IDENTIFIED BY '$password';
  create database $schema DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
  GRANT ALL PRIVILEGES ON $schema.* TO '${user}'@'%' WITH GRANT OPTION;
  GRANT ALL PRIVILEGES ON eventuate.* TO '${user}'@'%' WITH GRANT OPTION;
  USE $schema;
END
    cat /docker-entrypoint-initdb.d/template >> /docker-entrypoint-initdb.d/5.schema-per-service.sql
done