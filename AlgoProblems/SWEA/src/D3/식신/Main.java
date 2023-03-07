package D3.식신;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Main {
    static int N;
    static long K;
    static Info[] infos;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        simul(br, sb);
        System.out.println(sb);
    }

    static void simul(BufferedReader br, StringBuilder sb) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            long max = init(br);
            sb.append("#" + t + " " + parametricSearch(max) + "\n");
        }
    }

    static long parametricSearch(long max) {
        long min = 0;
        long mid;

        do {
            mid = (max + min) / 2;
            if (chkAvailable(mid, K)) max = mid;
            else min = mid + 1;
        } while (min != max);

        return min;
    }

    static boolean chkAvailable(long mid, long k) {
        for (Info info : infos) {
            if (info.calcTime() < mid) return true;
            long requireTrain = info.a - (mid / info.f);
            if (k < requireTrain) return false;
            k -= requireTrain;
        }

        return true;
    }

    static long init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        K = Long.parseLong(tmp[1]);
        infos = new Info[N];

        String[] tmp1 = br.readLine().split(" ");
        String[] tmp2 = br.readLine().split(" ");
        long max = 0;

        PriorityQueue<Integer> pq1 = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> pq2 = new PriorityQueue<>();
        for (int n = 0; n < N; n++) {
            pq1.add(Integer.parseInt(tmp1[n]));
            pq2.add(Integer.parseInt(tmp2[n]));
        }

        for (int n = 0; n < N; n++) {
            infos[n] = new Info(pq1.poll(), pq2.poll());
            max = Math.max(max, infos[n].calcTime());
        }

        Arrays.sort(infos);

        return max;
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
