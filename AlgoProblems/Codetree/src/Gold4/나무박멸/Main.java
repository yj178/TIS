package Gold4.나무박멸;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
문제 이름 : 나무 박멸
문제 링크 : https://www.codetree.ai/training-field/frequent-problems/tree-kill-all/description?page=3&pageSize=20
시작 시각 : 11:15
종료 시각 : 12:38
소요 시각 : 83분

나무를 박멸하는 과정에서 시작 위치를 누락함

*/

class Main {
	static int[][] map; // -1은 벽
	static int[][] period;
	static int N, M, K, SP;
	static int[] dr = { 0, 0, -1, 1 };
	static int[] dc = { 1, -1, 0, 0 };
	static int[] ddr = { 1, 1, -1, -1 };
	static int[] ddc = { 1, -1, 1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		init(br);

		System.out.println(simul());
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

	static boolean chkOut(int r, int c) {
		return r < 0 || r >= N || c < 0 || c >= N ? true : false;
	}

	static void growTree() {
		int[][] tmpMap = new int[N][N];

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if(map[r][c] <= 0) continue;
				int cnt = 0;
				// 인접한 4방에 나무가 존재한다면?
				for (int d = 0; d < 4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];
					if (chkOut(nr, nc))
						continue;
					if (map[nr][nc] > 0)
						cnt++;
				}
				// 존재한 만큼 성장
				tmpMap[r][c] = cnt;

			}
		}

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				// 기록만큼 성장
				map[r][c] += tmpMap[r][c];
			}
		}
	}

	static void breedingTree(int m) {
		int[][] tmpMap = new int[N][N];

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if(map[r][c] <= 0) continue;
				
				int cnt = 0;
				
				// 인접한 4방에 빈 공간이 존재한다면?
				for (int d = 0; d < 4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];
					if (chkOut(nr, nc))
						continue;
					if (map[nr][nc] == 0 && period[nr][nc] < m)
						cnt++;
				}
				// 빈 공간 수 만큼 번식 수행, 다만 0이면 생략
				if (cnt == 0)
					continue;
				for (int d = 0; d < 4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];
					if (chkOut(nr, nc))
						continue;
					if (map[nr][nc] == 0 && period[nr][nc] < m)
						tmpMap[nr][nc] += map[r][c] / cnt;
				}
			}
		}

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				// 기록만큼 성장
				map[r][c] += tmpMap[r][c];
			}
		}

	}

	static Info calcInfo(int r, int c) {
		int cnt = map[r][c]; // 처음 위치 값 누락
		if(map[r][c] == 0) return new Info(r, c, 0); // 나무가 없는 경우 바로 종료
		for (int d = 0; d < 4; d++) {
			// 확산 범위 k만큼
			for (int k = 1; k <= K; k++) {
				int nr = r + ddr[d] * k;
				int nc = c + ddc[d] * k;
				// 맵을 벗어나는 경우, 더 이상 확산할 수 없는 경우
				if (chkOut(nr, nc) || map[nr][nc] <= 0)
					break; // 종료

				cnt += map[nr][nc]; // 나무 기록
			}
		}
//		System.out.println(r +", " + c + ", " + cnt);
		return new Info(r, c, cnt);
	}

	static int sprayHerbicide(int m, Info info) {
		// 초기 위치 박멸 및 기록 누락함
		map[info.r][info.c] = 0;
		period[info.r][info.c] = m + SP;
		// 대각선 4방향
		for (int d = 0; d < 4; d++) {
			// 확산 범위 k만큼
			for (int k = 1; k <= K; k++) {
				int nr = info.r + ddr[d] * k;
				int nc = info.c + ddc[d] * k;
				// 맵을 벗어나는 경우 즉각 종료
				if (chkOut(nr, nc))
					break;
				// 비어있거나 벽인 경우 제초제만 살포 후 종료
				if (map[nr][nc] <= 0) {
					period[nr][nc] = m + SP;
					break;
				} else { // 나무가 있는 경우 제초제 살포 후 나무 박멸
					map[nr][nc] = 0;
					period[nr][nc] = m + SP;
				}
			}
		}
		return info.cnt;

	}

	static int simul() {
		// 4 * 400 * 1000 = 1,600,000 아무리 자라도 정수
		int cnt = 0;
		for (int m = 1; m <= M; m++) {
			// 나무의 성장
			growTree();
//			viewMap();
			// 나무의 번식
			breedingTree(m);
//			viewMap();
			// 제초제 위치 선정
			PriorityQueue<Info> pq = new PriorityQueue<>();

			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					// 벽이 있는 경우 확인 안함
					// 나무가 없는 경우도 포함함
					if (map[r][c] < 0)
						continue;

					// 해당 위치 결과 저장
					pq.add(calcInfo(r, c));
				}
			}

			// 제초제 살포
			cnt += sprayHerbicide(m, pq.poll());
//			viewMap();
		}
		return cnt;
	}

	static void init(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		SP = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		period = new int[N][N];
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
	}

	static class Pos {
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}

	}

	static class Info implements Comparable<Info> {
		int r, c, cnt;

		public Info(int r, int c, int cnt) {
			super();
			this.r = r;
			this.c = c;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Info o) {
			// 가장 많은 나무 박멸 -> 행이 작은 -> 열이 작은
			return this.cnt != o.cnt ? o.cnt - this.cnt : this.r != o.r ? this.r - o.r : this.c - o.c;
		}

	}
}
