package DBLayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by CHISOM ANUFOROM on 16/4/2020.
 */
public class AssetDatabaseOpenHelper {

    private static final String DB_NAME = "playpaddy.db";

    private Context context;

    public AssetDatabaseOpenHelper(Context context) {
        this.context = context;

    }

    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath("/data/data/org.landoria.mac.playpaddy/playpaddy.db");

//        context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);

//        File dbFile = context.getDatabasePath(DB_NAME);
//        context.getDatabasePath(DB_NAME);
//        dbFile.setWritable(true);
        if (!dbFile.exists()) {
            try {
//                exportDB();
//                dbFile.mkdirs();
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
//        else{
//
//        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

//    private static final String DATABASE_ALTER_TEAM_1 = "ALTER TABLE CONSULTATION ADD COLUMN  string;";
//
//    private static final String DATABASE_ALTER_TEAM_2 = "ALTER TABLE "
//            + TABLE_TEAM + " ADD COLUMN " + COLUMN_STADIUM + " string;";
//
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < 2) {
//            db.execSQL(DATABASE_ALTER_TEAM_1);
//        }
//        if (oldVersion < 3) {
//            db.execSQL(DATABASE_ALTER_TEAM_2);
//        }
//    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

    private void exportDB(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "org.landoria.mac.playpaddy" +"/databases/"+DB_NAME;
        String backupDBPath = DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            //Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
