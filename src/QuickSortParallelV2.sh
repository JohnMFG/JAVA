
#!/bin/bash

# Compile the Java source file
javac QuickSortParallelV2.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    # Run the Java program
    java QuickSortParallelV2
else
    echo "Compilation failed. Please fix the errors."
fi