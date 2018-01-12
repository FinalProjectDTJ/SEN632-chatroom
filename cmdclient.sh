#! /usr/bin/env bash

# Color
red='\033[0;31m'
green='\033[0;32m'
yellow='\033[0;33m'
plain='\033[0m'

echo -e "${green} Starting Command Line Client ... ${plain} "
java -cp . client.ChatroomClient
