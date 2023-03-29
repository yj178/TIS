# [SWEA] [D1 구구단_걷기](https://swexpertacademy.com/main/talk/codeBattle/problemDetail.do?contestProbId=AYaf9W8afyMDFAQ9&categoryId=AYagwMEql1wDFAQ9&categoryType=BATTLE&battleMainPageIndex=1)

## 목표
> 곱셈표 위에서 오른쪽과 아래쪽 방향 만으로 움직여 원하는 숫자에 도달하는 경우 중 최소로 움직이는 경우는 얼마인지 구하시오.

## 문제 조건
* $2 \leq N \leq 10^{12}$

## 추가로 고려해야 할 사항
* 약수를 구하는 과정이므로 $\sqrt{10^{12}} = 10^{6}$ 까지만 조회하면 충분하다.
* $10^{12}$는 정수의 범위를 초과하므로, Long을 사용해야 한다.

## 풀기 전 생각한 풀이 과정
* 1에서 1,000,000까지 조회하면서 약수의 합이 최소인 경우를 찾는다.
  * 다만, 숫자의 제곱이 찾는 수를 넘어가면 종료한다.

## 풀면서 추가로 고려한 사항
없습니다.

## 느낀 점
없습니다.