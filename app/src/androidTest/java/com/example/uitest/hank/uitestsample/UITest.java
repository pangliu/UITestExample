package com.example.uitest.hank.uitestsample;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.test.uiautomator.By;
import android.util.Log;

import junit.framework.TestCase;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UITest {

    private UiDevice mDevice;
    private static final int LAUNCH_TIMEOUT = 5000;
    private final String BASIC_SAMPLE_PACKAGE = "com.example.uitest.hank.uitestsample";
    private final String OTHER_PACKAGE = "com.example.rxjava.hank.rxjavasample";

    @Before
    public void setUp() {
        Log.d("msg", "UITest setUp");
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();
//        openApp(OTHER_PACKAGE);

    }

//    @Test
//    public void checkPreconditions() {
//        assertThat(mDevice, notNullValue());
//    }

    /**
     * 測試開啟 GooglePlay 商店及 YouTube
     * @throws UiObjectNotFoundException
     */
    @Test
    public void testGooglePlay() throws UiObjectNotFoundException {

        /** 用 UiObject 取得 play 商店 icon */
//        UiObject btnG = mDevice.findObject(new UiSelector()
//                .text("Play 商店"));
//        if(btnG.exists()) {
//            btnG.click();
//        } else {
//            Log.d("msg", "btnGooglePlay is null");
//        }
        /** 用 UiObject2 取得 play 商店 icon */
        UiObject2 btnGooglePlay = mDevice.findObject(By.text("Play 商店"));
//        if(null != btnGooglePlay) {
//            btnGooglePlay.click();
//        } else {
//            Log.d("msg", "btnGooglePlay is null");
//        }
        /** 從 play 商店取得父元素(桌面 view )*/
//        UiObject2 deskView = btnGooglePlay.getParent();
//        if(null != deskView) {
//            // 向左滑動
//            deskView.swipe(Direction.LEFT, 0.5f);
//        } else {
//            Log.d("msg", "deskView is null");
//        }

        UiObject2 btnGoogleSet = mDevice.findObject(By.text("Google"));
        if(null != btnGooglePlay) {
            btnGoogleSet.clickAndWait(Until.newWindow(), 3000);
        } else {
            Log.d("msg", "Google is null");
        }

        UiObject2 btnYoutube = mDevice.findObject(By.text("YouTube"));
        if(null != btnYoutube) {
            btnYoutube.clickAndWait(Until.newWindow(), 10000);
        } else {
            Log.d("msg", "btnYoutube is null");
        }

        UiObject2 btnMyCollectinon = mDevice.findObject(By.text("我的合輯"));

        boolean hasCollect = mDevice.hasObject(By.text("我的合輯"));
        Log.d("msg", "hasCollect: " + hasCollect);
        if(null != btnMyCollectinon) {
            btnMyCollectinon.clickAndWait(Until.newWindow(), 10000);
        } else {
            Log.d("msg", "btnYoutube is null");
        }

//        mDevice.wait(Until.hasObject(By.desc("計算機")), LAUNCH_TIMEOUT);
//        mDevice.findObject(By.desc("計算機")).click();
    }


    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    private void openApp(String packageName) {
        // Wait for launcher
//        final String launcherPackage = getLauncherPackageName();
        final String launcherPackage = packageName;
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launcher app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(packageName);

        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), LAUNCH_TIMEOUT);
    }

}
