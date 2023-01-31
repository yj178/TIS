package 업무_처리;

import java.util.*;
import java.io.*;


public class 업무_처리 {
    static Node[][] nodes;
    static Queue<Integer>[] leafs;
    static int H, K, R;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tmp = br.readLine().split(" ");
        H = Integer.parseInt(tmp[0]);
        K = Integer.parseInt(tmp[1]);
        R = Integer.parseInt(tmp[2]);
        nodes = new Node[H][];
        int nodeCnt = 1;
        for (int h = 0; h < H; h++) {
            nodes[h] = new Node[nodeCnt];
            for (int i = 0; i < nodeCnt; i++) {
                nodes[h][i] = new Node();
            }
            nodeCnt *= 2;
        }

        leafs = new LinkedList[nodeCnt];
        for (int i = 0; i < nodeCnt; i++) {
            leafs[i] = new LinkedList<>();
            tmp = br.readLine().split(" ");
            for(String t : tmp){
                leafs[i].add(Integer.parseInt(t));
            }
        }

        int ans = 0;

        for (int r = 1; r <= R; r++) {
            nodeCnt = 1;
            for (int h = 0; h < H; h++) {
                if (h == 0) {
                    if (r % 2 == 1) ans += nodes[0][0].l.isEmpty() ? 0 : nodes[0][0].l.poll();
                    else ans += nodes[0][0].r.isEmpty() ? 0 : nodes[0][0].r.poll();

                } else {
                    for (int i = 0; i < nodeCnt; i++) {

                        if (r % 2 == 1) {
                            if (!nodes[h][i].l.isEmpty()) {
                                if (i % 2 == 0) nodes[h - 1][i / 2].l.add(nodes[h][i].l.poll());
                                else nodes[h - 1][i / 2].r.add(nodes[h][i].l.poll());
                            }
                        } else {
                            if (!nodes[h][i].r.isEmpty()) {
                                if (i % 2 == 0) nodes[h - 1][i / 2].l.add(nodes[h][i].r.poll());
                                else nodes[h - 1][i / 2].r.add(nodes[h][i].r.poll());
                            }
                        }
                    }
                }
                nodeCnt *= 2;
            }

            if (r <= K) {
                for (int i = 0; i < nodeCnt; i++) {
                    if (i % 2 == 0) nodes[H - 1][i / 2].l.add(leafs[i].poll());
                    else nodes[H - 1][i / 2].r.add(leafs[i].poll());
                }
            }
        }

        System.out.println(ans);

    }

    static class Node {
        Queue<Integer> r, l;

        public Node() {
            this.r = new LinkedList<>();
            this.l = new LinkedList<>();
        }
    }
}