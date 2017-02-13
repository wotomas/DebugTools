package com.example.jimmy.debugtools;

import android.app.Application;

import com.example.jimmy.debugtools.network.GitHubService;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jimmy on 13/02/2017.
 */

public class DebugApplication extends Application {
  private static GitHubService service;
  @Override
  public void onCreate() {
    super.onCreate();

    if(BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this);
    }

    OkHttpClient stethoInterceptingClient = new OkHttpClient.Builder()
      .addNetworkInterceptor(new StethoInterceptor())
      .build();

    Retrofit githubRetrofit = new Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .client(stethoInterceptingClient)
      .build();

    service = githubRetrofit.create(GitHubService.class);
  }

  public static GitHubService getApiService() {
    return service;
  }
}
