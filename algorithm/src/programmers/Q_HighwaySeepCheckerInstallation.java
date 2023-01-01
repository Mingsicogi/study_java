package programmers;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Q_HighwaySeepCheckerInstallation {

    public static void main(String[] args) {
        int[][] routes = {{0,2},{2,3},{3,4},{4,6}};

        // answer 2
        System.out.println(solution(routes));
    }

    public static int solution(int[][] routes) {
//        AtomicInteger answer = new AtomicInteger(0);

        // 진출 기준으로 sort
//        List<Integer> outsOfRoutes = new ArrayList<>();
//        Map<Integer, Integer> routesMap = new HashMap<>(10000);
//        for (int[] route : routes) {
//            outsOfRoutes.add(route[1]);
//            routesMap.put(route[1], route[0]);
//        }
//
//        AtomicInteger beforeOut = new AtomicInteger(-30001);
//        outsOfRoutes.stream().sorted().filter(out -> beforeOut.get() < routesMap.get(out)).forEach(out -> {
//            answer.addAndGet(1);
//            beforeOut.set(out);
//        });
        // 진출 기준으로 sort
//        SortedMap<Integer, Integer> routesSortByOut = new TreeMap<>();
//        for (int[] route : routes) {
//            routesSortByOut.put(route[1], route[0]);
//        }
//
//        AtomicInteger beforeOut = new AtomicInteger(-30001);
//        routesSortByOut.forEach((out, in) -> {
//            if (beforeOut.get() < in) {
//                answer.addAndGet(1);
//                beforeOut.set(out);
//            }
//        });
//        return answer.get();

        Arrays.sort(routes, Comparator.comparingInt(route -> route[1]));

        int answer = 0;
        int cam = Integer.MIN_VALUE;

        for(int[] route : routes) {
            if(cam < route[0]) {
                cam = route[1];
                answer++;
            }
        }

        return answer;
    }
}
