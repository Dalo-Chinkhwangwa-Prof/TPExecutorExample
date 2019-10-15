package com.example.admin.daily3.tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.admin.daily3.utils.HandlerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyRunnable implements Runnable {


    Handler handler;

    ArrayList<Integer> randomTimes;

    List<Runnable> list_of_runnables = new ArrayList<Runnable>();

    List<TextView> tTimeArray = new ArrayList<TextView>();

    String msg;

    public MyRunnable(Handler handler,  List<TextView> tTimeArray)
    {
        this.handler = handler;
        this.tTimeArray = tTimeArray;
    }

    @Override
    public void run()
    {
        GenerateRandomTime grt = new GenerateRandomTime();
        this.randomTimes = grt.randomTimes();


        for (int i = 0; i < randomTimes.size(); i++)
        {
                if((i+1) < randomTimes.size())
                    msg += randomTimes.get(i)+",";

                else
                    msg += randomTimes.get(i);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < randomTimes.size() ; i++)
                {
                    tTimeArray.get(i).setText("Time : "+randomTimes.get(i) + " Seconds");
                    list_of_runnables.add(new TaskRunnable(i, randomTimes.get(i), handler));
                    HandlerUtil.with(handler, list_of_runnables).sendMessage("time_alloc:" + (i+1) + ":" + randomTimes.get(i));
                }

                HandlerUtil.with(handler, list_of_runnables).executeTasks();

            }
        });
    }
}
