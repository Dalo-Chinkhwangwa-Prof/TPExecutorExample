package com.example.admin.daily3.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateRandomTime {

    ArrayList<Integer> randomTimes = new ArrayList<Integer>();
    Random random;
    int time;

    public GenerateRandomTime()
    {}

    public ArrayList<Integer> randomTimes()
    {
        random = new Random();

        for (int i = 0; i < 4; i++) {

            time = random.nextInt(19)+1;
            randomTimes.add(time);

        }

        return  randomTimes;
    }

}
