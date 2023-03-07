package D3.식신;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Main2 {
    static int T;
    static int[] N;
    static long[] K, M;
    static Info[][] infos;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        init(br);
        simul(sb);
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }

    static void simul(StringBuilder sb) {
        for (int t = 1; t <= T; t++) {
            sb.append("#" + t + " " + parametricSearch(t - 1) + "\n");
        }
    }

    static long parametricSearch(int t) {
        long min = 0;
        long mid;
        long max = M[t];
        do {
            mid = (max + min) / 2;
            if (chkAvailable(mid, t)) max = mid;
            else min = mid + 1;
        } while (min != max);

        return min;
    }

    static boolean chkAvailable(long mid, int t) {
        long k = K[t];
        for (Info info : infos[t]) {
            if (info.calcTime() < mid) return true;
            long requireTrain = info.a - (mid / info.f);
            if (k < requireTrain) return false;
            k -= requireTrain;
        }

        return true;
    }

    static void init(BufferedReader br) throws IOException {
        T = Integer.parseInt(br.readLine());
        N = new int[T];
        K = new long[T];
        M = new long[T];
        infos = new Info[T][];
        for (int t = 0; t < T; t++) {
            String[] tmp = br.readLine().split(" ");
            N[t] = Integer.parseInt(tmp[0]);
            K[t] = Long.parseLong(tmp[1]);
            infos[t] = new Info[N[t]];

            String[] tmp1 = br.readLine().split(" ");
            String[] tmp2 = br.readLine().split(" ");

            PriorityQueue<Integer> pq1 = new PriorityQueue<>(Comparator.reverseOrder());
            PriorityQueue<Integer> pq2 = new PriorityQueue<>();
            for (int n = 0; n < N[t]; n++) {
                pq1.add(Integer.parseInt(tmp1[n]));
                pq2.add(Integer.parseInt(tmp2[n]));
            }

            for (int n = 0; n < N[t]; n++) {
                infos[t][n] = new Info(pq1.poll(), pq2.poll());
                M[t] = Math.max(M[t], infos[t][n].calcTime());
            }

            Arrays.sort(infos[t]);
        }
    }

    static class Info implements Comparable<Info> {
        long a, f;

        public Info(int a, int f) {
            this.a = a;
            this.f = f;
        }

        public long calcTime() {
            return this.a * this.f;
        }


        @Override
        public int compareTo(Info o) {
            return -Long.compare(this.a * this.f, o.a * o.f);
        }
    }
}
