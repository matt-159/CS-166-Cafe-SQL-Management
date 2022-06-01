#!/bin/bash

DIR="$(pwd)"
echo $DIR

mkdir $DIR/java/classes

# compile the java program
javac -d $DIR/java/classes $DIR/java/src/CafeServer.java

#run the java program
#Use your database name, port number and login
java -cp $DIR/java/classes:$DIR/java/lib/postgresql-42.3.6.jar CafeServer ${1} ${2} ${3} ${4} ${5}