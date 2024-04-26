package com.example.hackthetrick;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class KKtix extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private String url = "https://kktix.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kktix);

        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_bar);

        // 启用 JavaScript（如果网站使用 JavaScript）
        webView.getSettings().setJavaScriptEnabled(true);

        // 为 WebView 提供一个 WebViewClient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 页面加载完成后执行自动点击操作

            }
        });

        // 加载网页
        webView.loadUrl(url);
    }

    private void autoClickElementAfterDelay(String element, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String javascriptCode = "javascript:(function() {" +
                        // 选择特定的元素
                        "var element = document.querySelector('" + element + "');" +
                        // 模拟点击元素
                        "element.click();" +
                        "})();";

                // 执行 JavaScript 代码
                webView.loadUrl(javascriptCode);
            }
        }, delay);
    }
    private void autoScrollAfterDelay(int delay, int y) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.scrollTo(0, y);
            }
        }, delay);
    }

    private void autoInputTextAfterDelay(String element, String text, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String javascriptCode = "javascript:(function() {" +
                        // Select the text field
                        "var element = document.querySelector('" + element + "');" +
                        // Set the value of the text field
                        "element.value = '" + text + "';" +
                        "})();";

                // Execute JavaScript code
                webView.loadUrl(javascriptCode);
            }
        }, delay);
    }



}