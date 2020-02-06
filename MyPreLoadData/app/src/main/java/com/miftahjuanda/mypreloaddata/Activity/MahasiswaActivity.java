package com.miftahjuanda.mypreloaddata.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miftahjuanda.mypreloaddata.Adapter.MahasiswaAdapter;
import com.miftahjuanda.mypreloaddata.Database.MahasiswaHelper;
import com.miftahjuanda.mypreloaddata.Model.MahasiswaModel;
import com.miftahjuanda.mypreloaddata.R;

import java.util.ArrayList;

public class MahasiswaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MahasiswaAdapter mahasiswaAdapter;
    MahasiswaHelper mahasiswaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        recyclerView = findViewById(R.id.recyclerview);
        mahasiswaHelper = new MahasiswaHelper(this);
        mahasiswaAdapter = new MahasiswaAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mahasiswaAdapter);
        mahasiswaHelper.open();
        ArrayList<MahasiswaModel> mahasiswaModels = mahasiswaHelper.getAllData();
        mahasiswaHelper.close();
        mahasiswaAdapter.setData(mahasiswaModels);
    }
}
