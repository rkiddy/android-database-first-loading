package org.ganymede.databaseloadingapp;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *
 * Created by ray on 2015-09/10.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "MyDB.db";

    public static final String DATABASE_PATH = "/data/data/org.ganymede.databaseloadingapp/databases/";

    public static final boolean USING_PREBUILT_DATABASE_FILE = false;

    public static Activity activity;

    public static synchronized DatabaseHelper getInstance(Context ctx) {

        if (sInstance == null) {
            sInstance = new DatabaseHelper(ctx.getApplicationContext());
            activity = (Activity)ctx;
        }
        return sInstance;
    }

    /**
     *
     */
    public static SQLiteDatabase getDb(Context ctx, SQLiteDatabase db) {
        return (db != null && db.isOpen()) ? db : getInstance(ctx).getWritableDatabase();
    }

    /**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {

        try
        {
            SQLiteDatabase firstDb = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);

            firstDb.close();

            String outFileName = DATABASE_PATH + DATABASE_NAME;

            String[] fileNames = new String[] {
                    "files/mydb_aa",
                    "files/mydb_ab"
            };

            FileOutputStream Fos = new FileOutputStream( outFileName );

            for ( String fileName : fileNames )
            {
                InputStream inputFile = activity.getAssets().open(fileName);

                int totalLength = 0;
                try {
                    totalLength = inputFile.available();
                } catch ( java.io.IOException ioe) {
                    System.err.println("ERROR in totalLength: " + ioe + ": " + ioe.getMessage());
                }

                System.out.println("totalLength = " + totalLength);

                // Reading and writing the file Method 1 :
                byte[] buffer = new byte[totalLength];
                int len = 0;
                try {
                    len = inputFile.read(buffer);
                    System.err.println("read bytes # " + len);
                } catch ( java.io.IOException ioe) {
                    System.err.println("ERROR in read: " + ioe + ": " + ioe.getMessage());
                }
                Fos.write( buffer );
                inputFile.close();
            }
            Fos.close();

        } catch(java.io.IOException e) {
            Toast.makeText(activity, "IO Error Reading/writing File", Toast.LENGTH_SHORT).show();
        }

        Log.v("DataBase Installation", "End of creating files");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over. For now. -rrk 2015-05-19

        onCreate(db);
    }

    /**
     * Downgrade should not occur, so throws an exception.
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new IllegalStateException("Asked to downgrade the database, but this should not happen!");
    }
}
