package DBLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class GameOptionDB {


    AssetDatabaseOpenHelper assetDatabaseOpenHelper ;


    public GameOptionDB(Context applicationContext) {

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

    public void insertGameOption(HashMap<String, String> queryValues){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ContentValues values = new ContentValues();

        values.put("GAME_OPTION_ID", queryValues.get("GAME_OPTION_ID"));
        values.put("OPTION_NAME", queryValues.get("OPTION_NAME"));
        values.put("OPTION_DETAIL", queryValues.get("OPTION_DETAIL"));
        values.put("IMAGE", queryValues.get("IMAGE"));
        values.put("GAME_QUESTION_ID", queryValues.get("GAME_QUESTION_ID"));

        db.insert("GAME_OPTION", null, values);

        db.close();

        values.put("GAME_OPTION_ID", queryValues.get("GAME_OPTION_ID"));
    }


    public void updateGameOption(HashMap<String, String> queryValues){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ContentValues values = new ContentValues();

        values.put("GAME_OPTION_ID", queryValues.get("GAME_OPTION_ID"));
        values.put("OPTION_NAME", queryValues.get("OPTION_NAME"));
        values.put("OPTION_DETAIL", queryValues.get("OPTION_DETAIL"));
        values.put("IMAGE", queryValues.get("IMAGE"));
        values.put("GAME_QUESTION_ID", queryValues.get("GAME_QUESTION_ID"));

        db.update("GAME_OPTION", values, "GAME_OPTION_ID" + "= ?", new String[]{queryValues.get("GAME_OPTION_ID")});


        db.close();

    }


    public void deleteGameOption(String gameOptionId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        String deleteQuery = "DELETE FROM GAME_OPTION WHERE GAME_OPTION_ID ='"+gameOptionId+"'";

        db.execSQL(deleteQuery);

    }




    public ArrayList<HashMap<String, String>> getAllGameOptions(){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ArrayList<HashMap<String, String>> gameOptionArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM GAME_OPTION ORDER BY GAME_OPTION_ID ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{
                HashMap<String, String> gameOptionMap = new HashMap<String, String>();

                gameOptionMap.put("GAME_OPTION_ID", cursor.getString(0));
                gameOptionMap.put("OPTION_NAME", cursor.getString(1));
                gameOptionMap.put("OPTION_DETAIL", cursor.getString(2));
                gameOptionMap.put("IMAGE", cursor.getString(3));
                gameOptionMap.put("GAME_QUESTION_ID", cursor.getString(4));



                gameOptionArrayList.add(gameOptionMap);

            }while(cursor.moveToNext());

        }


        cursor.close();
        db.close();

        return gameOptionArrayList;

    }



    public HashMap<String, String> getGameOptionByGameOptionId(String gameOptionId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        HashMap<String, String> gameOptionMap = new HashMap<String, String>();

        String selectQuery = "SELECT * FROM GAME_OPTION WHERE GAME_OPTION_ID = '"+gameOptionId+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{

                gameOptionMap.put("GAME_OPTION_ID", cursor.getString(0));
                gameOptionMap.put("OPTION_NAME", cursor.getString(1));
                gameOptionMap.put("OPTION_DETAIL", cursor.getString(2));
                gameOptionMap.put("IMAGE", cursor.getString(3));
                gameOptionMap.put("GAME_QUESTION_ID", cursor.getString(4));

            }while(cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return gameOptionMap;

    }



    public HashMap<String, String> getGameOptionByGameQuestionIdAndOptionName(String gameQuestionId, String optionName){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        HashMap<String, String> gameOptionMap = new HashMap<String, String>();

        String selectQuery = "SELECT * FROM GAME_OPTION WHERE GAME_QUESTION_ID = '"+gameQuestionId+"' AND OPTION_NAME = '"+optionName+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{

                gameOptionMap.put("GAME_OPTION_ID", cursor.getString(0));
                gameOptionMap.put("OPTION_NAME", cursor.getString(1));
                gameOptionMap.put("OPTION_DETAIL", cursor.getString(2));
                gameOptionMap.put("IMAGE", cursor.getString(3));
                gameOptionMap.put("GAME_QUESTION_ID", cursor.getString(4));

            }while(cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return gameOptionMap;

    }

}
