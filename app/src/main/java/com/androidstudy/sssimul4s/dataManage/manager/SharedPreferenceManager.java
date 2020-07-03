package com.androidstudy.sssimul4s.dataManage.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    SharedPreferences sharedPreferences;
    Context context;

    public SharedPreferenceManager(Context context){
        this.context=context;
        sharedPreferences= context.getSharedPreferences("setting",Context.MODE_PRIVATE);
    }

    //SETTER
    private void setData(String key,String value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    //GETTER
    private String getData(String key){
        return sharedPreferences.getString(key, "");
    }
    //Wipe Data
    public void clearData(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }





    //nickname SETTER/GETTER
    public void setNickName(String nickname){
        setData("Nick_Name", nickname);
    }
    public String getNickName(){
        String nickname=getData("Nick_Name");

        return nickname;
    }
}