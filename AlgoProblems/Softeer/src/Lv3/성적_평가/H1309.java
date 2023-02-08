package Lv3.성적_평가;

import java.util.*;
import java.io.*;

// 대회 3개
// 참가자 0~1000 점수
// 한 대회에서 둘 이상의 참가자 동점인 경우 존재

// 등수는 점수가 높은 순
// 동점은 같은 등수수
public class H1309 {
    static int[][] score;
    static int N;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        score = new int[4][N];
        TreeMap<Integer, Integer>[] map = new TreeMap[4];
        for (int i = 0; i < 3; i++) {
            String[] tmp = br.readLine().split(" ");
            map[i] = new TreeMap<>(Collections.reverseOrder());
            for (int p = 0; p < N; p++) {
                score[i][p] = Integer.parseInt(tmp[p]);
                map[i].put(score[i][p], map[i].getOrDefault(score[i][p], 0) + 1);
                score[3][p] += score[i][p];
            }
        }
        map[3] = new TreeMap<>(Collections.reverseOrder());
        for (int p = 0; p < N; p++) {
            map[3].put(score[3][p], map[3].getOrDefault(score[3][p], 0) + 1);
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            HashMap<Integer, Integer> tmp = new HashMap<>();
            int idx = 0;
            for (Map.Entry<Integer, Integer> e : map[i].entrySet()) {
                tmp.put(e.getKey(), idx + 1);
                idx += e.getValue();
            }
            for (int p = 0; p < N; p++) {
                sb.append(tmp.get(score[i][p]) + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb);


    }
}