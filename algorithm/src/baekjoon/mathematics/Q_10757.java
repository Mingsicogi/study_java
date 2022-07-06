package baekjoon.mathematics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * https://www.acmicpc.net/problem/10757
 *
 */
public class Q_10757 {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String[] line = br.readLine()
                    .split(" ");
            System.out.println(new BigInteger(line[0]).add(new BigInteger(line[1])));
        } catch (Exception e) {
        }
    }
}
