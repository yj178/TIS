package D1.구구단_걷기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Main {
    static int TC;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        init(br);
        simul(br, sb);
        System.out.println(sb);
    }

    static void simul(BufferedReader br, StringBuilder sb) throws IOException {
        for (int t = 1; t <= TC; t++) {
            long target = Long.parseLong(br.readLine());
            sb.append("#" + t + " " + calcMin(target) + "\n");
        }
    }

    static long calcMin(long target) {
        long ans = Long.MAX_VALUE;
        for (long i = 1; i <= 1000000; i++) {
            if (i * i > target) break;
            if (target % i != 0) continue;
            ans = Math.min((target / i) + i, ans);
        }
        return ans - 2;
    }

    static void init(BufferedReader br) throws IOException {
        TC = Integer.parseInt(br.readLine());
    }
}
