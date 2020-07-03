package com.androidstudy.sssimul4s.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.androidstudy.sssimul4s.R;

public class StatusSimpleView extends LinearLayout {

    TextView tvName, tvPoint;
    Button btnLeft, btnRight;

    public StatusSimpleView(Context context) {
        super(context);

        init(context);
    }

    public StatusSimpleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
        getAttrs(attrs);
    }

    public StatusSimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
        getAttrs(attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StatusSimpleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
        getAttrs(attrs, defStyleAttr, defStyleRes);
    }



    //인플레이트 이후 view 초기화 진행
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvName =findViewById(R.id.tvStatusName);
        tvPoint =findViewById(R.id.tvStatusPoint);

        btnLeft =findViewById(R.id.btnLeft);
        btnRight =findViewById(R.id.btnRight);


    }

    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.status_simple, this);
    }


    //Attrs에서 값 호출 ( STR, DEX, INT, LCK )
    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.StatusSimpleView);

        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.StatusSimpleView, defStyle,0);

        setTypeArray(typedArray);
    }
    private void getAttrs(AttributeSet attrs, int defStyle, int defStyleRes){
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.StatusSimpleView, defStyle,defStyleRes);

        setTypeArray(typedArray);
    }




    private void setTypeArray(TypedArray typedArray){
        onFinishInflate();


        String status_name=typedArray.getString(R.styleable.StatusSimpleView_status_name);
        setName(status_name);

        typedArray.recycle();
    }



    /************************SETTER************************/
    public void setName(String status_name){
        tvName.setText(status_name);
    }
    public void setPoint(int status_point){
        tvPoint.setText(Integer.toString(status_point));
    }
    //버튼 활성, 비활성 관리
    public void setBtnVisible(boolean bool){
        if(bool){//참일경우 보일것
            btnLeft.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
        }else{//거짓일경우 숨길것
            btnLeft.setVisibility(View.INVISIBLE);
            btnRight.setVisibility(View.INVISIBLE);
        }


    }

    /************************GETTER************************/
    public String getName(){
        return tvName.getText().toString();
    }
    public Button getBtnLeft(){return btnLeft;}
    public Button getBtnRight() {return btnRight;}

    /*
    public int getPoint(){
        return Integer.parseInt(tvPoint.getText().toString());
    }
    */
}
