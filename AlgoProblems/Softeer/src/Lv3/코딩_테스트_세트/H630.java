package Lv3.코딩_테스트_세트;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class H630 {
    static int N, T;
    static long[][] C, D;
    static final long MAX = 1000000000000L;

    public static void main(String args[]) throws IOException {
        System.setIn(new FileInputStream("Softeer/input/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        for (int t = 0; t < T; t++) {
            System.out.println(simul(t));
        }
    }

    static long simul(int t) {
        long s = 0;
        long e = MAX;
        long mid = 0;
        while (s < e) {
            mid = (s + e + 1) / 2;
            if (chk(mid, t)) e = mid - 1;
            else s = mid;
        }

        return s;
    }

    static boolean chk(long target, int t) {
        long chkVal = D[t][0];
        // 0
        if (D[t][0] + C[t][0] < target) return true;
        chkVal -= target - C[t][0] <= 0 ? 0 : target - C[t][0];

        // 1 ~ N - 2
        for (int i = 1; i < N - 1; i++) {
            if (chkVal + D[t][i] + C[t][i] >= target) {
                if (chkVal + C[t][i] >= target) chkVal = D[t][i];
                else chkVal = D[t][i] + chkVal + C[t][i] - target;
            } else return true;
        }

        // N - 1
        if (chkVal + C[t][N - 1] < target) return true;

        return false;

    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        T = Integer.parseInt(tmp[1]);
        C = new long[T][N];
        D = new long[T][N];
        for (int t = 0; t < T; t++) {
            tmp = br.readLine().split(" ");
            for (int n = 0; n < N - 1; n++) {
                C[t][n] = Long.parseLong(tmp[n * 2]);
                D[t][n] = Long.parseLong(tmp[n * 2 + 1]);
            }
            C[t][N - 1] = Long.parseLong(tmp[2 * (N - 1)]);
        }
    }
}
