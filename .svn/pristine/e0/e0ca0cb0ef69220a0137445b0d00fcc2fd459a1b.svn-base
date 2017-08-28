package com.chuck.wheelpackerview.utils;

import android.view.View;

import com.chuck.wheelpackerview.R;
import com.chuck.wheelpackerview.library.WheelItemView;
import com.chuck.wheelpackerview.library.adapter.SimpleWheelAdapter;
import com.chuck.wheelpackerview.library.listener.IWheelViewModel;
import com.chuck.wheelpackerview.library.listener.OnItemSelectedListener;

import java.util.List;

/**
 * 管理WheelItemView
 * Created by Chuckchen on 16/12/22.
 */

public class PickerItemsUtils {
    private View view;
    private WheelItemView wiv1;
    private WheelItemView wiv2;
    private WheelItemView wiv3;

    private List<IWheelViewModel> mOneItems;
    private List<List<IWheelViewModel>> mTwoItems;
    private List<List<List<IWheelViewModel>>> mThreeItems;

    private boolean linkage;
    private OnItemSelectedListener wivOneListener;
    private OnItemSelectedListener wivTwoListener;

    public PickerItemsUtils(View view) {
        this.view = view;
    }

    public void setPickerData(List<IWheelViewModel> mOneItems) {
        this.setPickerData(mOneItems, null, null, false);
    }

    public void setPickerData(List<IWheelViewModel> mOneItems
            , List<List<IWheelViewModel>> mTwoItems
            , boolean linkage) {
        this.setPickerData(mOneItems, mTwoItems, null, linkage);
    }

    public void setPickerData(List<IWheelViewModel> mOneItems
            , final List<List<IWheelViewModel>> mTwoItems
            , final List<List<List<IWheelViewModel>>> mThreeItems
            , boolean linkage) {
        this.mOneItems = mOneItems;
        this.mTwoItems = mTwoItems;
        this.mThreeItems = mThreeItems;
        this.linkage = linkage;

        wiv1 = (WheelItemView) view.findViewById(R.id.wiv_one);
        if (mOneItems != null) {
            wiv1.setAdapter(new SimpleWheelAdapter(mOneItems));
            wiv1.setCurrentItem(0);
        }

        wiv2 = (WheelItemView) view.findViewById(R.id.wiv_two);
        if (mTwoItems != null) {
            wiv2.setAdapter(new SimpleWheelAdapter(mTwoItems.get(0)));
            wiv2.setCurrentItem(0);
        }

        wiv3 = (WheelItemView) view.findViewById(R.id.wiv_three);
        if (mThreeItems != null) {
            wiv3.setAdapter(new SimpleWheelAdapter(mThreeItems.get(0).get(0)));
            wiv3.setCurrentItem(0);
        }
        if (this.mTwoItems == null) {
            wiv2.setVisibility(View.GONE);
        }
        if (this.mThreeItems == null) {
            wiv3.setVisibility(View.GONE);
        }
        wivOneListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int itemPosition) {
                int wiv2Select = 0;
                if (mTwoItems != null) {
                    wiv2Select = wiv2.getCurrentItem();
                    List<IWheelViewModel> tempList = mTwoItems.get(itemPosition);
                    wiv2Select = wiv2Select >= tempList.size() - 1 ? tempList.size() - 1 : wiv2Select;
                    wiv2.setAdapter(new SimpleWheelAdapter(mTwoItems.get(itemPosition)));
                    wiv2.setCurrentItem(wiv2Select);
                }
                if (mThreeItems != null) {
                    wivTwoListener.onItemSelected(wiv2Select);
                }
            }
        };
        wivTwoListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int itemPosition) {
                if (mThreeItems != null) {
                    int wiv1Select;
                    wiv1Select = wiv1.getCurrentItem();
                    wiv1Select = wiv1Select >= mThreeItems.size() - 1 ? mThreeItems.size() - 1 : wiv1Select;
                    itemPosition = itemPosition >= mTwoItems.get(wiv1Select).size() - 1 ? mTwoItems.get(wiv1Select).size() - 1 : itemPosition;
                    int lastWiv3Select = wiv3.getCurrentItem();
                    lastWiv3Select = lastWiv3Select >= mThreeItems.get(wiv1Select).get(itemPosition).size() - 1 ? mThreeItems.get(wiv1Select).get(itemPosition).size() - 1 : lastWiv3Select;

                    wiv3.setAdapter(new SimpleWheelAdapter(mThreeItems
                            .get(wiv1.getCurrentItem()).get(itemPosition)));
                    wiv3.setCurrentItem(lastWiv3Select);
                }
            }
        };
        if (mTwoItems != null && linkage) {
            wiv1.setOnItemSelectedListener(wivOneListener);
        }
        if (mThreeItems != null && linkage) {
            wiv2.setOnItemSelectedListener(wivTwoListener);
        }

    }

    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = wiv1.getCurrentItem();
        currentItems[1] = wiv2.getCurrentItem();
        currentItems[2] = wiv3.getCurrentItem();
        return currentItems;
    }
}
