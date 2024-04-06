package dev.kenowi.exportify.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

/*
    @Override
    public Executor getAsyncExecutor() {

        //TODO Validate if this OK.
        // new SimpleAsyncTaskExecutor() vs new SimpleAsyncTaskScheduler() vs new ThreadPoolTaskExecutor().initialize();

        return new DelegatingSecurityContextAsyncTaskExecutor(new SimpleAsyncTaskScheduler());
    }
*/

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        DelegatingSecurityContextAsyncTaskExecutor executor = new DelegatingSecurityContextAsyncTaskExecutor(new SimpleAsyncTaskScheduler());
        eventMulticaster.setTaskExecutor(executor);
        return eventMulticaster;
    }

}
