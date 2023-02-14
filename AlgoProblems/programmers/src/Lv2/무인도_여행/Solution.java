package Lv2.무인도_여행;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        System.out.println(Arrays.toString(problem.solution(new String[]{"X591X", "X1X5X", "X231X", "1XXX1"})));
    }

    static boolean[][] chk;
    static int R, C;
    static int[] dr = {0, 0, 1, -1};
    static int[] dc = {1, -1, 0, 0};

    public int[] solution(String[] maps) {
        R = maps.length;
        C = maps[0].length();
        m = new int[R][C];
        chk = new boolean[R][C];

        for (int r = 0; r < R; r++) {
            char[] tmp = maps[r].toCharArray();
            for (int c = 0; c < C; c++) {
                if ('X' == tmp[c]) m[r][c] = 0;
                else m[r][c] = tmp[c] - '0';
            }
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (chk[r][c] || m[r][c] == 0) continue;
                pq.add(bfs(r, c));
            }
        }

        if (pq.isEmpty()) return new int[]{-1};


        int[] answer = new int[pq.size()];
        int idx = 0;
        while (!pq.isEmpty()) {
            answer[idx++] = pq.poll();
        }
        return answer;
    }

    static int bfs(int r, int c) {
        Queue<Pos> q = new LinkedList<>();
        q.add(new Pos(r, c));
        chk[r][c] = true;

        int cnt = m[r][c];
        while (!q.isEmpty()) {
            Pos tmp = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = tmp.r + dr[d];
                int nc = tmp.c + dc[d];
                if (mapOut(nr, nc) || chk[nr][nc] || m[nr][nc] == 0) continue;

                chk[nr][nc] = true;
                q.add(new Pos(nr, nc));
                cnt += m[nr][nc];
            }
        }
        return cnt;

    }

    static boolean mapOut(int r, int c) {
        if (r < 0 || r >= R || c < 0 || c >= C) return true;
        return false;
    }

    static class Pos {
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
