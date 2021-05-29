package com.example.cambiaractividades;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

public class MyApplciation extends Activity {
    long startTime,lastInteractionTime;
    boolean isScreenOff;
    boolean isForeGrnd;
    TextView tw;
    static boolean active = false;
    boolean isChanged = false;

    private final BroadcastReceiver myBroadcastReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    isScreenOff = true;
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    isScreenOff = false;
                    unregisterReceiver(this);
                }
            }
    };

    public MyApplciation(){
        startTime = System.currentTimeMillis();
    }

    protected void start(TextView tw){
        this.tw = tw;
        active = true;
        runThread();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isChanged)
            isForeGrnd = false;
        else
            isForeGrnd = true;
        unregisterReceiver(myBroadcastReciver);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("Close",false))
            finish();
        else if(intent.getBooleanExtra("Change",true))
            isChanged = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(myBroadcastReciver, filter);
        startTime = System.currentTimeMillis();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void runThread() {
        new Thread() {
            public void run() {
                while(true) {
                    if (!isChanged){
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setLastInteractionTime();
                                    if (isScreenOff || getLastInteractionTime() > 9000 || isForeGrnd) {
                                        //...... means USER has been INACTIVE over a period of
                                        // and you do your stuff like log the user out
                                        //System.out.println("Inactivo");
                                        //tw.setText("Usuario inactivo");
                                        finish();
                                    }
                                }
                            });
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    public long getLastInteractionTime() {
        return lastInteractionTime;
    }
    public void setLastInteractionTime() {
        lastInteractionTime = System.currentTimeMillis() - startTime;
    }

}
