package minssogi.study.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

class SecurityApplicationTests {

    @Test
    void contextLoads() {

        String a = "?경북 경산, 하양여자중학교?\n" +
                "\n" +
                "이 글 보시는 하양여중 학생 분들, 공감되시는 분은\n" +
                "이 글 복사해서 배달의민족 게시물에 붙여넣기 해 주세요!!\n" +
                "\n" +
                "먼저 신고 좀 하지 말아주세요!\n" +
                "까칠하고 배고픈 중학생 입니다. 저흰 잘못 안 했어요.\n" +
                "\n" +
                "안녕하세요 배달의 민족님\n" +
                "구구절절 저희 반 사연부터 말씀드릴게요\n" +
                "저희 반 선생님 께서는 짜장면을 사 주신다고 몇 달 전부터 말씀 하셨습니다. 그런데 그 몇 달이 6개월이 지났네요... 그래도 선생님은 벼르지도 않으십니다.. 저희 반 마른 아이들은 하루 하루가 지날 수록 말라가고 빈약해져 갑니다.\n" +
                "어떻게 생각하시나요?\n" +
                "한창 많이 먹고 크게 자라나야 할 아이들이\n" +
                "먹고싶은 거 하나 못 먹고 이렇게 굶주린 학생들이 되어갑니다......\n" +
                "우리는 짜장면이 먹고싶습니다.\n" +
                "그 검은 면발에 고소하고 짭조름한 그 양념을, 기름기는 있지만 목넘김이 스무스한 그런. 짜장면이 먹고싶습니다.\n" +
                "어젠 급식 스파게티에서 노랑 검정 콜라보 애벌레가 나왔구요.\n" +
                "학교가 산 꼭대기고 걸어서 가지도 못 해서 짜장면 먹기도 힘듭니다.\n" +
                "저흴 위해 항상 고생하시는 선생님들을 위해.\n" +
                "저희 학교는 전교생 1000명도 안 되는 조그만 학교에 많은 재능을 기부 해 주시는 선생님들을 위해서라도\n" +
                "짜장면 1000그릇 꼭 먹고 싶습니다.\n" +
                "\n" +
                "저와 함께 하는 동지 입니숙희숙민경민다연다지민지지영지손예진예진예진 @정다연 @백민지\n" +
                "모두가 마르고 굶주린 가엾은 아이들입니다.\n" +
                "\n" +
                "저희 학교 전체가 짜장면을 갈구 합니다.\n" +
                "\n" +
                "그리고 욕 좀 하지 마삼\n" +
                "\n" +
                "사랑합니다. 배달의 민족 ❤";

        char[] parent = a.toCharArray();
        char[] pattern = "하양중학교".toCharArray();
        System.out.println("target : " + Arrays.toString(parent));
        System.out.println("pattern : " + Arrays.toString(pattern));
        kmp(parent, pattern);
    }

    static int[] makeTable(char[] p) {
        int psize = p.length;
        int[] table = new int[psize];
        int j = 0;
        for (int i = 1; i < psize; i++) {
            while (j > 0 && p[i] != p[j]) {
                j = table[j - 1];
            }
            if (p[i] == p[j]) {
                table[i] = ++j;
            }
        }
        return table;
    }

    static void kmp(char[] parent, char[] pattern) {
        int[] table = makeTable(pattern);
        int parentSize = parent.length;
        int patternSize = pattern.length;
        int j = 0;
        for (int i = 0; i < parentSize; i++) {
            while (j > 0 && parent[i] != pattern[j]) {
                j = table[j - 1];
            }
            if (parent[i] == pattern[j]) {
                if (j == patternSize - 1) {
                    System.out.printf("%d. \n", i - patternSize + 1);
                    j = table[j];
                } else {
                    j++;
                }
            }
        }
    }

}
