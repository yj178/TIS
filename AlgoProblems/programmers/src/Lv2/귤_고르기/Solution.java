package Lv2.귤_고르기;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    static void main(String[] args) {
        Solution problem = new Solution();
        int k = 6;
        int[] tangerine = {1, 3, 2, 5, 4, 5, 2, 3};
        System.out.println(problem.solution(k, tangerine));

        k = 2;
        tangerine = new int[]{1, 1, 1, 1, 2, 2, 2, 3};
        System.out.println(problem.solution(k, tangerine));

    }

    public int solution(int k, int[] tangerine) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int t : tangerine) {
            if (!map.containsKey(t)) map.put(t, 0);
            map.put(t, map.get(t) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            pq.add(e.getValue());
        }

        int answer = 0;

        while (!pq.isEmpty() && k > 0) {
            int tmp = pq.poll();
            k -= tmp;
            answer++;
        }

        return answer;
    }
}
