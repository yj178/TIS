package silver3._2xn_타일링;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int rem = 10007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] dp = new int[N + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int idx = 2; idx <= N; idx++) {
            dp[idx] = (dp[idx - 2] + dp[idx - 1]) % rem;
        }
        System.out.println(dp[N]);
    }

}
