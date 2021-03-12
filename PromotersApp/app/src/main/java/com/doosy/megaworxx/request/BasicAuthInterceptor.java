package com.doosy.megaworxx.request;

import android.util.Log;

import com.doosy.megaworxx.util.Constants;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {

    private final String credentials;

    public BasicAuthInterceptor() {
        this.credentials = Credentials.basic("FleetGuru.VehicleManagementAPI",
                "Pa55wor736363909379888837d12!@");
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {


        //rewrite the request to add bearer token
        Request request = chain.request().newBuilder()
                .header("Authorization", credentials)
                .build();

        return chain.proceed(request);
    }

    /*
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {


        //rewrite the request to add bearer token
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer "+ token)
                .build();

        return chain.proceed(newRequest);
    }*/
}