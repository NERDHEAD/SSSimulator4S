package com.androidstudy.sssimul4s.dataManage.dto;

import android.widget.Button;
import android.widget.EditText;

import com.androidstudy.sssimul4s.customView.StatusDetailView;
import com.androidstudy.sssimul4s.customView.StatusSimpleView;

import java.util.HashMap;


//Status View의 정보를 담는 객체
public class StatusViewsInfo_DTO {
    private StatusDetailView status_lvl;
    private HashMap<String, StatusSimpleView> views =new HashMap<>();
    private EditText edtAbilityPoint;
    Button btnOK, btnCancel;

    //GETTER
    public StatusDetailView getLevel(){return status_lvl;}
    public HashMap<String, StatusSimpleView> getViews() {return views;}
    public EditText getAbilityPointView(){return edtAbilityPoint;}
    public Button getBtnOK(){return btnOK;};
    public Button getBtnCancel() {return btnCancel;}

    //SETTER
    public void setLvlView(StatusDetailView status_lvl) {this.status_lvl=status_lvl;}
    public void setStatusSimple(StatusSimpleView status_name) {views.put(status_name.getName(), status_name);}
    public void setAbilityPoint(EditText edtAbilityPoint) {this.edtAbilityPoint=edtAbilityPoint;}
    public void addButton(Button btnOK, Button btnCancel) {
        this.btnOK=btnOK;
        this.btnCancel=btnCancel;
    }
}
