package examples.springboot;

import io.github.jrasa.Action;
import io.github.jrasa.ActionExecutor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JRasaActionListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private ConfigurableListableBeanFactory beanFactory;

    @Resource
    private ActionExecutor actionExecutor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        beanFactory.getBeansOfType(Action.class).values().forEach(
                action -> actionExecutor.registerAction(action)
        );
    }
}
