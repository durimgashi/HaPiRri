package com.fiek.hapirri.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fiek.hapirri.constants.Constants;
import com.fiek.hapirri.model.Comment;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Comments ( ID INTEGER PRIMARY KEY AUTOINCREMENT, SIGNATURE TEXT, COMMENT TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Comments");
        onCreate(db);
    }

    public boolean insertData(String signature, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.SIGNATURE,signature);
        contentValues.put(Constants.COMMENT,comment);

        long result = db.insert("COMMENTS", null, contentValues);

        if (result == -1) return false;
        else
            return true;
    }

    public ArrayList<Comment> getAllData(){
        ArrayList<Comment> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Comments",null);
        while(cursor.moveToNext()){
            String id = cursor.getString(0);
            String signature = cursor.getString(1);
            String comment = cursor.getString(2);
            Comment comment1 = new Comment(id, signature,comment);

            arrayList.add(comment1);
        }
        return arrayList;
    }
}
