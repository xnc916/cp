package com.chuck.wheelpackerview.library.adapter;


import com.chuck.wheelpackerview.library.listener.IWheelViewModel;
import com.chuck.wheelpackerview.library.utils.WheelViewLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chuckchen on 16/12/21.
 */

public class SimpleWheelAdapter extends BaseWheelAdapter {
    private List<IWheelViewModel> items;

    public SimpleWheelAdapter(List<IWheelViewModel> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        WheelViewLog.i("this WheelItemView has" + getItemsList().size() + "item");
        return getItemsList().size();
    }

    @Override
    public IWheelViewModel getItem(int postion) {
        if (postion >= 0 && postion < getItemsList().size()) {
            return getItemsList().get(postion);
        }
        return null;
    }

    private List<IWheelViewModel> getItemsList() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    @Override
    public int indexOf(IWheelViewModel model) {
        return getItemsList().indexOf(model);
    }
}
