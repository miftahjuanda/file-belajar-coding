package com.miftahjuanda.myshowcase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnFocus;
    FancyShowCaseView mFancyShowCaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFocus = (Button) findViewById(R.id.btnFocus);
        btnFocus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnFocus:
                new FancyShowCaseView.Builder(this)
                        .focusOn(v)
                        .title("Focus a View")
                        .fitSystemWindows(true)
                        .build()
                        .show();
        }
    }
}
