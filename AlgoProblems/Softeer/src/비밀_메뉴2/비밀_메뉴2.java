package 비밀_메뉴2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class 비밀_메뉴2 {
    static int N, M, K;
    static int[] keys;
    static int[] inputs;
    static HashMap<Integer, ArrayList<Integer>> map;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(calcAns());
    }

    static int calcAns() {
        if (map.isEmpty()) return 0;

        int ans = 0;
        for (int n = 0; n < N; n++) {
            int chkNum = keys[n];
            if (!map.containsKey(chkNum)) continue;

            for (int chkStart : map.get(chkNum)) {
                int cnt = 0;
                for (int idx = 0; idx < N - n; idx++) {
                    if(chkStart + idx >= M) break;
                    if (keys[n + idx] == inputs[chkStart + idx]) cnt++;
                    else break;
                }
                ans = Math.max(ans, cnt);
                if (ans == N) return N;
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

        map = new HashMap<>();
        tmp = br.readLine().split(" ");
        for (int i = 0; i < M; i++) {
            inputs[i] = Integer.parseInt(tmp[i]);
            if (!map.containsKey(inputs[i])) map.put(inputs[i], new ArrayList<>());
            map.get(inputs[i]).add(i);
        }
    }
}
