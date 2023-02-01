package 장애물_인식_프로그램;

import java.util.*;
import java.io.*;


public class 장애물_인식_프로그램 {
    static int[] dr = {0, 0, 1, -1};
    static int[] dc = {1, -1, 0, 0};
    static int N;
    static int[][] map;
    static boolean[][] chk;
    static PriorityQueue<Integer> pq;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        chk = new boolean[N][N];
        pq = new PriorityQueue<>();
        for (int r = 0; r < N; r++) {
            String[] tmp = br.readLine().split("");
            for (int c = 0; c < N; c++) {
                map[r][c] = Integer.parseInt(tmp[c]);
            }
        }
        int total = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (map[r][c] == 0 || chk[r][c]) continue;
                int rcCnt = bfs(r, c);
                pq.add(rcCnt);
                total++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(total + "\n");
        while (!pq.isEmpty()) {
            sb.append(pq.poll() + "\n");
        }
        System.out.println(sb);


    }

    static int bfs(int r, int c) {
        int cnt = 0;
        Queue<Pos> q = new LinkedList<>();
        q.add(new Pos(r, c));
        chk[r][c] = true;

        while (!q.isEmpty()) {
            Pos tmp = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = tmp.r + dr[d];
                int nc = tmp.c + dc[d];
                if (chkMap(nr, nc) || chk[nr][nc] || map[nr][nc] == 0) continue;
                chk[nr][nc] = true;
                q.add(new Pos(nr, nc));
                cnt++;

            }
        }

        return cnt + 1;

    }

    static boolean chkMap(int r, int c) {
        if (r < 0 || r >= N || c < 0 || c >= N) return true;
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