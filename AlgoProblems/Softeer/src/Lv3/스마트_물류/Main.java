package Lv3.스마트_물류;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

class Main {
    static PriorityQueue<Integer> robots, parts;
    static int N, K;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] info = br.readLine().split(" ");
        N = Integer.parseInt(info[0]);
        K = Integer.parseInt(info[1]);

        robots = new PriorityQueue<>();
        parts = new PriorityQueue<>();

        char[] arr = br.readLine().toCharArray();
        int idx = 0;
        for (char c : arr) {
            if (c == 'H') parts.add(idx);
            else robots.add(idx);
            idx++;
        }

        int cnt = 0;
        while (!robots.isEmpty() && !parts.isEmpty()) {
            if (Math.abs(robots.peek() - parts.peek()) <= K) {
                cnt++;
                robots.poll();
                parts.poll();
            } else if (robots.peek() > parts.peek()) {
                parts.poll();
            } else {
                robots.poll();
            }

        }

        System.out.println(cnt);
    }
}
