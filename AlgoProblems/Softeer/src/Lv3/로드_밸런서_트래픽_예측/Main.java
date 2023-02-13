package Lv3.로드_밸런서_트래픽_예측;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int N;
    static long K;
    static Node[] nodes;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("input/load_balance_input"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        topologicalSort();
        calcRequest();
    }

    static void calcRequest() {
        long[] requestCnt = new long[N + 1];
        requestCnt[1] = K;
        for (int i = 1; i <= N; i++) {
            if (nodes[i].outdegrees == null) continue;
            long quotient = requestCnt[nodes[i].idx] / nodes[i].outdegrees.length;
            long remainder = requestCnt[nodes[i].idx] % nodes[i].outdegrees.length;

            for (int next : nodes[i].outdegrees) {
                requestCnt[next] += quotient;
            }

            for (int j = 0; j < remainder; j++) {
                requestCnt[nodes[i].outdegrees[j]]++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < N; i++) {
            sb.append(requestCnt[i] + " ");
        }
        sb.append(requestCnt[N]);
        System.out.println(sb);

    }

    static void topologicalSort() {
        // indegree update
        for (int i = 1; i <= N; i++) {
            if (nodes[i].outdegrees == null) continue;
            for (int outdegree : nodes[i].outdegrees) {
                nodes[outdegree].indegreeCnt++;
            }
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(nodes[1]);
        Node[] newNodes = new Node[N + 1];
        int idx = 1;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.outdegrees != null) {
                for (int outdegree : node.outdegrees) {
                    if(nodes[outdegree] == null) continue;
                    if (--nodes[outdegree].indegreeCnt == 0) queue.offer(nodes[outdegree]);
                }
            }
            newNodes[idx++] = node;
        }

        nodes = newNodes;
//        for(Node node : nodes){
//            System.out.println(node);
//        }
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        K = Long.parseLong(tmp[1]);

        nodes = new Node[N + 1];
        for (int i = 1; i <= N; i++) {
            nodes[i] = new Node(i, br.readLine());
        }
    }


    static class Node {
        int idx;
        int[] outdegrees;
        int indegreeCnt;

        public Node(int idx, String lines) {
            this.idx = idx;
            String[] tmp = lines.split(" ");
            int cnt = Integer.parseInt(tmp[0]);
            if (cnt != 0) {
                this.outdegrees = new int[cnt];
                for (int i = 1; i <= cnt; i++) {
                    outdegrees[i - 1] = Integer.parseInt(tmp[i]);
                }
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "idx=" + idx +
                    ", next=" + Arrays.toString(outdegrees) +
                    ", indegreeCnt=" + indegreeCnt +
                    '}';
        }
    }
}
