package com.example.jimmy.debugtools;

import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jimmy.debugtools.network.GitHubService;
import com.example.jimmy.debugtools.network.Repo;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  private Button createDatabaseButton;
  private Button createSharedPrefButton;
  private Button createNetworkCallButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    createDatabaseButton = (Button) findViewById(R.id.create_database_button);
    createSharedPrefButton = (Button) findViewById(R.id.create_shared_preference_button);
    createNetworkCallButton = (Button) findViewById(R.id.create_network_button);
    addOnClickListeners();
  }

  private void addOnClickListeners() {
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
  }
}
