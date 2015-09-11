
Filled the database file with this, which took way longer than it
seemed it was going to. But the use case I have is that I need a
database file bigger than 1 MB.

     $ sqlite3 mydb.db
     SQLite version 3.8.2 2013-12-06 14:53:30
     Enter ".help" for instructions
     Enter SQL statements terminated with a ";"
     sqlite> 
     sqlite> create table my_data (pk int primary key, stuff char(36));
     sqlite> .read t1.sql
     sqlite> .quit
     $ 

Now chop up the file.

     $ aplit -b 999K mydb.db mydb_

This gives me:

     $ ls -l my*
     -rw-rw-r-- 1 ray ray 1022976 Sep 10 16:48 mydb_aa
     -rw-rw-r-- 1 ray ray  158720 Sep 10 16:48 mydb_ab
     -rw-r--r-- 1 ray ray 1181696 Sep 10 16:35 mydb.db
     $

Then I move the fragments to the place the code expects them.

     $ mv mydb_* app/src/main/assets/files

And here is the result when I launch on a fresh emulator. I have seen this error\
and I have seen other errors, but this is what I see with this project right now.

The emulator that I am running in is a Nexus 5, running API 22. It has 1.5 GB of RAM
and 64 MB VM heap, which is the default setup for this emulator when created in Android Studio.


     09-10 16:54:32.916    1300-1300/? I/art﹕ Not late-enabling -Xcheck:jni (already on)
     09-10 16:54:35.876    1300-1300/org.ganymede.databaseloadingapp E/SQLiteLog﹕ (5) database is locked
     09-10 16:54:35.877    1300-1300/org.ganymede.databaseloadingapp E/SQLiteDatabase﹕ Failed to open database '/data/data/org.ganymede.databaseloadingapp/databases/MyDB.db'.
         android.database.sqlite.SQLiteDatabaseLockedException: database is locked (code 5): , while compiling: PRAGMA journal_mode
                 at android.database.sqlite.SQLiteConnection.nativePrepareStatement(Native Method)
                 at android.database.sqlite.SQLiteConnection.acquirePreparedStatement(SQLiteConnection.java:889)
                 at android.database.sqlite.SQLiteConnection.executeForString(SQLiteConnection.java:634)
                 at android.database.sqlite.SQLiteConnection.setJournalMode(SQLiteConnection.java:320)
                 at android.database.sqlite.SQLiteConnection.setWalModeFromConfiguration(SQLiteConnection.java:294)
                 at android.database.sqlite.SQLiteConnection.open(SQLiteConnection.java:215)
                 at android.database.sqlite.SQLiteConnection.open(SQLiteConnection.java:193)
                 at android.database.sqlite.SQLiteConnectionPool.openConnectionLocked(SQLiteConnectionPool.java:463)
                 at android.database.sqlite.SQLiteConnectionPool.open(SQLiteConnectionPool.java:185)
                 at android.database.sqlite.SQLiteConnectionPool.open(SQLiteConnectionPool.java:177)
                 at android.database.sqlite.SQLiteDatabase.openInner(SQLiteDatabase.java:806)
                 at android.database.sqlite.SQLiteDatabase.open(SQLiteDatabase.java:791)
                 at android.database.sqlite.SQLiteDatabase.openDatabase(SQLiteDatabase.java:694)
                 at android.database.sqlite.SQLiteDatabase.openDatabase(SQLiteDatabase.java:669)
                 at org.ganymede.databaseloadingapp.DatabaseHelper.onCreate(DatabaseHelper.java:60)
                 at android.database.sqlite.SQLiteOpenHelper.getDatabaseLocked(SQLiteOpenHelper.java:251)
                 at android.database.sqlite.SQLiteOpenHelper.getWritableDatabase(SQLiteOpenHelper.java:163)
                 at org.ganymede.databaseloadingapp.DatabaseHelper.getDb(DatabaseHelper.java:44)
                 at org.ganymede.databaseloadingapp.MainActivity.onCreate(MainActivity.java:18)
                 at android.app.Activity.performCreate(Activity.java:5990)
                 at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1106)
                 at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2278)
                 at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2390)
                 at android.app.ActivityThread.access$800(ActivityThread.java:151)
                 at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1303)
                 at android.os.Handler.dispatchMessage(Handler.java:102)
                 at android.os.Looper.loop(Looper.java:135)
                 at android.app.ActivityThread.main(ActivityThread.java:5257)
                 at java.lang.reflect.Method.invoke(Native Method)
                 at java.lang.reflect.Method.invoke(Method.java:372)
                 at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:903)
                 at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:698)
     09-10 16:54:35.877    1300-1300/org.ganymede.databaseloadingapp D/AndroidRuntime﹕ Shutting down VM
         --------- beginning of crash
     09-10 16:54:35.877    1300-1300/org.ganymede.databaseloadingapp E/AndroidRuntime﹕ FATAL EXCEPTION: main
         Process: org.ganymede.databaseloadingapp, PID: 1300
         java.lang.RuntimeException: Unable to start activity ComponentInfo{org.ganymede.databaseloadingapp/org.ganymede.databaseloadingapp.MainActivity}: android.database.sqlite.SQLiteDatabaseLockedException: database is locked (code 5): , while compiling: PRAGMA journal_mode
                 at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2325)
                 at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2390)
                 at android.app.ActivityThread.access$800(ActivityThread.java:151)
                 at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1303)
                 at android.os.Handler.dispatchMessage(Handler.java:102)
                 at android.os.Looper.loop(Looper.java:135)
                 at android.app.ActivityThread.main(ActivityThread.java:5257)
                 at java.lang.reflect.Method.invoke(Native Method)
                 at java.lang.reflect.Method.invoke(Method.java:372)
                 at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:903)
                 at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:698)
          Caused by: android.database.sqlite.SQLiteDatabaseLockedException: database is locked (code 5): , while compiling: PRAGMA journal_mode
                 at android.database.sqlite.SQLiteConnection.nativePrepareStatement(Native Method)
                 at android.database.sqlite.SQLiteConnection.acquirePreparedStatement(SQLiteConnection.java:889)
                 at android.database.sqlite.SQLiteConnection.executeForString(SQLiteConnection.java:634)
                 at android.database.sqlite.SQLiteConnection.setJournalMode(SQLiteConnection.java:320)
                 at android.database.sqlite.SQLiteConnection.setWalModeFromConfiguration(SQLiteConnection.java:294)
                 at android.database.sqlite.SQLiteConnection.open(SQLiteConnection.java:215)
                 at android.database.sqlite.SQLiteConnection.open(SQLiteConnection.java:193)
                 at android.database.sqlite.SQLiteConnectionPool.openConnectionLocked(SQLiteConnectionPool.java:463)
                 at android.database.sqlite.SQLiteConnectionPool.open(SQLiteConnectionPool.java:185)
                 at android.database.sqlite.SQLiteConnectionPool.open(SQLiteConnectionPool.java:177)
                 at android.database.sqlite.SQLiteDatabase.openInner(SQLiteDatabase.java:806)
                 at android.database.sqlite.SQLiteDatabase.open(SQLiteDatabase.java:791)
                 at android.database.sqlite.SQLiteDatabase.openDatabase(SQLiteDatabase.java:694)
                 at android.database.sqlite.SQLiteDatabase.openDatabase(SQLiteDatabase.java:669)
                 at org.ganymede.databaseloadingapp.DatabaseHelper.onCreate(DatabaseHelper.java:60)
                 at android.database.sqlite.SQLiteOpenHelper.getDatabaseLocked(SQLiteOpenHelper.java:251)
                 at android.database.sqlite.SQLiteOpenHelper.getWritableDatabase(SQLiteOpenHelper.java:163)
                 at org.ganymede.databaseloadingapp.DatabaseHelper.getDb(DatabaseHelper.java:44)
                 at org.ganymede.databaseloadingapp.MainActivity.onCreate(MainActivity.java:18)
                 at android.app.Activity.performCreate(Activity.java:5990)
                 at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1106)
                 at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2278)
                 at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2390)
                 at android.app.ActivityThread.access$800(ActivityThread.java:151)
                 at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1303)
                 at android.os.Handler.dispatchMessage(Handler.java:102)
                 at android.os.Looper.loop(Looper.java:135)
                 at android.app.ActivityThread.main(ActivityThread.java:5257)
                 at java.lang.reflect.Method.invoke(Native Method)
                 at java.lang.reflect.Method.invoke(Method.java:372)
                 at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:903)
                 at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:698)
     09-10 16:54:35.894    1300-1313/org.ganymede.databaseloadingapp I/art﹕ Background sticky concurrent mark sweep GC freed 3389(390KB) AllocSpace objects, 0(0B) LOS objects, 35% free, 722KB/1117KB, paused 7.742ms total 12.030ms

