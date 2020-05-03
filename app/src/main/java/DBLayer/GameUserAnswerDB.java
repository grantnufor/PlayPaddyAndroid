package DBLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class GameUserAnswerDB {



    AssetDatabaseOpenHelper assetDatabaseOpenHelper ;


    public GameUserAnswerDB(Context applicationContext) {

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

    public void insertGameUserAnswer(HashMap<String, String> queryValues){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ContentValues values = new ContentValues();

        values.put("GAME_USER_ANSWER_ID", queryValues.get("GAME_USER_ANSWER_ID"));
        values.put("GAME_QUESTION_ID", queryValues.get("GAME_QUESTION_ID"));
        values.put("USER_ID", queryValues.get("USER_ID"));
        values.put("USER_ANSWER", queryValues.get("USER_ANSWER"));
        values.put("CORRECT", queryValues.get("CORRECT"));
        values.put("POSITION", queryValues.get("POSITION"));
        values.put("TIME_SUBMITTED", queryValues.get("TIME_SUBMITTED"));
        values.put("DATE_SUBMITTED", queryValues.get("DATE_SUBMITTED"));

        db.insert("GAME_USER_ANSWER", null, values);

        db.close();

        values.put("GAME_USER_ANSWER_ID", queryValues.get("GAME_USER_ANSWER_ID"));
    }


    public void updateGameUserAnswer(HashMap<String, String> queryValues){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ContentValues values = new ContentValues();

        values.put("GAME_USER_ANSWER_ID", queryValues.get("GAME_USER_ANSWER_ID"));
        values.put("GAME_QUESTION_ID", queryValues.get("GAME_QUESTION_ID"));
        values.put("USER_ID", queryValues.get("USER_ID"));
        values.put("USER_ANSWER", queryValues.get("USER_ANSWER"));
        values.put("CORRECT", queryValues.get("CORRECT"));
        values.put("POSITION", queryValues.get("POSITION"));
        values.put("TIME_SUBMITTED", queryValues.get("TIME_SUBMITTED"));
        values.put("DATE_SUBMITTED", queryValues.get("DATE_SUBMITTED"));

        db.update("GAME_USER_ANSWER", values, "GAME_USER_ANSWER_ID" + "= ?", new String[]{queryValues.get("GAME_USER_ANSWER_ID")});


        db.close();

    }


    public void deleteGameUserAnswer(String gameUserAnswerId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        String deleteQuery = "DELETE FROM GAME_USER_ANSWER WHERE GAME_USER_ANSWER_ID ='"+gameUserAnswerId+"'";

        db.execSQL(deleteQuery);

    }




    public ArrayList<HashMap<String, String>> getAllGameUserAnswers(){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ArrayList<HashMap<String, String>> gameUserAnswerArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM GAME_USER_ANSWER ORDER BY GAME_USER_ANSWER_ID ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{
                HashMap<String, String> gameUserAnswerMap = new HashMap<String, String>();

                gameUserAnswerMap.put("GAME_USER_ANSWER_ID", cursor.getString(0));
                gameUserAnswerMap.put("GAME_QUESTION_ID", cursor.getString(1));
                gameUserAnswerMap.put("USER_ID", cursor.getString(2));
                gameUserAnswerMap.put("USER_ANSWER", cursor.getString(3));
                gameUserAnswerMap.put("CORRECT", cursor.getString(4));
                gameUserAnswerMap.put("POSITION", cursor.getString(5));
                gameUserAnswerMap.put("TIME_SUBMITTED", cursor.getString(6));
                gameUserAnswerMap.put("DATE_SUBMITTED", cursor.getString(7));



                gameUserAnswerArrayList.add(gameUserAnswerMap);

            }while(cursor.moveToNext());

        }


        cursor.close();
        db.close();

        return gameUserAnswerArrayList;

    }



    public HashMap<String, String> getGameUserAnswerByGameUserAnswerId(String gameUserAnswerId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        HashMap<String, String> gameUserAnswerMap = new HashMap<String, String>();

        String selectQuery = "SELECT * FROM GAME_USER_ANSWER WHERE GAME_USER_ANSWER_ID = '"+gameUserAnswerId+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{


                gameUserAnswerMap.put("GAME_USER_ANSWER_ID", cursor.getString(0));
                gameUserAnswerMap.put("GAME_QUESTION_ID", cursor.getString(1));
                gameUserAnswerMap.put("USER_ID", cursor.getString(2));
                gameUserAnswerMap.put("USER_ANSWER", cursor.getString(3));
                gameUserAnswerMap.put("CORRECT", cursor.getString(4));
                gameUserAnswerMap.put("POSITION", cursor.getString(5));
                gameUserAnswerMap.put("TIME_SUBMITTED", cursor.getString(6));
                gameUserAnswerMap.put("DATE_SUBMITTED", cursor.getString(7));

            }while(cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return gameUserAnswerMap;

    }


    public HashMap<String, String> getGameUserAnswerByGameQuestionId(String gameQuestionId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        HashMap<String, String> gameUserAnswerMap = new HashMap<String, String>();

        String selectQuery = "SELECT * FROM GAME_USER_ANSWER WHERE GAME_QUESTION_ID = '"+gameQuestionId+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{


                gameUserAnswerMap.put("GAME_USER_ANSWER_ID", cursor.getString(0));
                gameUserAnswerMap.put("GAME_QUESTION_ID", cursor.getString(1));
                gameUserAnswerMap.put("USER_ID", cursor.getString(2));
                gameUserAnswerMap.put("USER_ANSWER", cursor.getString(3));
                gameUserAnswerMap.put("CORRECT", cursor.getString(4));
                gameUserAnswerMap.put("POSITION", cursor.getString(5));
                gameUserAnswerMap.put("TIME_SUBMITTED", cursor.getString(6));
                gameUserAnswerMap.put("DATE_SUBMITTED", cursor.getString(7));

            }while(cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return gameUserAnswerMap;

    }


}
