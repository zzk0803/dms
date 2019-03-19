package zzk.project.dms.middle;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DmsEventBusService implements ApplicationContextAware {
    private static EventBus eventBus;

    @Autowired
    private ApplicationContext applicationContext;

    public static void publish(Object eventObject) {
        eventBus.post(eventObject);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        eventBus = new EventBus("domain-event-bus");
        applicationContext.getBeansWithAnnotation(ServiceAndSubscriber.class)
                .forEach((name, bean) -> eventBus.register(bean));
        System.out.println("-------------------------");
        System.out.println("eventBus Has Wired " + eventBus.toString());
        System.out.println("-------------------------");



    }
}
