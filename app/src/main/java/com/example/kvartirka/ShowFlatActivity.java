package com.example.kvartirka;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.kvartirka.Adapter.EXTRA_ADDRESS;
import static com.example.kvartirka.Adapter.EXTRA_PRICE;
import static com.example.kvartirka.Adapter.EXTRA_TITLE;
import static com.example.kvartirka.Adapter.EXTRA_URLS;

public class ShowFlatActivity extends AppCompatActivity {

    TextView tvAddress, tvPrice, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_flat);
        tvAddress = findViewById(R.id.fAddress);
        tvPrice = findViewById(R.id.fPrice);
        tvTitle = findViewById(R.id.fTitle);

        Intent intent = getIntent();
        String [] imageUrls = intent.getStringArrayExtra(EXTRA_URLS);
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);

        getIncomingIntent();
    }

    private void getIncomingIntent(){

        if (getIntent().hasExtra(EXTRA_ADDRESS) && getIntent().hasExtra(EXTRA_PRICE) &&
                getIntent().hasExtra(EXTRA_TITLE)){

            int dayPrice = getIntent().getIntExtra(EXTRA_PRICE, 0);
            String title = getIntent().getStringExtra(EXTRA_TITLE);
            String address = getIntent().getStringExtra(EXTRA_ADDRESS);

            setContent(dayPrice, title, address);
        }
    }

    private void setContent (int dayPrice, String title, String address){

        String sDayPrice = String.valueOf(dayPrice);
        tvPrice.setText("Цена: " + sDayPrice + " р.");
        tvAddress.setText("Адрес: " + address);
        tvTitle.setText("Размер: " + title);

    }
}