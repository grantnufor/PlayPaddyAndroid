package DBLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionDB {


    AssetDatabaseOpenHelper assetDatabaseOpenHelper ;


    public SessionDB(Context applicationContext) {

        assetDatabaseOpenHelper = new AssetDatabaseOpenHelper(applicationContext);//initialize the database helper

//		try {
//			dbHelper.createDataBase();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		database = dbHelper.openDataBase();//open the database connection to the existing database and assign the SQLiteDatabase

//		String settings = "CREATE TABLE IF NOT EXISTS " + "EXAM" + " ("
//				+ "EXAM_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//				+ "EXAM_NAME" + " TEXT, "
//				+ "INSTITUTION_NAME" + " TEXT, "
//				+ "COUNTRY_ID" + " INTEGER, "+ ");";
//		database.execSQL(settings);

    }

    public void insertSession(HashMap<String, String> queryValues){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ContentValues values = new ContentValues();

        values.put("SESSION_ID", queryValues.get("SESSION_ID"));
        values.put("USER_NAME", queryValues.get("USER_NAME"));
        values.put("PASSWORD", queryValues.get("PASSWORD"));
        values.put("STATUS", queryValues.get("STATUS"));
        values.put("USER_SERVER_ID", queryValues.get("USER_SERVER_ID"));

        db.insert("SESSION", null, values);

        db.close();

        values.put("SESSION_ID", queryValues.get("SESSION_ID"));
    }


    public void updateSession(HashMap<String, String> queryValues){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ContentValues values = new ContentValues();

        values.put("SESSION_ID", queryValues.get("SESSION_ID"));
        values.put("USER_NAME", queryValues.get("USER_NAME"));
        values.put("PASSWORD", queryValues.get("PASSWORD"));
        values.put("STATUS", queryValues.get("STATUS"));
        values.put("USER_SERVER_ID", queryValues.get("USER_SERVER_ID"));

        db.update("SESSION", values, "SESSION_ID" + "= ?", new String[]{queryValues.get("SESSION_ID")});


        db.close();

    }


    public void deleteSession(String sessionId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        String deleteQuery = "DELETE FROM SESSION WHERE SESSION_ID ='"+sessionId+"'";

        db.execSQL(deleteQuery);

    }




    public ArrayList<HashMap<String, String>> getAllSessions(){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ArrayList<HashMap<String, String>> sessionArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM SESSION ORDER BY SESSION_ID ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{
                HashMap<String, String> sessionMap = new HashMap<String, String>();

                sessionMap.put("SESSION_ID", cursor.getString(0));
                sessionMap.put("USER_NAME", cursor.getString(1));
                sessionMap.put("PASSWORD", cursor.getString(2));
                sessionMap.put("STATUS", cursor.getString(3));
                sessionMap.put("USER_SERVER_ID", cursor.getString(4));


                sessionArrayList.add(sessionMap);
            }while(cursor.moveToNext());

        }


        cursor.close();
        db.close();

        return sessionArrayList;

    }



    public HashMap<String, String> getSessionBySessionId(String sessionId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        HashMap<String, String> sessionMap = new HashMap<String, String>();

        String selectQuery = "SELECT * FROM SESSION WHERE SESSION_ID = '"+sessionId+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{

                sessionMap.put("SESSION_ID", cursor.getString(0));
                sessionMap.put("USER_NAME", cursor.getString(1));
                sessionMap.put("PASSWORD", cursor.getString(2));
                sessionMap.put("STATUS", cursor.getString(3));
                sessionMap.put("USER_SERVER_ID", cursor.getString(4));


            }while(cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return sessionMap;

    }

}
