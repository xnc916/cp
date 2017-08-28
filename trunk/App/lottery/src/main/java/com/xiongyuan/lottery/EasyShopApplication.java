package com.xiongyuan.lottery;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.Iterator;
import java.util.List;

public class EasyShopApplication extends Application {
    private static EasyShopApplication app = null;

    public static EasyShopApplication getInstance() {
        return app;
    }
    @Override
    public void onCreate() {

        if (isInit()) {
            super.onCreate();
            CachePreferences.init(this);
            app = this;
        }
    }
    private boolean isInit() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppProcessName(pid, this.getApplicationContext());
        if (processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())) {
            return false;
        }
        return true;
    }

    public String getAppProcessName(int pID, Context context) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }
}
