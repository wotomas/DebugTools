package com.example.jimmy.debugtools;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jimmy.debugtools.database.GitHubContract;
import com.example.jimmy.debugtools.module.FocusModule;
import com.example.jimmy.debugtools.network.Repo;
import com.example.jimmy.debugtools.view.LeakActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.commons.BuildModule;
import io.palaima.debugdrawer.commons.DeviceModule;
import io.palaima.debugdrawer.commons.NetworkModule;
import io.palaima.debugdrawer.commons.SettingsModule;
import io.palaima.debugdrawer.location.LocationModule;
import io.palaima.debugdrawer.okhttp3.OkHttp3Module;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_UNLOCKED;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  private Button createDatabaseButton;
  private Button createSharedPrefButton;
  private Button createNetworkCallButton;
  private Button createActivityLeakButton;
  private Button createDropFPSButton;
  private Timer timer;
  private DebugDrawer debugDrawer;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    createDatabaseButton = (Button) findViewById(R.id.create_database_button);
    createSharedPrefButton = (Button) findViewById(R.id.create_shared_preference_button);
    createNetworkCallButton = (Button) findViewById(R.id.create_network_button);
    createActivityLeakButton = (Button) findViewById(R.id.leak_memory_button);
    createDropFPSButton = (Button) findViewById(R.id.drop_fps_button);
    addOnClickListeners();

    setDebugDrawer();
  }

  /**
   * Debug Drawer example starts
   */
  private void setDebugDrawer() {
    debugDrawer = new DebugDrawer.Builder(this)
      .modules(
        new BuildModule(this),
        new DeviceModule(this),
        new SettingsModule(this),
        new NetworkModule(this),
        new LocationModule(this),
        new FocusModule(this, new FocusRequestListener() {
          @Override
          public void closeDrawer() {
            if (debugDrawer != null && debugDrawer.isDrawerOpen()) {
              debugDrawer.closeDrawer();
              debugDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
            }
          }

          @Override
          public void showDrawer() {
            if (debugDrawer != null && !debugDrawer.isDrawerOpen()) {
              debugDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED);
              debugDrawer.openDrawer();
            }
          }
        })
      ).build();
  }

  public interface FocusRequestListener {
    void closeDrawer();
    void showDrawer();
  }


  @Override
  protected void onStart() {
    super.onStart();
    debugDrawer.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    debugDrawer.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    debugDrawer.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    debugDrawer.onStop();
  }
  /**
   * Debug Drawer example ends
   */


  /**
   * Stetho Examples Starts
   */
  private void addOnClickListeners() {
    createDropFPSButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (createDropFPSButton.getText().equals("Drop FPS")) {
          createDropFPSButton.setText("Normalize FPS");
          timer = new Timer();
          timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
              MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  try {
                    Thread.sleep(1);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
              });
            }
          }, 100, 2);
        } else {
          createDropFPSButton.setText("Drop FPS");
          timer.cancel();
          timer.purge();
        }
      }
    });

    createActivityLeakButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent leakActivityIntent = new Intent(getBaseContext(), LeakActivity.class);
        startActivityForResult(leakActivityIntent, RESULT_CANCELED);
      }
    });

    createNetworkCallButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DebugApplication.getApiService().listRepos("wotomas").enqueue(new Callback<List<Repo>>() {
          @Override
          public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
            if(response.isSuccessful()) {
              Toast.makeText(getBaseContext(), "Api Call successful!", Toast.LENGTH_LONG).show();
            } else {
              Toast.makeText(getBaseContext(), "Api Call failed!", Toast.LENGTH_LONG).show();
            }
          }

          @Override
          public void onFailure(Call<List<Repo>> call, Throwable e) {
            Log.e(TAG, "network call failed", e);
            Toast.makeText(getBaseContext(), "Api Call failed!", Toast.LENGTH_LONG).show();
          }
        });
      }
    });

    createDatabaseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SQLiteDatabase db = DebugApplication.getDbHelper().getWritableDatabase();
        for(int i = 0; i < 40; i++) {
          ContentValues values = new ContentValues();
          values.put(GitHubContract.GitHubEntry.COLUMN_NAME_NAME, getRandomName());
          db.insert(GitHubContract.GitHubEntry.TABLE_NAME, null, values);
        }
        Log.d(TAG, "Database entry creation was successful!");
        Toast.makeText(getBaseContext(), "Database entry creation was successful", Toast.LENGTH_LONG).show();
        db.close();
      }
    });

    createSharedPrefButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences sharedPreferences = DebugApplication.getSharedPref();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i = 0; i < 40; i++) {
          editor.putString("" + i, getRandomName());
        }
        editor.apply();
        Log.d(TAG, "SharedPref creation was successful!");
        Toast.makeText(getBaseContext(), "SharedPref creation was successful", Toast.LENGTH_LONG).show();
      }
    });

  }

  private String getRandomName() {
    return UUID.randomUUID().toString();
  }
  /**
   * Stetho example ends
   */
}
