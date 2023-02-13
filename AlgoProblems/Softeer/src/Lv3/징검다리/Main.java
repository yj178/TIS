package Lv3.징검다리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int N, target;
    static int[] cnt, arr;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        cnt = new int[N];
        Arrays.fill(cnt, Integer.MAX_VALUE);
        arr = new int[N];
        String[] tmp = br.readLine().split(" ");
        int max = 0;
        for (int n = 0; n < N; n++) {
            arr[n] = Integer.parseInt(tmp[n]);
            target = arr[n];
            int idx = binarySearch(0, max + 1);
            cnt[idx] = Math.min(cnt[idx], arr[n]);
            max = Math.max(idx, max);
        }

        System.out.println(max + 1);


    }

    static int binarySearch(int s, int e) {
        if (s == e) return s;
        int m = (s + e) / 2;
        if (cnt[m] > target) return binarySearch(s, m);
        else return binarySearch(m + 1, e);
    }
}
