#!/bin/bash

# Limpa e prepara a pasta de build
rm -rf build/*
mkdir -p build

# Gera o VotingProtos.java a partir do .proto
protoc --java_out=src/main/java proto/voting.proto

# Compila todas as classes mantendo a estrutura de pacotes
javac -d build \
  -cp lib/protobuf-java-3.21.12.jar \
  src/main/java/client/*.java \
  src/main/java/server/*.java \
  src/main/java/voting/*.java

# Verifica se compilação foi bem-sucedida
if [ $? -ne 0 ]; then
  echo "Erro na compilação. Verifique os arquivos .java e o arquivo .proto"
  exit 1
fi

# Executa os componentes em terminais separados
gnome-terminal -- bash -c "cd build && java -cp .:../lib/protobuf-java-3.21.12.jar server.TCPServer"
sleep 1
gnome-terminal -- bash -c "cd build && java -cp .:../lib/protobuf-java-3.21.12.jar server.UDPMulticastServer"
sleep 1
gnome-terminal -- bash -c "cd build && java -cp .:../lib/protobuf-java-3.21.12.jar client.Voter"