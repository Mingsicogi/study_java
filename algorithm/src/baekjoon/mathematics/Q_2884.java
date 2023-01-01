package baekjoon.mathematics;

import java.util.Scanner;

/**
 * https://www.acmicpc.net/problem/2884
 *  - 바로 "45분 일찍 알람 설정하기"이다. 이 방법은 단순하다. 원래 설정되어 있는 알람을 45분 앞서는 시간으로 바꾸는 것이다. 어차피 알람 소리를 들으면, 알람을 끄고 조금 더 잘 것이기 때문이다.
 *
 */
public class Q_2884 {
    public static void main(String[] args) {
        int EARLY_MINUTES = 45;

        Scanner scanner = new Scanner(System.in);

        int hour = scanner.nextInt(); //23hours 59minutes + 1minute == 0 0
        int minute = scanner.nextInt(); // 59 -> 60minutes == 1hour

        // -45 minutes
        minute = minute - EARLY_MINUTES;
        if (minute < 0) {
            hour -= 1;
            minute = 60 + minute;
        }

        if (hour < 0) {
            hour = 23;
        }


        System.out.println(hour + " " + minute);
    }
}
