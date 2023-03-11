package Gold1.팩맨;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;

class Main {

    static int T, pr, pc;
    static int[] dr = { -1, -1, 0, 1, 1, 1, 0, -1 };
    static int[] dc = { 0, -1, -1, -1, 0, 1, 1, 1 };
    static long[][][] monsters;
    static int[][] deads;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        simul();
        System.out.println(cntMonsters());
    }

    static boolean chkOut(int r, int c) {
        return r < 0 || r >= 4 || c < 0 || c >= 4 ? true : false;
    }
    static void viewDead(){
        System.out.println();
        for(int r = 0 ; r < 4; r++){
            for(int c = 0; c <4;c++){
                System.out.print(deads[r][c] + " ");
            }
            System.out.println();
        }
    }

    static void simul() {
        long[][][] eggs;
        for (int t = 1; t <= T; t++) {
            // System.out.println(t + " 턴 ------------------");
            // 몬스터 복제
            eggs = new long[4][4][8];
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    for (int d = 0; d < 8; d++) {
                        eggs[r][c][d] += monsters[r][c][d]; // 알 추가
                    }
                }
            }

            viewDead();
            // 몬스터 이동
            long[][][] newMonsters = new long[4][4][8];
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    int step = 0;
                    for (int d = 0; d < 8; d++) {
                        if(monsters[r][c][d] == 0) continue;
                        step = 0;
                        for (int dd = 0; dd < 8; dd++) { // 이동 가능한 방향 탐색
                            int nd = (d + dd) % 8;
                            int nr = r + dr[nd];
                            int nc = c + dc[nd];
                            // if(chkOut(nr, nc)) continue;
                            // System.out.println(nr+", " +nc + "// " + deads[nr][nc]);
                            // 격자에서 벗어난 경우, 시체가 있는 경우, 팩맨이 있는 경우
                            if ((chkOut(nr, nc)) || (deads[nr][nc] >= t) || (nr == pr && nc == pc)) {
                                step++;
                                continue;
                            }
                            // System.out.println(nr + ", " + nc + " : " + nd + " : " + monsters[r][c][d]);
                        
                            newMonsters[nr][nc][nd] += monsters[r][c][d];
                            break;
                        }
                        if (step == 8)
                            break;
                    }
                    if (step == 8) { // 가만히 있는 경우
                        for (int d = 0; d < 8; d++) {
                            // System.out.println(r + ", " + c + " : " + d + " : " + monsters[r][c][d]);
                            newMonsters[r][c][d] += monsters[r][c][d];
                        }
                    }
                }
            }

            monsters = newMonsters;

            // System.out.println(cntMonsters());
            // 팩맨 이동
            bfs(t);
            // System.out.println(cntMonsters());
            // 시체 소멸
            // 시체 발생 시기 기록으로 대체

            // 복제 완성
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    for (int d = 0; d < 8; d++) {
                        monsters[r][c][d] += eggs[r][c][d];
                    }
                }
            }
            // System.out.println(cntMonsters());

        }
    }

    static void bfs(int t) {
        Queue<Pos> q = new LinkedList<>();
        q.add(new Pos(pr, pc, ""));
        int step = 0;
        PriorityQueue<Pos> pq = new PriorityQueue<>();
        // 3칸 이동
        while (!q.isEmpty() && step <= 3) {
            step++;
            int s = q.size();
            for (int i = 0; i < s; i++) {
                Pos now = q.poll();
                // 상하좌우로 이동
                for (int d = 0; d < 8; d += 2) {
                    int nr = now.r + dr[d];
                    int nc = now.c + dc[d];
                    // 맵을 벗어날 수 없음
                    if (chkOut(nr, nc))
                        continue;
                    if (step == 3)
                        pq.add(new Pos(nr, nc, now.log + d, pr, pc));
                    else
                        q.add(new Pos(nr, nc, now.log + d));
                }
            }
        }

        // 가장 많이 먹을 수 있는 방향 && 상좌하우 방향
        Pos costMax = pq.peek();
        int r = pr;
        int c = pc;
        // System.out.println("팩맨 : " + r +", " + c);
        // while(!pq.isEmpty()){
        // System.out.println(pq.peek().log +" : " + pq.peek().cnt);
        // pq.poll();
        // }
        for (int i = 0; i < 3; i++) {
            r = r + dr[costMax.log.charAt(i) - '0'];
            c = c + dc[costMax.log.charAt(i) - '0'];
            for (int d = 0; d < 8; d++) {
                if(monsters[r][c][d] == 0) continue;
                monsters[r][c][d] = 0; // 가장 많이 먹는 이동 경우에 대해 위치의 몬스터 제거
                deads[r][c] = t + 2; // 시체 갱신
            }
        }

        pr = costMax.r;
        pc = costMax.c;

    }

    static long cntMonsters() {
        long cnt = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                // int tmp = 0;
                for (int d = 0; d < 8; d++) {
                    // tmp += monsters[r][c][d];
                    cnt += monsters[r][c][d];
                }
                // if (tmp > 0)
                    // System.out.print("(" + r + ", " + c + ") " + tmp + " ");
            }
            // System.out.println();
        }
        return cnt;
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        int M = Integer.parseInt(tmp[0]);
        T = Integer.parseInt(tmp[1]);

        monsters = new long[4][4][8];
        deads = new int[4][4];

        tmp = br.readLine().split(" ");
        pr = Integer.parseInt(tmp[0]) - 1;
        pc = Integer.parseInt(tmp[1]) - 1;

        for (int m = 0; m < M; m++) {
            tmp = br.readLine().split(" ");
            int r = Integer.parseInt(tmp[0]) - 1;
            int c = Integer.parseInt(tmp[1]) - 1;
            int d = Integer.parseInt(tmp[2]) - 1;
            monsters[r][c][d]++;
        }
    }

    static class Pos implements Comparable<Pos> {
        int r, c;
        String log;
        long cnt;

        public Pos(int r, int c, String log) {
            this.r = r;
            this.c = c;
            this.log = log;
        }

        public Pos(int r, int c, String log, int rr, int cc) {
            this.r = r;
            this.c = c;
            this.log = log;
            boolean[][] chk = new boolean[4][4];
            this.cnt = 0;
            int nr = rr;
            int nc = cc;
            long tmp = 0;
            for (int i = 0; i < 3; i++) {
                nr = nr + dr[log.charAt(i) - '0'];
                nc = nc + dc[log.charAt(i) - '0'];
                if (chk[nr][nc])
                    continue;
                chk[nr][nc] = true;
                for (int d = 0; d < 8; d++) {
                    tmp += monsters[nr][nc][d];
                }
            }

            this.cnt = tmp;

        }

        @Override
        public int compareTo(Pos o) {
            return this.cnt != o.cnt ? -Long.compare(this.cnt, o.cnt) : this.log.compareTo(o.log);
        }
    }

}
