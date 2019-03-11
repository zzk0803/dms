package zzk.project.dms.middle;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Executors;

public class DmsEventBusService implements InitializingBean {
    private static EventBus eventBus;

    @Autowired
    private ApplicationContext applicationContext;

    public static void publish(Object eventObject) {
        eventBus.post(eventObject);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        eventBus = new AsyncEventBus(
                "domain-event-bus",
                Executors.newCachedThreadPool()
        );
        applicationContext.getBeansWithAnnotation(SubscriberAndService.class)
                .forEach((name, bean) -> eventBus.register(bean));
    }
}
