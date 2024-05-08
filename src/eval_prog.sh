#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <input>"
    exit 1
fi

input="$1"

output_file="${input%.*}.txt"

args=(
    "5 2:0 2:135"
    "5 1:180 4:180"
    "5 1:315 4:45"
    "5 3:90 0:0"
    "8 1:135 5:315"
    "8 4:0 7:90"
    "8 6:270 0:45"
    "10 9:225 2:45"
    "10 2:45 9:225"
    "10 7:270 7:90"
)

# Run the Java program with the specified command and arguments, and redirect the output to a text file
for arg_set in "${args[@]}"; do
    java P3main.java "$input" $arg_set >> "$output_file"
    echo "Search with input '$input' and arguments '$arg_set' executed, output appended to '$output_file'"
done

echo "Program executed and output saved to '$output_file'"
