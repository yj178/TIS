package Gold2.코드트리_빵;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 시작 시각 : 12:20
 * 종료 시각 : 15:19
 * 소요 시간 : 179분
 * 중간중간 다른 일처리도 있었지만 틀린 부분 찾는데 소요한 시간도 김
 * 
 */
class Main {
	static int N, M;
	static int[][] map;
	static HashSet<Pos> base, xx;
	static Person[] p;
	static int[] dr = { -1, 0, 0, 1 };
	static int[] dc = { 0, -1, 1, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		init(br);
		System.out.println(simul());
	}

	static boolean chkOut(int r, int c) {
		return r < 0 || c < 0 || c >= N || r >= N ? true : false;
	}

	static boolean chkXX(int r, int c) {
		return xx.contains(new Pos(r, c)) ? true : false;
	}

	static int simul() {
		// m명의 사람이, 1분에 1번사람, 2분에 2번 사람, 3분에 3번사람... 에 출발하여 편의점으로 이동함
		// 출발 시간이 되기 전까지 격자 밖에 나와있으며, 사람들이 목표로 하는 편의점은 모두 다르다.
		// 모든 일은 n*n에서 진행됨
		int t = 0;
		int cnt = 0;
		while (cnt != M) { // 모든 사람이 도착하는 시점에 종료

			// 아래 3가지 행동은 1분동안 진행됨, 1,2,3 순서로 진행되어야 함
			for (int i = 0; i < M; i++) {
				if (i >= t)
					break; // 아직 베이스 캠프에 도착 안한 사람부터는 확인할 필요 없음
				if (p[i].chk)
					continue;// 도착한 사람은 생략
				// 1. 격자에 있는 사람들이 본인이 가고 싶은 편의점 방향을 향해서 1칸 움직임
				// 움직여야 하는 방향 결정 및 이동
				if (move(i, bfs(i))) {
					// 2. 편의점에 도착하면 해당 편의점에서 멈추고, 이때부터 다른 사람들은 해당 편의점이 있는 칸을 지나갈 수 없게 됨
					// 사실 이 부분 해석이 좀 애매한데
					// 반드시 1,2,3 순서라면 1번 과정에서 편의점에 도착하더라고 일단은 이동이 가능한 것인지?
					// 아니면 이전 번호의 사람이 이미 편의점에 도착했다면 이동이 불가능한 것인지 확인이 필요할 듯
					xx.add(p[i].now);
					p[i].chk = true;
					cnt++;
				}
//				System.out.println("t: " + (t) + ", i : " + i + ", 위치  : " + p[i].now);

				// 최단 거리로 움직이며, 최단 거리로 움직이는 방법이 여러가지라면 상좌우하의 우선순위로 움직임
				// 여기서 최단거리라 함은 상하좌우 인접한 칸 중 이동가능한 칸으로만 이동하여 도달하기까지 거쳐야 하는 칸의 수가 최소가 되는 거리
			}

			// 3. 현재 시각이 t분이고 t<=m을 만족한다면, t번 사람은 자신이 가고 싶은 편의점과 가장 가까이 있는 베이스 캠프에 들어갑니다.
			// 가장 가까이에 있다는 뜻 역시 1과 같이 최단거리에 해다하는 곳을 의미
			// 가장 ㄱ가까운 베이스 캠프가 여럿이라면 행이 작은 같다면 열이 작은 순으로 선택하여 들어갑니다.
			// 베이스 캠프로 들어가는 데에는 시간이 전혀 소요되지 않습니다.
			// 들어간 후에는 해당 베이스 캠프를 지나갈 수 없습니다.
			if (t < M) {
				Pos pos = findInit(t);
//				System.out.println(t + "사람 위치 : " + pos);
				p[t].setPos(pos);
				xx.add(new Pos(pos.r, pos.c));
			}

			t++;
		}
		

		// 1. 베이스 캠프에 사람 들어가기
		// 2. 본인이 가고 싶은 편의점으로 이동하기

		return t;
	}

	static Pos findInit(int m) {
		Pos des = new Pos(p[m].store.r, p[m].store.c);
		Queue<Pos> q = new ArrayDeque<>();
		PriorityQueue<Pos> pq = new PriorityQueue<>((Pos p1, Pos p2) -> p1.r != p2.r ? p1.r - p2.r : p1.c - p2.c);
		q.add(new Pos(des.r, des.c));
		boolean[][] chk = new boolean[N][N];
		for(Pos p : xx) {
//			System.out.println("금지 : " + p);
			chk[p.r][p.c] = true;
		}
		chk[des.r][des.c] = true;
		boolean flag = false;
		while (!q.isEmpty()) {
			int qsize = q.size();
			for (int i = 0; i < qsize; i++) {
				Pos tmp = q.poll();
				chk[tmp.r][tmp.c] = true;
				for (int d = 0; d < 4; d++) {
					int nr = tmp.r + dr[d];
					int nc = tmp.c + dc[d];

					if (chkOut(nr, nc) ||  chk[nr][nc])
						continue;
					Pos next = new Pos(nr, nc);
					q.add(next);

					if (base.contains(next)) {
						flag = true;
//						System.out.println(next);
						pq.add(next);

					}
				}
			}
			if (flag)
				break;
		}

		return pq.poll();
	}

	static boolean move(int i, int d) {
		Person me = p[i];
		me.now.r = me.now.r + dr[d];
		me.now.c = me.now.c + dc[d];
		return me.goal();
	}

	static int bfs(int i) {
		Pos start = new Pos(p[i].now.r, p[i].now.c);
		Pos des = p[i].store;
		Queue<Pos> q = new ArrayDeque<>();
		boolean[][] chk = new boolean[N][N];
		q.add(start);
//		chk[p[i].now.r][p[i].now.c] = true;// 중복
		boolean flag = false;
		PriorityQueue<String> pq = new PriorityQueue<>();
		while (!q.isEmpty()) {
			int qsize = q.size();
			for (int idx = 0; idx < qsize; idx++) {
				Pos tmp = q.poll();
				chk[tmp.r][tmp.c] = true; // 다른 애들도 해당 위치 이용가능 하므로 이동 후에 체크한다.
				for (int d = 0; d < 4; d++) {
					int nr = tmp.r + dr[d];
					int nc = tmp.c + dc[d];

					// 이동할 수 없는 경우 생략
					if (chkOut(nr, nc) || chk[nr][nc] || chkXX(nr, nc))
						continue;

					// 해당 위치 이동 가능한 경우 체크
					Pos next = new Pos(nr, nc, tmp.log + d);
					q.add(next);

					// 해당 위치가 목적지라면?
					if (next.equals(p[i].store)) {
						flag = true;
						pq.add(next.log);
					}

				}

			}
			if (flag)
				break;

		}

		return pq.peek().charAt(0) - '0';
	}

	static void init(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 모든 일은 n*n에서 진행됨
		N = Integer.parseInt(st.nextToken());
		// 빵을 구하고자 하는 M명의 사람
		M = Integer.parseInt(st.nextToken());

		p = new Person[M];

		base = new HashSet<>();
		xx = new HashSet<>();
		map = new int[N][N];
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				int tmp = Integer.parseInt(st.nextToken());
				map[r][c] = tmp;
				if (tmp == 1)
					base.add(new Pos(r, c));
			}
		}

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			p[m] = new Person(new Pos(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1));
		}

	}

	static class Person {
		Pos store, now;
		boolean chk;

		public Person(Pos store) {
			this.store = store;
			now = new Pos(-1, -1);
			chk = false;
		}

		public boolean goal() {
			return store.equals(now);
		}

		public void setPos(Pos p) {
			now = p;
		}

	}

	static class Pos implements Comparable<Pos> {
		int r, c;
		String log;

		public Pos(int r, int c) {
			this.r = r;
			this.c = c;
			log = "";
		}

		public Pos(int r, int c, String log) {
			this.r = r;
			this.c = c;
			this.log = log;
		}

		@Override
		public String toString() {
			return "Pos [r=" + r + ", c=" + c + ", log=" + log + "]";
		}

		@Override
		public int compareTo(Pos o) {
			// TODO Auto-generated method stub
			return this.log.compareTo(o.log);
		}

		@Override
		public int hashCode() {
			return r * 16 + c;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pos other = (Pos) obj;
			return c == other.c && r == other.r;
		}

	}
}
