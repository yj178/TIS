package 디펜스_게임;
/*
보유한 병사 n명으로 적의 공격을 순서대로 막음
게임 룰
초기 n명의 병사 소유
라운드마다 enemy[i] 마리의 적 등장
    병사랑 enemy[i] 1:1교환으로 라운드 방어 가능
    남은 병사보다 enemy[i]가 더 많으면 게임 종료
무적권은 해당 라운드에 병사 소모 없이 한 라운드의 공격을 막을 수 있음
무적권은 K번 사용 가능
 */
public class 디펜스_게임 {
    public static void main(String[] args) {
        디펜스_게임 problem = new 디펜스_게임();
        int n = 7;
        int k = 3;
        int[] enemy = {4, 2, 4, 5, 3, 3, 1};
        System.out.println(problem.solution(n, k, enemy));
    }

    public int solution(int n, int k, int[] enemy) {
        int answer = 0;
        return answer;
    }
}
