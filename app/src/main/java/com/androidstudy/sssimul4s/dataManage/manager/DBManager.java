package com.androidstudy.sssimul4s.dataManage.manager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.androidstudy.sssimul4s.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//DB 관리 하는 클래스애오 \(oOo)/

//일단 디자인을 한개의 캐릭터로 잡았음.
//추후 다수의 캐릭터 생성에서 이용할수있게 USER.db(character_id, level) -> character_id FK -> STATUS.db(character_id, status_name, status_value) 형식으로 구현해볼것.
//일단 간편하게 STATUS에 level, status들을 함께 다루게 함
public class DBManager {

    private static final String DB_GameData="GameData.db";          //Data Base Name
    private static final int DB_VERSION=1;                          //Data Base Version

    private static final String TB_Status="Status";                 //Data Base Table Name
    private static final String TB_Status_Name="status_name";
    private static final String TB_Status_Value="status_value";

    public static String STATUS_LVL;
    public static String STATUS_STR;
    public static String STATUS_DEX;
    public static String STATUS_INT;
    public static String STATUS_LCK;
    public static String STATUS_AP;

    private static DBManager dbManager =null;
    private SQLiteDatabase database;

    private Context context;






    //Singleton Pattern
    public static  DBManager getInstance(Context context){
        if(dbManager==null){dbManager=new DBManager(context);};
        return  dbManager;
    }
    //Constructor
    private DBManager(Context context){
        this.context=context;

        STATUS_LVL=context.getString(R.string.STATUS_LVL);
        STATUS_STR=context.getString(R.string.STATUS_STR);
        STATUS_DEX=context.getString(R.string.STATUS_DEX);
        STATUS_INT=context.getString(R.string.STATUS_INT);
        STATUS_LCK=context.getString(R.string.STATUS_LCK);
        STATUS_AP=context.getString(R.string.STATUS_AP);
        //DB Open
        dbOpen();
    }

    //db 열기
    private void dbOpen(){
        database= context.openOrCreateDatabase(DB_GameData, context.MODE_PRIVATE, null);
        if(!checkTable()){
            createTable();
        };
    }
    //Table이 있는지 여부 체크
    private boolean checkTable(){
        try{
            database.rawQuery("SELECT '' FROM "+TB_Status+" limit 1;" , null);
        }catch(SQLiteException e){
            e.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //Table 초기화
    /*******************************CREATE*******************************/
    private void createTable(){
        String sqlCreate_Table_Status="CREATE TABLE IF NOT EXISTS " + TB_Status +
                "("+TB_Status_Name+" TEXT PRIMARY KEY, "+
                TB_Status_Value+" INTEGER);";
        database.execSQL(sqlCreate_Table_Status);

        insertStatus(STATUS_LVL, 1);
        insertStatus(STATUS_STR, 5);
        insertStatus(STATUS_DEX, 5);
        insertStatus(STATUS_INT, 5);
        insertStatus(STATUS_LCK, 5);
        insertStatus(STATUS_AP, 0);//Ability Point
    }



    /*******************************RESET*******************************/
    public void resetStatus() {
        String sql_DROP_Table_Status="DROP TABLE "+TB_Status;
        database.execSQL(sql_DROP_Table_Status);
        dbOpen();
    }




    /*******************************INSERT*******************************/
    //TB_Status_Insert
    private void insertStatus(String name, int key){
        //TODO : 추후 예외 처리 할 것
        //참고 링크 : https://stackoverflow.com/questions/433392/how-do-i-use-prepared-statements-in-sqlite-in-android
        database.beginTransaction();

        try {
            String sql_Insert_INTO_TB_Status_INIT = "INSERT INTO " + TB_Status +
                    "(" + TB_Status_Name + ", " +
                    TB_Status_Value + ") VALUES (?, ?);";
            SQLiteStatement stmt = database.compileStatement(sql_Insert_INTO_TB_Status_INIT);

            stmt.bindString(1, name);
            stmt.bindLong(2, key);
            stmt.executeInsert();
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.w("Exception Insert: ", e);
        }finally {
            database.endTransaction();
        }

    }

    /*******************************UPDATE*******************************/
    //단일처리 구현
    private void updateStatus(String name, int value){
        String sql_Update_TB_Status=
                "UPDATE '"+TB_Status+
                        "' SET "+TB_Status_Value+ "=?"+
                        " WHERE "+TB_Status_Name+"=?;";
        SQLiteStatement stmt=database.compileStatement(sql_Update_TB_Status);

        stmt.bindLong(1,value);
        stmt.bindString(2,name);

        stmt.execute();
        //TODO : executeUpdateDelete()가 작동 안함 안되는 이유 알아보기
        //int numberOfRowsAffected =stmt.executeUpdateDelete();
        //System.out.println("Update Status : "+numberOfRowsAffected);
        System.out.println("name : "+name+", value : "+value);
    }
    //변경사항은 HashMap으로 받으면 처리하게 함
    public void updateStauts(HashMap<String, Integer> statusList){
        database.beginTransaction();

        Set set=statusList.keySet();
        Iterator<String> iterator=set.iterator();
        try {
            while (iterator.hasNext()) {
                String key = iterator.next();
                int value = statusList.get(key);
                updateStatus(key, value);
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.w("Exception Update: ", e);
        }finally {
            database.endTransaction();
        }


    }






    /*******************************SELECT*******************************/
    private Cursor selectStatus(){
        String sql_Select_TB_Status="SELECT * FROM "+TB_Status;

        Cursor cursor=database.rawQuery(sql_Select_TB_Status,null);
        return cursor;
    }








    public HashMap<String, Integer> getStatusList() {
        HashMap<String, Integer> statusList=new HashMap<>();

        Cursor cursor=selectStatus();
        //TODO : HashMap에 id, value 순으로 담을것.
        while (cursor.moveToNext()) {
            //System.out.println(cursor.getString(0)+", "+cursor.getInt(1));        //TEST
            statusList.put(cursor.getString(0), cursor.getInt(1));
        }
        cursor.close();
        return statusList;
    }


}
