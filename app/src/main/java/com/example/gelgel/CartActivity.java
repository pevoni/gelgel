package com.example.gelgel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CartActivity extends AppCompatActivity {
    private String prevStarted = "yes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CartActivity.this, HomePage.class);
        startActivity(intent);
        finish();
        finishAndRemoveTask();
    }
}