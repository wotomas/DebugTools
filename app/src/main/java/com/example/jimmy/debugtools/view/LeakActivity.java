package com.example.jimmy.debugtools.view;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jimmy.debugtools.DebugApplication;
import com.example.jimmy.debugtools.R;
import com.facebook.stetho.common.LogUtil;
import com.squareup.leakcanary.RefWatcher;

public class LeakActivity extends AppCompatActivity {
  private final Handler leakyHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leak);
    leakyHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Log.d("LeakActivity", "onCreate() run!");
      }
    }, 1000 * 60 * 10);

    leakyHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        finish();
      }
    }, 3000);
  }
}
