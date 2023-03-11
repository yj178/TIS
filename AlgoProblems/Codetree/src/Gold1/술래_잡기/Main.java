package Gold1.술래_잡기;

import java.util.*;
import java.io.*;

class Main {
    static int N, K, H, M;
    // 오른쪽
    static final int[] dr1 = {0, 0};
    static final int[] dc1 = {1, -1};
    // 아래
    static final int[] dr2 = {1, -1};
    static final int[] dc2 = {0, 0};

    // 도망자 관리
    static TreeMap<Pos, LinkedList<Runner>> runners;
    static TreeSet<Pos> trees;
    static Catcher cat;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(simul());
    }

    static int simul() {
        int score = 0;

        for (int k = 1; k <= K; k++) {
            // 도망자 움직임
            TreeMap<Pos, LinkedList<Runner>> newRunner = new TreeMap<>();
            for (Map.Entry<Pos, LinkedList<Runner>> e : runners.entrySet()) {
                Pos now = e.getKey();
                while (!e.getValue().isEmpty()) {
                    Runner idx = e.getValue().poll();
                    Pos next = idx.move(now);
                    if (!newRunner.containsKey(next)) newRunner.put(next, new LinkedList<>());
                    newRunner.get(next).add(idx);
                }
            }

            runners = newRunner;

            // 술래 움직임 & 방향
            cat.move();
            cat.setDir();

            // 술래 도망자 잡기
            score += k * cat.deleteRunner();
        }

        return score;
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        H = Integer.parseInt(tmp[2]);
        K = Integer.parseInt(tmp[3]);
        runners = new TreeMap<>();
        trees = new TreeSet<>();
        cat = new Catcher(new int[]{-1, 0, 1, 0}, new int[]{0, 1, 0, -1}, 1, N / 2, N / 2, 0);

        for (int m = 0; m < M; m++) {
            tmp = br.readLine().split(" ");
            int r = Integer.parseInt(tmp[0]) - 1;
            int c = Integer.parseInt(tmp[1]) - 1;
            int d = Integer.parseInt(tmp[2]);
            Pos now = new Pos(r, c);
            if (d == 1) {
                if (!runners.containsKey(now)) runners.put(new Pos(r, c), new LinkedList<>());
                runners.get(new Pos(r, c)).add(new Runner(0, dr1, dc1));
            } else {
                if (!runners.containsKey(now)) runners.put(new Pos(r, c), new LinkedList<>());
                runners.get(new Pos(r, c)).add(new Runner(0, dr2, dc2));
            }
        }

        for (int h = 0; h < H; h++) {
            tmp = br.readLine().split(" ");
            int r = Integer.parseInt(tmp[0]) - 1;
            int c = Integer.parseInt(tmp[1]) - 1;

            trees.add(new Pos(r, c));
        }

    }

    static boolean chkOut(int r, int c) {
        return r < 0 || r >= N || c < 0 || c >= N ? true : false;
    }

    static class Catcher {
        int[] dr;
        int[] dc;
        int state, r, c, d;


        public Catcher(int[] dr, int[] dc, int state, int r, int c, int d) {
            this.dr = dr;
            this.dc = dc;
            this.state = state;
            this.r = r;
            this.c = c;
            this.d = d;
        }

        public void move() {
            this.r = this.r + this.dr[this.d];
            this.c = this.c + this.dc[this.d];
        }

        public void setDir() {
            if (this.r == 0 && this.c == 0) {
                this.d = 2;
                state = -1;
            } else if (this.r == N / 2 && this.c == N / 2) {
                this.d = 0;
                state = 1;
            } else if (this.r < N / 2 && this.c == this.r + 1) this.d += state;
            else if (this.r > N / 2 && this.c == this.r) this.d += state;
            else if (this.r == (N - 1) - this.c) this.d += state;
            if (this.d > 3) this.d = 0;
            if (this.d < 0) this.d = 3;
        }

        public int deleteRunner() {
            int cnt = 0;
            for (int i = 0; i < 3; i++) {
                Pos chkPos = new Pos(this.r + this.dr[this.d] * i, this.c + this.dc[this.d] * i);
                if (trees.contains(chkPos)) continue;
                if (runners.containsKey(chkPos)) {

                    cnt += runners.get(chkPos).size();
                    runners.remove(chkPos);
                }
            }
            return cnt;
        }

    }

    static class Runner {
        int dir;
        int[] dr, dc;

        public Runner(int dir, int[] dr, int[] dc) {
            this.dir = dir;
            this.dr = dr;
            this.dc = dc;
        }

        public Pos move(Pos now) {
            if (calcDis(now.r, now.c) > 3) return now;

            int nr = now.r + this.dr[this.dir];
            int nc = now.c + this.dc[this.dir];
            if (chkOut(nr, nc)) this.dir = (this.dir + 1) % 2;
            nr = now.r + this.dr[this.dir];
            nc = now.c + this.dc[this.dir];
            if (cat.r == nr && cat.c == nc) return now;
            else return new Pos(nr, nc);
        }

        public int calcDis(int r, int c) {
            return Math.abs(cat.r - r) + Math.abs(cat.c - c);
        }

    }

    static class Pos implements Comparable<Pos> {
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public int compareTo(Pos o) {
            return this.r != o.r ? this.r - o.r : this.c - o.c;
        }


    }
}