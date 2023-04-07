package platinum5.산타의_선물_공장2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 시작 시간 22:13
 * root, leaf
 * head, tail
 * 1개인 경우를 고려하지 않아 이틀 동안 고민함
 */

// 각 벨트의 맨 앞 선물의 번호
// 각 벨트의 마지막 선물의 번호
// n번째 선물의 위치 정보 <- 이건 타고 가서 찾을 수 밖에...
// 특성 선물이 어느 벨트에 위치해 있는지 파악할 수 있는 정보
public class Main3 {
	static int N, M;
	static int[] pos, head, tail, root, leaf, cnt;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int Q = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine());

			switch (st.nextToken()) {
				case "100":
					command100(st);
					break;
				case "200":
					sb.append(
							"" + command200(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())) + "\n");
					break;
				case "300":
					sb.append(
							"" + command300(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())) + "\n");
					break;
				case "400":
					sb.append(
							"" + command400(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())) + "\n");
					break;
				case "500":
					sb.append("" + command500(Integer.parseInt(st.nextToken())) + "\n");
					break;
				case "600":
					sb.append("" + command600(Integer.parseInt(st.nextToken())) + "\n");
					break;
			}
			// if (q > 10)
			// continue;
			// System.out.println(q + "번째 명령 결과");
			// for (int n = 1; n <= N; n++) {
			// int now = root[n];
			// int c = 0;
			// while (now != 0) {
			// System.out.print(now + " ");
			// now = tail[now];
			// if (c++ > 10)
			// break;
			// }
			// System.out.println(" / cnt : " + cnt[n] + ", root : " + root[n] + ", leaf : "
			// + leaf[n]);

			// }

			// for (int m = 1; m <= M; m++) {
			// System.out.println(m + "번 -> head : " + head[m] + ", tail : " + tail[m]);
			// }
		}
		// System.out.println("ans----------------------");
		System.out.println(sb);
	}

	static void command100(StringTokenizer st) {
		// 공장 설립
		// 각 벨트의 정보와 선물의 정보를 조회할 수 있는 기능들을 추가하여 새로운 공장을 만들고자 합니다.
		// n개의 벨트, m개의 물건
		// m개의 선물의 위치가 공백을 사이에 두고 주어짐
		// 선물의 번호는 오름차순으로 벨트에 쌓임
		// 별도의 출력 없음

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		pos = new int[M + 1];
		head = new int[M + 1];
		tail = new int[M + 1];

		root = new int[N + 1];
		leaf = new int[N + 1];
		cnt = new int[N + 1];

		for (int m = 1; m <= M; m++) {
			pos[m] = Integer.parseInt(st.nextToken());
			if (cnt[pos[m]] == 0) {
				root[pos[m]] = m;
				leaf[pos[m]] = m;

				cnt[pos[m]] = 1;
			} else {
				tail[leaf[pos[m]]] = m;

				head[m] = leaf[pos[m]];

				leaf[pos[m]] = m;
				cnt[pos[m]]++;
			}

		}

		// for (int m = 1; m <= M; m++) {
		// System.out.println(pos[m] + "번째 벨트 / cnt" + cnt[pos[m]] + ", head : " +
		// head[m] + ", tail : " + tail[m]);
		// }
		//
		// for (int n = 1; n <= N; n++) {
		// int now = root[n];
		// while (now != 0) {
		// System.out.print(now + " ");
		// now = tail[now];
		// }
		//
		// System.out.println();
		// }
	}

	static int command200(int m_src, int m_dst) {
		// 물건 모두 옮기기

		// 만약 m_src에 선물들이 없다면 아무것도 옮기지 않아도 문제 없다.
		if (cnt[m_src] == 0)
			return cnt[m_dst];

		// m_src 번째 벨트에 있는 선물들을 모두 m_dst번째 벨트 앞에 배치한다.
		// 옮긴뒤에 m_dst 번째 벨트에 선물들의 개수를 출력합니다.

		// 두 벨트의 맨 뒤와 맨 앞을 연결해준다
		// src 맨 뒤의 꼬리에 dst 맨 앞을 추가한다.
		tail[leaf[m_src]] = root[m_dst];
		// dst맨 앞의 머리에 src 맨 뒤를 추가
		if (cnt[m_dst] != 0)
			head[root[m_dst]] = leaf[m_src];

		// dst 루트 업데이트
		root[m_dst] = root[m_src];
		if (cnt[m_dst] == 0) {
			leaf[m_dst] = leaf[m_src];
			tail[leaf[m_dst]] = 0;
		}

		// 갯수 업데이트
		cnt[m_dst] += cnt[m_src];
		cnt[m_src] = 0;

		// src 루트 리프 업데이트
		root[m_src] = 0;
		leaf[m_src] = 0;

		return cnt[m_dst];
	}

	static int command300(int m_src, int m_dst) {
		// 앞 물건만 교체하기
		// m_src 번째 벨트에 있는 선물 중 가장 앞에 있는 선물을
		// m_dst 번쨰 벨트의 선물들 중 가장 앞에 있는 선물과 교체
		// 둘 중 하나의 벨트에 선물이 없다면 교체하지 않고 옮김
		// 옮긴 뒤에 m_dst 벨트의 선물들의 개수를 출력함

		if (cnt[m_src] == 0 && cnt[m_dst] == 0)
			return 0;

		else if (cnt[m_src] != 0 && cnt[m_dst] != 0) {
			// 둘 다 있어서 교환하는 경우
			int shead = root[m_src];
			int dhead = root[m_dst];

			// 두개의 꼬리를 교환
			int tmp = tail[shead];
			tail[shead] = tail[dhead];
			tail[dhead] = tmp;

			// 두 벨트의 루트를 교환
			root[m_src] = dhead;
			root[m_dst] = shead;

			if (cnt[m_src] == 1) {
				leaf[m_src] = root[m_src];
			}
			if (cnt[m_dst] == 1) {
				leaf[m_dst] = root[m_dst];
			}
		}

		else if (cnt[m_src] == 0 && cnt[m_dst] != 0) {
			// 한군데만 있어서 옮기는 경우 dst -> src

			// dst 루트와 루트의 꼬리를 구한다.
			int dhead = root[m_dst];
			int dtail = tail[dhead];

			// dst의 맨 앞의 꼬리 제거
			tail[dhead] = 0;

			// dst 꼬리의 머리 제거
			head[dtail] = 0;
			// dst 루트를 꼬리로 변경
			root[m_dst] = dtail;

			// src의 루트를 dst 머리로 변경
			root[m_src] = dhead;
			leaf[m_src] = dhead;

			cnt[m_src] = 1;
			cnt[m_dst] -= 1;

			if (cnt[m_dst] == 0) {
				leaf[m_src] = leaf[m_dst];
				leaf[m_dst] = 0;
			}

		} else if (cnt[m_src] != 0 && cnt[m_dst] == 0) {
			// 하나군데만 있어서 옮기는 경우 src -> dst
			int shead = root[m_src];
			int stail = tail[shead];

			tail[shead] = 0;

			head[stail] = 0;
			root[m_src] = stail;

			root[m_dst] = shead;
			leaf[m_dst] = shead;

			cnt[m_dst] = 1;
			cnt[m_src] -= 1;
			if (cnt[m_src] == 0) {
				leaf[m_dst] = leaf[m_src];
				leaf[m_src] = 0;
			}
		}

		return cnt[m_dst];
	}

	static int command400(int m_src, int m_dst) {
		// 물건 나누기

		// 만약 m_src 벨트에 선물이 1개인 경우는 옮기지 안흔ㄴ다.
		if (cnt[m_src] <= 1)
			return cnt[m_dst];

		// m_src 번째 벨트에 있는 선물들의 개수를 n이라고 할 때
		// floor(n/2)번째 까지있는 선물을 m_dst벨트 앞으로 옮김
		int n = cnt[m_src] / 2;
		// System.out.println("옮길 개수 : " + n);
		int num = root[m_src];
		for (int i = 1; i < n; i++) {
			num = tail[num];
		}

		if (cnt[m_dst] != 0) { // 목적지에 선물이 존재하는 경우
			int ntail = tail[num];
			int dhead = root[m_dst];

			// 맨뒤와 맨 앞을 이어줌
			head[dhead] = num;
			tail[num] = dhead;

			// 루트 변경
			root[m_dst] = root[m_src];
			cnt[m_dst] += n;

			root[m_src] = ntail;
			head[ntail] = 0;
			cnt[m_src] -= n;
		} else {
			int ntail = tail[num];

			root[m_dst] = root[m_src];
			leaf[m_dst] = num;
			tail[num] = 0;
			cnt[m_dst] = n;

			root[m_src] = ntail;
			head[ntail] = 0;
			cnt[m_src] -= n;
		}

		// 옮긴 뒤 m_dst 번째 벨트에 있는 선물들의 개수를 출력함
		return cnt[m_dst];

	}

	static int command500(int p_num) {
		// 선물 정보 얻기
		// 선물 번호 p_num가 주어질 때, 해당 선물의 앞 선물의 번호 a와
		// int a = head[p_num];
		// 뒤 선물 번호 b라고 할 때
		// int b = tail[p_num];
		// a + 2 *b를 출력함
		// 단 앞 선물이 없으면 a = -1, 뒤 선물이 없으면 b= -1이다.
		// return ((a == 0 ? -1 : a) + ((b == 0 ? -1 : b) * 2));

		int a = head[p_num] == 0 ? -1 : head[p_num];
		int b = tail[p_num] == 0 ? -1 : tail[p_num];

		return a + 2 * b;

	}

	static int command600(int b_num) {

		// 벨트 정보 얻기
		// b_num이 주어질 때, 해당 벨트의 맨 앞에 있는 선물의 번호를 a,
		// 맨 뒤에 있는 선물의 번호를 b
		// 해당 벨트에 있는 선물의 개수를 c라고 할때
		// a + 2*b + 3*c의 값을 출력합니다.
		// 선물이 없는 경우에는 a와 b 모두 -1이 됩니다.

		// int a = root[b_num];
		// int b = leaf[b_num];
		// int c = cnt[b_num];
		//
		// return ((c == 0 ? -1 : a) + (2 * (c == 0 ? -1 : b)) + (3 * c));
		int c = cnt[b_num];
		int a = root[b_num] == 0 ? -1 : root[b_num];
		int b = leaf[b_num] == 0 ? -1 : leaf[b_num];

		return a + 2 * b + 3 * c;

	}

}
