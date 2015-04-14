#!/bin/bash

if [ "$#" -ne 2 ]; then
echo "Usage <Line to add> <File in root>"
elif [ ! -f /root/$2 ]; then
    echo "File not found!"
else
echo $1 >> /root/$2
fi
