package Gold3.종전;

import java.io.*;

public class Main{
    static int N, ans, total;
    static Pos[] pos;
    static int[][] map;
    static int[] dr = {-1,-1,1};
    static int[] dc = {1,-1,-1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        for(int r = N-1; r > 0; r--){
            for(int c = 1; c < N-1; c++){
                pos[0] = new Pos(r,c);
                dfs(1, r, c);
            }
        }
        System.out.println(ans);

    }

    static int calcTwo(){
        int sum = 0;
        for(int r = 0; r < pos[3].r; r++){
            for(int c = 0; c <= pos[2].c - (r < pos[2].r ? 0 : r - pos[2].r + 1); c++){
                sum += map[r][c];
            }
        }
        return sum;
    }
    static int calcThree(){
        int sum = 0;
        for(int r = 0; r <= pos[1].r; r++){
            for(int c = pos[2].c + 1 + (r <= pos[2].r ? 0 : (r-pos[2].r)); c < N; c++){
                sum += map[r][c];
            }
        }
        return sum;
    }
    static int calcFour(){
        int sum = 0;
        for(int r = pos[3].r; r < N; r++){
            for(int c = 0; c < Math.min( pos[3].c + (r - pos[3].r ), pos[0].c); c++){
                sum += map[r][c];
            }
        }
        return sum;
    }
    static int calcFive(){
        int sum = 0;
        for(int r = pos[1].r+1; r < N; r++){
            for(int c = Math.max(pos[0].c, pos[0].c + (pos[0].r - r + 1)); c < N; c++){
                sum += map[r][c];
            }
        }
        return sum;
    }

    static void dfs(int dep, int r, int c){
        if(dep == 3){ // 종료 조건
            // pos[3] 계산
            pos[3] = new Pos(pos[2].r + (pos[0].r - pos[1].r), pos[2].c + (pos[0].c - pos[1].c));
            if(chkOut(pos[3].r, pos[3].c)) return;

            int[] cnts = new int[5];   
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            // 경계에 따라 2~5 부족 인원 계산
            cnts[1] = calcTwo();
            cnts[2] = calcThree();
            cnts[3] = calcFour();
            cnts[4] = calcFive();

            // 위에서 구한 2~5 부족 인원 합을 통해 부족 1 구함
            cnts[0] = total - cnts[1] - cnts[2] - cnts[3] - cnts[4];

            // 최대 최소 갱신
            for(int cnt : cnts){
                min = Math.min(min, cnt);
                max = Math.max(max, cnt);
            }
            
            // 정답 갱신
            ans = Math.min(ans, max-min);
            // 종료
            return;
        }
        int d = 1;
        while(!chkOut(r + dr[dep-1] * d, c + dc[dep] * d)){
            int nr = r + dr[dep-1] * d;
            int nc = c + dc[dep-1] * d;
            pos[dep] = new Pos(nr, nc); 
            dfs(dep + 1, nr, nc);
            d++;
        }
    }

    static boolean chkOut(int r, int c){
        return r < 0 || r >= N || c < 0 || c >= N ? true : false;
    }

    static void init(BufferedReader br)throws IOException{
        N = Integer.parseInt(br.readLine());
        ans = Integer.MAX_VALUE;
        pos = new Pos[4];
        map = new int[N][N];
        for(int r = 0; r < N; r++){
            String[] tmp = br.readLine().split(" ");
            for(int c = 0; c < N; c++){
                map[r][c] = Integer.parseInt(tmp[c]);
                total += map[r][c];
            }
        }
    }

    static class Pos{
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Pos [r=" + r + ", c=" + c + "]";
        }

    }
}