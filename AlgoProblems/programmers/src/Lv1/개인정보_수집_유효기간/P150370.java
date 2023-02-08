package Lv1.개인정보_수집_유효기간;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

class P150370 {
    static void main(String[] args) {
        P150370 problem = new P150370();
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};
        System.out.println(Arrays.toString(problem.solution(today, terms, privacies)));
    }

    static final int YEARTODAY = 336;
    static final int MONTODAY = 28;
    static int todayInt;
    static HashMap<String, Integer> map;
    static PriorityQueue<Data> pq;

    static int[] solution(String today, String[] terms, String[] privacies) {
        todayInt = calcToday(today);
        map = new HashMap<>();
        pq = new PriorityQueue<>();
        for (String term : terms) {
            String[] tmp = term.split(" ");
            map.put(tmp[0], Integer.parseInt(tmp[1]));
        }

        int idx = 0;
        for (String privacy : privacies) {
            String[] tmp = privacy.split(" ");
            pq.add(new Data(calcToday(tmp[0]), map.get(tmp[1]) * MONTODAY, ++idx));
        }

        PriorityQueue<Integer> ans = new PriorityQueue<>();
        while (!pq.isEmpty() && pq.peek().endDay <= todayInt) {
            ans.add(pq.poll().idx);
        }

        int[] answer = ans.stream().sorted().mapToInt(Integer::intValue).toArray();

        return answer;
    }

    static int calcToday(String today) {
        String[] tmp = today.split("\\.");
        return YEARTODAY * Integer.parseInt(tmp[0]) + MONTODAY * Integer.parseInt(tmp[1]) + Integer.parseInt(tmp[2]);
    }

    static class Data implements Comparable<Data> {
        int endDay, idx;

        public Data(int startDay, int type, int idx) {
            this.endDay = startDay + type;
            this.idx = idx;
        }

        @Override
        public int compareTo(Data o) {
            return Integer.compare(this.endDay, o.endDay);
        }
    }
}
