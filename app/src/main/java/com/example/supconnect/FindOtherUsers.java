package com.example.supconnect;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FindOtherUsers extends AppCompatActivity {
    private TextView student_card_id;
    private Button search;
    private EditText edtCardID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        search = findViewById(R.id.searchBtn);
        edtCardID = findViewById(R.id.student_card_id);
        edtCardID.setInputType(InputType.TYPE_NULL);
        edtCardID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                tới user profile
                String card_id = edtCardID.getText().toString();
                if(card_id.length() == 10) {
                    edtCardID.setText("");
                    Log.d("id", card_id);
                    Intent intent = new Intent(FindOtherUsers.this, OtherUserProfileActivity.class);
                    intent.putExtra("card_id", card_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });

        search.setOnClickListener(v-> {
            String card_id = edtCardID.getText().toString();
            if(!card_id.isEmpty()) {
                Intent intent = new Intent(FindOtherUsers.this, OtherUserProfileActivity.class);
                intent.putExtra("card_id", card_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
