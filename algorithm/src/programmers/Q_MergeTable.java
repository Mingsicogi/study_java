package programmers;

import java.util.*;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/150366
 * - 2023 카카오 블라인드 채용 - 테이블 합치기
 * "UPDATE r c value"
 * r, c는 선택할 셀의 위치를 나타내며, 1~50 사이의 정수입니다.
 * value는 셀에 입력할 내용을 나타내며, 알파벳 소문자와 숫자로 구성된 길이 1~10 사이인 문자열입니다.
 * "UPDATE value1 value2"
 * value1은 선택할 셀의 값, value2는 셀에 입력할 내용을 나타내며, 알파벳 소문자와 숫자로 구성된 길이 1~10 사이인 문자열입니다.
 * "MERGE r1 c1 r2 c2"
 * r1, c1, r2, c2는 선택할 셀의 위치를 나타내며, 1~50 사이의 정수입니다.
 * "UNMERGE r c"
 * r, c는 선택할 셀의 위치를 나타내며, 1~50 사이의 정수입니다.
 * "PRINT r c"
 * r, c는 선택할 셀의 위치를 나타내며, 1~50 사이의 정수입니다.
 *
 * @author minseok
 */
public class Q_MergeTable {

    private static final Map<String, Point> table = new HashMap<>(250);
    private static final String defaultValue = "EMPTY";
    public static void main(String[] args) {
        String[] commands = {"UPDATE 1 1 menu", "UPDATE 1 2 category", "UPDATE 2 1 bibimbap", "UPDATE 2 2 korean", "UPDATE 2 3 rice", "UPDATE 3 1 ramyeon", "UPDATE 3 2 korean", "UPDATE 3 3 noodle", "UPDATE 3 4 instant", "UPDATE 4 1 pasta", "UPDATE 4 2 italian", "UPDATE 4 3 noodle", "MERGE 1 2 1 3", "MERGE 1 3 1 4", "UPDATE korean hansik", "UPDATE 1 3 group", "UNMERGE 1 4", "PRINT 1 3", "PRINT 1 4"};
//        String[] commands = {"UPDATE 1 1 a", "UPDATE 1 2 b", "UPDATE 2 1 c", "UPDATE 2 2 d", "MERGE 1 1 1 2", "MERGE 2 2 2 1", "MERGE 2 1 1 1", "PRINT 1 1", "UNMERGE 2 2", "PRINT 1 1"};

        String[] result = solution(commands);

        for (String r: result) {
            System.out.println(r);
        }
    }


    public static String[] solution(String[] commands) {
        List<String> answer = new ArrayList<>();

        for (String command : commands) {
             doCommand(command, answer);
        }

        return answer.toArray(String[]::new);
    }

    public static void doCommand(String command, List<String> answer) {
        String[] splitCommand = command.split(" ");
        int r, c, r2, c2;
        switch (splitCommand[0]) {
            case "UPDATE":
                if (splitCommand.length == 4) {
                    r = Integer.parseInt(splitCommand[1]);
                    c = Integer.parseInt(splitCommand[2]);
                    String value = splitCommand[3];
                    table.put(r + "_" + c, new Point(r, c, value));
                } else if (splitCommand.length == 3) {
                    String value1 = splitCommand[1];
                    String value2 = splitCommand[2];
                    for (Point point : table.values()) {
                        if (point.value.equals(value1)) {
                            point.value = value2;
                        }
                    }
                }
                break;
            case "MERGE":
                r = Integer.parseInt(splitCommand[1]);
                c = Integer.parseInt(splitCommand[2]);
                r2 = Integer.parseInt(splitCommand[3]);
                c2 = Integer.parseInt(splitCommand[4]);

                if (r == r2 && c == c2) {
                    break;
                }

                Point point1 = Objects.requireNonNullElse(table.get(r + "_" + c), new Point(r, c, defaultValue));
                Point point2 = Objects.requireNonNullElse(table.get(r2 + "_" + c2), new Point(r2, c2, defaultValue));

                if (!isEmptyValue(point1) && !isEmptyValue(point2)) {
                    point2.beforeValue = point2.value;
                    point2.value = point1.value;
                } else if (!isEmptyValue(point1) && isEmptyValue(point2)) {
                    point2.beforeValue = point2.value;
                    point2.value = point1.value;
                } else if (isEmptyValue(point1) && !isEmptyValue(point2)) {
                    point1.beforeValue = point1.value;
                    point1.value = point2.value;
                }

                point1.links.add(point2);
                point2.links.add(point1);

                break;
            case "UNMERGE":
                r = Integer.parseInt(splitCommand[1]);
                c = Integer.parseInt(splitCommand[2]);
                Point point = table.get(r + "_" + c);

                if (point == null) {
                    break;
                }

                point.links.forEach(p -> {
                    p.value = p.beforeValue;
                    p.beforeValue = null;
                    p.links.remove(point);
                });

                point.links.clear();

                break;
            case "PRINT":
                r = Integer.parseInt(splitCommand[1]);
                c = Integer.parseInt(splitCommand[2]);
                answer.add(Objects.requireNonNullElse(table.get(r + "_" + c), new Point(r, c, defaultValue)).value);
                break;
        }
    }

    private static boolean isEmptyValue(Point point) {
        return point.value == null || point.value.equals(defaultValue) || point.value.equals("");
    }

    public static class Point {
        int r;
        int c;
        String value;
        String beforeValue;

        Set<Point> links = new HashSet<>();

        public Point(int r, int c, String value) {
            this.r = r;
            this.c = c;
            this.value = value;
            table.put(r + "_" + c, this);
        }
    }
}
