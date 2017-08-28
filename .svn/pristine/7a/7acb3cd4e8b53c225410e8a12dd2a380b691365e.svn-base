package com.xiongyuan.lottery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LinearLayout ll = (LinearLayout) findViewById(R.id.lay);

        //把数据显示至屏幕
        for (int i=0;i<5;i++) {
            TextView tv = new TextView(this);
            tv.setBackgroundResource(R.drawable.textviewred);
            LinearLayout.LayoutParams param= new LinearLayout.LayoutParams(52,52);
            param.setMargins(0,0,5,0);
            tv.setGravity(Gravity.CENTER);
            tv.setIncludeFontPadding (false);
            tv.setTextSize(16);
            tv.setTextColor(this.getResources().getColor(R.color.colorWhite));
            //2.把信息设置为文本框的内容
            tv.setText(i+"");
            ll.addView(tv,param);
        }
    }
}
