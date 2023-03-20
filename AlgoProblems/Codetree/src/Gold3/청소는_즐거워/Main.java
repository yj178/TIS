package Gold3.청소는_즐거워;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int N;
    static int[][] map;
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {-1, 0, 1, 0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(simul());
    }

    static int simul(){
        int sum = 0;
        int nr = N / 2;
        int nc = N / 2;
        int d = 0;

        while(true){
            // 이동
            nr += dr[d];
            nc += dc[d];
            // 청소
            sum += cleaning(nr, nc, d);

            // 방향 변경
            if(chkTurn(nr, nc)) d = turnDir(d, 1);

            // 마지막 위치 종료
            if(nr == 0 && nc == 0) break;
        }

        return sum;
    }

    static boolean chkTurn(int r, int c){
        if(r <= N/2 && r - 1 == c) return true;
        else if(r > N/2 && N - 1 - r == c) return true;
        else if(r > N/2 && r == c) return true;
        else if(r < N/2 && r == N - 1 - c) return true;
        else return false;
    }

    static int cleaning(int r, int c, int d){
        int outSum = 0;
        int sum = 0;
        int now = map[r][c];
        int tmp = 0;
        map[r][c] = 0;
        // 앞으로 2칸
        int nr = r + dr[d] * 2;
        int nc = c + dc[d] * 2;
        tmp = now / 20;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;

        // 앞으로 1칸 왼쪽 1칸
        nr = r + dr[d];
        nc = c + dc[d];
        nr += dr[turnDir(d, 1)];
        nc += dc[turnDir(d, 1)];
        tmp = now / 10;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;

        // 앞으로 1칸 오른쪽 1칸
        nr = r + dr[d];
        nc = c + dc[d];
        nr += dr[turnDir(d, -1)];
        nc += dc[turnDir(d, -1)];
        tmp = now / 10;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;

        // 왼쪽 1칸
        nr = r + dr[turnDir(d, 1)];
        nc = c + dc[turnDir(d, 1)];
        tmp = now * 7 / 100;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;

        // 오른쪽 1칸
        nr = r + dr[turnDir(d, -1)];
        nc = c + dc[turnDir(d, -1)];
        tmp = now * 7 / 100;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;

        // 왼쪽 2칸
        nr = r + dr[turnDir(d, 1)] * 2;
        nc = c + dc[turnDir(d, 1)] * 2;
        tmp = now / 50;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;

        // 오른쪽 2칸
        nr = r + dr[turnDir(d, -1)] * 2;
        nc = c + dc[turnDir(d, -1)] * 2;
        tmp = now / 50;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;
        
        // 뒤로 1칸 왼쪽으로 한 칸
        nr = r + dr[turnDir(d, 2)];
        nc = c + dc[turnDir(d, 2)];
        nr += dr[turnDir(d, 1)];
        nc += dc[turnDir(d, 1)];
        tmp = now / 100;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;

        // 뒤로 1칸 오른쪽으로 1칸
        nr = r + dr[turnDir(d, 2)];
        nc = c + dc[turnDir(d, 2)];
        nr += dr[turnDir(d, -1)];
        nc += dc[turnDir(d, -1)];
        tmp = now / 100;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;
        sum += tmp;

        // 남은 먼지 앞에 1칸
        nr = r + dr[d];
        nc = c + dc[d];
        tmp = now - sum;
        if(chkOut(nr, nc)) outSum += tmp;
        else map[nr][nc] += tmp;

        return outSum;
    }

    static boolean chkOut(int r, int c){
        return r < 0 || r >= N || c < 0 || c >= N ? true : false;
    }

    static int turnDir(int d, int pm){
        if(d + pm < 0) return d + pm + 4;
        if(d + pm > 3) return d + pm - 4;
        return d + pm;
    }


    static void init(BufferedReader br) throws IOException {
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        for(int r = 0; r < N; r++){
            String[] tmp = br.readLine().split(" ");
            for(int c = 0; c < N; c++){
                map[r][c] = Integer.parseInt(tmp[c]);
            }
        }
    }
}
