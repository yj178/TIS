package platinum5.최솟값_찾기;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.TreeMap;

class Main {
    static int N, L;
    static int[] arrays;
    static TreeMap<Integer, Integer> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        simul(bw);
        bw.close();
    }

    static void simul(BufferedWriter bw) throws IOException {
        int startIdx = 0;
        for (int n = 0; n < N; n++) {
            int key = arrays[startIdx];
            map.put(key, map.getOrDefault(key, 0) + 1);
            if (n - L + 1 >= 0 && startIdx < n - L + 1) {
                int cnt = map.get(key) - 1;
                map.remove(key);
                if (cnt != 0) map.put(key, cnt);
                startIdx++;
            }
            bw.write(map.firstKey() + " ");
        }
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        L = Integer.parseInt(tmp[1]);

        arrays = new int[N];
        tmp = br.readLine().split(" ");
        for (int n = 0; n < N; n++) {
            arrays[n] = Integer.parseInt(tmp[n]);
        }

        map = new TreeMap<>();
    }
}