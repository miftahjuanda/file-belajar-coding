package com.miftahjuanda.myunittesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainView {
    EditText edtLenght, edtHeight, edtWidth;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHeight = findViewById(R.id.edt_height);
        edtLenght = findViewById(R.id.edt_length);
        edtWidth = findViewById(R.id.edt_width);
        Button btnCalcutate = findViewById(R.id.btn_calculate);
        tvResult = findViewById(R.id.tv_result);

        final MainPresenter presenter = new MainPresenter(this);
        btnCalcutate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Height = edtHeight.getText().toString().trim();
                String Length = edtLenght.getText().toString().trim();
                String Width = edtWidth.getText().toString().trim();

                boolean isEmpetyFields = false;

                if (TextUtils.isEmpty(Length)){
                    isEmpetyFields = true;
                    edtLenght.setError("Field Ini Tidak Boleh Kosong");
                }

                if (TextUtils.isEmpty(Height)){
                    isEmpetyFields = true;
                    edtHeight.setError("Field Ini Tidak Boleh Kosong");
                }

                if (TextUtils.isEmpty(Width)){
                    isEmpetyFields = true;
                    edtWidth.setError("Field Ini Tidak Boleh Kosong");
                }

                if (!isEmpetyFields) {
                    double l = Double.parseDouble(Length);
                    double w = Double.parseDouble(Width);
                    double h = Double.parseDouble(Height);

                    presenter.calculateVolume(l, w, h);
                }
            }
        });
    }

    @Override
    public void showVolume(MainModel model) {
        tvResult.setText(String.valueOf(model.getVolume()));
    }
}
