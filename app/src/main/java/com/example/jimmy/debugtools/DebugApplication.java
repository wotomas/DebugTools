package com.example.jimmy.debugtools;

import android.app.Application;

import com.example.jimmy.debugtools.network.GitHubService;

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

    Retrofit githubRetrofit = new Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    service = githubRetrofit.create(GitHubService.class);
  }

  public static GitHubService getApiService() {
    return service;
  }
}
