package silver2.DFS와_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

class Main {
    static int N;
    static Node[] nodes;
    static boolean[] chk;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int start = init(br);
        sb = new StringBuilder();
        chk = new boolean[N + 1];
        dfs(start);

        sb.append("\n");

        chk = new boolean[N + 1];
        bfs(start);
        System.out.println(sb);
    }

    static void dfs(int now) {
        Node n = nodes[now];
        chk[now] = true;
        sb.append(now + " ");
        for (int next : n.next) {
            if (chk[next]) continue;
            dfs(next);
        }
    }

    static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        chk[start] = true;

        while (!q.isEmpty()) {
            int idx = q.poll();
            Node now = nodes[idx];
            sb.append(idx + " ");
            for (int next : now.next) {
                if (chk[next]) continue;
                chk[next] = true;
                q.add(next);
            }
        }
    }

    static int init(BufferedReader br) throws IOException {
        String[] line = br.readLine().split(" ");
        N = Integer.parseInt(line[0]);
        int M = Integer.parseInt(line[1]);
        int start = Integer.parseInt(line[2]);
        nodes = new Node[N + 1];
        for (int i = 1; i <= N; i++) {
            nodes[i] = new Node();
        }

        for (int m = 0; m < M; m++) {
            line = br.readLine().split(" ");
            int s = Integer.parseInt(line[0]);
            int e = Integer.parseInt(line[1]);

            nodes[s].next.add(e);
            nodes[e].next.add(s);
        }

        return start;

    }

    static class Node {
        TreeSet<Integer> next;

        public Node() {
            next = new TreeSet<>();
        }
    }
}
