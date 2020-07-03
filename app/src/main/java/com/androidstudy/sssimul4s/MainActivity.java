package com.androidstudy.sssimul4s;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidstudy.sssimul4s.dataManage.manager.SharedPreferenceManager;
import com.androidstudy.sssimul4s.customView.NicknameDialog;

public class MainActivity extends AppCompatActivity {
    //TODO: ListView 구성 요소 : status, skill, nicknameChange 기능
    private String[] itemList;
    private ListView menuListView;
    String nickName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMenuListView();
        sayHello();
    }

    //Menu 구성
    private void setMenuListView(){
        menuListView=findViewById(R.id.menuListView);
        MainMenuData mainMenuData=new MainMenuData(getApplicationContext());
        itemList=mainMenuData.getItemList();

        ArrayAdapter adapter =new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList);

        menuListView.setAdapter(adapter);
        //람다식 사용
        menuListView.setOnItemClickListener((parent,v,position, id)->{
            if(mainMenuData.startWithActivity(position)) startActivity(mainMenuData.getIntent(position));
            else if(mainMenuData.startWithDialog(position)) {
                NicknameDialog dialog=new NicknameDialog(MainActivity.this, new NicknameDialog.NicknameDialogListener() {
                    @Override
                    public void okButton() {
                        SharedPreferenceManager spManager= new SharedPreferenceManager(MainActivity.this);
                        String nickName=spManager.getNickName();
                        Toast.makeText(getApplicationContext(),"닉네임이 "+nickName+"으로 변경되었습니다!!",Toast.LENGTH_LONG).show();
                    }
                });
                dialog.startDialog();
            }
        });
    }
    private void sayHello() {

        //String nickName="";
        //SharedPreferenceManager spManager= new SharedPreferenceManager(MainActivity.this);
        //nickName=spManager.getNickName();
        //Toast.makeText(getApplicationContext(),nickName+"님 환영합니다",Toast.LENGTH_LONG).show();

        String nickName="";
        SharedPreferenceManager spManager=new SharedPreferenceManager(MainActivity.this);

        //TODO : Dialog가 MainMenuData에서도 호출됨 재사용 고려 해보기
        if(spManager.getNickName().equals("")){
            Toast.makeText(getApplicationContext(),"닉네임을 입력해 주새오!",Toast.LENGTH_LONG).show();
            NicknameDialog dialog=new NicknameDialog(MainActivity.this, new NicknameDialog.NicknameDialogListener() {
                @Override
                public void okButton() {
                    sayHello();
                }
            });
            dialog.startDialog();
        }else nickName=spManager.getNickName();

        Toast.makeText(getApplicationContext(),nickName+"님 환영합니다",Toast.LENGTH_LONG).show();
    }
}