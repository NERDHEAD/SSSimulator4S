package com.androidstudy.sssimul4s.customView;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidstudy.sssimul4s.R;
import com.androidstudy.sssimul4s.dataManage.manager.SharedPreferenceManager;

public class NicknameDialog {
    private Context context;
    private Dialog dialog;

    private NicknameDialogListener nicknameDialogListener;

    public NicknameDialog(Context context, NicknameDialog.NicknameDialogListener nicknameDialogListener) {
        this.context=context;
        this.nicknameDialogListener=nicknameDialogListener;
    }


    public void startDialog() {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_nickname);
        setDialogListener(dialog);
        dialog.show();
    }


    protected void setDialogListener(Dialog dialog) {
        final EditText edtNickname=dialog.findViewById(R.id.edtNickname);
        final Button btnOK=dialog.findViewById(R.id.btnOK);
        final Button btnCancel=dialog.findViewById(R.id.btnCancel);


        //Enter Key 입력시 btnOK 호출
        edtNickname.setOnKeyListener((view, keyCode, keyEvent) -> {
            if(keyCode==keyEvent.KEYCODE_ENTER) {
                btnOK.performClick();
                return true;
            }
            return false;
        });

        btnOK.setOnClickListener(view -> {
            SharedPreferenceManager spManager=new SharedPreferenceManager(context);
            String nickname=edtNickname.getText().toString();

            if(!nickname.equals("")){
                spManager.setNickName(nickname);
                nicknameDialogListener.okButton();
                dialog.dismiss();
            }else Toast.makeText(context,"닉네임을 입력해주새오", Toast.LENGTH_LONG).show();

        });

        btnCancel.setOnClickListener(view -> Toast.makeText(context,"닉네임 입력 거절은 거절합니당 헿", Toast.LENGTH_LONG).show());
    }


    public interface NicknameDialogListener{
        void okButton();
    }
}