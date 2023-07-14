package com.boomcat.boomcat.learn;

import java.util.concurrent.*;

public class Timeout {
    public static void main(String[] args){
        System.out.println("执行第一部分代码。。。。");
        System.out.println(new Timeout().test());
        System.out.println("执行第三部分代码。。。。");
    }

    private String test(){
        String result = "";
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<String> call = () -> {
            //开始执行耗时操作
            new Thread().sleep(5000);
            return "执行第二部分代码";
        };

        try {
            Future<String> future = exec.submit(call);
            result = future.get(1000 * 3, TimeUnit.MILLISECONDS); //任务处理超时时间设为 3 秒
        } catch (TimeoutException ex) {
            System.out.println("调用接口，处理超时......");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("调用接口，处理失败......");
            e.printStackTrace();
        }
        // 关闭线程池
        exec.shutdown();
        return result;
    }

}
