package com.example.uitest.hank.uitestsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Button btnTest, btnOk, btnCancel;
    private TextView tvLuck18, tvApiurl;
    private int testCounter = 0;
    public static final String EVENT_TEST_URL = "http://54.249.26.39:5566/";
    public static final String
            EVENT_LOG = "logs",
            EVENT_CLIENT_INPUT = "clientInput",
            EVENT_APIURLS = "apiUrls",
            EVENT_LUCK185 = "luck18Status";

    private String respLuck18 = "", respApiurl = "";

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(EVENT_TEST_URL);
        } catch (URISyntaxException e) {
            Log.d("msg", "URISyntaxException e: " + e);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        mSocket.on(EVENT_APIURLS, onEventApiUrl);
        mSocket.on(EVENT_LUCK185, onEventLuck18);
        mSocket.on(EVENT_CLIENT_INPUT, onEventInput);
        mSocket.on(EVENT_LOG, onEventLog);
//        try {
//            mSocket = IO.socket("http://54.249.26.39:5566/");
//        } catch (URISyntaxException e) {}
//        mSocket.on(EVET_LUCK185, new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvLuck18.setText(args[0].toString());
//                    }
//                });
//
//                Log.d("msg", "new message "+args[0]);
//            }
//        });
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.connect();
    }

    public void findView() {
        tvLuck18 = (TextView) findViewById(R.id.tv_luck18);
        tvApiurl = (TextView) findViewById(R.id.tv_apiurl);
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
                int random = (int)(Math.random()*1000);
                Log.d("msg","socket input: " + random);
                mSocket.emit(EVENT_CLIENT_INPUT, random);
            }
        });
    }

    private Emitter.Listener onEventApiUrl = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d("msg", "ApiUrl: " + args[0]);
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    respApiurl = respApiurl + args[0].toString() + '\n';
                    tvApiurl.setText(respApiurl);
                }
            });
        }
    };

    private Emitter.Listener onEventLuck18 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d("msg", "Luck18: " + args[0]);
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    respLuck18 = respLuck18 + args[0].toString() + '\n';
                    tvLuck18.setText(respLuck18);
                }
            });
        }
    };

    private Emitter.Listener onEventInput = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("msg", "input: " + args[0]);
        }
    };

    private Emitter.Listener onEventLog = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("msg", "log: " + args[0]);
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("msg", "onConnectError callback: " + args[0]);
            try {
                JSONObject json = new JSONObject(args.toString());
                Log.d("msg", "onConnectError callback: " + json.toString());
            } catch (JSONException e) {
                e.printStackTrace();

            }
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("msg", "Error connecting");
                    Toast.makeText(MainActivity.this,
                            "disconnect error", Toast.LENGTH_LONG).show();
                }
            });
        }
    };



    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off(EVENT_LUCK185, onEventApiUrl);
        mSocket.off(EVENT_LUCK185, onEventLuck18);
    }
}
