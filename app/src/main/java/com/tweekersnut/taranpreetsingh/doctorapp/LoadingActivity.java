package com.tweekersnut.taranpreetsingh.doctorapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

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

    AlertDialog.Builder errorBox;

    /*
    Loading Screen Check : Perform all checks required to run before starting main activity.
     */
    public void loadingScreenCheck(){
        loadingScreenHandler = new Handler();
        loadingScreenHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingLbl.setText("Checking Internet Connection!");
                boolean internetStatus = isNetworkConnected();
                if(internetStatus){
                    loadingScreenHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingLbl.setText("Connection to server!");
                           boolean pingStatus =  pingServer("tweekersnut.com");
                            if(pingStatus){
                                loadingLbl.setText("Downloading Components!");
                                loadingScreenHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingLbl.setText("Starting Up!");
                                        loadingScreenHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }, 1000);
                                    }
                                }, 1000);
                            }else{
                                createPop("Error Unable to connect to server.\n Please check your connection");
                                createError("Error Occur","Error : Internet Connection not Working.\n Please restart your connection.","Open Settings","Exit","Internet");
                            }
                        }
                    }, 1000);
                }else{
                    createError("Error Occur","Error : Internet Connection not found.","Open Settings","Exit","Internet");
                }
            }
        },1000);
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
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }

    /*
        Create error Box with message and options.
     */
    public void createError(String title,String msg,String yes,String no,String type){
        switch (type){
            case "Internet":
                    errorBox = new AlertDialog.Builder(LoadingActivity.this);
                    errorBox.setTitle(title);
                    errorBox.setMessage(msg);
                    errorBox.setPositiveButton(yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    });
                    errorBox.setNegativeButton(no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    errorBox.setCancelable(true);
                    errorBox.show();
                break;
            default:

                break;
        }
    }

    /*
        Stop backbutton action
     */
    @Override
    public void onBackPressed() {
       createPop("Please wait loading in process.");
    }

    /*
        Restart all process when back from settings.
     */
    @Override
    public void onPostResume(){
        super.onPostResume();
        loadingScreenCheck();
    }

    /*
        Ping server to check connection.
     */
    public boolean pingServer(String url){
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 "+ url);
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue "+mExitValue);
            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ignore)
        {
            ignore.printStackTrace();
            System.out.println(" Exception:"+ignore);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(" Exception:"+e);
        }
        return false;
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
