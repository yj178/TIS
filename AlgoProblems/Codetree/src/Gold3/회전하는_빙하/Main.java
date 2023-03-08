package Gold3.회전하는_빙하;
import java.util.*;
import java.io.*;
public class Main {
    static int N, Q, mapMax;
    static int[] rot;
    static int[][] map;
    static boolean[][] chk;
    static int[] dr = {0,0,-1,1};
    static int[] dc = {1,-1,0,0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        // viewMap();
        simul();
    }
    static void simul(){
        // viewMap();
        for(int q = 0; q < Q; q++){
            // rotate
            divide(q);
            // melt
            melt();

            // viewMap();
        }
        int cnt = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        chk = new boolean[mapMax][mapMax];
        for(int r = 0; r < mapMax; r++){
            for(int c = 0; c < mapMax; c++){
                cnt += map[r][c];
                if(chk[r][c] || map[r][c] == 0) continue;
                pq.add(bfs(r, c));
            }            
        }
        System.out.println(cnt);
        System.out.println(pq.isEmpty() ? 0 : pq.poll());
    }

    static void divide(int levelIdx){
        int level = calcMax(rot[levelIdx]);
        if(level == 1) return;
        int[][] newMap = new int[mapMax][mapMax];
        for(int r = 0; r < mapMax; r+=level){
            for(int c = 0; c < mapMax; c += level){
                rotate(newMap, level/2, r, c);
            }
        }
        
        map = newMap;
    }

    static void rotate(int[][] newMap, int l, int rInit, int cInit){
        // 좌상 -> 우상
        for(int r = rInit; r < rInit + l; r++){
            for(int c = cInit; c < cInit + l; c++){
                newMap[r][c+l] = map[r][c];
            }
        }
        // 우상 -> 우하
        for(int r = rInit; r < rInit + l; r++){
            for(int c = cInit + l; c < cInit + l + l; c++){
                newMap[r+l][c] = map[r][c];
            }
        }
        // 우하 -> 좌하
        for(int r = rInit + l; r < rInit + l + l; r++){
            for(int c = cInit + l; c < cInit + l + l; c++){
                newMap[r][c - l] = map[r][c];
            }
        }
        // 좌하 -> 좌상
        for(int r = rInit + l; r < rInit + l + l; r++){
            for(int c = cInit; c < cInit + l; c++){
                newMap[r-l][c] = map[r][c];
            }
        }
    }   

    static void melt(){
        int[][] melting = new int[mapMax][mapMax];
        for(int r = 0; r < mapMax; r++){
            for(int c = 0;c < mapMax; c++){
                if(map[r][c] == 0) continue;
                int con = 0;
                for(int d = 0; d < 4; d++){
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if(outChk(nr, nc) || map[nr][nc] == 0) con++;
                }

                if(con > 1) melting[r][c] = -1;
            }
        }

        for(int r = 0; r < mapMax; r++){
            for(int c = 0;c < mapMax; c++){
                map[r][c] += melting[r][c];
            }
        }
    }

    static int bfs(int r, int c){
        Queue<Pos> q = new LinkedList<>();
        q.add(new Pos(r, c));
        chk[r][c] = true;

        int step = 0;
        while(!q.isEmpty()){
            Pos tmp = q.poll();
            step++;
            for(int d = 0; d < 4; d++){
                int nr = tmp.r + dr[d];
                int nc = tmp.c + dc[d];

                if(outChk(nr, nc) || chk[nr][nc] || map[nr][nc] == 0) continue;

                q.add(new Pos(nr, nc));
                chk[nr][nc] = true;
            }
        }
        // System.out.println(r+", " + c + " : "+step);
        return step;
    }

    static boolean outChk(int r, int c){
        return r < 0 || r >= mapMax || c < 0 || c >= mapMax ? true : false;
    }

    static void viewMap(){
        System.out.println();
        for(int r = 0; r < mapMax; r++){
            for(int c = 0; c < mapMax; c++){
                System.out.print(map[r][c]+ " ");
            }            
            System.out.println();
        }
    }

    static void init(BufferedReader br) throws IOException{
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        Q = Integer.parseInt(tmp[1]);
        mapMax = calcMax(N);
        map = new int[mapMax][mapMax];
        rot = new int[Q];
        for(int r = 0; r < mapMax; r++){
            tmp = br.readLine().split(" ");
            for(int c = 0; c < mapMax; c++){
                map[r][c] = Integer.parseInt(tmp[c]);
            }            
        }

        tmp = br.readLine().split(" ");
        for(int q = 0; q < Q; q++){
            rot[q] = Integer.parseInt(tmp[q]);
        }

    }

    static int calcMax(int level){
        int l = 1;
        for(int n = 0; n < level; n++)
            l *= 2;

        return l;
    }

    static class Pos{
        int r, c;
        public Pos(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
}