# [programmers] [Lv2 귤_고르기](https://school.programmers.co.kr/learn/courses/30/lessons/138476)

## 목표
> 귤 k를 고를 때, 서로 다른 종류의 수의 최소값을 return 하시오.

## 문제 조건
* $1 \leq tangerine의 원소 \leq 10,000,000$
* $1 \leq k \leq tangerine의 길이 \leq 100,000$

## 추가로 고려해야 할 사항
없습니다.   

## 풀기 전 생각한 풀이 과정
1. 모든 귤을 탐색하면서 크기에 따라 HashMap으로 분류하고 개수를 기록합니다.
2. PriorityQueue를 이용해서 개수가 많은 순부터 조회할 수 있도록 저장합니다.
3. PriorityQueue에서 하나씩 꺼내면서 개수의 합이 k개 이상이 될려면 몇번 꺼내야 하는지 기록합니다.
4. 기록한 횟수를 출력합니다.

## 풀면서 추가로 고려한 사항
없습니다.   

## 느낀 점
없습니다.   

