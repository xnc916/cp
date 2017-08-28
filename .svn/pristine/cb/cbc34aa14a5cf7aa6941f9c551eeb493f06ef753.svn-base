package com.chuck.wheelpackerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chuck.wheelpackerview.interfaces.OnPickerSelectListener;
import com.chuck.wheelpackerview.library.WheelItemView;
import com.chuck.wheelpackerview.library.listener.IWheelViewModel;
import com.chuck.wheelpackerview.library.utils.WheelViewLog;
import com.chuck.wheelpackerview.utils.PickerItemsUtils;

import java.util.List;

/**
 * 地址三级联动
 * Created by Chuckchen on 16/12/22.
 */

public class AddressPickerView extends BasePickerView {
    private TextView btn_confirm;
    private TextView btn_cancel;
    private TextView title;
    private OnPickerSelectListener pickerSelectListener;
    private PickerItemsUtils pUtils;
    private ViewGroup vg_address;
    private int[] ids;


    public AddressPickerView(Context mContext) {
        super(mContext);
        View view = getInflateView(R.layout.view_address_picker, null);
        addView(view);
        if (view != null) {
            btn_confirm = (TextView) view.findViewById(R.id.btn_confirm);
            btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
            title = (TextView) view.findViewById(R.id.tvTitle);
            vg_address = (ViewGroup) view.findViewById(R.id.ll_address);
            initListener();
            pUtils = new PickerItemsUtils(view);
        }
        ids = new int[]{R.id.wiv_one, R.id.wiv_two, R.id.wiv_three};
    }

    private void initListener() {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickerSelectListener != null) {
                    int[] currentItems = pUtils.getCurrentItems();
                    WheelViewLog.i("Provice Position:" + currentItems[0]);
                    WheelViewLog.i("City Postion:" + currentItems[1]);
                    WheelViewLog.i("Dist Postion:" + currentItems[2]);
                    pickerSelectListener.onSelect(currentItems[0]
                            , currentItems[1]
                            , currentItems[2]);
                    startDismiss();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDismiss();
            }
        });
    }

    public void setPickerData(List<IWheelViewModel> mOneItems
            , final List<List<IWheelViewModel>> mTwoItems
            , final List<List<List<IWheelViewModel>>> mThreeItems
            , boolean linkage) {
        if (pUtils != null) {
            pUtils.setPickerData(mOneItems, mTwoItems, mThreeItems, linkage);
        }
    }

    public void setOnPickerSelectListener(OnPickerSelectListener opsl) {
        this.pickerSelectListener = opsl;
    }

    public void setTitle(String titleStr) {
        if (title != null) {
            title.setText(titleStr);
        }
    }

    public void setConfirmTextColor(int color) {
        if (btn_confirm != null) {
            btn_confirm.setTextColor(color);
        }
    }

    public void setConfirmText(String text) {
        if (btn_confirm != null) {
            btn_confirm.setText(text);
        }
    }

    public void setCancelTextColor(int color) {
        if (btn_confirm != null) {
            btn_confirm.setTextColor(color);
        }
    }

    public void setCancelText(String text) {
        if (btn_confirm != null) {
            btn_confirm.setText(text);
        }
    }

    public void setSelectTextSize(int size) {
        if (vg_address != null) {
            for (int id : ids) {
                WheelItemView view = (WheelItemView) vg_address.findViewById(id);
                if (view != null) {
                    view.setSelectTextSize(size);
                }
            }
        }
    }

    /**
     * 被选中Item字体颜色
     *
     * @param color
     */
    public void setSelectTextColor(int color) {
        if (vg_address != null) {
            for (int id : ids) {
                WheelItemView view = (WheelItemView) vg_address.findViewById(id);
                if (view != null) {
                    view.setSelectTextColor(color);
                }
            }
        }
    }


    public void setUnselectTextSize(int size) {
        if (vg_address != null) {
            for (int id : ids) {
                WheelItemView view = (WheelItemView) vg_address.findViewById(id);
                if (view != null) {
                    view.setUnselectTextSize(size);
                }
            }
        }
    }


    public void setUnselectTextColor(int colorId) {
        if (vg_address != null) {
            for (int id : ids) {
                WheelItemView view = (WheelItemView) vg_address.findViewById(id);
                if (view != null) {
                    view.setUnselectTextColor(colorId);
                }
            }
        }
    }
}
