package com.example.mytools;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expresscab.R;

public class MyToastUtil {
    public static Toast getCustomToast(Activity context, String msg, int duration, int picId){
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom_styles,
                (ViewGroup)context.findViewById(R.id.ll_Toast));
        ImageView image = (ImageView) layout.findViewById(R.id.iv_ImageToast);
        image.setImageResource(picId);
        TextView text = (TextView) layout.findViewById(R.id.tv_TextToast);
        text.setText(msg);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER|Gravity.BOTTOM, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);
        return toast;
    }
}
