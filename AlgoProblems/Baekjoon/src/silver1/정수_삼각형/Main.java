package silver1.정수_삼각형;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(init(br));
    }

    static int init(BufferedReader br) throws IOException {
        int N = Integer.parseInt(br.readLine());
        int[][] ans = new int[N + 1][];
        ans[1] = new int[3];
        ans[1][1] = Integer.parseInt(br.readLine());

        if(N == 1) return ans[1][1];

        int maxAns = 0;
        for (int r = 2; r <= N; r++) {
            ans[r] = new int[r + 2];
            String[] line = br.readLine().split(" ");
            for (int c = 1; c <= r; c++) {
                ans[r][c] = Math.max(ans[r - 1][c - 1], ans[r - 1][c]) + Integer.parseInt(line[c - 1]);
                if (r == N) maxAns = Math.max(maxAns, ans[r][c]);
            }
        }

        return maxAns;
    }
}
