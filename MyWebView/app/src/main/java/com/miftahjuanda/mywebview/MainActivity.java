package com.miftahjuanda.mywebview;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = findViewById(R.id.webView);
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Toast.makeText(MainActivity.this, "Web Dicoding berhasil dimuat", Toast.LENGTH_LONG).show();
            }
        });

        myWebView.setWebViewClient(new WebViewClient(){
            public boolean onJsAlert (WebView view, String url, String message, final android.webkit.JsResult result) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                result.confirm();
                return true;
            }
        });

        myWebView.loadUrl("https://www.dicoding.com");
    }
}
