#!/bin/bash

if [ "$#" -ne 2 ]; then
echo "Usage <Line to remove> <File in root>"
elif [ ! -f /root/$2 ]; then
    echo "File not found!"
else
./root/replace.sh $1 '' $2
fi
