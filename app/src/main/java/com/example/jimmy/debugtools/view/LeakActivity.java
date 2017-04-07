package com.example.jimmy.debugtools.view;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jimmy.debugtools.DebugApplication;
import com.example.jimmy.debugtools.R;
import com.facebook.stetho.common.LogUtil;
import com.squareup.leakcanary.RefWatcher;

public class LeakActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leak);

    new SampleTask().execute();
    Toast.makeText(getBaseContext(), "Now Rotate Device!", Toast.LENGTH_LONG).show();
  }

  private class SampleTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      try {
        Thread.sleep(1000 * 100);
      } catch (Exception e) {
        LogUtil.e(e.getMessage());
      }
      return null;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    RefWatcher refWatcher = DebugApplication.getRefWatcher(getBaseContext());
    refWatcher.watch(this);
  }
}
