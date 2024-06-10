package com.example.aaaa.localdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aaaa.R;

import java.util.Arrays;
import java.util.List;

public class RegistrationAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_acivity);
    }

    public void changee(View view) {
        String preferencesName = "UserDetails";
        List<UserModel> moldList = Arrays.asList(
                new UserModel("name", "email", "phonenumber","uuid", "time", "username","password",
                        "whatsappnumber", "depositbalance",
                        "currentbalance", "lastclick", "refername",
                        "withdrawtotal", "sendmoneytota")
        );
        SharedPreferencesManager.saveListToSharedPreferences(this, preferencesName, moldList, "userdetailsKey");
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }
}