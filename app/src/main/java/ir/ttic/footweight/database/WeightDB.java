package ir.ttic.footweight.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ir.ttic.footweight.model.WeightModel;

public class WeightDB {

  private final String dbName = "weightDB";
  private final String weightTableName = "Weight";

  SQLiteDatabase sqLiteDatabase;

  private static WeightDB weightDB;

  WeightDB(Context context) {
    sqLiteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
    createWeightTableIfNotExist();
  }


  public static WeightDB getInstance(Context context) {
    if (weightDB == null) {
      weightDB = new WeightDB(context);
    }
    return weightDB;
  }


  private void createWeightTableIfNotExist() {
    sqLiteDatabase.execSQL(String.format(Locale.US, "create table if not exists %s (Date varchar unique ,Weight varchar);", weightTableName));
  }


  @SuppressLint("DefaultLocale")
  public void insertNewWeight(WeightModel weightModel) {
    sqLiteDatabase.execSQL(
      String.format(
        "insert into %s values('%s','%f')",
        weightTableName,
        weightModel.getDate(),
        weightModel.getWeight()
      )
    );
  }

  public List<WeightModel> getAllWeights() {

    List<WeightModel> weightModels = new ArrayList<>();
    Cursor cursor = sqLiteDatabase.rawQuery(String.format("select * from %s", weightTableName), null);
    cursor.moveToFirst();
    for (int i = 0; i < cursor.getCount(); i++) {
      weightModels.add(cursorToWeightModel(cursor));
    }

    cursor.close();
    return weightModels;
  }


  private WeightModel cursorToWeightModel(Cursor cursor) {
    return new WeightModel(
      Long.parseLong(cursor.getString(0)),
      Double.parseDouble(cursor.getString(1))
    );
  }

  public void remove(WeightModel model) {
    sqLiteDatabase.execSQL(String.format("delete from %s where Date='%s'",weightTableName,model.getDate()));
  }
}
