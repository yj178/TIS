package GBC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GBC {
    static int N, M;
    static int[] limit;
    static int[][] nrec, mrec;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);

        limit = new int[101];
        nrec = new int[N + 1][2];
        mrec = new int[M + 1][2];

        for (int i = 1; i <= N; i++) {
            tmp = br.readLine().split(" ");
            nrec[i][0] = Integer.parseInt(tmp[0]) + nrec[i - 1][0];
            nrec[i][1] = Integer.parseInt(tmp[1]);
        }

        for (int i = 1; i <= M; i++) {
            tmp = br.readLine().split(" ");
            mrec[i][0] = Integer.parseInt(tmp[0]) + mrec[i - 1][0];
            mrec[i][1] = Integer.parseInt(tmp[1]);
        }
        int idx = 1;
        int idx2 = 1;
        int ans = 0;

        for (int i = 1; i <= 100; i++) {
            limit[i] = nrec[idx][1];
            if (i == nrec[idx][0]) idx++;
            ans = Math.max(ans, mrec[idx2][1] - limit[i] > 0 ? mrec[idx2][1] - limit[i] : 0);
            if (i == mrec[idx2][0]) idx2++;
        }
        System.out.println(ans);
    }
}
