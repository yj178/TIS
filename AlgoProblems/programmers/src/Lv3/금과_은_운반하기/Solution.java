package Lv3.금과_은_운반하기;

class Solution {
    public static void main(String[] args) {
        Solution problem = new Solution();
        int a = 10;
        int b = 10;
        int[] g = new int[]{100};
        int[] s = new int[]{100};
        int[] w = new int[]{7};
        int[] t = new int[]{10};

        System.out.println(problem.solution(a, b, g, s, w, t));
    }

    static Info[] infos;
    static int len, A, B;
    static final long TIME_MAX = 100000000000000L;

    public long solution(int a, int b, int[] g, int[] s, int[] w, int[] t) {
        len = g.length;
        A = a;
        B = b;
        infos = new Info[len];
        for (int i = 0; i < len; i++) {
            infos[i] = new Info(t[i], g[i], s[i], w[i]);
        }

        return parameterSearch(0, TIME_MAX);
    }

    static long parameterSearch(long s, long e) {
        if (s == e) return e;
        long m = (s + e) >>> 1;
        if (chk(m)) return parameterSearch(s, m);
        else return parameterSearch(m + 1, e);
    }

    static boolean chk(long m) {
        long g = 0L;
        long s = 0L;
        long sum = 0L;
        for (int i = 0; i < len; i++) {
            infos[i].pnot = (m - infos[i].t) / (infos[i].t * 2) + 1;
            infos[i].aw = infos[i].pnot * infos[i].w;
            g += Math.min(infos[i].g, infos[i].aw);
            s += Math.min(infos[i].s, infos[i].aw);
            sum += Math.min(infos[i].g + infos[i].s, infos[i].aw);
        }

        return A <= g && B <= s && A + B <= sum ? true : false;
    }

    static class Info {
        int t, g, s, w;
        long pnot, aw;

        public Info(int t, int g, int s, int w) {
            this.t = t;
            this.g = g;
            this.s = s;
            this.w = w;
        }
    }
}
