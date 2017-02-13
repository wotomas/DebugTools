package com.example.jimmy.debugtools.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.jimmy.debugtools.Constant.SQL_CREATE_ENTRIES;
import static com.example.jimmy.debugtools.Constant.SQL_DELETE_ENTRIES;

/**
 * Created by jimmy on 13/02/2017.
 */

public class GitHubDbHelper extends SQLiteOpenHelper {
  // If you change the database schema, you must increment the database version.
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "Github.db";

  public GitHubDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_ENTRIES);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // simply to discard the data and start over
    db.execSQL(SQL_DELETE_ENTRIES);
    onCreate(db);
  }
}
