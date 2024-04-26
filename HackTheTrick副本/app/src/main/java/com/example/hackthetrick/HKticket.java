package com.example.hackthetrick;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HKticket extends AppCompatActivity {

    private  String link = "https://m.hkticketing.com/default.aspx";

    private  String KeyWordReferesh = "#nestedContent > b > b > section";

    private  int KeyWordRefereshSecound = 3000;
    private WebView webView;
    private TextView ipAddressTextView;
    private TableLayout tableLayout;
    private ImageView toggleButton;
    private ImageView backButton;
    private  ImageView forwardButton;
    private Runnable autoClickRunnable;
    private Handler autoActionHandler = new Handler();
    private final Handler autoRefreshHandler = new Handler();
    private Runnable autoRefreshRunnable;
    private int refreshCount = 999999999; // 将 refreshCount 声明为类的成员变量


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
    private void autoScrollAfterDelay(int delay, int y) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.scrollTo(0, y);
            }
        }, delay);
    }

    private void RefereshIn(String elementSelector) {
        // 构造 JavaScript 代码来检查元素是否存在并设置背景色为红色
        String javascriptCode = "javascript:(function() {" +
                // 选择特定的元素
                "var element = document.querySelector('" + elementSelector + "');" +
                // 如果元素存在
                "if (element) {" +
                // 将元素背景色设置为红色
                "element.style.backgroundColor = 'red';" +
                // 返回 true 表示元素存在
                "return true;" +
                "} else {" +
                // 返回 false 表示元素不存在
                "return false;" +
                "}" +
                "})();";

        // 执行 JavaScript 代码并等待结果
        webView.evaluateJavascript(javascriptCode, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                // 处理 JavaScript 返回的结果
                boolean isElementPresent = "true".equals(value);
                if (!isElementPresent) {
                    // 如果元素不存在，调用 setupAutoRefresh 方法继续自动刷新任务
                    setupAutoRefresh(true);
                }
            }
        });
    }

    private void setupAutoRefresh(final boolean enable) {
        // 首先，停止任何已经存在的自动刷新任务
        autoRefreshHandler.removeCallbacksAndMessages(null);

        final TextView refreshCountTextView = findViewById(R.id.refreshCountTextView); // 获取对 TextView 的引用

        if (enable) {
            // 如果开启自动刷新，定义一个Runnable任务来刷新WebView
            autoRefreshRunnable = new Runnable() {
                @Override
                public void run() {
                    if (refreshCount > 0) { // 如果还有剩余刷新次数
                        webView.reload(); // 刷新WebView的内容
                        refreshCount--; // 刷新次数减一
                        // 更新 TextView 显示剩余的刷新次数
                        refreshCountTextView.setText("剩余刷新次数：" + refreshCount);
                    } else {
                        // 如果刷新次数已经用完，取消自动刷新任务
                        autoRefreshHandler.removeCallbacks(autoRefreshRunnable);
                        refreshCountTextView.setText("刷新次数已用完");
                    }
                }
            };
            // 立即开始执行自动刷新任务
            autoRefreshHandler.postDelayed(autoRefreshRunnable, KeyWordRefereshSecound);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_line);
        int speed = 2000;
        ipAddressTextView = findViewById(R.id.ip_address_text_view);
        webView = findViewById(R.id.webview);
        tableLayout = findViewById(R.id.tableLayout);
        toggleButton = findViewById(R.id.bot);
        ImageView refreshButton = findViewById(R.id.referesh);
        tableLayout.setVisibility(View.GONE);
        backButton = findViewById(R.id.back);
        forwardButton = findViewById(R.id.forward);
        String ipAddress = IPAddressHelper.getIPAddress();
        ipAddressTextView.setText("IP 地址: " + ipAddress);
        webView.getSettings().setJavaScriptEnabled(true);
        tableLayout.setVisibility(View.GONE);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tableLayout.getVisibility() == View.GONE) {
                    tableLayout.setVisibility(View.VISIBLE);
                } else {
                    tableLayout.setVisibility(View.GONE);
                }
            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goForward();
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //RefereshIn("#nestedContent > b > b > section");
                autoClickElementAfterDelay("#nestedContent > section.PerfSelectorStepOne > div > ul > li > a", speed);
                autoClickElementAfterDelay("#content > section.PerfSelectorStepOne > div > ul > li > a", speed);
                autoClickElementAfterDelay("#important-information > div > div > a.blueButton.close.no-history-on-click", speed);
                autoClickElementAfterDelay("#priceCategoryContainer > ul > li:nth-child(1)", speed);
                autoClickElementAfterDelay("#priceCategoryContainer > ul > li:nth-child(1) > button", speed);
                autoClickElementAfterDelay("#nestedContent > section.PerfSelectorStepOne > div", speed);

                //----------------------------------//#ctl00_ctl00_ctl00_contentContainer_contentContainer_contentContainer_priceTypesControl_priceTypeGroups_ctl00_pt_pt_ctl00_ptQuantity  #ctl00_ctl00_ctl00_contentContainer_contentContainer_contentContainer_priceTypesControl_priceTypeGroups_ctl00_pt_pt_ctl00_ptId
                // user input by themself
                //----------------------------------//login page
                autoScrollAfterDelay(speed, 100000);
                autoClickElementAfterDelay("#ctl00_ctl00_contentContainer_contentContainer_continueButton", speed);
                autoClickElementAfterDelay("#ctl00_ctl00_ctl00_contentContainer_contentContainer_contentContainer_loginControl_txtUsername",speed);
                autoInputTextAfterDelay("#ctl00_ctl00_ctl00_contentContainer_contentContainer_contentContainer_loginControl_txtUsername","ngmantsung0624@gmail.com", speed);
                autoClickElementAfterDelay("#ctl00_ctl00_ctl00_contentContainer_contentContainer_contentContainer_loginControl_txtPassword",speed);
                autoInputTextAfterDelay("#ctl00_ctl00_ctl00_contentContainer_contentContainer_contentContainer_loginControl_txtPassword","abcing0193",speed);
                autoClickElementAfterDelay("#ctl00_ctl00_ctl00_contentContainer_contentContainer_contentContainer_loginControl_btnLogin",speed);
                //----------------------------------//  #ctl00_ctl00_contentContainer_contentContainer_continueButton
                autoInputTextAfterDelay("#ctl00_ctl00_contentContainer_contentContainer_address_tbAddressLine1","N/A",speed);
                autoClickElementAfterDelay("#ctl00_ctl00_contentContainer_contentContainer_address_selAddressState",speed);
                autoClickElementAfterDelay("##ctl00_ctl00_contentContainer_contentContainer_address_selAddressState > option:nth-child(4)",speed);
                autoClickElementAfterDelay("#ctl00_ctl00_contentContainer_contentContainer_ctl05_uiAgree", speed);
                //autoClickElementAfterDelay("#ctl00_ctl00_contentContainer_contentContainer_submit", speed);
                //----------------------------------//
                //autoClickElementAfterDelay("##payForm > table > tbody > tr:nth-child(5) > td > div > table > tbody > tr:nth-child(5) > td > div > div > ul > li:nth-child(1) > a", speed);
                //autoInputTextAfterDelay("#cardNo2","1234 4567 7890",speed);
                //autoClickElementAfterDelay("#epMonth2",speed);
                //autoClickElementAfterDelay("#epMonth2 > option:nth-child(6)",speed);
                //autoClickElementAfterDelay("#epYear2",speed);
                //autoClickElementAfterDelay("#epYear2 > option:nth-child(5)",speed);
                //autoInputTextAfterDelay("#cardHolder2","chan tai man",speed);
                //autoInputTextAfterDelay("#SecurityCode > td.en_text > table > tbody > tr > td:nth-child(1) > input[type=\"password\"]","987",speed);
                //autoInputTextAfterDelay("#PayInfoForm > table > tbody:nth-child(17) > tr:nth-child(2) > td > div > input[type="submit"]:nth-child(1)",speed);

            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String url = prefs.getString("userUrl",link);
        webView.loadUrl(url);



    }
}
