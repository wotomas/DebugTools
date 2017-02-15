package com.example.jimmy.debugtools.module;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.example.jimmy.debugtools.MainActivity;
import com.example.jimmy.debugtools.R;

import io.palaima.debugdrawer.base.DebugModule;

/**
 * Created by jimmy on 02/02/2017.
 */
public class FocusModule implements DebugModule {
  private final MainActivity.FocusRequestListener focusRequestListener;
  private Activity activity;
  private Button findCurrentFocusButton;


  public FocusModule(Activity activity, MainActivity.FocusRequestListener focusRequestListener) {
    this.activity = activity;
    this.focusRequestListener = focusRequestListener;
  }

  @NonNull
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View view = inflater.inflate(R.layout.dd_debug_drawer_module_focus, parent, false);
    view.setClickable(false);
    view.setEnabled(false);

    findCurrentFocusButton = (Button) view.findViewById(R.id.dd_debug_focus_find);
    findCurrentFocusButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final View view = activity.getCurrentFocus();
        if (view != null) {
          Toast.makeText(activity, "Focus View ID: " + view.toString(), Toast.LENGTH_LONG).show();
          Log.d("DebugModule", "Focus View ID: " + view.toString());
          final Drawable originBackground = view.getBackground();
          final Drawable redBackground = ContextCompat.getDrawable(activity, R.drawable.red_sqaure);
          view.setBackground(redBackground);
          focusRequestListener.closeDrawer();
          view.postDelayed(new Runnable() {
            @Override
            public void run() {
              //return to normal
              view.setBackground(originBackground);
              focusRequestListener.showDrawer();
            }
          }, 2000);

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