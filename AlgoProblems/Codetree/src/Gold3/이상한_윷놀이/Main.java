package Gold3.이상한_윷놀이;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.Soundbank;

public class Main {
    static int N, K;
    static int[][] map;
    static int[] dr = { -1, 0, 1, 0 };
    static int[] dc = { 0, 1, 0, -1 };
    static HashMap<Integer, Node> vMap;
    static StringBuilder[][] kMap;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("Codetree/input/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(simul());
    }

    static void viewMap(){
        String[][] tmp = new String[N][N];
        for(Map.Entry<Integer, Node> e :vMap.entrySet()){
            if(tmp[e.getValue().r][e.getValue().c] == null) tmp[e.getValue().r][e.getValue().c] = "";
            tmp[e.getValue().r][e.getValue().c] += e.getKey();
        }
        System.out.println();
        for(int r = 0; r < N; r++){
            for(int c = 0; c < N; c++){
                System.out.print(tmp[r][c] + "\t");
            }
            System.out.println();
        }
    }

    static int simul() {
        int step;
        for (step = 1; step <= 1000; step++) {
            // viewMap();
            for (int k = 0; k < K; k++) {
                // k번의 위치 파악
                int r = vMap.get(k).r;
                int c = vMap.get(k).c;
                int d = vMap.get(k).d;

                // k번이 존재하는 블럭에서 k번을 기준으로 위아래 나누기
                int idx = kMap[r][c].indexOf("" + k);
                String down = kMap[r][c].substring(0, idx);
                StringBuilder up = new StringBuilder().append(kMap[r][c].substring(idx));

                // 아래는 기존 위치에 남기기
                kMap[r][c] = new StringBuilder().append(down);

                // 위는 이동시키기
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (chkOut(nr, nc) || map[nr][nc] == 2) { // 격자를 벗어나거나 파란색인 경우
                    d = (d + 2) % 4;
                    vMap.get(k).d = d;
                    nr = r + dr[d];
                    nc = c + dc[d];
                    if(chkOut(nr, nc) || map[nr][nc] == 2){
                        kMap[r][c].append(up);
                    }else if (map[nr][nc] == 0) {
                        kMap[nr][nc].append(up);
                        posUpdate(up.toString(), nr, nc);
                    }else if(map[nr][nc] == 1){
                        kMap[nr][nc].append(up.reverse().toString());
                        posUpdate(up.toString(), nr, nc);
                    }

                } else if (map[nr][nc] == 1) { // 빨간색인 경우
                    // System.out.println(k + ": " + up.toString());
                    kMap[nr][nc].append(up.reverse().toString());
                    posUpdate(up.toString(), nr, nc);
                } else { // 흰색인 경우
                    kMap[nr][nc].append(up);
                    posUpdate(up.toString(), nr, nc);
                }

                if(!chkOut(nr, nc) && kMap[nr][nc].toString().length() >= 4) {
                    // System.out.println("4칸 넘어감 : " + kMap[nr][nc]);
                    return step;
                }

            }
        }
        return -1;
    }

    static void posUpdate(String s, int r, int c) {
        for (char ch : s.toCharArray()) {
            vMap.get(ch - '0').r = r;
            vMap.get(ch - '0').c = c;
        }
    }

    static boolean chkOut(int r, int c) {
        return r < 0 || r >= N || c < 0 || c >= N ? true : false;
    }

    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        K = Integer.parseInt(tmp[1]);

        map = new int[N][N];
        kMap = new StringBuilder[N][N];
        vMap = new HashMap<>();
        for (int r = 0; r < N; r++) {
            tmp = br.readLine().split(" ");
            for (int c = 0; c < N; c++) {
                kMap[r][c] = new StringBuilder();
                map[r][c] = Integer.parseInt(tmp[c]);
            }
        }

        for (int k = 0; k < K; k++) {
            tmp = br.readLine().split(" ");
            int r = Integer.parseInt(tmp[0]) - 1;
            int c = Integer.parseInt(tmp[1]) - 1;
            int d = changeDir(Integer.parseInt(tmp[2]));

            kMap[r][c].append(k);
            vMap.put(k, new Node(d, r, c));
        }

    }

    static int changeDir(int d) {
        switch (d) {
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 0;
            case 4:
                return 2;
        }
        return -1;
    }

    static class Node {
        int d, r, c;

        public Node(int d, int r, int c) {
            this.d = d;
            this.r = r;
            this.c = c;
        }

    }

}