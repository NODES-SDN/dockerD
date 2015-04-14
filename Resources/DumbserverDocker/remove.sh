#!/bin/bash

if [ "$#" -ne 2 ]; then
echo "Usage <Line to remove> <File in root>"
elif [ ! -f /root/$2 ]; then
    echo "File not found!"
else
sed s/$1// /root/$2 > /root/$2
fi
