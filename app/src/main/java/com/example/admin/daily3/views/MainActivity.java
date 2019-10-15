package com.example.admin.daily3.views;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.daily3.R;
import com.example.admin.daily3.tasks.MyRunnable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    ProgressBar pBar1, pBar2, pBar3, pBar4;

    TextView tName1, tName2, tName3, tName4;

    TextView tTime1, tTime2, tTime3, tTime4;

    double inc1, inc2, inc3, inc4;

    TextView[] tTimeA = null;

    List<TextView> tTimeList = new ArrayList<TextView>();

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createViewItems();

        handler = new Handler(this);
    }

    public void createViewItems() {
        pBar1 = findViewById(R.id.pBar1);
        pBar2 = findViewById(R.id.pBar2);
        pBar3 = findViewById(R.id.pBar3);
        pBar4 = findViewById(R.id.pBar4);

        pBar1.setProgress(0);
        pBar2.setProgress(0);
        pBar3.setProgress(0);
        pBar4.setProgress(0);

        tName1 = findViewById(R.id.t1ThreadName1);
        tName2 = findViewById(R.id.t1ThreadName2);
        tName3 = findViewById(R.id.t1ThreadName3);
        tName4 = findViewById(R.id.t1ThreadName4);

        tTime1 = findViewById(R.id.t1Time1);
        tTime2 = findViewById(R.id.t1Time2);
        tTime3 = findViewById(R.id.t1Time3);
        tTime4 = findViewById(R.id.t1Time4);

        tTimeList.add(tTime1);
        tTimeList.add(tTime2);
        tTimeList.add(tTime3);
        tTimeList.add(tTime4);

    }

    public void onGenerate(View view)
    {
        tName1.setText("n/a");
        tName2.setText("n/a");
        tName3.setText("n/a");
        tName4.setText("n/a");

        MyRunnable mrun = new MyRunnable(handler, tTimeList);
        Thread x  = new Thread(mrun);
        x.start();
    }

    @Override
    public boolean handleMessage(Message msg)
    {
        String message = msg.getData().getString("message");
        String code = "";

        String [] tokenizer = null;
        tokenizer = message.split(":");

        switch(tokenizer[0])
        {
            case "thread_name":


                switch(tokenizer[1])
                {
                    case "1":
                        tName1.setText(tokenizer[2]);
                        break;
                    case "2":
                        tName2.setText(tokenizer[2]);
                        break;
                    case "3":
                        tName3.setText(tokenizer[2]);
                        break;
                    case "4":
                        tName4.setText(tokenizer[2]);
                        break;

                }
                break;

            case "time_alloc":
                switch (tokenizer[1])
                {
                    case "1":
                        inc1 = 100 / Integer.parseInt(tokenizer[2]);
                        break;
                    case "2":
                        inc2 = 100 / Integer.parseInt(tokenizer[2]);
                        break;
                    case "3":
                        inc3 = 100 / Integer.parseInt(tokenizer[2]);
                        break;
                    case "4":
                        inc4 = 100 / Integer.parseInt(tokenizer[2]);
                        break;

                }

                break;

            case "time_prog":
                switch (tokenizer[1])
                {
                    case "1":
                        pBar1.setProgress(Integer.parseInt(tokenizer[2])*(int)inc1);
                        break;
                    case "2":
                        pBar2.setProgress(Integer.parseInt(tokenizer[2])*(int)inc2);
                        break;
                    case "3":
                        pBar3.setProgress(Integer.parseInt(tokenizer[2])*(int)inc3);
                        break;
                    case "4":

                        pBar4.setProgress(Integer.parseInt(tokenizer[2])*(int)inc4);
                        break;

                }
                break;

            case "end":
                switch (tokenizer[1])
                {
            case "1":
                pBar1.setProgress(0);
                break;
            case "2":
                pBar2.setProgress(0);
                break;
            case "3":
                pBar3.setProgress(0);
                break;
            case "4":
                pBar4.setProgress(0);
                break;}
                break;
        }

        return false;
    }

}

