package programmers;

import java.util.Arrays;


public class Q_NumberGame {
    public static void main(String[] args) {
        int[] A = new int[]{5,1,3,7};
        int[] B = new int[]{2,2,6,8};

//        int[] A = new int[]{2,2,2,2};
//        int[] B = new int[]{1,1,1,1};

        System.out.println(solution(A, B));
    }

    public static int solution(int[] A, int[] B) {
        int answer = 0;

        Arrays.sort(A);
        Arrays.sort(B);

        int indexOfA = 0;
        int indexOfB = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[indexOfA] > B[indexOfB]) {
                indexOfB++;
            } else if (A[indexOfA] == B[indexOfB]) {
                indexOfB++;
            } else {
                indexOfA++;
                indexOfB++;
                answer++;
            }
        }
        return answer;
    }
}
