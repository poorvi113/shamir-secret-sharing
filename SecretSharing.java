// import java.io.*;
// import java.math.BigInteger;
// import java.nio.file.*;
// import java.util.*;
// import org.json.JSONObject;

// public class SecretSharing {
    
//     // Method to read JSON file
//     public static Map<Integer, BigInteger> readJson(String filePath) {
//         Map<Integer, BigInteger> points = new HashMap<>();

//         try {
//             String content = new String(Files.readAllBytes(Paths.get(filePath)));
//             JSONObject json = new JSONObject(content);

//             JSONObject keys = json.getJSONObject("keys");
//             int k = keys.getInt("k");

//             for (String key : json.keySet()) {
//                 if (!key.equals("keys")) {
//                     int x = Integer.parseInt(key);
//                     JSONObject root = json.getJSONObject(key);
//                     int base = root.getInt("base");
//                     String valueStr = root.getString("value");
//                     BigInteger y = new BigInteger(valueStr, base);  // Convert value from given base
//                     points.put(x, y);
//                 }
//             }
            
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
        
//         return points;
//     }

//     // Lagrange Interpolation to find the secret (constant term)
//     public static BigInteger lagrangeInterpolation(Map<Integer, BigInteger> points) {
//         BigInteger secret = BigInteger.ZERO;

//         for (Map.Entry<Integer, BigInteger> entry1 : points.entrySet()) {
//             int xi = entry1.getKey();
//             BigInteger yi = entry1.getValue();
//             BigInteger numerator = BigInteger.ONE;
//             BigInteger denominator = BigInteger.ONE;

//             for (Map.Entry<Integer, BigInteger> entry2 : points.entrySet()) {
//                 int xj = entry2.getKey();
//                 if (xi != xj) {
//                     numerator = numerator.multiply(BigInteger.valueOf(-xj));
//                     denominator = denominator.multiply(BigInteger.valueOf(xi - xj));
//                 }
//             }

//             BigInteger term = yi.multiply(numerator).divide(denominator);
//             secret = secret.add(term);
//         }

//         return secret;
//     }

//     public static void main(String[] args) {
//         String filePath = "input.json";
//         Map<Integer, BigInteger> points = readJson(filePath);
        
//         // Take only the required k points
//         int k = 7;
//         Map<Integer, BigInteger> selectedPoints = new LinkedHashMap<>();
//         int count = 0;
//         for (Map.Entry<Integer, BigInteger> entry : points.entrySet()) {
//             if (count < k) {
//                 selectedPoints.put(entry.getKey(), entry.getValue());
//                 count++;
//             } else {
//                 break;
//             }
//         }
        
//         BigInteger secret = lagrangeInterpolation(selectedPoints);
//         System.out.println("Secret: " + secret);
//     }
// }
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SecretSharing {
    public static void main(String[] args) throws Exception {
        // Read input JSON dynamically
        FileReader reader = new FileReader("input.json");
        JSONObject json = new JSONObject(new JSONTokener(reader));

        // Extract number of roots (n) and required minimum (k)
        JSONObject keys = json.getJSONObject("keys");
        int n = keys.getInt("n");
        int k = keys.getInt("k");

        // Extract (x, y) values dynamically
        List<BigInteger[]> points = new ArrayList<>();
        for (String key : json.keySet()) {
            if (!key.equals("keys")) { // Skip metadata section
                JSONObject root = json.getJSONObject(key);
                
                // Extract x value dynamically
                BigInteger x = new BigInteger(key);
                
                // Extract y value dynamically (Fix: Correct base handling)
                int base = root.getInt("base"); // Now handles both int & string in JSON
                BigInteger y = new BigInteger(root.getString("value"), base);

                // Store the extracted (x, y) pair
                points.add(new BigInteger[]{x, y});
            }
        }

        // Print extracted (x, y) pairs for debugging
        System.out.println("Extracted (x, y) points:");
        for (BigInteger[] point : points) {
            System.out.println("(" + point[0] + ", " + point[1] + ")");
        }

        // Compute secret dynamically using Lagrange Interpolation
        BigInteger secret = lagrangeInterpolation(points, k);
        
        // Print the computed secret
        System.out.println("Secret: " + secret);
    }

    // ✅ Fixed Lagrange Interpolation Function
    public static BigInteger lagrangeInterpolation(List<BigInteger[]> points, int k) {
        BigInteger secret = BigInteger.ZERO;
        for (int i = 0; i < k; i++) {
            BigInteger term = points.get(i)[1]; // Start with y-value
            
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    // Compute Lagrange multiplier correctly
                    BigInteger numerator = BigInteger.ZERO.subtract(points.get(j)[0]); // (0 - xj)
                    BigInteger denominator = points.get(i)[0].subtract(points.get(j)[0]); // (xi - xj)
                    
                    term = term.multiply(numerator).divide(denominator);
                }
            }
            // Sum up all terms to get the constant term (secret)
            secret = secret.add(term);
        }
        return secret;
    }
}
