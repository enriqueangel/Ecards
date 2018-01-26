package com.example.enriq.ecards;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Laura on 9/01/2018.
 */

public class Crear_Tarjeta extends AppCompatActivity {

    private ViewPager mPager;
    private int[] layouts = {R.layout.fragment_creart_1, R.layout.fragment_creart_2};
    private ViewAdapter mpagerAdapter;

    private LinearLayout Dots_layout;
    private ImageView[] dots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        setContentView(R.layout.activity_crear_tarjeta);

        mPager = (ViewPager) findViewById(R.id.viewpager);
        mpagerAdapter = new ViewAdapter(layouts, this);
        mPager.setAdapter(mpagerAdapter);

        Dots_layout = (LinearLayout) findViewById(R.id.layoutDots);
        createDots(0);


        mPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });}


    private void createDots(int current_position) {
        if (Dots_layout != null)
            Dots_layout.removeAllViews();

        dots = new ImageView[layouts.length];
        for (int i = 0; i < layouts.length; i++) {
            dots[i] = new ImageView(this);
            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.disable_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            Dots_layout.addView(dots[i], params);
        }

    }

/*
    String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.fragment_creart_1, ITEMS);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner = (MaterialSpinner) findViewById(R.id.spinner);
    spinner.setAdapter(adapter);
*/

}

