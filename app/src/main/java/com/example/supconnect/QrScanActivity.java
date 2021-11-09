package com.example.supconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class QrScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    private BarcodeReader barcodeReader;
    private RelativeLayout mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        mainView = findViewById(R.id.mainView);
    }

    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        Log.d("barcode", barcode.displayValue);
        Intent intent = new Intent(QrScanActivity.this, PaymentInputActivity.class);
        intent.putExtra(PaymentInputActivity.PAYMENT_DATA, barcode.displayValue);
        QrScanActivity.this.startActivity(intent);
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {
        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra khi quét mã!" + errorMessage, Toast.LENGTH_SHORT).show();
    }
}