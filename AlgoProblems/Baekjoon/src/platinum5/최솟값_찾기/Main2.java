package platinum5.최솟값_찾기;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Main2 {
    static Deque<Num> window;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        simul(br, bw);
        bw.close();
    }

    static void simul(BufferedReader br, BufferedWriter bw) throws IOException {
        String[] tmp = br.readLine().split(" ");
        int N = Integer.parseInt(tmp[0]);
        int L = Integer.parseInt(tmp[1]);

        window = new ArrayDeque<>();

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int n = 0; n < N; n++) {
            Num now = new Num(Integer.parseInt(st.nextToken()), n);
            while (!window.isEmpty() && window.getLast().n > now.n) window.pollLast();
            window.addLast(now);
            if (window.getFirst().i < n - L + 1) window.pollFirst();
            bw.write(window.getFirst().n + " ");
        }

    }

    static class Num {
        int n, i;

        public Num(int n, int i) {
            this.n = n;
            this.i = i;
        }
    }
}