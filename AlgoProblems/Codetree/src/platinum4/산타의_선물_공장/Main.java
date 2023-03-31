package platinum4.산타의_선물_공장;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
문제 이름 : 산타의 선물 공장
문제 링크 : https://www.codetree.ai/training-field/frequent-problems/santa-gift-factory/description?page=3&pageSize=20
시작 시각 : 07:50
종료 시각 : 
소요 시각 : 


 */
class Main {
	static int Q, M, N;
	static boolean[] chk; // 벨트의 정상 유무 확인
	static HashMap<Long, Integer> map; // id, belt
	static HashMap<Long, Long> info; // id, weight
	static ArrayDeque<Long>[] belts;

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("C:/Users/deter/OneDrive/바탕 화면/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		simul(br);
	}

	static void command100(StringTokenizer st) {
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		info = new HashMap<>();
		map = new HashMap<>();
		chk = new boolean[M + 1];
		belts = new ArrayDeque[M + 1];
		for (int i = 1; i <= M; i++) {
			belts[i] = new ArrayDeque<>();
		}
		long[] tmpId = new long[N + 1];
		int idx = 1;
		for (int n = 1; n <= N; n++) {
			tmpId[n] = Long.parseLong(st.nextToken());

			map.put(tmpId[n], idx);
			belts[idx].add(tmpId[n]);
			if (n % (N / M) == 0)
				idx++;
		}

		for (int n = 1; n <= N; n++) {
			long w = Long.parseLong(st.nextToken());
			info.put(tmpId[n], w);
		}

//		for (int i = 30; i >= 0; i--) {
//			System.out.println(i + ", " + tmpId[i] + "," + info.get(tmpId[i]));
//		}

	}

	static Long command200(long w_max) {
		long sum = 0;

		for (int i = 1; i <= M; i++) {
			if (belts[i].isEmpty())
				continue;

			// 물건하차
			long id = belts[i].removeFirst();
			long w = info.get(id);
			// w_max가 주어지고 컨테이너 벨트의 각각마다
			if (w <= w_max) {
				// w_max이하라면 물건 하차
				map.remove(id);
				sum += w;
			} else {
				// w_max를 초과한다면 해당 컨테이너 벨트의 맨 뒤에 넣기
				belts[i].add(id);
			}
		}

		// 하차한 모든 물건의 무게 합을 리턴한다.
		return sum;
	}

	static long command300(long r_id) {
		// 산타가 제거하기를 원하는 물건의 고유번호 r_id가 주어진다.
		// 상자가 존재한다면 r_id 값을, 없다면 -1을 출력
		if (!map.containsKey(r_id))
			return -1;
		// 해당 고유 번호에 해당하는 상자가 놓여있는 벨트가 있다면
		// 해당 벨트에서 상자를 제거하고, 뒤에 있는 상자들은 앞으로 한 칸씩 내려옴
		belts[map.get(r_id)].remove(r_id);
		map.remove(r_id);
		return r_id;
	}

	static int command400(long f_id) {
		// 물건을 확인하기 원하는 고유번호 f_id가 주어짐
		// 없다면 -1을 출력
		if (!map.containsKey(f_id))
			return -1;
		// 놓여있는 벨트가 있다면 해당 벨트의 번호를 출력
		int b = map.get(f_id);
		// 이때, 물건은 해당 컨테이너 벨트의 가장 앞으로 가져온다.
		ArrayDeque<Long> tmp = new ArrayDeque<>();
		while (belts[b].getFirst() != f_id) {
			tmp.add(belts[b].pollFirst());
		}

		belts[b].addAll(tmp);

		return b;
	}

	static int command500(int b_num) {
		// 고장이 발생한 벨트 번호 b_num이 주어진다
		// 만약 해당 벨트가 이미 망가져 있었다면 -1을 반환
		if (chk[b_num])
			return -1;

		// 고장이 발생하면 해당 벨트는 다시는 사용할 수 없게 되고
		chk[b_num] = true;
		// b_num 벨트의 바로 오른쪽 벨트부터 순서대로 보며 보며, m개를 넘어가는 경우 처음부터 확인한다
		int i = b_num;
		while (i < 2 * M + 2) {
			i++;
			int idx = i % (M + 1);
			if (idx == 0) {
				i++;
				idx++;
			}
			if (chk[idx])
				continue;

			// 고장나지 않은 벨트가 보이면 바로 해당 벨트의 뒤에 모든 물건을 추가한다
			while (!belts[b_num].isEmpty()) {

				long id = belts[b_num].pollFirst();
				map.put(id, idx);
				belts[idx].addLast(id);
			}
			break;
		}

		// 지금 망가진다면 b_num을 출력한다.
		return b_num;

	}

	static void simul(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		Q = Integer.parseInt(st.nextToken());
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine());
			String outLine = "";
			switch (Integer.parseInt(st.nextToken())) {
			case 100: // 공장 설립
				command100(st);
				break;
			case 200: // 물건 하차
				outLine += command200(Long.parseLong(st.nextToken())) + "\n";
				break;
			case 300: // 물건 제거
				outLine += command300(Long.parseLong(st.nextToken())) + "\n";
				break;
			case 400: // 물건 확인
				outLine += command400(Long.parseLong(st.nextToken())) + "\n";
				break;
			case 500: // 벨트 고장
				outLine += command500(Integer.parseInt(st.nextToken())) + "\n";
				break;
			}
//			if (q < 30)
//				System.out.print(outLine);
			bw.write(outLine);
		}

		bw.close();

	}
}
