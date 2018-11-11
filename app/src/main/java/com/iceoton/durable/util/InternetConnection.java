package com.iceoton.durable.util;


import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * สำหรับตรวจสอบว่าอุปกรณ์มีการเชื่อมต่อ Internet หรือไม่
 */
public class InternetConnection {
    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    /**
     * ทำการตรวจสอบการเชื่อมต่อ Internet ด้วยการทดสอบเรียกเว็บไซต์ google.com ดูว่าจะได้ผลลัพธ์หรือไม่
     * @param mContext Context ของหน้าแอพที่ต้องการตรวจสอบ
     * @param timeOutMilliseconds ค่าจำกัดเวลาในการทดสอบการเชื่อมต่อ internet หน่วยเป็นมิลลิวินานี
     * @return true หากติดต่อไปที่ google.com ผ่าน internet ได้, false ในกรณีติดต่อไม่ได้หรือกรณีอื่นๆ
     */
    public static boolean isInternetAvailable(Context mContext, int timeOutMilliseconds) {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(timeOutMilliseconds, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return inetAddress != null && !inetAddress.getHostAddress().equals("");
    }

}
