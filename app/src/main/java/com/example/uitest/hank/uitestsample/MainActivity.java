package com.example.uitest.hank.uitestsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnTest, btnOk, btnCancel;
    private TextView tvHello;
    private int testCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHello = (TextView) findViewById(R.id.tv_hello);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("msg", "click ok");
            }
        });
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("msg", "click cancel");
            }
        });
        btnTest = (Button) findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("msg", "click test: " + testCounter);

                if(testCounter == 0) {
                    tvHello.setText("WWWWWWWWWWWWWW");
                } else {
                    tvHello.setText("EEEEEEEE");
                }
                testCounter++;
            }
        });
    }
}
