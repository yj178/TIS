package Lv3.통근버스_출발_순서_검증하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] arr;
    static int[][] cnt;
    static int N;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        cnt = new int[N][N];
        String[] tmp = br.readLine().split(" ");

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tmp[i]);
        }

        for (int i = 0; i < N; i++) {
            if (arr[i] > arr[N - 1]) cnt[i][N - 1] = 1;
            for (int j = N - 2; j > i; j--) {
                if (arr[i] > arr[j]) cnt[i][j] = cnt[i][j + 1] + 1;
                else cnt[i][j] = cnt[i][j + 1];
            }
        }


        long ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N - 1; j++) {
                if (arr[i] < arr[j]) ans += cnt[i][j];
            }
        }
        System.out.println(ans);
    }
}
