package Gold2.색깔_폭탄;

import java.io.*;
import java.util.*;

class Main {
    static int N, M;
    static int[][] map;
    static boolean[][] chk;
    static int[] dr = {0,0,-1,1};
    static int[] dc = {1,-1,0,0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        // rotate();
        // viewMap();
        // gravity();
        // viewMap();

        System.out.println(simul());
    }

    static void init(BufferedReader br) throws IOException{
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        map = new int[N][N];
        for(int r = 0; r < N; r++){
            tmp = br.readLine().split(" ");
            for(int c = 0; c < N; c++){
                map[r][c] = Integer.parseInt(tmp[c]);
            }
        }
    }
    
    static void viewMap(){
        System.out.println();
        for(int r = 0; r < N; r++){
            for(int c = 0; c < N; c++){
                System.out.print(map[r][c] + " ");
            }
            System.out.println();
        }
    }

    static int simul(){
        int score = 0;
        
        while(true){
            // 폭탄 그룹 찾기
            chk = new boolean[N][N];
            PriorityQueue<Group> pq = new PriorityQueue<>();
            for(int r = 0; r < N; r++){
                for(int c = 0; c < N; c++){
                    if(map[r][c] <= 0 || chk[r][c]) continue;
                    pq.add(bfs(r, c));
                }
            }

            if(pq.isEmpty() || pq.peek().list.size() <= 1) return score;
            
            // 폭탄 그룹 폭파
            int c = pq.peek().list.size();
            score += c * c;
            for(Pos p : pq.peek().list){
                map[p.r][p.c] = -2;
            }
            
            // viewMap();
            // 중력 작용
            gravity();
            
            // 반시계 방향으로 90도 회전
            rotate();
            
            // 중력 작용
            gravity();
            
            
        }
    }
    
    private static void rotate() {
        int[][] newMap = new int[N][N];
        for(int r = 0; r < N; r++){
            for(int c = 0; c < N; c++){
                newMap[N - 1 - c][r] = map[r][c];
            }
        }
        map = newMap;
    }

    private static void gravity() {
        for(int c = 0; c < N; c++){
            int idx = N - 1;
            for(int r = N - 1; r >= 0; r--){
                if(map[r][c] == -2) continue;
                else if(map[r][c] == -1) idx = r - 1;
                else {
                    int tmp = map[r][c];
                    map[r][c] = -2;
                    map[idx][c] = tmp;
                    idx--;
                }
            }
        }
    }

    static Group bfs(int r, int c){
        int type = map[r][c];
        Queue<Pos> q = new LinkedList<>();
        q.add(new Pos(r, c, map[r][c]));
        PriorityQueue<Pos> pq = new PriorityQueue<>();
        ArrayList<Pos> list = new ArrayList<>();
        int redCount = 0;
        boolean[][] nowChk = new boolean[N][N];
        nowChk[r][c] = true;

        while(!q.isEmpty()){
            Pos now = q.poll();
            list.add(new Pos(now.r, now.c, now.type));
            pq.add(new Pos(now.r, now.c, now.type));
            if(map[now.r][now.c] == 0) redCount++;
            else chk[now.r][now.c] = true;

            for(int d = 0; d < 4; d++){
                int nr = now.r + dr[d];
                int nc = now.c + dc[d];

                if(chkOut(nr, nc) || nowChk[nr][nc] || !((map[nr][nc] == type) || (map[nr][nc] == 0))) continue;

                q.add(new Pos(nr, nc, map[nr][nc]));
                nowChk[nr][nc] = true;
            }
        }

        // System.out.println(r + ", " + c + " : start");
        // for(Pos p : list){
        //     System.out.println(p);
        // }

        return new Group(list, pq.poll(), redCount);
    }

    static boolean chkOut(int r, int c){
        return r < 0 || r >= N || c < 0 || c >= N ? true : false;
    }


    static class Group implements Comparable<Group>{
        ArrayList<Pos> list;
        Pos standard;
        int redCount;
        public Group(ArrayList<Pos> list, Pos standard, int redCount) {
            this.list = list;
            this.standard = standard;
            this.redCount = redCount;
        }
        @Override
        public int compareTo(Group o) {
            int thisSize = this.list.size();
            int oSize = o.list.size();
            return thisSize != oSize ? oSize - thisSize : this.redCount != o.redCount ? this.redCount - o.redCount : this.standard.compareTo(o.standard) ;
        }
    }

    static class Pos implements Comparable<Pos>{
        int r,c, type;
        public Pos(int r, int c, int type) {
            this.r = r;
            this.c = c;
            this.type = type;
        }

        @Override
        public int compareTo(Pos o) {
            if(this.type == 0 && o.type != 0 ) return 1;
            else if(this.type != 0 && o.type == 0) return -1;
            else if(this.type == 0 && o.type == 0)  return 0;
            else return this.r != o.r ? o.r - this.r : this.c - o.c;
        }

        @Override
        public String toString() {
            return "Pos [r=" + r + ", c=" + c + "]";
        }
        
    }
}