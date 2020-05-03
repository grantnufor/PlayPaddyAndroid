package DBLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class GameQuestionDB {


    AssetDatabaseOpenHelper assetDatabaseOpenHelper ;


    public GameQuestionDB(Context applicationContext) {

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

    public void insertGameQuestion(HashMap<String, String> queryValues){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ContentValues values = new ContentValues();

        values.put("GAME_QUESTION_ID", queryValues.get("GAME_QUESTION_ID"));
        values.put("INSTRUCTION", queryValues.get("INSTRUCTION"));
        values.put("DETAIL", queryValues.get("DETAIL"));
        values.put("IMAGE", queryValues.get("IMAGE"));
        values.put("ANSWER", queryValues.get("ANSWER"));
        values.put("EXPLANATION", queryValues.get("EXPLANATION"));
        values.put("TIME_ALLOTED", queryValues.get("TIME_ALLOTED"));
        values.put("GAME_ID", queryValues.get("GAME_ID"));

        db.insert("GAME_QUESTION", null, values);

        db.close();

        values.put("GAME_QUESTION_ID", queryValues.get("GAME_QUESTION_ID"));
    }


    public void updateGameQuestion(HashMap<String, String> queryValues){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ContentValues values = new ContentValues();

        values.put("GAME_QUESTION_ID", queryValues.get("GAME_QUESTION_ID"));
        values.put("INSTRUCTION", queryValues.get("INSTRUCTION"));
        values.put("DETAIL", queryValues.get("DETAIL"));
        values.put("IMAGE", queryValues.get("IMAGE"));
        values.put("ANSWER", queryValues.get("ANSWER"));
        values.put("EXPLANATION", queryValues.get("EXPLANATION"));
        values.put("TIME_ALLOTED", queryValues.get("TIME_ALLOTED"));
        values.put("GAME_ID", queryValues.get("GAME_ID"));

        db.update("GAME_QUESTION", values, "GAME_QUESTION_ID" + "= ?", new String[]{queryValues.get("GAME_QUESTION_ID")});


        db.close();

    }


    public void deleteGameQuestion(String gameQuestionId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        String deleteQuery = "DELETE FROM GAME_QUESTION WHERE GAME_QUESTION_ID ='"+gameQuestionId+"'";

        db.execSQL(deleteQuery);

    }




    public ArrayList<HashMap<String, String>> getAllGameQuestions(){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        ArrayList<HashMap<String, String>> optionArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM GAME_QUESTION ORDER BY GAME_QUESTION_ID ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{
                HashMap<String, String> gameQuestionMap = new HashMap<String, String>();

                gameQuestionMap.put("GAME_QUESTION_ID", cursor.getString(0));
                gameQuestionMap.put("INSTRUCTION", cursor.getString(1));
                gameQuestionMap.put("DETAIL", cursor.getString(2));
                gameQuestionMap.put("IMAGE", cursor.getString(3));
                gameQuestionMap.put("ANSWER", cursor.getString(4));
                gameQuestionMap.put("EXPLANATION", cursor.getString(5));
                gameQuestionMap.put("TIME_ALLOTED", cursor.getString(6));
                gameQuestionMap.put("GAME_ID", cursor.getString(7));


                optionArrayList.add(gameQuestionMap);
            }while(cursor.moveToNext());

        }


        cursor.close();
        db.close();

        return optionArrayList;

    }



    public HashMap<String, String> getGameQuestionByGameQuestionId(String gameQuestionId){

        //Open connection to write data
        SQLiteDatabase db = assetDatabaseOpenHelper.openDatabase();
        HashMap<String, String> gameQuestionMap = new HashMap<String, String>();

        String selectQuery = "SELECT * FROM GAME_QUESTION WHERE GAME_QUESTION_ID = '"+gameQuestionId+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{

                gameQuestionMap.put("GAME_QUESTION_ID", cursor.getString(0));
                gameQuestionMap.put("INSTRUCTION", cursor.getString(1));
                gameQuestionMap.put("DETAIL", cursor.getString(2));
                gameQuestionMap.put("IMAGE", cursor.getString(3));
                gameQuestionMap.put("ANSWER", cursor.getString(4));
                gameQuestionMap.put("EXPLANATION", cursor.getString(5));
                gameQuestionMap.put("TIME_ALLOTED", cursor.getString(6));
                gameQuestionMap.put("GAME_ID", cursor.getString(7));

            }while(cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return gameQuestionMap;

    }


}
