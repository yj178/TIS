# TIS Convention
Today I Solved repository 사용 절차

## 문제 풀이 절차
1. [이슈 생성](https://github.com/yj178/TIS/blob/main/.github/ISSUE_TEMPLATE/a-problem-i-will-solve-today-.md)
2. 이슈에서 branch 생성
3. 문제 읽고 풀이 작성 
4. 풀이 내용으로 코드 작성
5. 풀이 내용에서 고려하지 못한 점이나 그 외 특이사항 정리
6. pull request 생성 후 main branch에 병합

## commit Convention
{YYYYMMDD}-{type}-{#이슈번호}: 한줄평 

내용 : fix, docs, refactor의 경우 왜 했는지, 어떤 점을 변경했는지 작성할 것

20230307-fix-#53: git convention 수정

fix와 refactor 사용이 필요한 경우를 명확히 나눔
기존의 problem은 커밋상에서 확인할 때 문제가 발생했다는 의미로 받아들일 수 있을 것으로 보여 feat으로 변경


### type 종류
* feat : 알고리즘 문제 코드 작성, 알고리즘 문제 풀이 문서 작성하는 경우
* docs : readme, issue template과 같은 문서를 작성하는 경우
* fix : 코드의 기능을 수정하거나 문서 내용을 수정하는 경우
* refactor : 코드의 가독성을 향상시키기 위해 수정하는 경우

## package Convention
* module - 문제풀이 사이트 명
  * level - 각 사이트의 문제 레벨
    * name - 문제 이름
      * 각 사이트에 요구하는 클래스 명으로 설정
      * solution.md : 문제 풀이 방법
