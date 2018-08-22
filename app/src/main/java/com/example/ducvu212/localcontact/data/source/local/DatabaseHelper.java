package com.example.ducvu212.localcontact.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ducvu212.localcontact.data.model.Contact;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseConstants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DatabaseConstants.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean addContact(Contact contact) {
        if (contact == null) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.NAME, contact.getName());
        values.put(DatabaseConstants.PHONE_NUMBER, contact.getPhone());
        values.put(DatabaseConstants.PHOTO, contact.getPhoto());
        values.put(DatabaseConstants.FAVORITE, contact.getFavorite());
        db.insert(DatabaseConstants.DATABASE_TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public ArrayList<Contact> getAllContactDatabase() {
        ArrayList<Contact> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(DatabaseConstants.QUERY_ALL_RECODRD, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                String name = cursor.getString(1);
                String sdt = cursor.getString(2);
                String photo = cursor.getString(3);
                int fav = cursor.getInt(4);
                arrayList.add(new Contact(name, sdt, photo, fav));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public boolean isTableExists(boolean openDb) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (openDb) {
            if (db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if (!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase();
            }
        }

        Cursor cursor = db.rawQuery(DatabaseConstants.CHECK_TABLE, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void update(int fav, int position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.FAVORITE, fav);
        db.update(DatabaseConstants.DATABASE_TABLE_NAME, values, DatabaseConstants.ID + "=?",
                new String[] { String.valueOf(position) });
    }
}
