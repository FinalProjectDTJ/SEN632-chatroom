#! /usr/bin/env bash

# Color
red='\033[0;31m'
green='\033[0;32m'
yellow='\033[0;33m'
plain='\033[0m'

echo -e "${plain}Compile ./server/chatbot/*.java \c"
javac -cp . ./server/chatbot/*.java
echo -e "${green} Done."

echo -e "${plain}Compile ./server/*.java \c"
javac -cp . ./server/*.java
echo -e "${green} Done."

echo -e "${plain}Compile ./client/*.java \c"
javac -cp . ./client/*.java
echo -e "${green} Done."

echo -e "${yellow}d(ŐдŐ๑)${plain} All done."
