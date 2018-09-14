package com.example.mytools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

    public static String[] DescSplit(String text){
        String regx = "\\d";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(text);

        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);


        String[] str = pattern.split(text);

        String[] sep = new String[2];

        sep[0] = str[0];
        sep[1] = m.replaceAll("");

        return sep;
    }
}
