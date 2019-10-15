package com.example.admin.daily3.tasks;

import android.os.Handler;
import android.util.Log;

import com.example.admin.daily3.utils.HandlerUtil;

public class TaskRunnable implements Runnable {
    Handler handler;
    int time_to_run;
    private int timer = 0;
    int pos;

    public TaskRunnable(int pos, int time_to_run, Handler handler)
    {
        this.time_to_run = time_to_run*1000;
        this.handler = handler;
        this.pos = pos;
    }

    @Override
    public void run() {

        timer = 0;
                HandlerUtil.with(handler, null).sendMessage("thread_name:"+(pos+1)+":"+Thread.currentThread().getName());

        try {

            while (time_to_run >= timer) {

                Thread.currentThread().sleep(1000);

                timer += 1000;
                Log.d("TAG_DE", Thread.currentThread().getName()+"_TAG ......"+(time_to_run/1000) + " vs " + (timer/1000));
                HandlerUtil.with(handler, null).sendMessage("time_prog:"+(pos+1) + ":"+(timer/1000));

            }

            Log.d("TAG_DE", Thread.currentThread().getName()+"_TAG "+ "DONE!");

        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        HandlerUtil.with(handler, null).sendMessage("thread_name:"+(pos+1)+":"+ "DONE!");
        HandlerUtil.with(handler, null).sendMessage("end:"+(pos+1));
    }
}
