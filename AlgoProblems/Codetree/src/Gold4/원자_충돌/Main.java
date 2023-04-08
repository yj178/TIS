package Gold4.원자_충돌;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {

	static int N, M, K;
	static HashMap<Pos, ArrayDeque<Atom>> map;

	static int[] dr = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dc = { 0, 1, 1, 1, 0, -1, -1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		init(br);
		System.out.println(simul());
	}

	static int calcPos(int p) {
		if (p >= 0 && p < N)
			return p;
		int tmp = p / N;
		return p < 0 ? p + (Math.abs(tmp) + ((p % N == 0) ? 0 : 1)) * N : p - tmp * N;
	}

	static void move() {
		// 모든 원자는 1초가 지날 때마다 자신의 방향으로 자신의 속력만큼 이동한다.
		// 이동 과정에서 만나는 경우에는 합성하지 않는다.

		HashMap<Pos, ArrayDeque<Atom>> tmp = new HashMap<>();
		for (Map.Entry<Pos, ArrayDeque<Atom>> e : map.entrySet()) {
			int r = e.getKey().r;
			int c = e.getKey().c;

			for (Atom a : e.getValue()) {
				int nr = calcPos(r + dr[a.d] * a.s);
				int nc = calcPos(c + dc[a.d] * a.s);
				Pos p = new Pos(nr, nc);
				if (!tmp.containsKey(p)) {
					tmp.put(p, new ArrayDeque<>());
				}
				tmp.get(p).add(new Atom(a.m, a.d, a.s));
			}

		}
//		System.out.println("이동 후");
//		for (Map.Entry<Pos, ArrayDeque<Atom>> e : tmp.entrySet()) {
//			for (Atom a : e.getValue()) {
//				System.out.println(e.getKey().toString() + " / " + a.toString());
//			}
//		}

		map = tmp;
	}

	static void fusion() {
		// 이동이 모두 끝난 뒤에 하나의 칸에 2개 이상의 원자가 있는 경우에는 다음과 같은 합성이 일어납니다.
		// 같은 칸에 있는 원자들은 각각의 질량과 속력을 모두 합한 하나의 원자로 합쳐진다.
		// 이후 합쳐진 원자는 4개로 나눠진다.
		// 나워진 원자들은 모두 해당 칸에 위치하고, 질량과 속력, 방향은 다음 기준을 따라 결정된다.
		// 질량은 합쳐진 원자의 질량에 5를 나눈 값
		// 질량이 0인 원소는 소멸된다.
		// 속력은 합쳐진 원자의 속력에 합쳐진 원자의 개수를 나눈 값
		// 방향은 합쳐지는 원자의 방향이 모두 항사좡 중 하나이거나 대각선 중에 하나이면, 각각 상하좌우의 방향을 가지며
		// 그렇지 않다면 대각선 네 방향의 값을 가집니다.
		// 편의상 나눗셈 과정에서 생기는 소숫점 아래는 버린다.
//System.out.println(":test");
		HashMap<Pos, ArrayDeque<Atom>> tmp = new HashMap<>();
		for (Map.Entry<Pos, ArrayDeque<Atom>> e : map.entrySet()) {
			int r = e.getKey().r;
			int c = e.getKey().c;
			int dia = 0;
			int pal = 0;
			int summ = 0;
			int sums = 0;
			Pos p = new Pos(r, c);
			int size = e.getValue().size();
			if (size == 1) {
				Atom one = e.getValue().poll();
				if (!tmp.containsKey(p)) {
					tmp.put(p, new ArrayDeque<>());
				}
				tmp.get(p).add(new Atom(one.m, one.d, one.s));
			} else if(size > 1) {
				for (Atom a : e.getValue()) {
					summ += a.m;
					sums += a.s;
					if (a.d % 2 == 1)
						dia++;
					else
						pal++;
				}
//				System.out.println(sums);

				if (!tmp.containsKey(p)) {
					tmp.put(p, new ArrayDeque<>());
				}

				if (summ / 5 == 0)
					continue;

				for (int dd = 0; dd < 8; dd += 2) {
					tmp.get(p).add(new Atom(summ / 5, (dia == 0 || pal== 0 ? dd : dd+1), sums / size));
				}
			}
		}
//		System.out.println("분열 후");
//		for (Map.Entry<Pos, ArrayDeque<Atom>> e : tmp.entrySet()) {
//			for (Atom a : e.getValue()) {
//				System.out.println(e.getKey().toString() + " / " + a.toString());
//			}
//		}
		map = tmp;
	}

	static int simul() {
		for (int k = 1; k <= K; k++) {
			// 모든 원자가 움직인다.
			move();

			// 합성한다.
			fusion();
		}

		int sum = mSum();
		return sum;

	}

	static int mSum() {
		int sum = 0;
		for (Map.Entry<Pos, ArrayDeque<Atom>> e : map.entrySet()) {
			for (Atom a : e.getValue()) {
				sum += a.m;
			}
		}
		return sum;
	}

	static void init(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new HashMap<>();
		for (int mm = 0; mm < M; mm++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			Pos pos = new Pos(x - 1, y - 1);
			if (!map.containsKey(pos))
				map.put(pos, new ArrayDeque<>());
			map.get(pos).add(new Atom(m, d, s));
		}
	}

	static class Atom {
		int m, d, s;

		public Atom(int m, int d, int s) {
			this.m = m;
			this.d = d;
			this.s = s;
		}

		@Override
		public String toString() {
			return "Atom [m=" + m + ", d=" + d + ", s=" + s + "]";
		}

	}

	static class Pos {
		int r, c;

		public Pos(int r, int c) {
			this.r = r;
			this.c = c;
		}

	
		@Override
		public int hashCode() {
			return Objects.hash(c, r);
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

		@Override
		public String toString() {
			return "Pos [r=" + r + ", c=" + c + "]";
		}

	}
}
