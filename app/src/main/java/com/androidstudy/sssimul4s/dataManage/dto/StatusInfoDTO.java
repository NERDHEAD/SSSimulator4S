package com.androidstudy.sssimul4s.dataManage.dto;

import com.androidstudy.sssimul4s.dataManage.manager.DBManager;

import java.util.HashMap;

public class StatusInfoDTO {
    private Integer abilityPoint_init;
    private Integer abilityPoint_current;
    private Integer statusLVL;
    private Integer statusSTR;
    private Integer statusDEX;
    private Integer statusINT;
    private Integer statusLCK;


    //TODO : 결합도가 높음, 나중에 리팩토링 할것
    //객체를 전달할게 아니고 배열이랑 상수가지고 StatusManager에서 관리할것.  -> 일일이 한개식 set?
    public StatusInfoDTO(DBManager conn){
        HashMap<String, Integer> statusList=conn.getStatusList();

        abilityPoint_init =statusList.get(DBManager.STATUS_AP);
        abilityPoint_current =statusList.get(DBManager.STATUS_AP);
        statusLVL=statusList.get(DBManager.STATUS_LVL);
        statusSTR=statusList.get(DBManager.STATUS_STR);
        statusDEX=statusList.get(DBManager.STATUS_DEX);
        statusINT=statusList.get(DBManager.STATUS_INT);
        statusLCK=statusList.get(DBManager.STATUS_LCK);

        System.out.println(DBManager.STATUS_LVL+" : "+statusLVL);
        System.out.println(DBManager.STATUS_STR+" : "+statusSTR);
        System.out.println(DBManager.STATUS_DEX+" : "+statusDEX);
        System.out.println(DBManager.STATUS_INT+" : "+statusINT);
        System.out.println(DBManager.STATUS_LCK+" : "+statusLCK);
        System.out.println(DBManager.STATUS_AP+" : "+abilityPoint_init);
    }

    //SETTER
    public void setAbilityPoint_init(Integer abilityPoint_init) {
        this.abilityPoint_init = abilityPoint_init;
    }
    public void setAbilityPoint_current(Integer abilityPoint_current) {
        this.abilityPoint_current = abilityPoint_current;
    }
    public void setStatusLVL(Integer statusLVL) {
        this.statusLVL = statusLVL;
    }
    public void setStatusSTR(Integer statusSTR) {
        this.statusSTR = statusSTR;
    }
    public void setStatusDEX(Integer statusDEX) {
        this.statusDEX = statusDEX;
    }
    public void setStatusINT(Integer statusINT) {
        this.statusINT = statusINT;
    }
    public void setStatusLCK(Integer statusLCK) {
        this.statusLCK = statusLCK;
    }
    ///GETTER
    public int getAbilityPoint_init() {return abilityPoint_init;}
    public int getAbilityPoint_current() {return abilityPoint_current;}
    public int getStatusLVL() {return statusLVL;}
    public int getStatusSTR() {return statusSTR;}
    public int getStatusDEX() {return statusDEX;}
    public int getStatusINT() {return statusINT;}
    public int getStatusLCK() {return statusLCK;}

    public HashMap<String, Integer> getStatusList(){
        HashMap<String, Integer> statusList=new HashMap<>();
        statusList.put(DBManager.STATUS_LVL, statusLVL);
        statusList.put(DBManager.STATUS_STR, statusSTR);
        statusList.put(DBManager.STATUS_DEX, statusDEX);
        statusList.put(DBManager.STATUS_INT, statusINT);
        statusList.put(DBManager.STATUS_LCK, statusLCK);
        statusList.put(DBManager.STATUS_AP, abilityPoint_current);

        return statusList;
    }

    public void setStatus(String name, int value) {
        final String STATUS_STR=DBManager.STATUS_STR;
        final String STATUS_DEX=DBManager.STATUS_DEX;
        final String STATUS_INT=DBManager.STATUS_INT;
        final String STATUS_LCK=DBManager.STATUS_LCK;
        final String STATUS_AP=DBManager.STATUS_AP;
        if (name.equals(DBManager.STATUS_LVL)) {setStatusLVL(value); }
        else if (name.equals(DBManager.STATUS_STR)) {setStatusSTR(value); }
        else if (name.equals(DBManager.STATUS_DEX)) {setStatusDEX(value); }
        else if (name.equals(DBManager.STATUS_INT)) {setStatusINT(value); }
        else if (name.equals(DBManager.STATUS_LCK)) {setStatusLCK(value); }
        else if (name.equals(DBManager.STATUS_AP)) {setAbilityPoint_current(value);}
    }
}
