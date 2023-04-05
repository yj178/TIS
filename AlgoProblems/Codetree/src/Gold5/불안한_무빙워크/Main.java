package Gold5.불안한_무빙워크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int[] arr;
	static boolean[] chk;
	static int N, K, start, end;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		init(br);
		System.out.println(simul());
	}

	static int simul() {
		int cnt = 0;
		int time = 1;
		while (true) {
			// 무빙워크 한칸 회전
			rotate();

			// up 뒤에서부터 사람이 다음 칸으로 이동할 수 있는지 확인하고 이동하기
			if (start < end) {
				if (chk[end])
					chk[end] = false;

				for (int i = end - 1; i >= start; i--) {
					int next = next(i);
					if (!chk[i] || arr[next] <= 0 || chk[next])
						continue;
					chk[i] = false;
					if (--arr[next] == 0)
						cnt++;
					chk[next] = true;
					if(next == end) chk[next] = false; 
				}
			} else {
				if (chk[end])
					chk[end] = false;

				for (int i = end - 1; i >= 0; i--) {
					int next = next(i);
					if (!chk[i] || arr[next] <= 0 || chk[next])
						continue;
					chk[i] = false;
					if (--arr[next] == 0)
						cnt++;
					chk[next] = true;
					if(next == end) chk[next] = false; 
				}

				for (int i = 2 * N - 1; i >= start; i--) {
					int next = next(i);
					if (!chk[i] || arr[next] <= 0 || chk[next])
						continue;
					chk[i] = false;
					if (--arr[next] == 0)
						cnt++;
					chk[next] = true;
				}

			}

			// 1번에 사람 탈 수 있는지 확인 후 탑승
			if (arr[start] > 0) {
				if (--arr[start] == 0)
					cnt++;
				chk[start] = true;
			}
			if (time < 10) {
				if (start < end) {
					for (int i = start; i <= end; i++) {
						System.out.print((i + 1) + ":" + arr[i] + " ");
					}
					System.out.println();

				} else {

					for (int i = start; i < 2 * N; i++) {
						System.out.print((i + 1) + ":" + arr[i] + " ");
					}
					for (int i = 0; i <= end; i++) {
						System.out.print((i + 1) + ":" + arr[i] + " ");
					}
					System.out.println();
				}
				System.out.println(time + "/" + cnt);
			}

			if (cnt >= K)
				return time;
			time++;
		}

	}

	static void rotate() {
		if (--start < 0)
			start = 2 * N - 1;
		if (--end < 0)
			end = 2 * N - 1;
	}

	static int next(int now) {
		return ((now + 1) >= (2 * N) ? 0 : (now + 1));
	}

	static void init(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		start = 0;
		end = N - 1;
		arr = new int[N * 2];
		chk = new boolean[N * 2];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 2 * N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
	}

	static class Node {
		int safe, idx;
		boolean human;

		public Node(int safe, int idx) {
			this.safe = safe;
			this.idx = idx;
		}
	}
}
