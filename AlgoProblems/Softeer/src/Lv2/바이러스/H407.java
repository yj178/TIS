package Lv2.바이러스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class H407 {
    static long K, P, N;
    static final long e = 1000000007;
    public static void main(String args[]) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tmp = br.readLine().split(" ");
        K = Long.parseLong(tmp[0]);
        P = Long.parseLong(tmp[1]);
        N = Long.parseLong(tmp[2]);
        long ans = K;
        for(long n = 0; n < N;n++){
            ans *= P;
            ans %= e;
        }
        System.out.println(ans);
    }
}
