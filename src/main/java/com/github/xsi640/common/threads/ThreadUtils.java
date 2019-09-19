package com.github.xsi640.common.threads;

import com.github.xsi640.common.Ref;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 线程工具类
 * 提供多线程方式运行的方法
 */
public class ThreadUtils {
    /**
     * 开启一个线程并立即运行
     *
     * @param runnable 执行的方法
     */
    public static void run(final Runnable runnable) {
        run(runnable, null, 0);
    }

    /**
     * 开启一个线程，延时后运行
     *
     * @param runnable     执行的方法
     * @param milliseconds 延时的毫秒数
     */
    public static void run(final Runnable runnable, final long milliseconds) {
        run(runnable, null, milliseconds);
    }

    /**
     * 开启一个线程立即运行
     *
     * @param future 执行的方法
     * @param <T>
     */
    public static <T> void run(final FutureTask<T> future) {
        if (future == null)
            return;
        Thread thread = new Thread(future);
        thread.start();
    }

    /**
     * 开启一个线程，延时后运行
     *
     * @param future       执行的方法
     * @param milliseconds 延时的毫秒数
     * @param <T>
     */
    public static <T> void run(final FutureTask<T> future, final long milliseconds) {
        if (future == null)
            return;

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(milliseconds);
                future.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    /**
     * 开启一个线程，立即运行（带返回值）
     *
     * @param callable 带返回值的方法
     * @param ref      返回值封装类
     * @param <T>
     */
    public static <T> void run(final Callable<T> callable, final Ref<T> ref) {
        if (callable == null)
            return;
        ThreadUtils.run(() -> {
            try {
                ref.setValue(callable.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 开启一个线程，延时执行（带返回值）
     *
     * @param callable     带返回值的方法
     * @param ref          返回值的封装类
     * @param milliseconds 延时的毫秒数
     * @param <T>
     */
    public static <T> void run(final Callable<T> callable, final Ref<T> ref, final long milliseconds) {
        if (callable == null)
            return;
        ThreadUtils.run(() -> {
            try {
                Thread.sleep(milliseconds);
                ref.setValue(callable.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 开启一个线程，延时执行（带回调支持参数）
     *
     * @param callable     带返回值的方法
     * @param callback     回调方法
     * @param milliseconds 延时的毫秒数
     * @param <T>
     */
    public static <T> void run(final Callable<T> callable, final Callback<T> callback, final long milliseconds) {
        if (callable == null)
            return;
        new Thread(() -> {
            try {
                if (milliseconds > 0)
                    Thread.sleep(milliseconds);
                T t = callable.call();
                if (callback != null) {
                    callback.back(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 开启一个线程，延时执行（带回调不支持参数）
     *
     * @param runnable     带返回值的方法
     * @param callback     回调方法
     * @param milliseconds 延时的毫秒数
     */
    public static void run(final Runnable runnable, final Runnable callback, final long milliseconds) {
        if (runnable == null)
            return;
        new Thread(() -> {
            if (milliseconds > 0) {
                try {
                    Thread.sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runnable.run();
            if (callback != null)
                callback.run();
        }).start();
    }
}
