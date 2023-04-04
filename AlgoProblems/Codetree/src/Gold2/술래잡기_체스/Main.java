package Gold2.술래잡기_체스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 시작 시각 : 06:07
 * 종료 시각 : 07:41
 * 소요 시간 : 94분
 */
class Main {
    // 이동방향 상하좌우 대각선 8방
    static int[] dr = { -1, -1, 0, 1, 1, 1, 0, -1 };
    static int[] dc = { 0, -1, -1, -1, 0, 1, 1, 1 };

    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 4x4 체스판에서 술래잡시 실행
        simul(br);

        System.out.println(ans);
    }

    static boolean chkOut(int r, int c) {
        return r < 0 || r >= 4 || c < 0 || c >= 4 ? true : false;
    }

    static int setDir(int d) {
        return d > 7 ? d - 8 : d;
    }

    // 최대 16dep으로 종료됨, 복사될 map크기 4*4
    static void dfs(int dep, int[][] map, int[] rr, int[] cc, int[] dd, int zr, int zc, int zd, int score) {
        ans = Math.max(ans, score);

        if (dep >= 16)
            return;

        int[][] newm = new int[4][4];
        int[] newr = new int[17];
        int[] newc = new int[17];
        int[] newd = new int[17];
        // System.out.println();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                newm[r][c] = map[r][c];
                // if(dep == 1){
                // System.out.printf("%2d ",newm[r][c]);
                // }
            }
            // System.out.println();
        }

        for (int i = 1; i < 17; i++) {
            newr[i] = rr[i];
            newc[i] = cc[i];
            newd[i] = dd[i];
        }

        // 도둑말의 이동
        // 도둑말은 번호 순서대로 본인이 가지고 있는 방향대로 이동
        for (int i = 1; i < 17; i++) {

            // 도둑말은 이동할 수 있을때까지 45도 반시계 회전하며 갈 수 있는 칸을 탐색
            // 이동 불가 시 이동하지 않음
            for (int d = 0; d < 8; d++) {
                // 한번에 한칸 이동
                if (newd[i] == -1)
                    continue;
                int nd = setDir(newd[i] + d);
                int nr = newr[i] + dr[nd];
                int nc = newc[i] + dc[nd];
                // 술래말이나 격자를 벗어난 공간은 이동 불가
                if (chkOut(nr, nc) || (nr == zr && nc == zc))
                    continue;

                // 빈 칸이나 다른 도둑말이 있는 칸은 이동할 수 있는 칸
                int target = newm[nr][nc];
                if (target == 0) {// 이동이 가능한 경우 이동
                    newm[newr[i]][newc[i]] = 0;
                    newm[nr][nc] = i;
                    newr[i] = nr;
                    newc[i] = nc;
                    newd[i] = nd;
                } else {// 단 해당 칸에 다른 도둑말이 존재한다면 이동할 칸에 있는 도둑말과 위치를 변경함

                    // 다른 도둑말 위치 정보 갱신
                    newm[newr[i]][newc[i]] = target;
                    newr[target] = newr[i];
                    newc[target] = newc[i];

                    // 본인 위치 갱신
                    newm[nr][nc] = i;
                    newr[i] = nr;
                    newc[i] = nc;
                    newd[i] = nd;
                }
                break;
            }

        }

        // 술래의 이동
        // 한번에 여러개의 칸도 이동 가능
        for (int dis = 1; dis < 4; dis++) {
            // 이동가능한 방향의 어느칸이나 이동 가능
            // 술래말이 잡고자하는 도둑말로 이동할 때 지나는 칸들의 말들은 잡지 않음
            // System.out.println(dep +", " + zd);
            // System.out.println(Arrays.toString(newd));
            int nr = zr + dr[zd] * dis;
            int nc = zc + dc[zd] * dis;

            if (chkOut(nr, nc))
                break;
            // 도둑말이 없는 칸으로 이동할 수 없음
            if (newm[nr][nc] == 0)
                continue;

            int target = newm[nr][nc];
            newm[nr][nc] = 0;
            int nextd = newd[target];
            newd[target] = -1;

            // 술래는 잡은 도둑말의 방향을 갖는다

            // System.out.println(dep +", dis " + dis + " : score = " + (score+target));
            dfs(dep + 1, newm, newr, newc, newd, nr, nc, nextd, score + target);
            newm[nr][nc] = target;
            newd[target] = nextd;
        }
        // 술래말이 다른 도둑말을 잡은 뒤에는 다시 도둑말이 번호 순서대로 움직임

        // 종료 조건
        // 술래가 이동할 수 있는 곳?에 도둑말이 더이상 존재하지 않으면 게임을 종료함
    }

    static void simul(BufferedReader br) throws IOException {
        // 도둑들의 초기 상태 저장
        int[][] m = new int[4][4];
        int[] pr = new int[17];
        int[] pc = new int[17];
        int[] dir = new int[17]; // -1이면 아웃
        for (int r = 0; r < 4; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 0; c < 4; c++) {
                int idx = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken()) - 1;
                m[r][c] = idx;
                pr[idx] = r;
                pc[idx] = c;
                dir[idx] = d;

            }
        }

        // 술래말 하나만 사용하여 도둑말 잡음
        // 술래말을 잡을 때마다 도둑말의 방향을 갖게 됨
        // 초기에는 0,0 도둑말을 잡으면서 시작함
        int idx = m[0][0];
        int d = dir[idx];
        dir[idx] = -1;
        m[0][0] = 0;

        // 시뮬레이션 수행
        dfs(0, m, pr, pc, dir, 0, 0, d, idx);
    }

}