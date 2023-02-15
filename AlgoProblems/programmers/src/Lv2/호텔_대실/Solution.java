package Lv2.호텔_대실;

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        System.out.println(problem.solution(new String[][] {{"15:00", "17:00"}, {"16:40", "18:20"}, {"14:20", "15:20"}, {"14:10", "19:20"}, {"18:20", "21:20"}}));
    }
    public int solution(String[][] book_time) {
        PriorityQueue<Info> pq = new PriorityQueue<>((o1, o2) -> o1.time != o2.time ? Integer.compare(o1.time, o2.time) : o1.inout && o2.inout ? -1 : o1.inout ? 1 : -1);
        for (String[] time : book_time) {
            String[] inTime = time[0].split(":");
            pq.add(new Info(true, Integer.parseInt(inTime[0]) * 60 + Integer.parseInt(inTime[1])));
            String[] outTime = time[1].split(":");
            pq.add(new Info(false, Integer.parseInt(outTime[0]) * 60 + Integer.parseInt(outTime[1]) + 10));
        }

        int cnt = 0;
        int answer = 0;
        while (!pq.isEmpty()) {
            Info tmp = pq.poll();
            cnt += tmp.inout ? 1 : -1;
            answer = Math.max(cnt, answer);
            // System.out.println((tmp.inout ? "입실" : "퇴실") +" " + tmp.time/60 +":"+tmp.time%60);
        }

        return answer;
    }

    static class Info {
        boolean inout; // in true
        int time;

        public Info(boolean inout, int time) {
            this.inout = inout;
            this.time = time;
        }
    }
}