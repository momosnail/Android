package com.wgl.tdlib.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 待优化
 */
public class ToastUtils {
    /** 之前显示的内容 */
    private static String oldMsg ;
    /** Toast对象 */
    private static Toast toast = null ;
    /** 第一次时间 */
    private static long oneTime = 0 ;
    /** 第二次时间 */
    private static long twoTime = 0 ;
    //单例的Toast ，避免重复调用
    public static void showToast(Context context, String msg){
        if(toast == null){
            Log.e("---","----------------------1");
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
         /*   oneTime = System.currentTimeMillis() ;
            oldMsg=msg;
            toast.show() ;*/
        }else{
           /* twoTime = System.currentTimeMillis() ;
            if(msg.equals(oldMsg)){
                Log.e("---t:","twoTime----"+twoTime+"   oneTime---"+oneTime+" result:"+(twoTime-oneTime)+"  short:"+Toast.LENGTH_SHORT);

                if(twoTime - oneTime > 1000){
                    toast.cancel();
                    toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);

                    Log.e("---","----------------------2");

                }else {
                    Log.e("---","----------------------3");
                    toast.show() ;
                }

            }else{
                toast.cancel();
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                oldMsg = msg ;
                toast.show();
                Log.e("---","----------------------4");

            }
            oneTime=twoTime;*/

            toast.cancel();
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
