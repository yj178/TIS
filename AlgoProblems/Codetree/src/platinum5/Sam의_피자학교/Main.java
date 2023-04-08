package platinum5.Sam의_피자학교;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * 시작 시각 : 11:33
 * 종료 시각 : 14:23
 * 소요 시각 : 170분
 * 
 * 
 */
public class Main {
	static int N, K, max, min;
	static int[][] dough;
	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		init(br);
		System.out.println(simul());
	}

	static void view() {
		for (int r = N - 1; r >= 0; r--) {
			for (int c = 0; c < N; c++) {
				System.out.printf("%4d ", dough[r][c]);

			}
			System.out.println();
		}
	}

	static int simul() {
		int t = 0;
//		int zzz = 1;
//		System.out.println("init");
//		view();
//		while (Math.abs(max - min) > K && zzz++ <10) {
		while (true) {
			// 1. 밀가루 양이 가장 작은 위치에 밀가루 1만큼 넣기 (가장 작은이 여러 군데라면 모두 넣기)
			add();
//			System.out.println("add");
//			view();
			// 2. 모두를 말아준다.
			roll();
//			System.out.println("roll");
//			view();

			// 3. 도우를 꾹 눌러준다.
			press();
//			System.out.println("press");
//			view();

			// 4. 도우를 두 번 반으로 접어준다.
			fold();
//			System.out.println("fold");
//			view();

			// 5. 3번 반복
			press();

//			System.out.println(t + "번째---------------");
//			view();
//			System.out.println(max - min);
			t++;
			if(max - min <= K) break;
		}
		return t;

		// 2차원 배열을 하나 만들어서 변경값 d에 대해 기록한다.
		// 인접한 위치의 차이 값의 5로 나눈 몫이 d이다.
		// 이후 열은 작은 것부터 행은 큰 것부터 차례대로 나열한다.

		// 행을 반으로 접어줍니다.
		// 반으로 접힌 것은 순서가 뒤집힙니다.

		// 5.3번을 반복한다.
		// 반복...

		// 해당 과정을 최대값과 최소값의 차이가 k이하가 될 때까지 반복한다.
	}

	static void fold() {
		for (int c = 0; c < N / 2; c++) {
			dough[1][N - 1 - c] = dough[0][c];
			dough[0][c] = 0;
		}

		for (int c = N / 2; c < N / 2 + N / 4; c++) {
			dough[3][N - 1 - (c - N / 2)] = dough[0][c];
			dough[2][N - 1 - (c - N / 2)] = dough[1][c];
			dough[0][c] = 0;
			dough[1][c] = 0;
		}
	}

	static void flat() {
		int tmpMin = 3001;
		int tmpMax = 0;
		int start = 0;
		for (start = 0; start < N; start++) {
			if (dough[0][start] != 0)
				break;
		}
		int idx = 0;
		for (int c = start; c < N; c++) {
			for (int r = 0; r < N && 0 != dough[r][c]; r++) {
				tmpMin = Math.min(dough[r][c], tmpMin);
				tmpMax = Math.max(dough[r][c], tmpMax);
				dough[0][idx++] = dough[r][c];
				if (r != 0 || c != idx - 1)
					dough[r][c] = 0;

			}
		}

		min = tmpMin;
		max = tmpMax;

	}

	static boolean chkOut(int r, int c) {
		return r < 0 || r >= N || c < 0 || c >= N ? true : false;
	}

	static void press() {
		int start = 0;
		for (start = 0; start < N; start++) {
			if (dough[0][start] != 0)
				break;
		}
		int[][] diff = new int[N][N];

		for (int c = start; c < N; c++) {
			for (int r = 0; r < N && 0 != dough[r][c]; r++) {
				for (int d = 0; d < 4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];

					if (chkOut(nr, nc) || dough[nr][nc] == 0)
						continue;

					int val = Math.abs(dough[nr][nc] - dough[r][c]) / 5;
					if (dough[nr][nc] < dough[r][c]) {
						diff[r][c] -= val;
						diff[nr][nc] += val;
					} else {
						diff[nr][nc] -= val;
						diff[r][c] += val;
					}

				}
			}
		}

//		for (int c = start; c < N; c++) {
//			for (int r = 0; 0 != dough[r][c]; r++) {
		for (int c = 0; c < N; c++) {
			for (int r = 0; r < N; r++) {
				dough[r][c] += diff[r][c] / 2;

			}
		}
		flat();
	}

	static void roll() {
		// 처음엔 한칸을 다음의 위로 올린다.
		// 쌓여 있는 행의 길이 만큼 올맇ㄴㄱ다
		// 만약 행의 길이가 남은 열의 길이보다 크다면 종료한다.
		// 행의 길이만큼 열의 길이 위에 올린다.
		// int len = N;
		// i 말기 시작하는 위치
		// h i의 높이
		// w 말아야 하는 넓이
		for (int h = 1, w = 1, i = 0; i < N; i += (h - 1)) {
//			System.out.println("#"+i);

			// 도우를 말았을때 밑바닥을 벗어나는 경우
			if(i+h+w-1 >= N) break;
//			System.out.println();
			for (int c = i; c < i + w; c++) {
				for (int r = 0; r < h; r++) {
//					System.out.println(i + r + w);
					dough[w - (c - i)][i + w + r] = dough[r][c];
					dough[r][c] = 0;
				}
			}
			int exh = h;
			int exw = w;
			w = exh;
			h = exw + 1;
			// len -= w;
			
		}

	}

	static void add() {
		int min = 3000;
		ArrayList<Integer> pos = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			if (dough[0][i] > min)
				continue;
			if (dough[0][i] < min) {
				pos = new ArrayList<>();
				pos.add(i);
				min = dough[0][i];
			} else {
				pos.add(i);
			}
		}

		for (int p : pos) {
			dough[0][p]++;
		}

	}

	static void init(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		max = 0;
		min = 3001;
		dough = new int[N][N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			dough[0][i] = Integer.parseInt(st.nextToken());
		}
	}
}
