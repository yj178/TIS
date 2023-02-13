package Lv2.디펜스_게임;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        int n = 7;
        int k = 3;
        int[] enemy = {4, 2, 4, 5, 3, 3, 1};
        System.out.println(problem.solution(n, k, enemy));
    }


    static TreeMap<Integer, Integer> map;
    static long[] sum;

    public int solution(int n, int k, int[] enemy) {
        int len = enemy.length;
        map = new TreeMap<>(Comparator.reverseOrder());
        sum = new long[len + 1];
        int i = 0;
        for (i = 0; i < len; i++) {
            int N = n;
            int K = k;
            sum[i + 1] = sum[i] + enemy[i];
            map.put(enemy[i], map.getOrDefault(enemy[i], 0) + 1);

            if (i < k) continue;

            int cnt = 0;
            int bigEnemySum = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int tmp = cnt + entry.getValue() <= k ? entry.getValue() : k - cnt;
                cnt += tmp;
                bigEnemySum += entry.getValue() * entry.getKey();
                if (cnt >= k) break;
            }

            if (sum[i + 1] - bigEnemySum > n) break;

        }
        return i;
    }
}
