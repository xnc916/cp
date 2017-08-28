package com.chuck.wheelpackerview.model;

import com.chuck.wheelpackerview.library.listener.IWheelViewModel;

/**
 * Created by Chuckchen on 16/12/22.
 */

public class AddressModel implements IWheelViewModel {
    public String addressName;
    public String addressId;

    @Override
    public String getValueString() {
        return addressName;
    }

    @Override
    public String getValueId() {
        return addressId;
    }
}
