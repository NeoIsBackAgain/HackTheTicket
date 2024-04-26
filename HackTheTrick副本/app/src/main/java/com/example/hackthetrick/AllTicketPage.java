package com.example.hackthetrick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

public class AllTicketPage extends AppCompatActivity {
    Button buttonGoHkT;
    Button buttonGoCity;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ticket_page);


        buttonGoHkT = findViewById(R.id.buttonGoHK);

        TabLayout tabLayout = findViewById(R.id.tab_layout);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle tab selection
                switch (tab.getPosition()) {
                    case 0:
                        startActivity(new Intent(AllTicketPage.this, InfoPage.class));
                        break;
                    case 1:
                        startActivity(new Intent(AllTicketPage.this, Tostore.class));
                        break;
                    case 2:
                        startActivity(new Intent(AllTicketPage.this, AllTicketPage.class));
                        break;
                    case 3:
                        startActivity(new Intent(AllTicketPage.this, SettingsActivity.class));
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

        buttonGoHkT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(AllTicketPage.this, HKticket.class); // Replace CurrentActivity with the name of your current activity and NewActivity with the name of the activity you want to start
                startActivity(intent);
            }
        });

    }

}
