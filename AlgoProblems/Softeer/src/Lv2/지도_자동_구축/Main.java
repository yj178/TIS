package Lv2.지도_자동_구축;

import java.util.Scanner;

class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int ans = 2;
        for (int i = 0; i < N; i++) {
            ans = 2 * ans - 1;
        }
        System.out.println(ans * ans);
    }
}
