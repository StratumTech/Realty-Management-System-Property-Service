#!/bin/bash

generate_random_string() {
    local length=$1
    tr -dc 'a-zA-Z0-9' </dev/urandom | head -c "$length"
}

DATABASE_HOST=localhost
DATABASE_PORT=5433
DATABASE_NAME=rmspropertydb
DATABASE_USER=$(generate_random_string 5)
DATABASE_PASSWORD=$(generate_random_string 20)
DATABASE_DATASOURCE_URL="jdbc:postgresql://${DATABASE_HOST}:\${DATABASE_PORT}/\${DATABASE_NAME}"

cat <<EOF > .env
DATABASE_HOST=${DATABASE_HOST}
DATABASE_PORT=${DATABASE_PORT}
DATABASE_NAME=${DATABASE_NAME}
DATABASE_USER=${DATABASE_USER}
DATABASE_PASSWORD=${DATABASE_PASSWORD}
DATABASE_DATASOURCE_URL=${DATABASE_DATASOURCE_URL}
KAFKA_BOOTSTRAP_SERVERS=
EOF

echo "The .env file has been created successfully"