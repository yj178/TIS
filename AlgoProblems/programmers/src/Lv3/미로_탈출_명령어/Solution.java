package Lv3.미로_탈출_명령어;

class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
//        System.out.println(problem.solution(3, 4, 2, 3, 3, 1, 5));
        System.out.println(problem.solution(2, 2, 1, 1, 2, 2, 2));
    }

    static int N, M, R, C, K;

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        N = n;
        M = m;
        R = r;
        C = c;
        K = k;
        if ((k - calcDis(x, y) < 0) || (k - calcDis(x, y)) % 2 != 0) return "impossible";

        return makeCommand(x, y);
    }

    static String makeCommand(int x, int y) {
        String ans = "";
        int cnt = 0;
        while ((calcDis(x, y) == (K - cnt) && x < R) || (calcDis(x, y) < (K - cnt) && x != N)) {
            ans += "d";
            x++;
            cnt++;
        }

        while ((calcDis(x, y) == (K - cnt) && y > C) || (calcDis(x, y) < (K - cnt) && y != 1)) {
            ans += "l";
            y--;
            cnt++;
        }


        while (calcDis(x, y) < (K - cnt)) {
            ans += "rl";
            cnt += 2;
        }

        while (y != C) {
            ans += "r";
            y++;
        }
        while ( x != R) {
            ans += "u";
            x--;
        }


        return ans;
    }

    static public int calcDis(int x, int y) {
        return Math.abs(x - R) + Math.abs(y - C);
    }

}
