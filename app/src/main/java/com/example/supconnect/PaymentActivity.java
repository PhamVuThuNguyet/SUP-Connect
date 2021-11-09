package com.example.supconnect;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.WalletService;
import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.RetrofitResponse.WalletResponse;
import com.example.supconnect.Utils.Constant;
import com.example.supconnect.Utils.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentActivity extends AppCompatActivity {
    public static final String PAY_AMOUNT = "amount";
    public static final String PAY_CATEGORY = "category";
    private Button btnyes;
    private EditText edtMoney;
    private TextView txtdepartment,txtaccount,txtcatepayment,txtmoney;
    private Dialog dialog;
    private String money;
    private int category;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnyes = findViewById(R.id.btn_confirm_payment);
        edtMoney = findViewById(R.id.edt_input_payment);
        txtaccount = findViewById(R.id.wallet_txt);
        txtcatepayment = findViewById(R.id.txt_paymentcate);
        txtdepartment = findViewById(R.id.txt_department);
        txtmoney = findViewById(R.id.txt_money);

        Intent i = getIntent();
        money = i.getStringExtra(PAY_AMOUNT);
        category = i.getIntExtra(PAY_CATEGORY, 1);

        txtcatepayment.setText(Constant.payCate.get(category));
        txtmoney.setText(money);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogInputPass(Gravity.CENTER);
            }
        });
    }
    private void openDialogInputPass(int gravity){
        Button btnconfirm,btncancel;
        EditText edtPass;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_input_pass);
        btnconfirm = dialog.findViewById(R.id.btn_confirm);
        btncancel = dialog.findViewById(R.id.btn_close);
        edtPass = dialog.findViewById(R.id.edt_input_pass);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowLayout = window.getAttributes();
        windowLayout.gravity = gravity;
        window.setAttributes(windowLayout);

        dialog.setCancelable(true);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PAYMENT: ", "ONCLICK");
                SharedPreferences userSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
                String passInput = edtPass.getText().toString();
                String md5Password = Helper.getMd5(passInput);
                if((!passInput.isEmpty()) && md5Password.equals(userSharedPreferences.getString("password", ""))) {
                    storePayment();
                } else {
                    openDialogResponse("Mật khẩu không đúng");
                }
            }
        });
        dialog.show();
    }

    private void storePayment() {
        SharedPreferences userSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Retrofit retrofit = new RetrofitClient().getRetrofit(this);
        //TODO: pass transaction category here
        retrofit.create(WalletService.class).storePayment(userSharedPreferences.getString("account_id", ""), 2, money, category, "")
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        Log.d("PAYMENT: ", response.message());
                        if(response.isSuccessful()) {
                            if(response.body().getSuccess()) {
                                Toast.makeText(PaymentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
                                intent.putExtra("type", response.body().getType());
                                intent.putExtra("department", response.body().getDepartment());
                                intent.putExtra("date", response.body().getDate());
                                intent.putExtra("money", money);
                                startActivity(intent);
                            } else {
                                openDialogResponse(response.body().getMessage());
                                dialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        openDialogResponse("Có lỗi xảy ra, vui lòng thử lại");
                        Log.d("payment err", t.getMessage());
                    }
                });
    }

    private void openDialogResponse(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage(message);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
