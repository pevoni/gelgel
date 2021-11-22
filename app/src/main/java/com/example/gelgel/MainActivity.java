package com.example.gelgel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button btnLog;
    private EditText editText;
private Dialog dialog;
private String prevStarted = "yes";
private EditText address, age, name;
private String phoneNumber, addressStr, ageStr, nameStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean(prevStarted, false)){
            startActivity(new Intent(MainActivity.this, CartActivity.class));

            finish();
        }
        setContentView(R.layout.activity_main);
address = findViewById(R.id.editTextAddress);
age = findViewById(R.id.editTextAge);
name = findViewById(R.id.editTextName);

        editText = findViewById(R.id.editTextNumberDecimal);
        btnLog = findViewById(R.id.button);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             phoneNumber = editText.getText().toString();
                addressStr = address.getText().toString();
                nameStr = name.getText().toString();
                ageStr = age.getText().toString();

                if(addressStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Address Can't be empty", Toast.LENGTH_SHORT).show();
                }

                if(ageStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Age Can't be empty", Toast.LENGTH_SHORT).show();
                }

                if(nameStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Name Can't be empty", Toast.LENGTH_SHORT).show();
                }
                if (phoneNumber.isEmpty() || phoneNumber.length() > 8 || phoneNumber.length() < 2) {
                    Toast.makeText(MainActivity.this, "Enter a correct phone num", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+993" + phoneNumber, 60, TimeUnit.SECONDS, MainActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            signInUser(phoneAuthCredential);
                            Toast.makeText(MainActivity.this, "Sent code", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(MainActivity.this, "Failed to verify", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);
                             dialog = new Dialog(MainActivity.this);
                            dialog.setContentView(R.layout.verify_popup);
                           EditText codeVer = dialog.findViewById(R.id.editTextNumberDecimalCode);
                           Button btnVerifyCode = dialog.findViewById(R.id.buttonCode);
                           btnVerifyCode.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   String code = codeVer.getText().toString();
                                   if(code.isEmpty()){
                                       Toast.makeText(MainActivity.this, "Empty Code", Toast.LENGTH_SHORT).show();
                                       return;
                                   }
                                   else{
                                    PhoneAuthCredential cred = PhoneAuthProvider.getCredential(s, code);
                                       signInUser(cred);
                                   }
                               }
                           });
                            dialog.show();
                        }
                    });
                }
            }
        });
    }

    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                dialog.dismiss();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                 UserModel user = new UserModel(phoneNumber, addressStr, nameStr, ageStr);
                 ref.child("users").child(phoneNumber).setValue(user);
                 ref.child("users").child(phoneNumber).child("productSelected").setValue(Product.productChosen);
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
                if(!sharedPreferences.getBoolean(prevStarted, false)){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(prevStarted, Boolean.TRUE);
                    editor.apply();
                }

                finish();
             }else{
                Log.e("MainActiv", "onComplete: "+task.getException().getLocalizedMessage());
                   }
            }
        });
    }
}