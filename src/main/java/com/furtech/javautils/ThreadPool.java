package com.furtech.javautils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @des 线程池的简单实现(可扩展)
 *
 * @author 719383495@qq.com | 719383495qq@gmail.com | 有问题可以邮箱或者github联系我
 * @date 2019/8/4 13:55
 */
public class ThreadPool {
    /**@des logger */
    private static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);
    private final int poolSize;
    private final LinkedBlockingQueue queue;
    private final PoolWorker[] runable;

    public ThreadPool(int poolSize) {
        this.poolSize = poolSize;
        queue = new LinkedBlockingQueue();
        runable = new PoolWorker[poolSize];
        for (int i = 0; i < poolSize; i++) {
            runable[i] = new PoolWorker();
            new Thread(runable[i], "pool-" + i).start();
        }
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    private class PoolWorker implements Runnable {
        @Override
        public void run() {
            Runnable task ;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (Exception e) {
                            logger.info("exception in queue waiting :{}",e.getMessage());
                        }
                    }
                    task = (Runnable) queue.poll();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    logger.info("run exception : {}", e.getMessage());
                }

            }
        }
    }

}

class ThreadPoolMain {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(5);
        int MaxSize = 100;
        for (int i = 0; i < MaxSize; i++) {
            pool.execute(() -> System.out.println(Thread.currentThread()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


