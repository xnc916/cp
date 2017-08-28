package com.xiongyuan.lottery.mypage.view.team;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.homepage.wheelview.ScreenInfo;
import com.xiongyuan.lottery.homepage.wheelview.WheelOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * 选项选择器，可支持一二三级联动选择
 *
 * @author Sai
 */
public class TimePopupWindow extends PopupWindow implements OnClickListener {
    private View rootView; // 总的布局
    WheelOptions wheelOptions;
    private View btnSubmit, btnCancel;
    private TextView starttv,endtv,suo;
    private LinearLayout startll,endll,time_picker;
    private OnOptionsSelectListener optionsSelectListener;
    private Shishi shishi;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_SUO = "suosuo";

    private boolean isRun = true;


    private int i = 0;

    public TimePopupWindow(Context context) {
        super(context);

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        rootView = mLayoutInflater.inflate(R.layout.activity_pop_left, null);
        // -----确定和取消按钮
        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = rootView.findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        suo = (TextView) rootView.findViewById(R.id.sou);
        suo.setTag(TAG_SUO);
        suo.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        startll = (LinearLayout) rootView.findViewById(R.id.start_ll);
        endll = (LinearLayout) rootView.findViewById(R.id.end_ll);
        time_picker = (LinearLayout) rootView.findViewById(R.id.time_picker);
        startll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                time_picker.setVisibility(View.VISIBLE);
                i = 0;
            }
        });
        endll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                time_picker.setVisibility(View.VISIBLE);
                i = 1;
            }
        });

        starttv = (TextView) rootView.findViewById(R.id.start_time);
        endtv = (TextView) rootView.findViewById(R.id.end_time);
        // ----转轮
        final View optionspicker = rootView.findViewById(R.id.optionspicker);
        ScreenInfo screenInfo = new ScreenInfo((Activity) context);
        wheelOptions = new WheelOptions(optionspicker);


        wheelOptions.screenheight = screenInfo.getHeight();



        setContentView(rootView);
        // 设置SelectPicPopupWindow的View
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x5e000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setOutsideTouchable(true);
        rootView.setOnClickListener(dismissListener);




    }




    public void setPicker(ArrayList<String> optionsItems) {
        wheelOptions.setPicker(optionsItems, null, null, false);
    }

    public void setPicker(ArrayList<String> options1Items,
                          ArrayList<ArrayList<String>> options2Items, boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, null, linkage);
    }

    public void setPicker(ArrayList<String> options1Items,
                          ArrayList<ArrayList<String>> options2Items,
                          ArrayList<ArrayList<ArrayList<String>>> options3Items,
                          boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, options3Items,
                linkage);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        wheelOptions.setCurrentItems(option1, 0, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     */
    public void setSelectOptions(int option1, int option2) {
        wheelOptions.setCurrentItems(option1, option2, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     * @param option3
     */
    public void setSelectOptions(int option1, int option2, int option3) {
        wheelOptions.setCurrentItems(option1, option2, option3);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     */
    public void setLabels(String label1) {
        wheelOptions.setLabels(label1, null, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     */
    public void setLabels(String label1, String label2) {
        wheelOptions.setLabels(label1, label2, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3) {
        wheelOptions.setLabels(label1, label2, label3);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelOptions.setCyclic(cyclic);
    }

    private OnClickListener dismissListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            time_picker.setVisibility(View.GONE);
            return;
        } else {
            if (tag.equals(TAG_SUBMIT)) {
                if (optionsSelectListener != null) {
                    int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                    optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);

                    String s = wheelOptions.updateTitle();
                    s = s.replaceAll("年", "-");
                    s = s.replaceAll("月", "-");
                    s = s.replaceAll("日", " ");

                    long l = System.currentTimeMillis();
//new日期对象
                    Date date = new Date(l);
//转换提日期输出格式
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                    if (i == 0) {
                        starttv.setText(s+dateFormat.format(date));
                    }
                    if (i == 1) {
                        endtv.setText(s+dateFormat.format(date));
                    }
                }
                isRun = false;
                time_picker.setVisibility(View.GONE);
                return;
            } else {
                shishi.onshishi(getTime(starttv.getText().toString()),getTime(endtv.getText().toString()));
                dismiss();
                return;
            }
        }
    }

    public interface OnOptionsSelectListener {
        public void onOptionsSelect(int options1, int option2, int options3);
    }

    public void setOnoptionsSelectListener(
            OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }

    public interface Shishi {
        public void onshishi(String start,String end);
    }

    public void setshishi(
            Shishi shi) {
        this.shishi = shi;
    }
    public static String getTime(String timeString){
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }
}
