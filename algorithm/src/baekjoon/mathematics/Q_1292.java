package baekjoon.mathematics;

import java.util.Scanner;

/**
 * https://www.acmicpc.net/problem/1292
 * - 1을 한 번, 2를 두 번, 3을 세 번, 이런 식으로 1 2 2 3 3 3 4 4 4 4 5 ..
 */
public class Q_1292 {
    public static void main(String[] args) {
        // data initialize
        int MAX_SIZE = 1_000;
        int[] dataList = new int[MAX_SIZE * MAX_SIZE + 1];
        int dataListIndex = 1;
        for (int i = 1; i <= MAX_SIZE; i++) {
            for (int j = 0; j < i; j++) {
                dataList[dataListIndex++] = i;
            }
        }

        Scanner scanner = new Scanner(System.in);

        int start = scanner.nextInt();
        int end = scanner.nextInt();

        int result = 0;
        for (int i = start; i <= end; i++) {
            result += dataList[i];
        }

        System.out.println(result);
    }
}
