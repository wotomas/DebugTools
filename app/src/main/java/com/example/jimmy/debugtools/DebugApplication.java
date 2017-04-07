package com.example.jimmy.debugtools;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.example.jimmy.debugtools.database.GitHubDbHelper;
import com.example.jimmy.debugtools.network.GitHubService;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jimmy on 13/02/2017.
 */

public class DebugApplication extends MultiDexApplication {
  private static GitHubService service;
  private static GitHubDbHelper dbHelper;
  private static SharedPreferences sharedPreferences;
  private RefWatcher refWatcher;

  @Override
  public void onCreate() {
    super.onCreate();
    OkHttpClient stethoInterceptingClient = null;
    Retrofit githubRetrofit = null;

    if(BuildConfig.DEBUG) {
      if (LeakCanary.isInAnalyzerProcess(this)) return;
      refWatcher = LeakCanary.install(this);
      Stetho.initializeWithDefaults(this);

      stethoInterceptingClient = new OkHttpClient.Builder()
        .addNetworkInterceptor(new StethoInterceptor())
        .build();

      githubRetrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(stethoInterceptingClient)
        .build();
    } else {
      githubRetrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }

    service = githubRetrofit.create(GitHubService.class);
    dbHelper = new GitHubDbHelper(getBaseContext());
    sharedPreferences = getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);

    if (BuildConfig.DEBUG) StrictMode.enableDefaults();
  }

  public static RefWatcher getRefWatcher(Context context) {
    DebugApplication application = (DebugApplication) context.getApplicationContext();
    return application.refWatcher;
  }

  public static GitHubService getApiService() {
    return service;
  }

  public static GitHubDbHelper getDbHelper() {
    return dbHelper;
  }

  public static SharedPreferences getSharedPref() {
    return sharedPreferences;
  }
}
