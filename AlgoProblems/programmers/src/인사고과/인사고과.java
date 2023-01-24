package 인사고과;

import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;

public class 인사고과 {
    public static void main(String[] args) {
        인사고과 problem = new 인사고과();
        int[][] scores = new int[][]{{2, 2}, {1, 4}, {3, 2}, {3, 2}, {2, 1}};
        System.out.println(problem.solution(scores));
    }

    static HashMap<Score, Integer> map;
    static PriorityQueue<Score> pq;
    static Score wanho;

    public int solution(int[][] scores) {
        wanho = new Score(scores[0][0], scores[0][1]);
        pq = new PriorityQueue<>();
        map = new HashMap<>();
        for (int[] score : scores) {
            map.put(new Score(score[0], score[1]), map.getOrDefault(new Score(score[0], score[1]), 0) + 1);
        }

        for (Score s : map.keySet()) {
            if (wanho.chk(s)) return -1;
            pq.add(s);
        }

        int answer = 0;

        while (!pq.isEmpty() && pq.peek().compareTo(wanho) < 0) {
            answer += map.get(pq.poll());
        }

        return answer + 1;
    }

    static class Score implements Comparable<Score> {
        int a, b;

        public Score(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public boolean chk(Score o) {
            return this.a < o.a && this.b < o.b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Score score = (Score) o;
            return a == score.a && b == score.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }

        @Override
        public int compareTo(Score o) {
            return -Integer.compare(this.a + this.b, o.a + o.b);
        }

        @Override
        public String toString() {
            return "Score{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }
}
