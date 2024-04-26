package com.example.hackthetrick;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InfoPage extends AppCompatActivity {
    private TextView textView;
    private List<String> elementsText = new ArrayList<>();
    private List<String> elementLinks = new ArrayList<>();

    private List<String> imageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infopage);

        textView = findViewById(R.id.textView);
        ImageView imageView = findViewById(R.id.imageView);
        new FetchWebPageTask(imageView).execute("https://hk.trip.com/events/150235-2024-hong-kong-concerts-collection/");


        TabLayout tabLayout = findViewById(R.id.tab_layout1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle tab selection
                switch (tab.getPosition()) {
                    case 0:
                        startActivity(new Intent(InfoPage.this, InfoPage.class));
                        break;
                    case 1:
                        startActivity(new Intent(InfoPage.this, Tostore.class));
                        break;
                    case 2:
                        startActivity(new Intent(InfoPage.this, AllTicketPage.class));
                        break;
                    case 3:
                        startActivity(new Intent(InfoPage.this, SettingsActivity.class));
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

    private class FetchWebPageTask extends AsyncTask<String, Void, Void> {
        private ImageView imageView;

        public FetchWebPageTask(ImageView imageView) {
            this.imageView = imageView;
        }
        @Override
        protected Void doInBackground(String... urls) {
            String url = urls[0];
            String cssSelectorName = "#__next > div.travel_guide_root_class > div > div > div.main > div.l-module > div.EventsInfoModule__InfoWrapperStyle-sc-1wepwwr-0.CPsas.tablistOffset > div.cardlist > div > div.right-info > h2";
            String cssSelectorDate = "#__next > div.travel_guide_root_class > div > div > div.main > div.l-module > div.EventsInfoModule__InfoWrapperStyle-sc-1wepwwr-0.CPsas.tablistOffset > div.cardlist > div > div.right-info > div.time > span";
            String cssSelectorLink = "#__next > div.travel_guide_root_class > div > div > div.main > div.l-module > div.EventsInfoModule__InfoWrapperStyle-sc-1wepwwr-0.CPsas.tablistOffset > div.cardlist > div > a";
            String cssSelectorImage = "#__next > div.travel_guide_root_class > div > div > div.main > div.l-module > div.EventsInfoModule__InfoWrapperStyle-sc-1wepwwr-0.CPsas.tablistOffset > div.cardlist > div:nth-child(1) > div.left-img > img";
            try {
                Document doc = Jsoup.connect(url).get();
                Elements elementName = doc.select(cssSelectorName);
                Elements elementDate = doc.select(cssSelectorDate);
                Elements elementLink = doc.select(cssSelectorLink);
                Elements elementImage = doc.select(cssSelectorImage);

                for (int i = 0; i < elementName.size(); i++) {
                    String name = elementName.get(i).text().trim();
                    String date = "";
                    if (i < elementDate.size()) {
                        date = elementDate.get(i).text().trim();
                    }
                    String imageUrl = "";
                    if (i < elementImage.size()) {
                        imageUrl = elementImage.get(i).attr("src");
                    }
                    String link = "";
                    if (i < elementLink.size()) {
                        link = elementLink.get(i).attr("href");
                        elementLinks.add(link);
                    }
                    imageUrls.add(imageUrl);
                    elementsText.add(name + "\n" + date);
                     // Add image URL to the list
                }
            } catch (IOException e) {
                Log.e("FetchWebPageTask", "Failed to fetch the webpage", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i = 0; i < elementsText.size(); i++) {
                String text = elementsText.get(i);
                spannableStringBuilder.append(text);
                spannableStringBuilder.append("\n");

                String imageUrl = imageUrls.get(i);
                if (!imageUrl.isEmpty()) {
                    // Load image using Picasso
                    Picasso.get().load(imageUrl).into(imageView);
                }
                final int index = i;
                String link = elementLinks.get(i);
                spannableStringBuilder.append("傳送門");
                spannableStringBuilder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        Intent intent = new Intent(InfoPage.this, OpenWebActivity.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                }, spannableStringBuilder.length() - 3, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append("\n");
                spannableStringBuilder.append("------------------------------------------------------------------");
                spannableStringBuilder.append("\n");
            }
            textView.setText(spannableStringBuilder);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
