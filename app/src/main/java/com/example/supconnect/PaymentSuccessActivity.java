package com.example.supconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.supconnect.Utils.Helper;

public class PaymentSuccessActivity extends AppCompatActivity {
    
    private TextView txtDate, txtType, txtDepartment, txtMoney;
    private Button btn_home;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);
        init();
    }

    private void init() {
        txtDate = findViewById(R.id.txt_date_payment);
        txtType = findViewById(R.id.txt_paymentcate);
        txtDepartment = findViewById(R.id.txt_department);
        txtMoney = findViewById(R.id.txt_money);
        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(v -> {
            goHome();
        });
        setInfo();
    }

    private void setInfo() {
        txtDate.setText(Helper.convertDateTime(getIntent().getStringExtra("date")));
        txtType.setText(getIntent().getStringExtra("type"));
        txtDepartment.setText(getIntent().getStringExtra("department"));
        txtMoney.setText(getIntent().getStringExtra("money"));
    }

    void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
