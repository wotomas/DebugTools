package com.example.jimmy.debugtools;

import com.example.jimmy.debugtools.database.GitHubContract;


/**
 * Created by jimmy on 13/02/2017.
 */

public final class Constant {
  public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
    GitHubContract.GitHubEntry.TABLE_NAME + " (" +
    GitHubContract.GitHubEntry._ID + " INTEGER PRIMARY KEY," +
    GitHubContract.GitHubEntry.COLUMN_NAME_NAME  + " TEXT)";

  public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
    GitHubContract.GitHubEntry.TABLE_NAME;

  public static final String PREFERENCE_NAME = "Pref_name";
}
