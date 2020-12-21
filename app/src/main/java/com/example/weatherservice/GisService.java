package com.example.weatherservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GisService extends Service {
    private static String LOG_TAG="Weather";
    public  static  final String CHANNEL="GIS_SERVICE";
    public  static  final String INFO="INFO";
    private final IBinder binder=new LocalBinder();
    public class LocalBinder extends Binder{
        GisService getService(){
            return GisService.this;
        }
    }
    public GisService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }
    public void getWheather(int tid)
    {
        GisAsyncTask t=new GisAsyncTask();
        t.execute(tid);
    }
    private class GisAsyncTask extends AsyncTask<Integer,Void,String>
    {
        @Override
        protected void onPostExecute(String s) {
            Intent intent=new Intent(CHANNEL);
            intent.putExtra(INFO,s);
            sendBroadcast(intent);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String result="";
            try
            {
                URL url=new URL("http://icomms.ru/inf/meteo.php?tid="+integers[0]);
                URLConnection urlc=url.openConnection();
                BufferedReader buffer=new BufferedReader(new InputStreamReader(urlc.getInputStream(),"UTF8"));
                StringBuilder builder=new StringBuilder();
                int byteRead;
                while ((byteRead=buffer.read())!=-1)
                {
                    builder.append((char)byteRead);
                }
                buffer.close();
                result=builder.toString();
            }
            catch (Exception e)
            {

            }
            return result;
        }
    }
}