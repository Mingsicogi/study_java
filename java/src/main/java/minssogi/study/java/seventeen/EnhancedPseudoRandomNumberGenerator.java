package minssogi.study.java.seventeen;

import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

public class EnhancedPseudoRandomNumberGenerator {
    public static void main(String[] args) {
        EnhancedPseudoRandomNumberGenerator generator = new EnhancedPseudoRandomNumberGenerator();
        generator.getPseudoInts("L32X64MixRandom", 10).forEach(System.out::println);
    }

    public IntStream getPseudoInts(String algorithm, int streamSize) {
        // returns an IntStream with size @streamSize of random numbers generated using the @algorithm
        // where the lower bound is 0 and the upper is 100 (exclusive)
        return RandomGeneratorFactory.of(algorithm)
                .create()
                .ints(streamSize, 0,100);
    }


}


