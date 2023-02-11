package Lv3.좌석_관리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class H625 {
    static boolean[][] chk;
    static HashMap<Integer, Integer> state;
    static TreeSet<Pos> kitchen;
    static int N, M, Q;
    static int[] dr = {0, 0, -1, 1};
    static int[] dc = {1, -1, 0, 0};

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);

        StringBuilder sb = new StringBuilder();
        for (int q = 0; q < Q; q++) {
            String[] tmp = br.readLine().split(" ");
            if (tmp[0].equals("In")) sb.append(chkIn(Integer.parseInt(tmp[1])));
            else sb.append(chkOut(Integer.parseInt(tmp[1])));
        }

        System.out.println(sb);
    }

    static int findSeat() {
        if (kitchen.isEmpty()) return 0;
        PriorityQueue<Pos> pq = new PriorityQueue<>((o1, o2) -> o1.score != o2.score ? -Integer.compare(o1.score, o2.score) :
                o1.r != o2.r ? Integer.compare(o1.r, o2.r) : Integer.compare(o1.c, o2.c));
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (chk[r][c]) continue;
                PriorityQueue<Pos> pqTmp = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.score, o2.score));
                for (Pos p : kitchen) {
                    pqTmp.add(new Pos(r, c, p));
                }
                if (!pqTmp.isEmpty()) pq.add(pqTmp.poll());
            }
        }

        return pq.isEmpty() ? -1 : pq.peek().r * M + pq.peek().c;
    }

    static void chkSeat() {
        chk = new boolean[N][M];
        for (Pos p : kitchen) {
            chk[p.r][p.c] = true;
            for (int d = 0; d < 4; d++) {
                int nr = p.r + dr[d];
                int nc = p.c + dc[d];
                if (outMap(nr, nc)) continue;
                chk[nr][nc] = true;
            }
        }
    }

    static boolean outMap(int r, int c) {
        if (r < 0 || r >= N || c < 0 || c >= M) return true;
        return false;
    }

    static String chkIn(int id) {
        if (!state.containsKey(id) || state.get(id) < 0) {
            state.put(id, findSeat());
            if (state.get(id) < 0) return "There are no more seats.\n";
            else {
                int r = state.get(id) / M;
                int c = state.get(id) % M;
                kitchen.add(new Pos(r, c, 0));
                chkSeat();
                return id + " gets the seat (" + (r + 1) + ", " + (c + 1) + ").\n";
            }
        } else if (state.get(id) < 400) {
            return id + " already seated.\n";
        } else {
            return id + " already ate lunch.\n";
        }
    }

    static String chkOut(int id) {
        if (!state.containsKey(id) || state.get(id) < 0) {
            return id + " didn't eat lunch.\n";
        } else if (state.get(id) < 400) {
            int r = state.get(id) / M;
            int c = state.get(id) % M;
            kitchen.remove(new Pos(r, c, 0));
            chkSeat();
            state.put(id, 400);
            return id + " leaves from the seat (" + (r + 1) + ", " + (c + 1) + ").\n";
        } else {
            return id + " already left seat.\n";
        }
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        Q = Integer.parseInt(tmp[2]);

        chk = new boolean[N][M];
        state = new HashMap<>();
        kitchen = new TreeSet<>((o1, o2) -> o1.r == o2.r ? Integer.compare(o1.c, o2.c) : Integer.compare(o1.r, o2.r));
    }

    static class Pos {
        int r, c, score;

        public Pos(int r, int c, int score) {
            this.r = r;
            this.c = c;
            this.score = score;
        }

        public Pos(int r, int c, Pos p) {
            this.r = r;
            this.c = c;
            int absX = Math.abs(r - p.r);
            int absY = Math.abs(c - p.c);
            this.score = absX * absX + absY * absY;
        }
    }
}
