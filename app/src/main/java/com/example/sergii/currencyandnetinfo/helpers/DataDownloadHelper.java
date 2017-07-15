package com.example.sergii.currencyandnetinfo.helpers;

import android.os.AsyncTask;
import android.util.Log;
import com.example.sergii.currencyandnetinfo.interfaces.IDataDownloadFinish;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sergii on 14.07.2017.
 */

public class DataDownloadHelper extends AsyncTask<Void, Void, Void> {

    private String LOG_TAG = getClass().getName();
    private String URLPath;
    private StringBuilder downloadedData;
    private IDataDownloadFinish onTaskFinishListener;

    public DataDownloadHelper(String URLPath){
        this.URLPath = URLPath;
        initStringBuilder();
    }

    private void initStringBuilder(){
        downloadedData = new StringBuilder();
    }

    public void setOnTaskFinishListener(IDataDownloadFinish listener){
        onTaskFinishListener = listener;
    }

    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        URL url;
        HttpURLConnection urlConnection;
        BufferedReader reader = null;

        try{
            url = new URL(URLPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("connection", "close");

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            int content;
            while((content=reader.read())!=-1){
                downloadedData.append((char)content);
            }

            Log.e(LOG_TAG,downloadedData.toString());

        }
        catch(IOException exc){
             exc.printStackTrace();
             publishProgress();
        } finally {
            try{
                reader.close();
            } catch(IOException exc){
                exc.printStackTrace();
            }
        }
        return null;
    }

    protected void onPostExecute(Void arg0){
        super.onPostExecute(null);
        if(onTaskFinishListener!=null){
            onTaskFinishListener.onDownloadTaskCompleted(downloadedData.toString());
        }
    }
}
