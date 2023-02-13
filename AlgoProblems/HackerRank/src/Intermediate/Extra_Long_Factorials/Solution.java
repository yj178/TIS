package Intermediate.Extra_Long_Factorials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

class Result {

    public static void extraLongFactorials(int n) {
        // Write your code here
        System.out.println(fac(n));
    }

    private static BigInteger fac(int n){
        if(n == 1) return BigInteger.valueOf(1);
        return BigInteger.valueOf(n).multiply(fac(n-1));
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        Result.extraLongFactorials(n);

        bufferedReader.close();
    }
}

