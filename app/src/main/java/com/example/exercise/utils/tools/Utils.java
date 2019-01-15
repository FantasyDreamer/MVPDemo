package com.example.exercise.utils.tools;

import android.os.Build;

/**
 * Created by Administrator on 2019/1/10 0010.
 */

public class Utils {
    //判断手机SDK版本
    public static boolean judgeSDK(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return true;
        }
        return false;
    }
}
