package com.example.mytools;

import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCount extends CountDownTimer {
    private Button btn;

    public TimeCount(long paramLong1, long paramLong2, Button paramButton)
    {
        super(paramLong1, paramLong2);
        this.btn = paramButton;
    }

    public void onFinish()
    {
        this.btn.setText("获取验证码");
        this.btn.setClickable(true);
    }

    public void onTick(long paramLong)
    {
        this.btn.setClickable(false);
        this.btn.setText(paramLong / 1000L + "秒后\n重新获取");
    }
}
