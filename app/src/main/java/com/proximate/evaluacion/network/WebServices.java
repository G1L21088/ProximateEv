package com.proximate.evaluacion.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class WebServices {

    private static final String _WS_URL = "https://serveless.proximateapps-services.com.mx/";
    private Services services;

    public WebServices() {
           HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                return chain.proceed(requestBuilder.build());
            }
        });
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(_WS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        services = retrofit.create(Services.class);
    }

    public Call<ResponseLogin> login(String correo, String contrasenia) {
        return services.login(new LoginRequest(correo, contrasenia));
    }

    public Call<ResponseUser> userData(String token) {
        return services.userdata(token);
    }

    public interface Services {
        @POST("catalog/dev/webadmin/authentication/login")
        Call<ResponseLogin> login(
                @Body() LoginRequest body
        );

        @POST("catalog/dev/webadmin/users/getdatausersession")
        Call<ResponseUser> userdata(
                @Header("Authorization") String token
        );

    }

    class LoginRequest {
        final String correo;
        final String contrasenia;

        LoginRequest(String correo, String contrasenia) {
            this.correo = correo;
            this.contrasenia = contrasenia;
        }
    }

}
