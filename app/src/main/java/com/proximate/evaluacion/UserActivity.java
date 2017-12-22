package com.proximate.evaluacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.proximate.evaluacion.databases.DBController;
import com.proximate.evaluacion.databases.DatabaseAccess;
import com.proximate.evaluacion.network.DataItem;
import com.proximate.evaluacion.network.ResponseUser;
import com.proximate.evaluacion.network.WebServices;
import com.proximate.evaluacion.utils.Herramientas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private TextView tvInfo;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        sharedPreferences = getSharedPreferences("Local", MODE_PRIVATE);
        progressDialog = Herramientas.progressStat(UserActivity.this, null);
        tvInfo = findViewById(R.id.tvInfo);

        databaseAccess = DatabaseAccess.getInstance(this);
        if (databaseAccess.selectUser() != null)
            tvInfo.setText(databaseAccess.selectUser());
        else
            loadInfo();

    }

    private void loadInfo() {
        progressDialog.show();
        String token = (sharedPreferences.getString("token", ""));
        if (token.equals("")) {
            finish();
            startActivity(new Intent(UserActivity.this, MainActivity.class));
        }
        new WebServices().userData(token)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        databaseAccess.crateTables();
                        try {
                            ResponseUser user = response.body();
                            if (user != null) {
                                if (user.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), user.getMessage(), Toast.LENGTH_SHORT).show();
                                    databaseAccess.insertUser(user);
                                    tvInfo.setText(databaseAccess.selectUser());
                                }
                                if (user.isError()) {
                                    databaseAccess.dropTables();
                                    Toast.makeText(getApplicationContext(), user.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }



                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        databaseAccess.dropTables();
                        progressDialog.dismiss();
                        Log.e("Error", t.getMessage());
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
