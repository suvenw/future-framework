package com.suven.framework.test.junit;

import com.suven.framework.util.ids.GlobalId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GeneratorTest {

    @Test
    public void testIdGenerator() {
        long avg = 0;
        for (int k = 0; k < 10; k++) {
            List<Callable<Long>> partitions = new ArrayList<Callable<Long>>();
            final GlobalId idGen = GlobalId.get();
            for (int i = 0; i < 1400000; i++) {
                partitions.add(new Callable<Long>() {
                    @Override
                    public Long call() {
                        return idGen.nextId();
                    }
                });
            }
            ExecutorService executorPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            try {
                long s = System.currentTimeMillis();
                executorPool.invokeAll(partitions, 10000, TimeUnit.SECONDS);
                long s_avg = System.currentTimeMillis() - s;
                avg += s_avg;
                System.out.println("完成时间需要: " + s_avg / 1.0e3 + "秒");
                executorPool.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(GlobalId.get().nextId() +"平均完成时间需要: " + avg / 10 / 1.0e3 + "秒");
        for (int k = 0; k < 1000; k++) {
             System.out.println(GlobalId.get().nextId() +"平均完成时间需要: " + avg / 10 / 1.0e3 + "秒");
        }
        long d = 911108431874621445l;
    }
}