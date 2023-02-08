package Lv3.거리합_구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// 서브트리 사이즈란 본인 노드를 포함하여 하위 노드의 개수의 합을 의미한다.
// 루트 노드의 경우 서브트리 사이즈는 전체의 개수와 같다.
// 리프 노드의 경우 서브트리 사이즈는 1이다.
// 해당 문제에서 서브트리 개수를 파악해야 효율적으로 계산이 가능하다.
//
class H635 {

    static int N;
    static Node[] nodes;
    static int[] subtreeSize;
    static long[] distSum;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        StringBuilder sb = new StringBuilder();

        calcInit(1, 1);
        calcAllDistSum(1, 1);
        for (int i = 1; i <= N; i++) {
            sb.append(distSum[i] + "\n");
        }
        System.out.println(sb);
    }

    static void calcInit(int idx, int parent) {
        subtreeSize[idx] = 1;
        for (Edge e : nodes[idx].next) {
            if (e.d == parent) continue;
            calcInit(e.d, idx);
            distSum[idx] += distSum[e.d] + subtreeSize[e.d] * e.t;
            subtreeSize[idx] += subtreeSize[e.d];
        }
    }

    static void calcAllDistSum(int idx, int parent) {
        for (Edge e : nodes[idx].next) {
            if (e.d == parent) continue;
            distSum[e.d] = distSum[idx] + (long) (N - 2 * subtreeSize[e.d]) * e.t;
            calcAllDistSum(e.d, idx);
        }

    }

    static void init(BufferedReader br) throws IOException {
        N = Integer.parseInt(br.readLine());
        nodes = new Node[N + 1];
        for (int i = 0; i <= N; i++) {
            nodes[i] = new Node();
        }
        for (int i = 0; i < N - 1; i++) {
            String[] line = br.readLine().split(" ");
            int s = Integer.parseInt(line[0]);
            int d = Integer.parseInt(line[1]);
            int t = Integer.parseInt(line[2]);
            // System.out.println(s+", "+d+", "+t);
            nodes[s].next.add(new Edge(d, t));
            nodes[d].next.add(new Edge(s, t));
        }
        subtreeSize = new int[N + 1];
        distSum = new long[N + 1];
    }

    static class Node {
        ArrayList<Edge> next;

        public Node() {
            next = new ArrayList<>();
        }
    }

    static class Edge {
        int d, t;

        public Edge(int d, int t) {
            this.d = d;
            this.t = t;
        }
    }
}
