package 미로_탈출_명령어;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class 미로_탈출_명령어 {
    public static void main(String[] args) {
        미로_탈출_명령어 problem = new 미로_탈출_명령어();
        System.out.println(problem.solution(3, 4, 2, 3, 3, 1, 5));
    }

    static PriorityQueue<String> pq;
    static int[] dr = {0, 0, -1, 1};
    static int[] dc = {-1, 1, 0, 0};
    static int N, M, R, C, K;

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        pq = new PriorityQueue<>();
        N = n;
        M = m;
        R = r - 1;
        C = c - 1;
        K = k;
        if ((k - (Math.abs(x - r) + Math.abs(y - c))) % 2 != 0) return "impossible";
        bfs(x - 1, y - 1);

        return pq.isEmpty() ? "impossible" : pq.poll();
    }

    static void bfs(int r, int c) {
        Queue<Pos> q = new LinkedList<>();
        q.add(new Pos(r, c, ""));
        if (chkDis(r, c, K)) return;
        int k = 0;
        while (!q.isEmpty() && k <= K) {
            int len = q.size();
            for (int i = 0; i < len; i++) {
                Pos tmp = q.poll();
                if (k == K && tmp.r == R && tmp.c == C) pq.add(tmp.record);
                for (int d = 0; d < 4; d++) {
                    int nr = tmp.r + dr[d];
                    int nc = tmp.c + dc[d];
                    if (chkMap(nr, nc) || chkDis(nr, nc, K - k)) continue;
                    // if(chkMap(nr, nc)) continue;
                    if (k < K) q.add(new Pos(nr, nc, tmp.record + dirChar(d)));
                }
            }
            k++;
        }
    }

    static char dirChar(int d) {
        switch (d) {
            case 0:
                return 'l';
            case 1:
                return 'r';
            case 2:
                return 'u';
            case 3:
                return 'd';
        }
        return '-';
    }

    static boolean chkMap(int r, int c) {
        if (r < 0 || r >= N || c < 0 || c >= M) return true;
        return false;
    }

    static boolean chkDis(int r, int c, int k) {

        return k < (Math.abs(R - r) + Math.abs(C - c)) ? true : false;
    }

    static class Pos {
        int r, c;
        String record;

        public Pos(int r, int c, String record) {
            this.r = r;
            this.c = c;
            this.record = record;
        }
    }
}
