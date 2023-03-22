package Gold5.나무_타이쿤;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static int[][] map;
	static boolean[][] potion;
	static int[][] command;
	static int N, M;
	static int[] dr = { 0, -1, -1, -1, 0, 1, 1, 1 };
	static int[] dc = { 1, 1, 0, -1, -1, -1, 0, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		init(br);
		System.out.println(simul());
	}

	static void viewPotion() {
		System.out.println();
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				System.out.print(potion[r][c] + " ");
			}
			System.out.println();
		}

	}

	static void viewMap() {
		System.out.println();
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}

	}

	static int simul() {

		for (int m = 0; m < M; m++) {
			// 영양제 이동
			movePotion(m);
//			viewPotion();
			// 나무 성장
			growTree();
//			viewMap();
			// 나무 -> 영양제
			changeTreeToPotion();

//			viewMap();

		}

		return cntHeight();
	}

	static void changeTreeToPotion() {
		boolean[][] newPotion = new boolean[N][N];

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (map[r][c] < 2 || potion[r][c])
					continue;

				map[r][c] -= 2;
				newPotion[r][c] = true;
			}
		}

		potion = newPotion;
	}

	static boolean chkOut(int r, int c) {
		return r < 0 || r >= N || c < 0 || c >= N ? true : false;
	}

	static int cntDiag(int[][] newMap, int r, int c) {
		int cnt = 0;
		for (int d = 1; d < 8; d += 2) {
			int nr = r + dr[d];
			int nc = c + dc[d];

			if (chkOut(nr, nc))
				continue;

			if (newMap[nr][nc] >= 1)
				cnt++;
		}
		return cnt;
	}

	static void growTree() {
		int[][] newMap = new int[N][N];
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				newMap[r][c] = map[r][c] + (potion[r][c] ? 1 : 0);
			}
		}

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (!potion[r][c])
					continue;
				newMap[r][c] += cntDiag(newMap, r, c);
			}
		}

		map = newMap;
	}

	static int outMap(int x) {
		while (x < 0 || x >= N) {
			if (x < 0)
				x += N;
			else if (x >= N)
				x -= N;
		}
		return x;
	}

	static void movePotion(int m) {
		boolean[][] newPotion = new boolean[N][N];
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (!potion[r][c])
					continue;

				int nr = outMap(r + dr[command[m][0]] * command[m][1]);
				int nc = outMap(c + dc[command[m][0]] * command[m][1]);

				newPotion[nr][nc] = true;
			}
		}

		potion = newPotion;
	}

	static int cntHeight() {
		int cnt = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				cnt += map[r][c];
			}
		}
		return cnt;
	}

	static void init(BufferedReader br) throws IOException {
		String[] tmp = br.readLine().split(" ");
		N = Integer.parseInt(tmp[0]);
		M = Integer.parseInt(tmp[1]);

		map = new int[N][N];

		for (int r = 0; r < N; r++) {
			tmp = br.readLine().split(" ");
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(tmp[c]);
			}
		}

		potion = new boolean[N][N];
		potion[N - 1][0] = true;
		potion[N - 1][1] = true;
		potion[N - 2][0] = true;
		potion[N - 2][1] = true;

		command = new int[M][2];
		for (int m = 0; m < M; m++) {
			tmp = br.readLine().split(" ");
			command[m][0] = Integer.parseInt(tmp[0]) - 1;
			command[m][1] = Integer.parseInt(tmp[1]);
		}
	}

}
