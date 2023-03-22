package Gold3.생명과학부_랩_인턴;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

class Main {
    static int N, M, K;
    static int[] dr = { -1, 0, 1, 0 };
    static int[] dc = { 0, 1, 0, -1 };
    static TreeSet<Gompang>[][] map;
    static TreeSet<Gompang> goms;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(simul());
    }

    static int simul() {
        int cnt = 0;

        for (int m = 0; m < M; m++) {
            // System.out.println();
            // for (int r = 0; r < N; r++) {
            //     for(int c= 0; c < M ; c++){
            //         if(!map[r][c].isEmpty())
            //         System.out.println(map[r][c].first());
            //     }
            // }
            // System.out.println();

            // 곰팡이 채취
            for (int r = 0; r < N; r++) {
                if (!map[r][m].isEmpty()) {
                    Gompang now = map[r][m].pollFirst();
                    cnt += now.b;
                    System.out.println("채취 : "+now);
                    goms.remove(now);
                    break;
                }
            }

            // 곰팡이 이동
            ArrayList<String> chk = moveGom();
      
            // 곰팡이 식사시간
            for(String p : chk){
                String[] pp = p.split("/");
                int r = Integer.parseInt(pp[0]);
                int c = Integer.parseInt(pp[1]);
                while(map[r][c].size() > 1) {
                    Gompang g = map[r][c].pollFirst();
                    // System.out.println("먹방 : " + g);
                    goms.remove(g);
                }
            }
        }

        return cnt;
    }

    private static ArrayList<String> moveGom() {
        map = new TreeSet[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                map[r][c] = new TreeSet<>();
            }
        }

        TreeSet<Gompang> tmp = new TreeSet<>();
        ArrayList<String> chk = new ArrayList<>();
        for (Gompang gom : goms) {
            int nr = 0;
            int nc = 0;
            if (gom.d == 0) {
                int dis = (N - 1) - gom.r + gom.s;
                while(dis >= N){
                    dis -= (N-1);
                    gom.d = (gom.d + 2) % 4;
                }
                if(gom.d == 0) nr = N - 1 - dis;
                else nr = dis;
                nc = gom.c;
            } else if (gom.d == 1) {
                int dis = gom.c + gom.s;
                while(dis >= M){
                    dis -= (M-1);
                    gom.d = (gom.d + 2) % 4;
                }
                if(gom.d == 3) nc = M - 1 - dis;
                else nc = dis;
                nr = gom.r;
            } else if (gom.d == 2) {
                int dis = gom.r + gom.s;
                while(dis >= N){
                    dis -= (N-1);
                    gom.d = (gom.d + 2) % 4;
                }
                if(gom.d == 0) nr = N - 1 - dis;
                else nr = dis;
                nc = gom.c;
            } else if (gom.d == 3) { // 왼쪽
                int dis = (M-1) - gom.c + gom.s;
                while(dis >= M){
                    dis -= (M-1);
                    gom.d = (gom.d + 2) % 4;
                }
                if(gom.d == 3) nc = M - 1 - dis;
                else nc = dis;
                nr = gom.r;
            } else{
                System.out.println("방향 에러");
            }
            // System.out.println("이동 후 : " + new Gompang(nr, nc, gom.s, gom.d, gom.b));

            tmp.add(new Gompang(nr, nc, gom.s, gom.d, gom.b));
            map[nr][nc].add(new Gompang(nr, nc, gom.s, gom.d, gom.b));
            if(map[nr][nc].size() != 1) chk.add(nr + "/" + nc);
        }
        goms = tmp;
        return chk;
    }

    
    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        K = Integer.parseInt(tmp[2]);
        goms = new TreeSet<>();
        
        map = new TreeSet[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                map[r][c] = new TreeSet<>();
            }
        }

        for (int k = 0; k < K; k++) {
            tmp = br.readLine().split(" ");
            int r = Integer.parseInt(tmp[0])-1;
            int c = Integer.parseInt(tmp[1])-1;
            int s = Integer.parseInt(tmp[2]);
            int d = Integer.parseInt(tmp[3]);
            int b = Integer.parseInt(tmp[4]);
            switch (d) {
                case 1:
                    d = 0;
                    break;
                case 2:
                    d = 2;
                    break;
                case 3:
                    d = 1;
                    break;
                case 4:
                    d = 3;
                    break;
            }

            goms.add(new Gompang(r, c, s, d, b));
            map[r][c].add(new Gompang(r, c, s, d, b));
        }
    }

    static class Gompang implements Comparable<Gompang> {
        int r, c, s, d, b;

        public Gompang(int r, int c, int s, int d, int b) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.b = b;
            this.d = d;
        }

        @Override
        public int compareTo(Gompang o) {
            return Integer.compare(this.b, o.b);
        }

        @Override
        public String toString() {
            return "Gompang [r=" + r + ", c=" + c + ", s=" + s + ", d=" + d + ", b=" + b + "]";
        }

        
    }
}
