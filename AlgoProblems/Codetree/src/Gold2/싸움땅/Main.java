package Gold2.싸움땅;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

class Main {
    static int N, M, K;
    static Person[] persons;
    static PriorityQueue<Integer>[][] gun;
    static HashSet<Integer>[][] pos;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        simul();
        System.out.println(printAns());
    }

    

    static void simul(){
        for(int k = 1; k <= K; k++){
            for(int m = 0; m < M; m++){
                // System.out.println(k +", " + m);
                if(move(m)) fight(m);
                else selectGun(m);
            }
            // System.out.println(k+" : " +printAns());
        }
    }

    static void fight(int m){
        int r = persons[m].r;
        int c = persons[m].c;
        
        int tmp = -1;
        for(int idx : pos[r][c]){
            if(idx != m) tmp = idx;
        }
        int b = tmp;
        
        Person A = persons[m];
        Person B = persons[b];
        // System.out.println("fight : " + m +", " + b);
        // System.out.println("ap : " + A.calcAP() +", " + B.calcAP());
        if(A.compareTo(B) < 1){
            A.score += A.calcAP() - B.calcAP();
            if(B.g != 0){
                gun[r][c].add(B.g);
                B.g = 0;
            }
            selectGun(m);
            moveLoser(b);
        }else{
            B.score += B.calcAP() - A.calcAP();
            if(A.g != 0){
                gun[r][c].add(A.g);
                A.g = 0;
            }
            selectGun(b);
            moveLoser(m);
        }
    }

    static void moveLoser(int m){
        int r = persons[m].r;
        int c = persons[m].c;
        int d = persons[m].d;

        // pos에서 현 위치 제거
        pos[r][c].remove(m);

        for(int dd = 0; dd < 4; dd++){
            int nd = (d + dd) % 4;
            int nr = r + dr[nd];
            int nc = c + dc[nd];
            if(chkOut(nr, nc) || !pos[nr][nc].isEmpty()) continue;
            persons[m].d = nd;
            persons[m].c = nc;
            persons[m].r = nr;
            break;
        }
        // System.out.println(m+"loser move :" + "("+r+","+c+") -->" + "("+persons[m].r+","+persons[m].c+")");
        pos[persons[m].r][persons[m].c].add(m);
        selectGun(m);

    }

    static void selectGun(int m){
        int r = persons[m].r;
        int c = persons[m].c;
        int g = persons[m].g;
        if(!gun[r][c].isEmpty() && g < gun[r][c].peek()){
            if(g != 0) gun[r][c].add(g);
            persons[m].g = gun[r][c].poll();
        }
    }

    static boolean move(int m){
        int r = persons[m].r;
        int c = persons[m].c;
        // System.out.print("move : " + m + "("+r+","+ c+")");
        
        // pos에서 현 위치 제거
        pos[r][c].remove(m);

        // 이동한 좌표
        int nr = r + dr[persons[m].d];
        int nc = c + dc[persons[m].d];
        // 이동시 격자를 벗어난다면 반대방향으로 전환 후 한칸 이동
        if(chkOut(nr, nc)) {
            persons[m].d = (persons[m].d + 2) % 4;
            nr = r + dr[persons[m].d];
            nc = c + dc[persons[m].d];
        }

        // 위치 갱신
        persons[m].r = nr;
        persons[m].c = nc;

        pos[nr][nc].add(m);

        // System.out.println("->" +"("+nr+","+nc+")");

        if(pos[nr][nc].size() == 1) return false; // 1명만 존재하면 총 선택
        else return true; // 2명이 존재하면 전투

    }

    static boolean chkOut(int r, int c){
        return r < 0 || r >= N || c < 0 || c >= N ? true : false;
    }

    static String printAns(){
        String ans = "";
        for(int m = 0; m < M;m++) {
            ans += persons[m].score + " ";
        }
        return ans.substring(0,ans.length());
    }

    static void init(BufferedReader br) throws IOException{
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        K = Integer.parseInt(tmp[2]);
        gun = new PriorityQueue[N][N];
        pos = new HashSet[N][N];
        persons = new Person[M];
        for(int r = 0; r < N; r++){
            tmp = br.readLine().split(" ");
            for(int c = 0; c < N; c++){
                int ap = Integer.parseInt(tmp[c]);
                gun[r][c]= new PriorityQueue<>(Collections.reverseOrder());
                if(ap > 0) gun[r][c].add(ap);
                pos[r][c] = new HashSet<>();
            }
        }

        for(int m = 0; m < M; m++){
            tmp = br.readLine().split(" ");
            int r = Integer.parseInt(tmp[0])-1;
            int c = Integer.parseInt(tmp[1])-1;
            int d = Integer.parseInt(tmp[2]);
            int s = Integer.parseInt(tmp[3]);
            persons[m] = new Person(r, c, d, s);
            pos[r][c].add(m);
        }
    }
    

    static class Person implements Comparable<Person> {
        int r, c, d, s, g, score;

        public Person(int r, int c, int d, int s) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.s = s;
        }

        public int calcAP(){
            return this.s + this.g;
        }

        @Override
        public int compareTo(Person o) {
            return this.calcAP() != o.calcAP() ? Integer.compare(o.calcAP(), this.calcAP()) : Integer.compare(o.s, this.s);
        }
    }
}
