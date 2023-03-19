package Gold2.윷놀이_사기단;

import java.io.*;

public class Main {
    static Node[] nodes;
    static int[] moves, pos;
    static boolean[] chk;
    static int ans;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        dfs(0, 0);
        System.out.println(ans);
    }

    static void dfs(int dep, int score){
        if(dep == 10){
            ans = Math.max(score, ans);
            return;
        }

        for(int i = 0; i < 4; i++){
            if(chk[i]) continue;
            
            int now = pos[i];
            int next = pos[i];

            for(int m = 0; m < moves[dep]; m++){
                if(next >= 32) break; // 만약 다음위치가 마지막 위치 32을 넘어가는 경우 이동 중단
                if(m == 0 && nodes[now].n2 != -1){ // 파란색 칸에서 시작한 경우
                    next = nodes[next].n2; // 빨간색 이동 경로 이용
                    continue;
                } 
                next = nodes[next].n1; // 검은색 이동 경로 이용
            }

            if(overPos(i, next)) continue; // 칸은 칸에 말이 있다면 이동할 수 없으므로 생략
            if(next >= 32) { // 다음 위치가 32을 넘는 경우
                chk[i] = true; // 도착했다고 알림
                pos[i] = 32; // 위치 갱신
                dfs(dep + 1, score); // 다음 동작
                pos[i] = now; // 현재 위치로 복원
                chk[i] = false; // 도착 취소
            } else{ // 다음 위치로 이동하는 경우
                pos[i] = next; // 다음 위치 갱신
                dfs(dep + 1, score + nodes[next].s); // 다음 동작 + 점수 갱신
                pos[i] = now; // 위치 복원
            }
        }
    }

    static boolean overPos(int i, int next){ // 같은 칸에 말이 있는지 확인
        for(int idx= 0; idx < 4; idx++){
            if(i == idx) continue; // 자기 자신 제외
            if(next < 32 && pos[idx] == next) return true; // 도착하기 전에 같은 위치가 있다면
        }
        return false;
    }

    static void init(BufferedReader br) throws IOException{
        nodes = new Node[33];
        nodes[0] = new Node(1, 0);
        nodes[1] = new Node(2, 2);
        nodes[2] = new Node(3, 4);
        nodes[3] = new Node(4, 6);
        nodes[4] = new Node(5, 8);
        nodes[5] = new Node(6, 21, 10);
        nodes[6] = new Node(7, 12);
        nodes[7] = new Node(8, 14);
        nodes[8] = new Node(9, 16);
        nodes[9] = new Node(10, 18);
        nodes[10] = new Node(11, 27,  20);
        nodes[11] = new Node(12, 22);
        nodes[12] = new Node(13, 24);
        nodes[13] = new Node(14, 26);
        nodes[14] = new Node(15, 28);
        nodes[15] = new Node(16,29,  30);
        nodes[16] = new Node(17, 32);
        nodes[17] = new Node(18, 34);
        nodes[18] = new Node(19, 36);
        nodes[19] = new Node(20, 38);
        nodes[20] = new Node(32, 40);
        nodes[21] = new Node(22, 13);
        nodes[22] = new Node(23, 16);
        nodes[23] = new Node(24, 19);
        nodes[24] = new Node(25, 25);
        nodes[25] = new Node(26, 30);
        nodes[26] = new Node(20, 35);
        nodes[27] = new Node(28, 22);
        nodes[28] = new Node(24, 24);
        nodes[29] = new Node(30, 28);
        nodes[30] = new Node(31, 27);
        nodes[31] = new Node(24, 26);
        nodes[32] = new Node(1000,0);

        pos = new int[4];

        moves = new int[10];
        chk = new boolean[4];
        String[] tmp = br.readLine().split(" ");
        for(int i = 0; i < 10; i++){
            moves[i] = Integer.parseInt(tmp[i]);
        }
    }

    static class Node{
        int n1, n2, s;
        // n1 이동하는 칸
        // n2 파란색인 경우 -1이 아님
        // s 점수

        public Node(int n1, int n2, int s) {
            this.n1 = n1;
            this.n2 = n2;
            this.s = s;
        }

        public Node(int n1, int s) {
            this.n1 = n1;
            this.n2 = -1;
            this.s = s;
        }
    }
}