package programmers;

import java.util.Arrays;

public class Q_BestMultiSet {
    public static void main(String[] args) {
        int n = 2;
        int s = 100_000_000;

//        int n = 2;
//        int s = 1;
//
//        int n = 2;
//        int s = 8;

//        int n = 3;
//        int s = 11;

        System.out.println(Arrays.toString(solution(n, s)));
    }

    public static int[] solution(int n, int s) {
        if(n > s)
            return new int[]{-1};

        int[] answer = new int[n];
        Arrays.fill(answer, s / n);

        for(int i=0; i<s%n; i++)
            answer[i] ++;

        Arrays.sort(answer);

        return answer;
    }


}
