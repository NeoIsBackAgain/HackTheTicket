package com.example.hackthetrick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

public class Tostore extends AppCompatActivity {

    private EditText nameEditText;
    private EditText contactEditText;
    private EditText paymentEditText;
    private EditText cardNumberEditText;
    private EditText cvvEditText;
    private EditText expiryDateEditText;
    private EditText otherInfoEditText;
    private Button submitButton;
    private Button editButton;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tostore);

        // 初始化視圖
        nameEditText = findViewById(R.id.edit_text_name);
        contactEditText = findViewById(R.id.edit_text_contact);
        paymentEditText = findViewById(R.id.edit_text_payment);
        cardNumberEditText = findViewById(R.id.edit_text_card_number);
        cvvEditText = findViewById(R.id.edit_text_cvv);
        expiryDateEditText = findViewById(R.id.edit_text_expiry_date);
        otherInfoEditText = findViewById(R.id.edit_text_other_info);
        submitButton = findViewById(R.id.button_submit);
        editButton = findViewById(R.id.button_edit);


        // 設置提交按鈕的點擊事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 獲取用戶輸入的訂單信息
                String name = nameEditText.getText().toString();
                String contact = contactEditText.getText().toString();
                String payment = paymentEditText.getText().toString();
                String cardNumber = cardNumberEditText.getText().toString();
                String cvv = cvvEditText.getText().toString();
                String expiryDate = expiryDateEditText.getText().toString();
                String otherInfo = otherInfoEditText.getText().toString();

                // TODO: 將訂單信息發送給票務網站的服務器

                // 顯示提交成功的提示
                Toast.makeText(Tostore.this, "訂單信息已提交", Toast.LENGTH_SHORT).show();
            }
        });

        // 設置編輯按鈕的點擊事件
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 啟用輸入框的編輯模式
                enableEditMode();
            }
        });
        TabLayout tabLayout = findViewById(R.id.tab_layout2);

        // Add TabLayout listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle tab selection
                switch (tab.getPosition()) {
                    case 0:
                        startActivity(new Intent(Tostore.this, InfoPage.class));
                        break;
                    case 1:
                        startActivity(new Intent(Tostore.this, InfoPage.class));
                        break;
                    case 2:
                        startActivity(new Intent(Tostore.this, AllTicketPage.class));
                        break;
                    case 3:
                        startActivity(new Intent(Tostore.this, SettingsActivity.class));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle unselected tabs if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle reselected tabs if needed
            }
        });
    }

    // 啟用輸入框的編輯模式
    private void enableEditMode() {
        nameEditText.setEnabled(true);
        contactEditText.setEnabled(true);
        paymentEditText.setEnabled(true);
        cardNumberEditText.setEnabled(true);
        cvvEditText.setEnabled(true);
        expiryDateEditText.setEnabled(true);
        otherInfoEditText.setEnabled(true);
    }


}
