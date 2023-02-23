package minssogi.study.theory.hashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapDeepDive {
    public static void main(String[] args) {

        HashMap<Test, String> map = new HashMap<>(1000000000);
//
//        Test testDto = new Test("kk");
//        map.put(testDto, "a");
//
//        int hashCode = testDto.hashCode();
//        System.out.println(hashCode);
//
        Test[] table = new Test[16];
//
        int n = table.length;
//        table[hashCode & (n - 1)] = testDto;
//
//        System.out.println(table[hashCode & (n - 1)]);

        for (int i = 0; i < 100; i++) {
            Test test = new Test("kk");
            System.out.println(test.hashCode() & (n - 1));
        }
    }

    public static class Test {
       private String value;

         public Test(String value) {
              this.value = value;
         }
    }
}
