package com.shivenderkumar.kitchenbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.shivenderkumar.kitchenbook.model.User;

import java.util.ArrayList;
import java.util.List;

public class KitchenBookDatabaseHelper extends SQLiteOpenHelper {

    private static KitchenBookDatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "kitchenBookDatabase";
    private static final int DATABASE_VERSION = 1;


    public static synchronized KitchenBookDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new KitchenBookDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private KitchenBookDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.SQL_CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /* Inner class that defines the table contents */
    public static class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_IMAGE_URL = "image_url";

        private static final String SQL_CREATE_TABLE_USER =
                "CREATE TABLE " + UserTable.TABLE_NAME + " (" +
                        UserTable.COLUMN_NAME + " TEXT," +
                        UserTable.COLUMN_EMAIL + " TEXT," +
                        UserTable.COLUMN_IMAGE_URL + " TEXT)";

        private static final String SQL_DELETE_TABLE_USER =
                "DROP TABLE IF EXISTS " + UserTable.TABLE_NAME;

    }

    public void addUser(User user){
        // Create and/or open the database for writing
        SQLiteDatabase db = sInstance.getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(UserTable.COLUMN_NAME, user.getName());
            values.put(UserTable.COLUMN_EMAIL, user.getEmail());
            values.put(UserTable.COLUMN_IMAGE_URL, user.getImage_url());


            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(UserTable.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println("Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

    }

    public List<User> getUser(){
        List<User> users = new ArrayList<>();

        String USER_SELECT_QUERY =
                String.format("SELECT * FROM "+UserTable.TABLE_NAME);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    User newUser = new User();
                    newUser.setName(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME)));
                    newUser.setEmail(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_EMAIL)));
                    newUser.setImage_url(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_IMAGE_URL)));

                    users.add(newUser);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return users;

    }

    public void removeUser(){
        SQLiteDatabase db = sInstance.getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(UserTable.TABLE_NAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println("Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }

    }

}
