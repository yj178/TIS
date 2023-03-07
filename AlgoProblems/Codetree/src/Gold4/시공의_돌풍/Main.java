package Gold4.시공의_돌풍;
import java.util.*;
import java.io.*;

public class Main {
    static int N, M, T, SR1, SR2;
    static int[][] map, next;
    static int[] dr1 ={-1,0,1,0};
    static int[] dc1 ={0,1,0,-1};
    static int[] dr2 ={1,0,-1,0};
    static int[] dc2 ={0,1,0,-1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(simul());
    }
    static void veiwMap(){
        System.out.println();
        for(int r = 0; r < N;r++){
            for(int c = 0; c< M;c++){
                System.out.print(map[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println();

    }

    static int simul(){
        for(int t = 0; t < T; t++){
            spreadDust();
            veiwMap();
            actStorm();
            veiwMap();
        }

        return totalDust();
    }

    static void actStorm(){
        int d = 0;
        int sr1 = SR1;
        int sc1 = 0;
        int sr2 = SR2;
        int sc2 = 0;

        while(true){
            if(chkOut(sr1 + dr1[d], sc1 + dc1[d]) || sr1 + dr1[d] > SR1) d++;
            int nr = sr1 + dr1[d];
            int nc = sc1 + dc1[d];
            if(nr == SR1 && nc == 0) break;
            if(!(sr1 == SR1 && sc1 == 0)) map[sr1][sc1] = map[nr][nc];
            map[nr][nc] = 0;
            sr1 += dr1[d];
            sc1 += dc1[d];
        }
        d = 0;
        while(true){
            if(chkOut(sr2 + dr2[d], sc2 + dc2[d]) || sr2 + dr2[d] < SR2) d++;
            int nr = sr2 + dr2[d];
            int nc = sc2 + dc2[d];
            if(nr == SR2 && nc == 0) break;
            if(!(sr2 == SR2 && sc2 == 0)) map[sr2][sc2] = map[nr][nc];
            map[nr][nc] = 0;
            sr2 += dr2[d];
            sc2 += dc2[d];
        }
    }

    static int dirChk(int d){
        if(d < 0) return 3;
        if(d >= 4) return 0;
        return d;
    }
    static void spreadDust(){
        next = new int[N][M];
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                if(map[r][c] == -1) continue;
                int cnt = 0;
                int req = map[r][c] / 5;
                for(int d = 0; d < 4; d++){
                    int nr = r + dr1[d];
                    int nc = c + dc1[d];
                    // 방을 벗어나거나 돌풍이 있다면 확산 안함
                    if(chkOut(nr, nc) || map[nr][nc] == -1) continue;
                    next[nr][nc] += req;
                    cnt++;
                }
                // 확산된 양 만큼 감소
                next[r][c] -= req * cnt;
            }
        }
        // 한번에 추가됨
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M;c++){
                if(map[r][c] == -1) continue;
                map[r][c] += next[r][c];
            }
        }
    }

    static boolean chkOut(int r, int c){
        return r < 0 || r >= N || c < 0 || c >= M ? true : false;
    }

    static int totalDust(){
        int ans = 0;
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                ans += map[r][c];
            }
        }
        return ans + 2;
    }

    static void init(BufferedReader br )throws IOException{
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        T = Integer.parseInt(tmp[2]);

        map = new int[N][M];

        for(int r = 0; r < N; r++){
            tmp = br.readLine().split(" ");
            for(int c = 0; c < M; c++){
                map[r][c] = Integer.parseInt(tmp[c]);
                if(map[r][c] == -1){
                    SR1 = r-1;
                    SR2 = r;
                }
            }
        }

    }
}