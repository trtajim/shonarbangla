package com.tajim.shonarbangla;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tajim.shonarbangla.adapter.HistoryAdapter;
import com.tajim.shonarbangla.databinding.ActivityHistoryBinding;
import com.tajim.shonarbangla.others.SQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {
    SQLiteHelper sqLiteHelper ;
    ArrayList<HashMap<String,String>> list;
    HashMap<String,String> map;
    ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        list = new ArrayList<>();
        sqLiteHelper = new SQLiteHelper(this);
        Cursor cursor = sqLiteHelper.getData();

        while (cursor.moveToNext()){

            map = new HashMap<>();
            map.put("date", cursor.getString(7));
            map.put("c22_bd", cursor.getString(1));
            map.put("c21_bd", cursor.getString(2));
            map.put("c18_bd", cursor.getString(3));
            map.put("c22_sa", cursor.getString(8));
            map.put("c21_sa", cursor.getString(9));
            map.put("c18_sa", cursor.getString(10));

            list.add(map);


        }
        setup();




    }
    private void setup(){
        HistoryAdapter historyAdapter = new HistoryAdapter(this, list);
        binding.recycler.setAdapter(historyAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        
        binding.imgArrowBack.setOnClickListener(v->{onBackPressed();});
        binding.imgDownload.setOnClickListener(v->{
            Toast.makeText(this, "Download in excel format, coming soon", Toast.LENGTH_SHORT).show();
        });


    }
}