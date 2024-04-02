package de.wittenbude.exportify.application.configuration;

import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@SuppressWarnings({"deprecation"})
public class ContextAwarePoolExecutor extends ThreadPoolTaskExecutor {
    @Override
    @NonNull
    public <T> Future<T> submit(@NonNull Callable<T> task) {
        return super.submit(new ContextAwareCallable<>(task, RequestContextHolder.currentRequestAttributes()));
    }

    @Override
    @NonNull
    public <T> ListenableFuture<T> submitListenable(@NonNull Callable<T> task) {
        return super.submitListenable(new ContextAwareCallable<>(task, RequestContextHolder.currentRequestAttributes()));
    }


}
