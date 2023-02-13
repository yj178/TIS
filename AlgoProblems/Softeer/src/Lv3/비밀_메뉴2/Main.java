package Lv3.비밀_메뉴2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N, M, K;
    static int[] keys, inputs;
    static int[][] map;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(calcAns());
    }

    static int calcAns() {
        int ans = 0;
        map = new int[N][M];
        for (int n = 0; n < N; n++) {
            for (int m = 0; m < M; m++) {
                if (keys[n] != inputs[m]) continue;
                if (n == 0 || m == 0) map[n][m] = 1;
                else map[n][m] = map[n - 1][m - 1] + 1;
                ans = Math.max(ans, map[n][m]);
            }
        }
        return ans;
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        K = Integer.parseInt(tmp[2]);

        keys = new int[N];
        inputs = new int[M];

        tmp = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            keys[i] = Integer.parseInt(tmp[i]);
        }

        tmp = br.readLine().split(" ");
        for (int i = 0; i < M; i++) {
            inputs[i] = Integer.parseInt(tmp[i]);
        }
    }
}
