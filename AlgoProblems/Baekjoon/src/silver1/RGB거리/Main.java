package silver1.RGB거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    static int N;
    static int[][] cost;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(simul());

    }

    static int simul() {
        int[][] dp = new int[3][N];
        dp[0][0] = cost[0][0];
        dp[1][0] = cost[0][1];
        dp[2][0] = cost[0][2];

        for (int i = 1; i < N; i++) {
            dp[0][i] = Math.min(dp[1][i - 1], dp[2][i - 1]) + cost[i][0];
            dp[1][i] = Math.min(dp[0][i - 1], dp[2][i - 1]) + cost[i][1];
            dp[2][i] = Math.min(dp[0][i - 1], dp[1][i - 1]) + cost[i][2];
        }

        return Math.min(dp[0][N - 1], Math.min(dp[1][N - 1], dp[2][N - 1]));

    }

    static void init(BufferedReader br) throws IOException {
        N = Integer.parseInt(br.readLine());
        cost = new int[N][3];
        for (int r = 0; r < N; r++) {
            String[] line = br.readLine().split(" ");
            for (int c = 0; c < 3; c++) {
                cost[r][c] = Integer.parseInt(line[c]);
            }
        }
    }
}
