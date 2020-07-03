package com.androidstudy.sssimul4s.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.androidstudy.sssimul4s.R;


//TODO : StatusDetailView, StatusSimpleView 의 중복된 관계를 상속으로 해결 할것.
public class StatusDetailView extends LinearLayout {
    TextView tvName, tvPoint;

    public StatusDetailView(Context context) {
        super(context);

        init(context);
    }

    public StatusDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
        getAttrs(attrs);
    }

    public StatusDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
        getAttrs(attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StatusDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
        getAttrs(attrs, defStyleAttr, defStyleRes);
    }



    //인플레이트 이후 view 초기화 진행
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvName =findViewById(R.id.tvStatusDName);
        tvPoint =findViewById(R.id.tvStatusDPoint);
    }

    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.status_detail, this);
    }


    //Attrs에서 값 호출 ( STR, DEX, INT, LCK )
    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.StatusDetailView);

        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.StatusDetailView, defStyle,0);

        setTypeArray(typedArray);
    }
    private void getAttrs(AttributeSet attrs, int defStyle, int defStyleRes){
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.StatusDetailView, defStyle,defStyleRes);

        setTypeArray(typedArray);
    }




    private void setTypeArray(TypedArray typedArray){
        onFinishInflate();


        String status_name=typedArray.getString(R.styleable.StatusDetailView_status_dname);
        setName(status_name);

        typedArray.recycle();
    }



    //SETTER
    public void setName(String status_name){
        tvName.setText(status_name);
    }
    public void setPoint(int status_point){
        tvPoint.setText(Integer.toString(status_point));
    }


    //GETTER
    public String getName(){
        return tvName.getText().toString();
    }
    public int getPoint(){
        return Integer.parseInt(tvPoint.getText().toString());
    }

}
