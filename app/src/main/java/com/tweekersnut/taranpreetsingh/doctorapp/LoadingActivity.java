package com.tweekersnut.taranpreetsingh.doctorapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.InetAddress;

public class LoadingActivity extends AppCompatActivity {

    /*
        Loading Screen Handler Object (Handler.OS)
     */
    Handler loadingScreenHandler;

    /*
        UI Objects.
        All UI Objects initialize in onCreate Method.
     */

    TextView loadingLbl;

    /*
    Loading Screen Check : Perform all checks required to run before starting main activity.
     */
    public void loadingScreenCheck(){
        loadingScreenHandler = new Handler();
        loadingScreenHandler.post(new Runnable() {
            @Override
            public void run() {
                loadingLbl.setText("Checking Internet Connection!");
                boolean internetStatus = isNetworkConnected();
                if(internetStatus){
                    createPop("Internet Connected!");
                }else{
                    createError("Error Occur","Error : Internet Connection not found.","Exit","Open Settings");
                }
            }
        });
    }

    /*
        Check is internet connection available
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    /*
        Create Toast
     */
    public void createPop(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    /*
        Create error Box with message and options.
     */
    public void createError(String title,String msg,String yes,String no){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadingLbl = (TextView)findViewById(R.id.loadingLbl);

        loadingScreenCheck();
    }
}
