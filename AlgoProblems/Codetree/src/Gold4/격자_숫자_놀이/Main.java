package Gold4.격자_숫자_놀이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {
    static int N, M, K, R, C;
    static boolean dir;
    static int[][] map;
    static HashMap<Integer, Integer>[] cnt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        System.out.println(simul());
    }

    static void viewMap(){
        System.out.println();
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                System.out.print(map[r][c] + " ");
            }
            System.out.println();
        }
    }

    static int simul(){
        if(chkOut(3) && map[R][C] == K ) return 0;
        for(int i = 1; i <= 100; i++){
            // viewMap();
            if(N >= M) row();
            else col();

            if(makeMap()) return i;
        }
        return -1;
    }
    static boolean chkOut(int max){
        return max <= R || max <= C ? false : true;
    }
    static boolean makeMap(){
        int max = N > M ? N : M;
        map = new int[max][max];
        if(dir){
            for(int r = 0; r < N; r++){
                PriorityQueue<Info> pq = new  PriorityQueue<>();
                for(Map.Entry<Integer, Integer> e : cnt[r].entrySet()){
                    pq.add(new Info(e.getKey(), e.getValue()));
                }
                for(int c = 0; c < M; c+=2){
                    if(pq.isEmpty()) break;
                    Info info = pq.poll();
                    map[r][c] = info.val;
                    map[r][c + 1] = info.cnt;
                }
                if(chkOut(max) && map[R][C]== K) return true;
            }
        }else{
            for(int c = 0; c < M; c++){
                PriorityQueue<Info> pq = new  PriorityQueue<>();
                for(Map.Entry<Integer, Integer> e : cnt[c].entrySet()){
                    pq.add(new Info(e.getKey(), e.getValue()));
                }
                for(int r = 0; r < N; r+=2){
                    if(pq.isEmpty()) break;
                    Info info = pq.poll();
                    map[r][c] = info.val;
                    map[r + 1][c] = info.cnt;
                }
                if(chkOut(max) && map[R][C]== K) return true;
            }
        }
        return false;
    }
    static void col(){
        dir = false;
        cnt = new HashMap[M];
        int maxSize = 0;
        for(int c = 0; c < M; c++){
            cnt[c] = new HashMap<>();
            for(int r = 0; r < N; r++){
                if(map[r][c] == 0) continue;
                cnt[c].put(map[r][c], cnt[c].getOrDefault(map[r][c], 0) + 1);
            }
            maxSize = Math.max(cnt[c].size() * 2, maxSize);
        }
        N = maxSize < 100 ? maxSize : 100;
    }
    static void row(){
        dir = true;
        cnt = new HashMap[N];
        int maxSize = 0;
        for(int r = 0; r < N; r++){
            cnt[r] = new HashMap<>();
            for(int c = 0; c < M; c++){
                if(map[r][c] == 0) continue;
                cnt[r].put(map[r][c], cnt[r].getOrDefault(map[r][c], 0) + 1);
            }
            maxSize = Math.max(cnt[r].size() * 2, maxSize);
        }
        M = maxSize < 100 ? maxSize : 100;
    }


    static void init(BufferedReader br) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = 3;
        M = 3;
        R = Integer.parseInt(tmp[0]) - 1;
        C = Integer.parseInt(tmp[1]) - 1;
        K = Integer.parseInt(tmp[2]);
        map = new int[N][M];
        for (int r = 0; r < 3; r++) {
            tmp = br.readLine().split(" ");
            for (int c = 0; c < 3; c++) {
                map[r][c] = Integer.parseInt(tmp[c]);
            }
        }
    }

    static class Info implements Comparable<Info>{
        int val, cnt;

        public Info(int val, int cnt) {
            this.val = val;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Info o) {
            return this.cnt == o.cnt ? this.val - o.val : this.cnt - o.cnt;
        }

    }
}
