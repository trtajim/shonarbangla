package com.tajim.shonarbangla;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.tabs.TabLayout;
import com.tajim.shonarbangla.adapter.ViewPager2;
import com.tajim.shonarbangla.databinding.ActivityMainBinding;
import com.tajim.shonarbangla.databinding.AlertCustomBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    ViewPager2 pagerAdapter;
    public static float PRICE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        pagerAdapter = new ViewPager2(this);
        setupViewpager();
        setup();


    }

    private void setupViewpager(){

        binding.pager.setAdapter(pagerAdapter);

        binding.tabLay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.pager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.pager.registerOnPageChangeCallback(new androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLay.getTabAt(position).select();
            }
        });



    }

    private void setup() {
        binding.imgI.setOnClickListener(v -> {
            // Inflate custom alert layout using ViewBinding
            AlertCustomBinding alertBinding = AlertCustomBinding.inflate(getLayoutInflater());

            // Create and show dialog
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setView(alertBinding.getRoot())
                    .setCancelable(true)
                    .create();

            alertDialog.show();

            // Adjust dialog width
            Window window = alertDialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = (int) (340 * getResources().getDisplayMetrics().density);
                window.setAttributes(params);
            }

            // Set listeners (using concise lambdas)
            alertBinding.declineCus.setOnClickListener(z -> {
                openUrl("https://www.facebook.com/trtajim/");
                alertDialog.dismiss();
            });

            alertBinding.allowCus.setOnClickListener(z -> {
                openUrl("http://localhost");
                alertDialog.dismiss();
            });
        });
    }

    // Utility method for opening URLs safely
    private void openUrl(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open link", Toast.LENGTH_SHORT).show();
        }
    }





}