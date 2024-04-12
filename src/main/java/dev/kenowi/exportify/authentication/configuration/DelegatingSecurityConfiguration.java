package dev.kenowi.exportify.authentication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
class DelegatingSecurityConfiguration implements AsyncConfigurer {


    @Override
    public Executor getAsyncExecutor() {

        //TODO Validate if this OK.
        // new SimpleAsyncTaskExecutor() vs new SimpleAsyncTaskScheduler() vs new ThreadPoolTaskExecutor().initialize();

        return new DelegatingSecurityContextAsyncTaskExecutor(new SimpleAsyncTaskScheduler());
    }

/*
    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        DelegatingSecurityContextAsyncTaskExecutor executor = new DelegatingSecurityContextAsyncTaskExecutor(new SimpleAsyncTaskScheduler());
        eventMulticaster.setTaskExecutor(executor);
        return eventMulticaster;
    }
*/
}
