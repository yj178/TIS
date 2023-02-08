package gold2.가장_긴_증가하는_부분_수열_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class B12015 {
    static int N;
    static int[] arr;
    static int[] table;

    static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);

        System.out.println(chklen());

    }

    static int chklen() {
        int max = 0;
        int ans = 0;
        for (int n = 1; n <= N; n++) {
            int idx = binarySearch(arr[n], ans);
//            System.out.println("search : " + arr[n] +", "+ idx);
            max = idx + 1;
            if (table[idx] < arr[n]) {
                table[max] = Math.min(table[max], arr[n]);
                ans = Math.max(max, ans);
            }
//            System.out.println(Arrays.toString(table) + ", max : " + max + ", search : " + arr[n]);
        }

        return ans;
    }

    static int binarySearch(int target, int end) {
        int s = 0;
        int e = end;
        int mid = 0;

        while (s <= e) {
            mid = (s + e) / 2;
            if (table[mid] == target) return mid;
            else if (table[mid] < target) s = mid + 1;
            else e = mid - 1;
        }
        return e;
    }

    static void init(BufferedReader br) throws IOException {
        N = Integer.parseInt(br.readLine());
        String[] tmp = br.readLine().split(" ");
        arr = new int[N + 1];
        table = new int[N + 1];
        for (int n = 1; n <= N; n++) {
            table[n] = Integer.MAX_VALUE;
            arr[n] = Integer.parseInt(tmp[n - 1]);
        }
        table[0] = 0;
    }
}
