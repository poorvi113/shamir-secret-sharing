Shamir's Secret Sharing - Polynomial Interpolation
📌 Project Overview
This project implements a simplified version of Shamir's Secret Sharing algorithm to find the constant term (c) of an unknown polynomial given a set of roots in JSON format.

The program:

Reads the test case (JSON input) containing encoded (x, y) pairs.
Decodes y values from different bases.
Uses Lagrange Interpolation to reconstruct the polynomial.
Extracts and prints the secret (c), which is the constant term of the polynomial.
⚙️ How It Works
Read Input Data: The program reads the JSON file containing n roots of the polynomial.
Decode Encoded Values:
Each (x, y) pair is stored in different number bases (e.g., binary, octal, decimal, etc.).
The program converts each y value into decimal format.
Polynomial Interpolation:
Uses Lagrange Interpolation to reconstruct the polynomial equation.
Finds the constant term (c) of the polynomial.
Prints the Secret (c)
📂 Project Structure
pgsql
Copy
Edit
shamir_secret_sharing/
│── input.json               # Input test case in JSON format  
│── SecretSharing.java        # Java implementation of the algorithm  
│── json-20210307.jar         # JSON library for Java  
│── README.md                 # Documentation (this file)  
🔧 How to Run the Code
Step 1: Setup Environment
Download JSON Library:
If you haven't already, download the org.json library:
👉 https://github.com/stleary/JSON-java

Place json-20210307.jar in the project directory.

Step 2: Compile the Code
Run the following command in the terminal:

sh
Copy
Edit
javac -cp .;json-20210307.jar SecretSharing.java
Step 3: Run the Program
Execute the program using:

sh
Copy
Edit
java -cp .;json-20210307.jar SecretSharing
📥 Example Input (input.json)
json
Copy
Edit
{
  "keys": {
    "n": 10,
    "k": 7
  },
  "1": { "base": "7", "value": "420020006424065463" },
  "2": { "base": "7", "value": "10511630252064643035" },
  "3": { "base": "2", "value": "101010101001100101011100000001000111010010111101100100010" },
  "4": { "base": "8", "value": "31261003022226126015" },
  "5": { "base": "7", "value": "2564201006101516132035" },
  "6": { "base": "15", "value": "a3c97ed550c69484" },
  "7": { "base": "13", "value": "134b08c8739552a734" }
}
📤 Expected Output
makefile
Copy
Edit
Secret: 271644355478965
📚 Algorithm Used
Lagrange Interpolation
Lagrange interpolation reconstructs a polynomial from known points (x, y). The formula is:


 
Where:

x_i, y_i are the given points.
k is the minimum number of points required (k = m + 1, where m is the polynomial degree).
This method ensures that the polynomial passes through all given points and allows us to extract the constant term (c).
🛠 Improvements & Next Steps
✅ Improve performance using Gaussian Elimination.
✅ Implement modular arithmetic for finite field cryptography.
✅ Extend the project to a full Shamir's Secret Sharing implementation.
✅ Add automated test cases to validate results.

📜 License
This project is open-source and free to use.

