/*
package com.cs360_project_alayman.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs360_project_alayman.model.User;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final String TABLE_USER = "users";

    // Column names correspond to the User model
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // SQL statement to create the user table
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT" + ")";

    // SQL statement to drop the user table
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {

        if (databaseManager == null) {
            databaseManager = new DatabaseManager(context);
        }
    }

    private DatabaseManager(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop the table if it already exists
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        // Create the table again
        onCreate(sqLiteDatabase);
    }

    public void addUser (User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, user.getId());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
    }

    public Boolean authenticateUser (String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "= ?"
                + " AND " + COLUMN_PASSWORD + "= ?";
        String[] queryArgs = {username, password};
        Cursor cursor = db.rawQuery(query, queryArgs);

        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
*/