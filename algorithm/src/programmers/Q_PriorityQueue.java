package programmers;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Q_PriorityQueue {
    public static void main(String[] args) {
//        String[] command = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};
        String[] command = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};

        for (int a : solution(command)) {
            System.out.println(a);
        }
    }

    public static int[] solution(String[] operations) {
        int[] answer = new int[2];

        PriorityQueue<Integer> reverseQueue = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.naturalOrder());

        Set<Integer> removedOfReverseQueue = new HashSet<>();
        Set<Integer> removedOfQueue = new HashSet<>();

        for (String operation : operations) {
            String[] split = operation.split(" ");
            int number = Integer.parseInt(split[1]);
            switch (split[0]) {
                case "I":
                    reverseQueue.add(number);
                    queue.add(number);
                    break;
                case "D":
                    if (number == 1) {
                        Integer val = 0;

                        do {
                            val = reverseQueue.poll();
                        } while (!removedOfQueue.contains(val));

                        if (val != 0) {
                            removedOfReverseQueue.add(val);
                        }
                    } else if (number == -1) {
                        Integer val = 0;

                        do {
                            val = queue.poll();
                        } while (!removedOfReverseQueue.contains(val));

                        if (val != 0) {
                            removedOfQueue.add(val);
                        }
                    }
                    break;
            }
        }

        answer[0] = findAnswer(reverseQueue, removedOfQueue);
        answer[1] = findAnswer(queue, removedOfReverseQueue);

        return answer;
    }

    private static int findAnswer(PriorityQueue<Integer> queue, Set<Integer> removedOfQueue) {
        do {
            Integer val = queue.poll();
            if (val != null && !removedOfQueue.contains(val)) {
                return val;
            }
        } while (!queue.isEmpty());

        return 0;
    }
}
