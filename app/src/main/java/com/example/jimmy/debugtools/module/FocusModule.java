package com.example.jimmy.debugtools.module;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.example.jimmy.debugtools.R;

import io.palaima.debugdrawer.base.DebugModule;

/**
 * Created by jimmy on 02/02/2017.
 */
public class FocusModule implements DebugModule {
  private Activity activity;
  private Button findCurrentFocusButton;
  private Animation animation;


  public FocusModule(Activity activity) {
    this.activity = activity;
  }

  @NonNull
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View view = inflater.inflate(R.layout.dd_debug_drawer_module_focus, parent, false);
    view.setClickable(false);
    view.setEnabled(false);

    animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
    animation.setDuration(500); // duration - half a second
    animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
    animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
    animation.setRepeatMode(Animation.REVERSE);

    findCurrentFocusButton = (Button) view.findViewById(R.id.dd_debug_focus_find);
    findCurrentFocusButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final View view = activity.getCurrentFocus();
        if (view != null) {
          Toast.makeText(activity, "Focus View ID: " + view.toString(), Toast.LENGTH_LONG).show();
          Log.d("DebugModule", "Focus View ID: " + view.toString());
          view.setAnimation(animation);
          view.animate();

          view.postDelayed(new Runnable() {
            @Override
            public void run() {
              view.clearAnimation();
            }
          }, 5000);
        } else {
          Toast.makeText(activity, "Focus View Not Found", Toast.LENGTH_LONG).show();
          Log.d("DebugModule", "Focus View Not Found");
        }

      }
    });

    return view;
  }

  @Override
  public void onOpened() {

  }

  @Override
  public void onClosed() {

  }

  @Override
  public void onResume() {

  }

  @Override
  public void onPause() {

  }

  @Override
  public void onStart() {

  }

  @Override
  public void onStop() {

  }
}