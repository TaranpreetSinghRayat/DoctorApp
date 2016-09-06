package com.tweekersnut.taranpreetsingh.doctorapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

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
                loadingLbl.setText("Checking is doctors available");

            }
        });
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
