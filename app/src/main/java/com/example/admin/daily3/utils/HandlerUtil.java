package com.example.admin.daily3.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerUtil {

    private static final int KEEP_ALIVE_TIME = 1;

    private static TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    public static final int CORE_POOL_SIZE = 3;

    public static final int MAXIMUM_POOL_SIZE = 3;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    // A queue of Runnables for the image download pool
    private BlockingQueue<Runnable> timerWorkQueue= new LinkedBlockingQueue<Runnable>();

    List<Runnable> new_list = new ArrayList<Runnable>();

    // A managed pool of background download threads
    private ThreadPoolExecutor timerThreadPool;

    private static HandlerUtil instance = null;
    Handler handler;

    private HandlerUtil()
    {


    }
    private HandlerUtil(Handler handler, List<Runnable> runThese) //Method overloading
    {
        this.handler = handler;
        this.new_list = runThese;
    }

    public static HandlerUtil with (Handler handler, List<Runnable> runThese)
    {
        if(instance == null)
        {
            instance = new HandlerUtil(handler, runThese);
        }
        instance.setHandler(handler);
        return instance;
    }

    private void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    public void sendMessage(String message)
    {
        Message msg = new Message();

        Bundle bundle = new Bundle();
        bundle.putString("message", message);

        msg.setData(bundle);

        handler.sendMessage(msg);
    }

    public void executeTasks()
    {

        timerThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, timerWorkQueue);

        for (int i = 0; i < new_list.size(); i++) {

            timerThreadPool.execute(new_list.get(i));
        }

    }
}
