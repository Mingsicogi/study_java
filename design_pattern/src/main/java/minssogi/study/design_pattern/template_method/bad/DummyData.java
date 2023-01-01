package minssogi.study.design_pattern.template_method.bad;

import java.util.Map;

public class DummyData {

    public static Map<String, User> NAVER_USER_DB_DUMMY_DATA = Map.of(
            "minssogi", new User("minssogi", "123", "Minseok Jeon"),
            "smalldevil", new User("smalldevil", "123", "Pilgeun Kim"),
            "eomsh", new User("eomsh", "123", "Seung-a Um"),
            "myh814", new User("myh814", "123", "Youngha Moon")
    );

    public static Map<String, User> KAKAO_USER_DB_DUMMY_DATA = Map.of(
            "dlcorud29", new User("dlcorud29", "123", "Chaegyeong Lee"),
            "cmkim", new User("cmkim", "123", "E-An Kim"),
            "daeunc", new User("daeunc", "123", "Daeun Jang")
    );
}
