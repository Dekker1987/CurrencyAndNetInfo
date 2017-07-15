package com.example.sergii.currencyandnetinfo.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Sergii on 15.07.2017.
 */

public class Utils {

    private Utils(){
        throw new AssertionError();
    }

    public static boolean isInternetConnected(Context inContext){
        ConnectivityManager connManager;
        Context context = inContext;

        if(context!=null){
            connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                return true;
            } else if (
                    connManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            connManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
                return false;
            }
            return false;
        }
        return false;
    }
}
