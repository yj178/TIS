# Lv1.카드_뭉치

## 목표
> 두 개의 카드 뭉치에서 차례대로 카드를 뽑아 원하는 단어 배열을 만들 수 있는지 확인하기

## 문제 조건
* 1 ≤ cards1의 길이, cards2의 길이 ≤ 10
  * 1 ≤ cards1[i]의 길이, cards2[i]의 길이 ≤ 10
  * cards1과 cards2에는 서로 다른 단어만 존재합니다.
* 2 ≤ goal의 길이 ≤ cards1의 길이 + cards2의 길이
  * 1 ≤ goal[i]의 길이 ≤ 10
  * goal의 원소는 cards1과 cards2의 원소들로만 이루어져 있습니다.
* cards1, cards2, goal의 문자열들은 모두 알파벳 소문자로만 이루어져 있습니다.

## 추가로 고려해야 할 사항

## 풀기 전 생각한 풀이 과정
* goal의 단어를 순서대로 cards1과 card2에서 찾아 존재한다면 해당 카드의 인덱스를 기록합니다.
* 카드에서 찾은 단어의 인덱스가 기록하고 있는 인덱스보다 작은 경우 return "No"
* 단어를 끝까지 찾았다면 return "Yes"

## 풀면서 추가로 고려한 사항
실패한 풀이들에서는 `카드를 사용하지 않고 다음 카드로 넘어갈 수 없습니다.` 라는 조건을 누락하고 풀었습니다.

## 느낀 점
조건을 정확하게 구현합니다....

