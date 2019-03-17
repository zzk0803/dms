package zzk.project.dms.utilies;

import org.springframework.context.ApplicationContext;

public class SpringBeansUtil {
    public static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeansUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static Object getBean(Class<?> clazz) {
        return applicationContext.getBean(clazz);
    }
}
