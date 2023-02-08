package gold5.평범한_배낭;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class B12865 {
    static int N, K;
    static int[][] product, dp;

    static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        K = Integer.parseInt(tmp[1]);
        product = new int[N + 1][2];
        for (int n = 1; n <= N; n++) {
            tmp = br.readLine().split(" ");
            product[n][0] = Integer.parseInt(tmp[0]);
            product[n][1] = Integer.parseInt(tmp[1]);
        }

        dp = new int[2][K + 1];
        for (int n = 1; n <= N; n++) {
            for (int k = 1; k <= K; k++) {
                int w = product[n][0];
                int p = product[n][1];
                dp[n % 2][k] = Math.max(dp[n % 2][k], k - w >= 0 ? Math.max(dp[(n + 1) % 2][k - w] + p, dp[(n + 1) % 2][k]) : dp[(n + 1) % 2][k]);
            }
        }

        System.out.println(Math.max(dp[0][K], dp[1][K]));

    }

}