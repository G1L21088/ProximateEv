package com.proximate.evaluacion;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.proximate.evaluacion.databases.DatabaseAccess;
import com.proximate.evaluacion.network.ResponseUser;
import com.proximate.evaluacion.network.WebServices;
import com.proximate.evaluacion.utils.Herramientas;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private TextView tvInfo;
    private ImageView imgUser;
    private Button btnCerrar;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        sharedPreferences = getSharedPreferences("Local", MODE_PRIVATE);
        progressDialog = Herramientas.progressStat(UserActivity.this, null);
        setFields();
    }

    private void setFields() {
        tvInfo = findViewById(R.id.tvInfo);
        imgUser = findViewById(R.id.imgUser);
        btnCerrar = findViewById(R.id.btnCerrar);
        databaseAccess = DatabaseAccess.getInstance(this);
        if (databaseAccess.selectUser() != null && !databaseAccess.selectUser().equals(""))
            tvInfo.setText(databaseAccess.selectUser());
        else
            loadInfo();

        String path = sharedPreferences.getString("path", "");
        if (!path.equals(""))
            imgUser.setImageURI(Uri.fromFile(new File(path)));


        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(UserActivity.this)
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted())
                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .start(UserActivity.this);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                databaseAccess.dropTables();
                finish();
                startActivity(new Intent(UserActivity.this, MainActivity.class));
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                sharedPreferences.edit().putString("path", resultUri.getPath()).apply();
                imgUser.setImageURI(Uri.fromFile(new File(sharedPreferences.getString("path", ""))));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
