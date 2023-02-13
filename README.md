# TIS Convention
Today I Solved를 repository 운영 방식

## commit Convention
{YYYYMMDD}-{state}-{문제 이름}-{#이슈번호}: 한줄평

세부 항목   
#풀이 방법 태그   

ex) 20230131-solved-업무처리-#10: 문제 통과 / 풀이 완료
  
완전 이진 트리 형식으로 그래프를 생성하고 계층별 node의 인덱스 관계를 파악하여 문제를 해결함   

#complete binary tree   

### state 종류
* solving : 문제 푸는 중 (풀다가 다른 작업을 하게 되는 경우 임시로 저장)
* solved : 문제 풀이 완료
* refactor : TIS에 올린 내용 중 변경 사항이 발생한 경우
* docs : readme, solution과 같은 문서를 작성하는 경우


## package Convention
* algorithmTheorem : 문제 풀면서 정리가 필요한 내용들
  * algoritm name : 알고리즘 이름
    * name.md : 알고리즘 내용 정리
    * name.java : 정리하 내용 검증용 코드
* module - 문제풀이 사이트 명
  * level - 각 사이트의 문제 레벨
    * name - 문제 이름
      * 각 사이트에 요구하는 클래스 명으로 설정
      * solution.md : 문제 풀이 방법
