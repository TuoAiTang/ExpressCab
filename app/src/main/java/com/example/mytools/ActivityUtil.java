package com.example.mytools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.expresscab.GetExpActivity;
import com.example.expresscab.RetrieveActivity;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityUtil {

    public static void delayJump(final Activity packageContext, final Class<?> cls, int delay){
        Timer time = new Timer();
        TimerTask tk = new TimerTask() {
            Intent intent = new Intent(packageContext, cls);
            @Override
            public void run() {
                packageContext.startActivity(intent);
                packageContext.finish();
            }
        };
        time.schedule(tk, delay);
    }
}
