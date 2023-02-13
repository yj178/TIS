package Lv1.크기가_작은_부분_문자열;

class Solution {
    public static void main(String[] args) {
        String t = "3141592";
        String p = "271";
        Solution problem = new Solution();
        System.out.println(problem.solution(t, p));
    }


    public int solution(String t, String p) {
        int answer = 0;
        long longP = Long.parseLong(p);

        int eIdx = p.length()-1;
        int len = t.length();
        StringBuilder sb = new StringBuilder("0" + t.substring(0, eIdx));
        while (eIdx < len) {
            sb.deleteCharAt(0);
            sb.append(t.charAt(eIdx++));
            if (Long.parseLong(sb.toString()) <= longP)
                answer++;
        }
        return answer;
    }
}
