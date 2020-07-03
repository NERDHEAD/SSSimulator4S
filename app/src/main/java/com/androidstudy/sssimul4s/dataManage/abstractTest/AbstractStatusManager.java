package com.androidstudy.sssimul4s.dataManage.abstractTest;

import android.widget.Button;
import android.widget.EditText;

import com.androidstudy.sssimul4s.customView.StatusSimpleView;

import java.util.ArrayList;


//실제로 상속받아서 생성하지 않을 예정
//코드를 어떻게 짤지 계획 단계

abstract class AbstractStatusManager {
    //스탯 포인트 확인용
    private int AbilityPoint_init;
    private int AbilityPoint_current;
    //view 전달 받을 용도
    private ArrayList<StatusSimpleView> views;
    //DTO HashMap
    //HashMap<String StatusDTO.getName(), StatusDTO statusDTO> statusData;

    //View
    EditText abilityPointView;
    Button submit, cancel;

    //AbilityPoint_current를 표시할 EditText -> Enable =false 및 비활성 시 달라지는 color를 검은색으로 통일


    AbstractStatusManager(
            ArrayList<StatusSimpleView> views,
            EditText abilityPointView,
            Button submit, Button cancel
            ){}

    ////1-1. manager 객체 생성
    ////1-2. addView(arrayList(view들)) 전달
    ////1-3. addAbilityPointView(남는 포인트를 전달 받을 view 전달
    //1-4. 적용, 확인 버튼 전달
    //2. manager에서 db 불러옴 -> 남는포인트 및 스텟(str, dex, int, lck)
    //3. addView로 전달한 view들의 name에 따라 point를 set함
    //4. 남은 포인트 여부에 따라 view들의 버튼 활성화 여부를 정함.


    //getStatusData 가져옴 -> HashMap<String StatusDTO.getName(), StatusDTO statusDTO> 의 형태로
    //
    abstract void init();
    //init이 끝난 후 받은 DB data를 기반으로 view에 데이터 전달



    //view를 ArrayList로 받음 -> manager에서 객체를 전부 관리 하게 하기위해.
    abstract void addView(ArrayList<StatusSimpleView> views);
    //남는 포인트 표시할 객체 전달
    abstract void addAbilityPointView(EditText abilityPointView);
    //적용, 확인 버튼 -> 적용은 적용기능, 적용이 완료되면 확인 버튼 ( 초기 point와 현재 point가 같을때 = 확인, 다를때 =취소?)
    abstract void addButtons(Button submit, Button cancel);

    //db가져오기, DBconn에서 역할별로 class 구분?
    //해당 값들을 스탯은 hashmap에 name, point로 분배
    //남는 포인트로 AbilityPoint_init, AbilityPoint_current 초기화
    abstract void getStatData();



    //레벨업 관리 -> test용 레벨업 버튼
    //레벨 테이블 lvl 총스텟, 사용한 스탯(기본 스탯 5pt 종류 4개 =20pt를 제외한 합산?



}
