package com.androidstudy.sssimul4s;

import android.content.Context;
import android.content.Intent;

import com.androidstudy.sssimul4s.customView.NicknameDialog;

import java.util.LinkedHashMap;

public class MainMenuData {
    //value 값에는 인텐트?
    private LinkedHashMap<String, Class> itemList= new LinkedHashMap<>();
    private Context context;

    public MainMenuData(Context applicationContext) {
        //TODO : 나중에 세부 동작도 설정할수있게 할것. (Activity, Dialog 등)
        this.context =applicationContext;
        setItemList("Status", StatusActivity.class);
        setItemList("Skill", SkillActivity.class);
        setItemList("Change Nickname", NicknameDialog.class);
    }
    //SETTER
    private void setItemList(String itemName,Class activityName){
        itemList.put(itemName, activityName);
    }
    //GETTER
    public String[] getItemList(){
        return itemList.keySet().toArray(new String[itemList.size()]);
    }
    public Intent getIntent(int position){
        Intent intent=new Intent(context, itemList.get(getItemList()[position]));
        return intent;
    }
    public boolean startWithActivity(int position){
        if(
                getItemList()[position].equals("Status")||
                        getItemList()[position].equals("Skill")
        )return true;
        else return false;
    }
    public boolean startWithDialog(int position){
        if(getItemList()[position].equals("Change Nickname")) return true;
        else return false;
    }
}