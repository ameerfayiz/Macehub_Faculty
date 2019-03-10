package com.macehub.faculty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "staff_details";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_DEP = "department";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_FAV = "fav";
    private static final String KEY_ID = "id";
    private static final String KEY_IMG_LOC = "imgloc";
    private static final String KEY_NAME = "name";
    private static final String TABLE_STAFFS = "ma_staffs";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE ma_staffs(id TEXT PRIMARY KEY,name TEXT,details TEXT,imgloc TEXT,department TEXT,fav INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ma_staffs");
        onCreate(sQLiteDatabase);
    }

    void addStaff(staff staff) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, staff.getId());
        contentValues.put(KEY_NAME, staff.getName());
        contentValues.put(KEY_DETAILS, staff.getDetails());
        contentValues.put(KEY_IMG_LOC, staff.getImgloc());
        contentValues.put(KEY_DEP, staff.getDepartment());
        contentValues.put(KEY_FAV, Integer.valueOf(staff.getFav()));

        try{
            writableDatabase.insertOrThrow(TABLE_STAFFS, null, contentValues);
            writableDatabase.close();
        }catch (SQLException e){
            contentValues.remove(KEY_ID);
            contentValues.remove(KEY_FAV);
            writableDatabase.update(TABLE_STAFFS, contentValues, "id = ?", new String[]{staff.getId()});
            writableDatabase.close();
        }


    }

    staff getStaff(String str) {
        Cursor query = getReadableDatabase().query(TABLE_STAFFS, new String[]{KEY_ID, KEY_NAME, KEY_DETAILS, KEY_IMG_LOC, KEY_DEP, KEY_FAV}, "id=?", new String[]{str}, KEY_ID, null, null, null);
        if (query != null) {
            query.moveToFirst();
        }
        return new staff(query.getString(0), query.getString(1), query.getString(2), query.getString(3), query.getString(4), query.getInt(5));
    }

    public ArrayList<staff> getAllstaffs() {
        ArrayList<staff> arrayList = new ArrayList();
        Cursor rawQuery = getWritableDatabase().rawQuery("SELECT  * FROM ma_staffs", null);
        if (rawQuery.moveToFirst()) {
            do {
                staff staff = new staff();
                staff.setId(rawQuery.getString(0));
                staff.setName(rawQuery.getString(1));
                staff.setDetails(rawQuery.getString(2));
                staff.setImgloc(rawQuery.getString(3));
                staff.setDepartment(rawQuery.getString(4));
                staff.setFav(rawQuery.getInt(5));
                arrayList.add(staff);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public ArrayList<staff> getStaffsByDep(String str) {
        ArrayList<staff> arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM ma_staffs WHERE department='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        Cursor rawQuery = getWritableDatabase().rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            do {
                staff staff = new staff();
                staff.setId(rawQuery.getString(0));
                staff.setName(rawQuery.getString(1));
                staff.setDetails(rawQuery.getString(2));
                staff.setImgloc(rawQuery.getString(3));
                staff.setDepartment(rawQuery.getString(4));
                staff.setFav(rawQuery.getInt(5));
                arrayList.add(staff);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public ArrayList<staff> getStaffsFav() {
        ArrayList<staff> arrayList = new ArrayList();
        Cursor rawQuery = getWritableDatabase().rawQuery("SELECT  * FROM ma_staffs WHERE fav=1", null);
        if (rawQuery.moveToFirst()) {
            do {
                staff staff = new staff();
                staff.setId(rawQuery.getString(0));
                staff.setName(rawQuery.getString(1));
                staff.setDetails(rawQuery.getString(2));
                staff.setImgloc(rawQuery.getString(3));
                staff.setDepartment(rawQuery.getString(4));
                staff.setFav(rawQuery.getInt(5));
                arrayList.add(staff);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public ArrayList<staff> getstaffsByName(String str) {
        String[] chips=str.split("\\s+");
        int count=chips.length,i;

        ArrayList<staff> arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM ma_staffs WHERE ");

        stringBuilder.append("name LIKE '%");
        for(i=0;i<count;i++){
            stringBuilder.append(chips[i] + "%");
        }
        stringBuilder.append("' ");


        stringBuilder.append("OR details LIKE '%");
        for(i=0;i<count;i++){
            stringBuilder.append(chips[i] + "%");
        }
        stringBuilder.append("' ");


        Cursor rawQuery = getWritableDatabase().rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            do {
                staff staff = new staff();
                staff.setId(rawQuery.getString(0));
                staff.setName(rawQuery.getString(1));
                staff.setDetails(rawQuery.getString(2));
                staff.setImgloc(rawQuery.getString(3));
                staff.setDepartment(rawQuery.getString(4));
                staff.setFav(rawQuery.getInt(5));
                arrayList.add(staff);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public int setfav(int i, String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FAV, Integer.valueOf(i));
        return writableDatabase.update(TABLE_STAFFS, contentValues, "id = ?", new String[]{str});
    }

    public int updatestaff(staff staff) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, staff.getName());
        contentValues.put(KEY_DETAILS, staff.getDetails());
        contentValues.put(KEY_IMG_LOC, staff.getImgloc());
        contentValues.put(KEY_DEP, staff.getDepartment());
        return writableDatabase.update(TABLE_STAFFS, contentValues, "id = ?", new String[]{staff.getId()});
    }

    public void deletestaff(staff staff) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(TABLE_STAFFS, "id = ?", new String[]{String.valueOf(staff.getId())});
        writableDatabase.close();
    }

    public int getstaffsCount() {
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM ma_staffs", null);
        int count = rawQuery.getCount();
        rawQuery.close();
        return count;
    }

    public int getstaffsCountByDep(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM ma_staffs WHERE department=");
        stringBuilder.append(str);
        Cursor rawQuery = getReadableDatabase().rawQuery(stringBuilder.toString(), null);
        int count = rawQuery.getCount();
        rawQuery.close();
        return count;
    }

    public int getstaffsCountByName(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM ma_staffs WHERE name LIKE '%");
        stringBuilder.append(str);
        stringBuilder.append("%'");
        Cursor rawQuery = getReadableDatabase().rawQuery(stringBuilder.toString(), null);
        int count = rawQuery.getCount();
        rawQuery.close();
        return count;
    }
}
