package silver3._2xn_타일링;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main2 {
    static final int rem = 10007;
    static int N, cnt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        simul(0);
        System.out.println(cnt);
    }

    static void simul(int len) {
        if (len > N) return;

        if (len == N) {
            cnt = cnt + 1 >= rem ? 0 : cnt + 1;
            return;
        }

        simul(len + 1);
        simul(len + 2);

    }

    static void init(BufferedReader br) throws IOException {
        N = Integer.parseInt(br.readLine());
    }

}
