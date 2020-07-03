package com.androidstudy.sssimul4s;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.androidstudy.sssimul4s.customView.StatusDetailView;
import com.androidstudy.sssimul4s.dataManage.dto.StatusViewsInfo_DTO;
import com.androidstudy.sssimul4s.customView.StatusSimpleView;
import com.androidstudy.sssimul4s.dataManage.manager.StatusManager;

public class StatusActivity extends AppCompatActivity {
    StatusDetailView status_lvl;
    StatusSimpleView status_STR, status_DEX, status_INT, status_LCK;
    EditText edtAbilityPoint;
    Button btnOK, btnCancle;

    StatusManager statusManager;



    //Level Up Test 용 View
    Button btnPlus1, btnPlus2;
    //reset Test 용 View
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //생성된 customView에서 name을 받아서 dto를 돌려줌
        //customView(manager.getPoint(name));
        //객체의 값으로는 name, point와 분배가능 포인트가 ref로 전달됨
        //분배가능 포인트가 0이면 버튼 비활성화, 아니면 활성화
        //버튼 누를때 마다 분배가능 포인트를 줄이고 값은 증가 시켜야함.

        //manager에서 객체를 전부받아 값을 관리 할것?


        //1-1. manager 객체 생성
        //1-2. addView(arrayList(view들)) 전달 -> manager에서 객체를 전부 관리 하게 하기위해.
        //1-3. addAbilityPointView(남는 포인트를 전달 받을 view 전달
        //1-4. 적용, 확인 버튼 전달
        //2. manager에서 db 불러옴 -> 남는포인트 및 스텟(str, dex, int, lck)
        //3. addView로 전달한 view들의 name에 따라 point를 set함
        //4. 남은 포인트 여부에 따라 view들의 버튼 활성화 여부를 정함.

        init();

        //TEST CODE
        level_up_test();
        reset_test();

    }

    /************************initialization************************/
    private void init(){

        //View 초기화
        status_lvl=findViewById(R.id.statusLVL);
        status_STR=findViewById(R.id.statusSTR);
        status_DEX=findViewById(R.id.statusDEX);
        status_INT=findViewById(R.id.statusINT);
        status_LCK=findViewById(R.id.statusLCK);
        edtAbilityPoint=findViewById(R.id.edtAbilityPoint);
        btnOK=findViewById(R.id.btnOK);
        btnCancle=findViewById(R.id.btnCancel);

        //View DTO에 전달
        StatusViewsInfo_DTO viewsInfo=new StatusViewsInfo_DTO();

        viewsInfo.setLvlView(status_lvl);
        viewsInfo.setStatusSimple(status_STR);
        viewsInfo.setStatusSimple(status_DEX);
        viewsInfo.setStatusSimple(status_INT);
        viewsInfo.setStatusSimple(status_LCK);
        viewsInfo.setAbilityPoint(edtAbilityPoint);
        viewsInfo.addButton(btnOK, btnCancle);

        statusManager=new StatusManager(viewsInfo, this);

    }

    /************************Level Up Test************************/
    private void level_up_test(){
        btnPlus1=findViewById(R.id.btnPlus1);
        btnPlus2=findViewById(R.id.btnPlus2);

        btnPlus1.setOnClickListener(view -> statusManager.doLevelUp(1));
        btnPlus2.setOnClickListener(view -> statusManager.doLevelUp(10));
    }
    /************************Reset Test************************/
    private void reset_test(){
        btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(view -> statusManager.doResetTable());
    }
}