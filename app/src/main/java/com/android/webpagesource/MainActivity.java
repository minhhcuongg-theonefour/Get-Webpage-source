package com.android.webpagesource;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView src;
    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        src = (TextView) findViewById(R.id.sourceView);
        url = (EditText) findViewById(R.id.textURL);
    }

    public void Onclick(View v){
        Download down = new Download();
        String a = String.valueOf(url.getText());
        down.execute(a);
    }

    class Download extends AsyncTask<String,Void,String>{
        public String doInBackground(String... urls){
            String result = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = reader.readLine()) != null){
                    result = result + line;
                }
                conn.disconnect();
            }catch (Exception e){
                Log.e("ERROR", e.toString());
                result = "ERROR";
            }
            return result;
        }
        @Override
        protected void onPostExecute (String result){
            src.setText(result);
        }
    }
}