package com.proximate.evaluacion;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proximate.evaluacion.network.ResponseLogin;
import com.proximate.evaluacion.network.WebServices;
import com.proximate.evaluacion.utils.Herramientas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtCorreo;
    private EditText edtContrasenia;
    private Button btnIniciar;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("Local", MODE_PRIVATE);
        if (sharedPreferences.contains("token")) {
            startActivity(new Intent(MainActivity.this, UserActivity.class));
        }
        progressDialog = Herramientas.progressStat(MainActivity.this, null);
        setField();
    }

    private void setField() {
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContrasenia = findViewById(R.id.edtContrasenia);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    private void Login() {
        progressDialog.show();
        new WebServices().login(edtCorreo.getText().toString().trim(), edtContrasenia.getText().toString().trim()).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                progressDialog.dismiss();
                try {
                    final ResponseLogin login = response.body();
                    if (login != null) {
                        if (login.isSuccess()) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage(login.getMessage())
                                    .setNeutralButton(getResources().getText(R.string.btn_aceptar), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            sharedPreferences.edit()
                                                    .putString("token", login.getToken()).apply();
                                            startActivity(new Intent(MainActivity.this, UserActivity.class));
                                        }
                                    })
                                    .create().show();
                        }
                        if (login.isError()) {
                            Toast.makeText(getApplicationContext(), login.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
