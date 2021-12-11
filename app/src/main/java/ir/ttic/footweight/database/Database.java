package ir.ttic.footweight.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ir.ttic.footweight.model.Track;
import ir.ttic.footweight.model.Weight;

public class Database {

  private final String dbName = "database.db";
  private final String weightTableName = "Weight";
  private final String trackTableName = "Track";

  SQLiteDatabase sqLiteDatabase;

  private static Database database;

  Database(Context context) {
    sqLiteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
    createWeightTableIfNotExist();
    createTrackTableIfNotExist();
  }


  public static Database getInstance(Context context) {
    if (database == null) {
      database = new Database(context);
    }
    return database;
  }

  private void createWeightTableIfNotExist() {
    sqLiteDatabase.execSQL(String.format(Locale.US,
      "create table if not exists %s (Date real unique ,Weight real);",
      weightTableName
    ));
  }

  private void createTrackTableIfNotExist() {
    sqLiteDatabase.execSQL(String.format(Locale.US,
      "create table if not exists %s (" +
        "ID real ,User varchar,Longitude real,Latitude real,Speed real,Date date);",
      trackTableName));
  }


  @SuppressLint("DefaultLocale")
  public void insertNewWeight(Weight weight) {
    sqLiteDatabase.execSQL(
      String.format(
        "insert into %s values('%s','%f')",
        weightTableName,
        weight.getDate(),
        weight.getWeight()
      )
    );
  }


  @SuppressLint("DefaultLocale")
  public void insertNewTrack(Track track) {
    sqLiteDatabase.execSQL(
      String.format(
        "insert into %s values(%d,'%s',%f,%f,%f,datetime('now'));",
        trackTableName,
        track.getId(),
        track.getUser(),
        track.getLongitude(),
        track.getLatitude(),
        track.getSpeed()
      )
    );
  }

  public List<Weight> getAllWeights() {

    List<Weight> weights = new ArrayList<>();

    Cursor cursor = sqLiteDatabase.rawQuery(
      String.format("select * from %s", weightTableName),
      null
    );

    cursor.moveToFirst();
    for (int i = 0; i < cursor.getCount(); i++) {
      weights.add(cursorToWeightModel(cursor));
      cursor.moveToNext();
    }

    cursor.close();
    return weights;
  }

  public List<Track> getNavigations(){

    List<Track> tracks = new ArrayList<>();

    Cursor cursor = sqLiteDatabase.rawQuery(
      String.format("select * from (select * from %s order by Date asc) group by ID", trackTableName),
      null
    );

    cursor.moveToFirst();
    for (int i = 0; i < cursor.getCount(); i++) {
      tracks.add(cursorToTrackModel(cursor));
      cursor.moveToNext();
    }

    cursor.close();
    return tracks;

  }

  public List<Track> getNavigationTracks(long id) {

    List<Track> tracks = new ArrayList<>();

    Cursor cursor = sqLiteDatabase.rawQuery(
      String.format(Locale.US,"select * from %s where ID=%d order by Date asc", trackTableName,id),
      null
    );

    cursor.moveToFirst();
    for (int i = 0; i < cursor.getCount(); i++) {
      tracks.add(cursorToTrackModel(cursor));
      cursor.moveToNext();
    }

    cursor.close();
    return tracks;

  }

  public List<Track> getAllTracks() {
    List<Track> tracks = new ArrayList<>();

    Cursor cursor = sqLiteDatabase.rawQuery(
      String.format("select * from %s", trackTableName),
      null
    );

    cursor.moveToFirst();
    for (int i = 0; i < cursor.getCount(); i++) {
      tracks.add(cursorToTrackModel(cursor));
      cursor.moveToNext();
    }

    cursor.close();
    return tracks;
  }

  private Track cursorToTrackModel(Cursor cursor) {
    return new Track(
      cursor.getLong(0),
      cursor.getString(1),
      cursor.getDouble(2),
      cursor.getDouble(3),
      cursor.getDouble(4)
    ).setDate(cursor.getString(5));
  }


  private Weight cursorToWeightModel(Cursor cursor) {
    return new Weight(
      Long.parseLong(cursor.getString(0)),
      Double.parseDouble(cursor.getString(1))
    );
  }

  public void remove(Weight model) {
    sqLiteDatabase.execSQL(String.format("delete from %s where Date='%s'", weightTableName, model.getDate()));
  }

}


