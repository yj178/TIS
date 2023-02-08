package silver2.랜선_자르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class B1654 {
    static int N, K;
    static long[] lengths;

    static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(parametricSearch(0, Long.MAX_VALUE));
    }

    static long parametricSearch(long s, long e) {
        if (s == e) return e;

        long m = (s + e + 1) >>> 1;
        if (chk(m)) return parametricSearch(m, e);
        else return parametricSearch(s, m - 1);
    }

    static boolean chk(long m) {
        long cnt = 0;
        for (long len : lengths) {
            cnt += len / m;
            if (cnt >= N) return true;
        }
        return false;
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        K = Integer.parseInt(tmp[0]);
        N = Integer.parseInt(tmp[1]);
        lengths = new long[K];

        for (int k = 0; k < K; k++) {
            lengths[k] = Long.parseLong(br.readLine());
        }

    }
}
