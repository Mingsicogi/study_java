package minssogi.study.ehcache.utils;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContainerUtil {

    private static GenericApplicationContext context;

    public SpringContainerUtil(GenericApplicationContext context) {
        SpringContainerUtil.context = context;
    }

    public static <T> T getBean(String beanName, Class<T> clz) {
        return context.getBean(beanName, clz);
    }

    public static <T> T getBean(Class<T> clz) {
        return context.getBean(clz);
    }
}
