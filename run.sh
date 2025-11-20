#!/bin/bash

# Compilar
javac -d bin -cp ".:mysql-connector-j-8.0.33.jar" src/*.java

# Iniciar servidor
java -cp "bin:mysql-connector-j-8.0.33.jar" WebServer
