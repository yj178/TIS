package Gold3.이상한_다트_게임;

import java.io.*;

class Main {
    static int N, M, Q, sum, cnt;
    static int[][] map;
    static int[][] rotateInfo;
    static int[] dr = { 0, 0, -1, 1 };
    static int[] dc = { 1, -1, 0, 0 };

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("C:/git/TIS/AlgoProblems/Codetree/input/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(simul());
    }

    static int simul() {

        for (int q = 0; q < Q; q++) {
            rotate(q);
            sum = 0;
            cnt = 0;
            // viewMap();
            if (deleteScore())
                normalization();
            // viewMap();
        }

        return calcScore();
    }

    static void rotate(int q) {
        int x = rotateInfo[q][0]; // 회전 대상
        int d = rotateInfo[q][1]; // 회전 방향
        int k = rotateInfo[q][2]; // 회전 칸 수
        for(int xx = x; xx <= N; xx+=x){
            int[] newMap = new int[M]; // 회전 결과 저장
            for (int c = 0; c < M; c++)
                newMap[(c + k * d + M) % M] = map[xx][c];
            map[xx] = newMap;
        }
    }

    static void normalization() {
        // System.out.println("정규화 실행");
        if (cnt == 0)
            return;
        int avg = sum / cnt;

        for (int r = 1; r <= N; r++) {
            for (int c = 0; c < M; c++) {
                if (map[r][c] == 0)
                    continue;
                if (map[r][c] > avg)
                    map[r][c] -= 1;
                else if (map[r][c] < avg)
                    map[r][c] += 1;
            }
        }
    }

    static boolean deleteScore() {
        boolean flag = true;
        boolean[][] chk = new boolean[N+1][M];
        for (int r = 1; r <= N; r++) {
            for (int c = 0; c < M; c++) {
                if (map[r][c] == 0 || chk[r][c])
                    continue;
                int now = map[r][c];
                // System.out.println("now : " + r +", " + c);
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (chkOutR(nr))
                        continue;
                    if (chkOutC(nc))
                        nc = nc < 0 ? nc + M : nc - M;
                    // System.out.println("d : " + nr +", " + nc);
                    if (map[nr][nc] == now) {
                        chk[r][c] = true;
                        chk[nr][nc] = true;
                    }
                }
            }
        }
        for (int r = 1; r <= N; r++) {
            for (int c = 0; c < M; c++) {
                if (chk[r][c]) {
                    flag = false;
                    map[r][c] = 0;
                }
                if (map[r][c] != 0) {
                    sum += map[r][c];
                    cnt++;
                }
            }
        }

        return flag;
    }

    static boolean chkOutR(int r) {
        return r <= 0 || r > N ? true : false;
    }

    static boolean chkOutC(int c) {
        return c < 0 || c >= M ? true : false;
    }

    static void viewMap() {
        System.out.println();
        System.out.println("sum : " + sum);
        System.out.println("cnt : " + cnt);
        if (cnt != 0)
            System.out.println("avg : " + sum / cnt);
        for (int r = 1; r <= N; r++) {
            for (int c = 0; c < M; c++) {
                System.out.print(map[r][c] + " ");
            }
            System.out.println();
        }
    }

    static int calcScore() {
        int score = 0;
        for (int r = 1; r <= N; r++) {
            for (int c = 0; c < M; c++) {
                score += map[r][c];
            }
        }
        return score;
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        Q = Integer.parseInt(tmp[2]);
        map = new int[N+1][M];
        for (int r = 1; r <= N; r++) {
            tmp = br.readLine().split(" ");
            for (int c = 0; c < M; c++) {
                map[r][c] = Integer.parseInt(tmp[c]);
            }
        }
        rotateInfo = new int[Q][3];
        for (int q = 0; q < Q; q++) {
            tmp = br.readLine().split(" ");
            rotateInfo[q][0] = Integer.parseInt(tmp[0]);
            rotateInfo[q][1] = Integer.parseInt(tmp[1]) == 0 ? 1 : -1;
            rotateInfo[q][2] = Integer.parseInt(tmp[2]);
        }
    }
}