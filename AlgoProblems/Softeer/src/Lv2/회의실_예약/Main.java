package Lv2.회의실_예약;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;

class Main {
    static int N, M;
    static String[] names;
    static HashMap<String, Integer> map;
    static PriorityQueue<Info>[] table;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        printTable();

    }

    static void printTable() {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < N; n++) {
            sb.append("Room " + names[n] + ":\n");
            StringBuilder sb2 = new StringBuilder();
            int cnt = 0;
            int time = 9;
            while (!table[n].isEmpty()) {
                Info tmp = table[n].poll();
                if (time - tmp.start == 0) time = tmp.end;
                else {
                    sb2.append(String.format("%02d-%02d\n", time, tmp.start));
                    time = tmp.end;
                    cnt++;
                }
            }

            if (time != 18) {
                sb2.append(String.format("%02d-%02d\n", time, 18));
                cnt++;
            }
            ;

            if (cnt == 0) sb.append("Not available\n");
            else sb.append(cnt + " available:\n" + sb2);
            if (n != N - 1) sb.append("-----\n");
        }
        System.out.println(sb);
    }


    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        map = new HashMap<>();
        names = new String[N];
        PriorityQueue<String> pq = new PriorityQueue<>();
        for (int n = 0; n < N; n++) {
            pq.add(br.readLine());
        }
        for (int n = 0; n < N; n++) {
            String s = pq.poll();
            names[n] = s;
            map.put(s, n);
        }
        table = new PriorityQueue[N];
        for (int n = 0; n < N; n++) {
            table[n] = new PriorityQueue<>();
        }

        for (int m = 0; m < M; m++) {
            tmp = br.readLine().split(" ");
            table[map.get(tmp[0])].add(new Info(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2])));
        }
    }

    static class Info implements Comparable<Info> {
        int start, end;

        public Info(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Info o) {
            return Integer.compare(this.start, o.start);
        }
    }
}
