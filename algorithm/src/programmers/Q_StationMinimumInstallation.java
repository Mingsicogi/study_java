package programmers;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12979
 * - 30점
 *
 */
public class Q_StationMinimumInstallation {

    public static void main(String[] args) {
//        int n = 11;
        int n = 20;
//        int[] station = {4, 11};
        int[] station = {20};
//        int w = 1;
        int w = 19;
//        int answer = 3;
        int answer = 1;

        System.out.println(solution(n, station, w));
        if (solution(n, station, w) != answer) throw new RuntimeException();
    }

    public static int solution(int n, int[] stations, int w) {
        int answer = 0;

        int beforeStation = 1;
        for (int station : stations) {
            int criteria = station - (2 * w) - 1;
            while (criteria > beforeStation) {
                answer++;

                int tempCriteria = criteria - (2 * w) - 1;
                if (tempCriteria < beforeStation + w) {
                    answer++;
                    break;
                }

                criteria = tempCriteria;
            }

            beforeStation = station;
        }

        int lastStation = stations[stations.length - 1];
        if (lastStation + w < n) {
            int criteria = lastStation + (2 * w) + 1;
            do {
                answer++;
                if (criteria + w >= n) {
                    break;
                }
                criteria = criteria + (2 * w) + 1;
            } while (criteria <= n);
        }

        return answer;
    }
}
