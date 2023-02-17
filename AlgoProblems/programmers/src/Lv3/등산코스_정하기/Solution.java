package Lv3.등산코스_정하기;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        System.out.println(Arrays.toString(problem.solution(6, new int[][]{{1, 2, 3}, {2, 3, 5}, {2, 4, 2}, {2, 5, 4}, {3, 4, 4}, {4, 5, 3}, {4, 6, 1}, {5, 6, 1}}, new int[]{1, 3}, new int[]{5})));
    }

    static Node[] nodes;
    static PriorityQueue<Info> pq;
    static HashSet<Integer> peak, entrance;

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        peak = new HashSet<>();
        entrance = new HashSet<>();
        nodes = new Node[n + 1];
        pq = new PriorityQueue<>();
        pq.add(new Info(Integer.MAX_VALUE, Integer.MAX_VALUE));
        int[] visit = new int[n + 1];
        Arrays.fill(visit, Integer.MAX_VALUE);
        PriorityQueue<Edge> q = new PriorityQueue<>((o1, o2)-> o1.w - o2.w);
        for (int i = 0; i <= n; i++) {
            nodes[i] = new Node();
        }
        for (int gate : gates) {
            entrance.add(gate);
            q.add(new Edge(gate, 0));
        }
        for (int summit : summits) {
            peak.add(summit);
        }
        for (int[] path : paths) {
            int s = path[0];
            int e = path[1];
            int w = path[2];
            nodes[s].next.add(new Edge(e, w));
            nodes[e].next.add(new Edge(s, w));
        }


        while (!q.isEmpty()) {
            Edge e = q.poll();
            if (visit[e.n] <= e.w) continue;
            visit[e.n] = e.w;
            if (peak.contains(e.n)) {
                pq.add(new Info(visit[e.n], e.n));
                continue;
            }
            for (Edge next : nodes[e.n].next) {
                if (visit[next.n] > next.w) {
                    q.add(new Edge(next.n, Math.max(next.w, visit[e.n])));
                }
            }
        }
        int[] answer = new int[2];
        answer[0] = pq.peek().h;
        answer[1] = pq.peek().i;
        return answer;
    }


    static class Edge {
        int n, w;

        public Edge(int n, int w) {
            this.n = n;
            this.w = w;
        }
    }

    static class Node {
        ArrayList<Edge> next;

        public Node() {
            next = new ArrayList<>();
        }
    }

    static class Info implements Comparable<Info> {
        int i, h;

        public Info(int i, int h) {
            this.i = i;
            this.h = h;
        }

        @Override
        public int compareTo(Info o) {
            return this.i == o.i ? Integer.compare(this.h, o.h) : Integer.compare(this.i, o.i);
        }

    }
}
