package com.example.jimmy.debugtools.database;

import android.provider.BaseColumns;

/**
 * Created by jimmy on 13/02/2017.
 */

public final class GitHubContract {
  private GitHubContract(){}

  public static class GitHubEntry implements BaseColumns {
    public static final String TABLE_NAME = "github_entry";
    public static final String COLUMN_NAME_NAME = "name";
  }

}
