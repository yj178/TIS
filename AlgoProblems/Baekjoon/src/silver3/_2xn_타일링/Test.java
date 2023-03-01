package silver3._2xn_타일링;

public class Test {
    static int N, ans1, ans;
    static final int rem = 10007;
    public static void main(String[] args) {

        for(int i = 1; i <= 1000; i++){
            N = i;
            ans1 = 0;
            ans = 0;
            sol1(0);
            sol(i);

            System.out.println(i+"번 \t"+(ans1 == ans ? "맞았습니다." : "틀렸습니다.") + " ans1 : "+ans1);
        }

    }

    static void sol(int n){
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int idx = 2; idx <= n; idx++) {
            dp[idx] = (dp[idx - 2] + dp[idx - 1]) % rem;
        }
        ans = dp[n];
    }

    static void sol1(int len) {
        if (len > N) return;

        if (len == N) {
            ans1 = ans1 + 1 >= rem ? 0 : ans1 + 1;
            return;
        }

        sol1(len + 1);
        sol1(len + 2);

    }
}
