package com.example.kvartirka;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.kvartirka.Adapter.EXTRA_ADDRESS;
import static com.example.kvartirka.Adapter.EXTRA_PRICE;
import static com.example.kvartirka.Adapter.EXTRA_TITLE;
import static com.example.kvartirka.Adapter.EXTRA_URLS;

public class ShowFlatActivity extends AppCompatActivity {

    TextView tvAddress, tvPrice, tvTitle;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_flat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvAddress = findViewById(R.id.fAddress);
        tvPrice = findViewById(R.id.fPrice);
        tvTitle = findViewById(R.id.fTitle);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        Intent intent = getIntent();
        String [] imageUrls = intent.getStringArrayExtra(EXTRA_URLS);
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);

        getIncomingIntent();

        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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