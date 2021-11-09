package com.example.supconnect;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.supconnect.Utils.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class PaymentInputActivity extends AppCompatActivity {
    public static final String PAYMENT_DATA = "paymentData";

    private FloatingActionButton fab;
    private CoordinatorLayout mainView;
    private Button btnyes;
    private EditText edtMoney;
    private TextView txtdepartment,txtaccount,txtcatepayment;
    private HashMap<String, Double> dataMap = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_input);
        /**
         * TODO: add more category
         */
        Constant.payCate.put(1, "Học phí");
        initViews();

        fab.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = edtMoney.getText().toString();
                Intent i = new Intent(v.getContext(),PaymentActivity.class);
                i.putExtra(PaymentActivity.PAY_AMOUNT, m);
                i.putExtra(PaymentActivity.PAY_CATEGORY, dataMap.get("id").intValue());
                startActivity(i);
            }
        }
        );
    }

    private void initViews() {
        btnyes = findViewById(R.id.btn_yes_payment);
        edtMoney = findViewById(R.id.edt_input_payment);
        txtaccount = findViewById(R.id.txt_account);
        txtcatepayment = findViewById(R.id.txt_paymentcate);
        fab = findViewById(R.id.fab_home);
        txtdepartment = findViewById(R.id.txt_department);
        mainView = findViewById(R.id.inputMainView);
        edtMoney.setFocusable(true);
        getIntentInfo();
    }

    private void getIntentInfo() {
        String dataStr = getIntent().getStringExtra(PAYMENT_DATA);
        Gson gson = new Gson();
        try {
            /**
             * Get the data from intent
             */
            //prepare a type of hashmap to store data
            Type type = new TypeToken<HashMap<String, Double>>(){}.getType();
            // dataMap store id
            Log.d("data str", dataStr);
            dataMap = gson.fromJson(dataStr, type);
            Log.d("cate id str", dataMap.toString());
            // get the name of category
            int id = dataMap.get("id").intValue();
            Log.d("cateName", Constant.payCate.get(id));
            txtcatepayment.setText(Constant.payCate.get(id));

//            Log.d("amount", dataMap.get("amount")+"");
//            //set the input field with amount of money
//            edtMoney.setText(dataMap.get("amount").intValue()+"");
        } catch (Exception exception) {
            /**
             * If the data receive is unaccepted, prompts user to rescan it.
             */
            btnyes.setEnabled(false);
            edtMoney.setFocusable(false);
            showErrSnackBar();
            edtMoney.setOnClickListener(v -> {
                ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                showErrSnackBar();
            });
        }
    }

    private void showErrSnackBar() {
        Snackbar.make(this, mainView, "Có lỗi khi quét QRCode!", BaseTransientBottomBar.LENGTH_LONG)
                .setAction("Quét lại", v -> {
                    // get back to scan activity
                    finish();
                })
                .show();
    }
}
