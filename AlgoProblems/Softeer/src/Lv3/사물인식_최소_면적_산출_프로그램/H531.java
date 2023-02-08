package Lv3.사물인식_최소_면적_산출_프로그램;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class H531 {
    static int N, K, ans;
    static HashMap<Integer, ArrayList<Pos>> map;
    public static void main(String args[])throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        dfs(0, 1000, -1000, -1000, 1000);
        System.out.println(ans);
    }

    static void dfs(int dep, int x1, int y1, int x2, int y2){
        if(dep == K){
            ans = Math.min(Math.abs(x1 - x2) * Math.abs(y1 - y2), ans);
            return;
        }

        for(Pos p : map.get(dep)){
            if(Math.abs(Math.min(p.x, x1) - Math.max(x2, p.x)) * Math.abs(Math.max(p.y, y1) -Math.min(y2, p.y)) >= ans) continue;
            dfs(dep + 1, Math.min(p.x, x1), Math.max(p.y, y1), Math.max(x2, p.x), Math.min(y2, p.y));
        }


    }

    static public void init(BufferedReader br ) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        K = Integer.parseInt(tmp[1]);
        map = new HashMap<>();
        for(int n = 0; n < N; n++){
            tmp = br.readLine().split(" ");
            int x = Integer.parseInt(tmp[0]);
            int y = Integer.parseInt(tmp[1]);
            int k = Integer.parseInt(tmp[2])-1;
            if(!map.containsKey(k)) map.put(k, new ArrayList<>());
            map.get(k).add(new Pos(x, y));
        }
        ans = Integer.MAX_VALUE;
    }


    static public class Pos{
        int x, y;
        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
