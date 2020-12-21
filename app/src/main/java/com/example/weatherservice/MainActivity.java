package com.example.weatherservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private Spinner spinner;
    private MeteoAdapter adapter;
    private GisService gisService;
    private boolean isBound=false;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GisService.LocalBinder binder=(GisService.LocalBinder)service;
            gisService=binder.getService();
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound=false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] cities={"Калининград","Москва"};
        rv=findViewById(R.id.rv);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter=null;
                String item=(String)parent.getItemAtPosition(position);
                switch (item)
                {
                    case "Калининград":gisService.getWheather(24);break;
                    case "Москва":gisService.getWheather(35);break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        registerReceiver(receiver,new IntentFilter(GisService.CHANNEL));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(MainActivity.this,GisService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBound){
            unbindService(serviceConnection);
            isBound=false;
        }
    }
    protected BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try
            {
                String json=intent.getStringExtra(GisService.INFO);
                Gson gson=new Gson();
                Type userListType=new TypeToken<ArrayList<Meteo>>(){}.getType();
                List<Meteo> list=gson.fromJson(json,userListType);
                adapter=new MeteoAdapter(list);
                adapter.notifyDataSetChanged();
                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rv.setAdapter(adapter);
            }
            catch (Exception e)
            {

            }
        }
    };
}