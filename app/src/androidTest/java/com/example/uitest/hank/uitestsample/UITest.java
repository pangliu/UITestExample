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
@LargeTest
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

        openApp(OTHER_PACKAGE);
    }

//    @Test
//    public void checkPreconditions() {
//        assertThat(mDevice, notNullValue());
//    }

    @Test
    public void testRxjava() throws UiObjectNotFoundException {
        Log.d("msg", "UITest testRxjava: " + mDevice.getCurrentPackageName());
        UiObject2 btnFragment1 = mDevice.findObject(By.res("com.example.rxjava.hank.rxjavasample:id/btn_fragment1"));

        // 按下後等待五秒
        btnFragment1.clickAndWait(Until.newWindow(), 5000);


        UiObject btnFragment2 = mDevice.findObject(new UiSelector()
                .text("f2")
                .className("android.widget.Button"));

        if(btnFragment2.exists()) {
            btnFragment2.click();
        }

        btnFragment1.click();
        UiObject2 btnGetVersion = mDevice.findObject(By.res("com.example.rxjava.hank.rxjavasample:id/btn_get_version"));
        btnGetVersion.clickAndWait(Until.newWindow(), 5000);

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
