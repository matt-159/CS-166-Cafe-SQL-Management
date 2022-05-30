#!/bin/bash

#For use when compiling on Lab Machines
DIR="$(pwd)"
echo $DIR

mkdir $DIR/java/classes

#export JAVA_HOME=/usr/csshare/pkgs/jdk1.7.0_17
#export PATH=$JAVA_HOME/bin:$PATH

# compile the java program
javac -d $DIR/java/classes $DIR/java/src/Cafe.java

#run the java program
#Use your database name, port number and login
java -cp $DIR/java/classes:$DIR/java/lib/pg73jdbc3.jar Cafe ${1} ${2} ${3}

sleep 10

