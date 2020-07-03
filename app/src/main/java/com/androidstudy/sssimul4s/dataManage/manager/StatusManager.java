package com.androidstudy.sssimul4s.dataManage.manager;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;

import com.androidstudy.sssimul4s.R;
import com.androidstudy.sssimul4s.customView.StatusSimpleView;
import com.androidstudy.sssimul4s.dataManage.dto.StatusInfoDTO;
import com.androidstudy.sssimul4s.dataManage.dto.StatusViewsInfo_DTO;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


//TODO : Bug -> AP를 전부 소모했을때 다른 버튼들은 비활성화가 안됨 -> -1로 떨어짐
public class StatusManager {
    private Context context;

    //Status View에 접근을 위한 객체
    private StatusViewsInfo_DTO viewsInfo;
    //스탯 포인트 확인용
    private StatusInfoDTO statusInfo;
    HashMap<String, StatusSimpleView> views;

    //DB Manager
    private DBManager conn;


    /************************Constructor************************/
    public StatusManager(StatusViewsInfo_DTO viewsInfo, Context context) {
        this.context=context;
        this.viewsInfo=viewsInfo;
        init();
    }
    /************************initialization************************/
    private void init(){
        setStatusFromDB();
        setViews();
        setButtonListener();
    }



    /************************DB Load************************/
    // DB 불러와서 처리하는 method
    // DB에서 스탯 불러오와서 HashMap 처리
    //TODO: SETTER 수동 대입 할 것 -> 객체 전달하면 결합도가 너무 올라가유우~
    private void setStatusFromDB(){
        conn=DBManager.getInstance(context);
        statusInfo=new StatusInfoDTO(conn);
    }
    /************************View set************************/
    private void setViews(){
        //TODO : View에 DB내용 입력

        viewsInfo.getAbilityPointView().setText(Integer.toString(statusInfo.getAbilityPoint_current()));

        views=viewsInfo.getViews();

        viewsInfo.getLevel().setPoint(statusInfo.getStatusLVL());
        views.get(conn.STATUS_STR).setPoint(statusInfo.getStatusSTR());
        views.get(conn.STATUS_DEX).setPoint(statusInfo.getStatusDEX());
        views.get(conn.STATUS_INT).setPoint(statusInfo.getStatusINT());
        views.get(conn.STATUS_LCK).setPoint(statusInfo.getStatusLCK());

        //TODO : AbilityPoint의 값은 init, current로 구별
        //TODO : 각각 DB의 값과 같은 값, DB에 적용전 값으로 나뉨.

        //각 버튼들 설정
        checkBtnOK();
        checkAP();
        checkButtonEnable();
    }
    /************************Button set************************/
    private void setButtonListener(){

        // btnOK
        // btnOK.getText()에 따라 달라짐
        viewsInfo.getBtnOK().setOnClickListener(view -> {
            String okSTR=viewsInfo.getBtnOK().getText().toString();
            if(okSTR.equals(context.getString(R.string.Submit))){
                submitValues();
            }else{
                ((Activity)context).finish();
            }

        });
        //btnCancel
        viewsInfo.getBtnCancel().setOnClickListener(view -> {
            //TODO : btnOK가 적용이면 변경사항 취소할껀지 다이얼로그 띄우기
            //확인 누르면 변경사항이 롤백되고(DB에서 다시불러옴), 취소버튼를 다시누르면 닫히게됨
            //String okSTR=viewsInfo.getBtnOK().getText().toString();
            //if(checkBtnOK)로 하면될듯

            ((Activity)context).finish();
        });


        Set set=views.keySet();
        Iterator<String> iterator=set.iterator();

        while(iterator.hasNext()){
            String name=iterator.next();
            //View에서 btn객체 리턴 받기
            views.get(name).getBtnLeft().setOnClickListener(view -> {
                int current_AP=statusInfo.getAbilityPoint_current();
                int init_Ap=statusInfo.getAbilityPoint_init();
                int status_value=statusInfo.getStatusList().get(name);

                //current_AP가 init_AP보다 클수없음

                statusInfo.setAbilityPoint_current(++current_AP);
                statusInfo.setStatus(name, --status_value);
                checkButtonEnable();

                setViews();
            });
            views.get(name).getBtnRight().setOnClickListener(view -> {
                int current_AP=statusInfo.getAbilityPoint_current();
                int status_value=statusInfo.getStatusList().get(name);
                statusInfo.setAbilityPoint_current(--current_AP);
                statusInfo.setStatus(name, ++status_value);
                checkButtonEnable();

                setViews();
            });
        }
    }




    //AP가 0이면 버튼이 안보이게
    private void checkAP(){
        boolean bool;
        if(statusInfo.getAbilityPoint_init()==0) bool=false;
        else bool=true;

        setButtonVisible(bool);
    }

    //View들의 버튼들을 보이게 할건지 설정
    private void setButtonVisible(boolean bool){
        Set set=views.keySet();
        Iterator<String> iterator=set.iterator();

        while(iterator.hasNext()){
            String name=iterator.next();
            System.out.println(name+" : "+bool);
            views.get(name).setBtnVisible(bool);
        }
    }

    private void checkButtonEnable(){
        Set set=views.keySet();
        Iterator<String> iterator=set.iterator();

        while(iterator.hasNext()){
            String name=iterator.next();
            if(statusInfo.getAbilityPoint_current()==0&&statusInfo.getAbilityPoint_init()!=0){
                //스텟을 쓸수없음, 뺄순있음
                views.get(name).getBtnLeft().setEnabled(true);
                views.get(name).getBtnRight().setEnabled(false);
                return;
            }
            if(statusInfo.getAbilityPoint_init()>statusInfo.getAbilityPoint_current()){
                //current가 init보다 작은경우 -> 사용된경우 -> 쓸수도있고 뺄수도있음
                views.get(name).getBtnLeft().setEnabled(true);
                views.get(name).getBtnRight().setEnabled(true);
            }else{
                //current가 init보다 크거나 같은경우 -> 뺄수없음
                views.get(name).getBtnLeft().setEnabled(false);
                views.get(name).getBtnRight().setEnabled(true);
            }
            if(statusInfo.getAbilityPoint_current()<=0){
                views.get(name).getBtnRight().setEnabled(false);
            }

            //views.get(name).getBtnLeft().setEnabled();
        }
    }




    //init==current일경우 btnOK의 값이 확인, 다를경우 적용-> 누르면 DB에 적용됨
    private boolean checkBtnOK() {
        if (statusInfo.getAbilityPoint_current() == statusInfo.getAbilityPoint_init()) {
            viewsInfo.getBtnOK().setText(context.getString(R.string.OK));
            return false;
        } else {
            viewsInfo.getBtnOK().setText(context.getString(R.string.Submit));
            return true;
        }
    }

    /************************SEND TO DataBase************************/
    //viewInfo 값 DB에 저장
    private void submitValues(){
        HashMap<String, Integer> statusList=statusInfo.getStatusList();
        conn.updateStauts(statusList);
        init();
    }
    /**************SEND LVL UP TO DataBase**************/
    //임시 기능이라 Activity에서 호출 할수있게 public으로 설정
    //level up 명령
    public void doLevelUp(int LEVEL_UP){
        System.out.println("Level up!! +"+LEVEL_UP);

        int LEVEL_UP_POINT=LEVEL_UP*5;
        int currentLevel=statusInfo.getStatusLVL();
        int currentAP=statusInfo.getAbilityPoint_current();
        statusInfo.setStatusLVL(currentLevel+LEVEL_UP);
        statusInfo.setAbilityPoint_current(currentAP+LEVEL_UP_POINT);

        //test
        printStatusInfo();
        //level과 ap를 각각 올린 객체를 다시 적용
        submitValues();
    }
    /**************RESET DataBase**************/
    public void doResetTable() {
        conn.resetStatus();
        init();
    }


    /**************Print statusInfo 4 Test**************/
    public void printStatusInfo(){
        System.out.println("LVL : "+statusInfo.getStatusLVL()+", AP : "+statusInfo.getAbilityPoint_init());
    }


}
