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

    @Before
    public void setUp() {
        Log.d("msg", "UITest setUp");
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
//        final String launcherPackage = getLauncherPackageName();
//        assertThat(launcherPackage, notNullValue());
//        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launcher app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);

        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

//    @Test
//    public void checkPreconditions() {
//        assertThat(mDevice, notNullValue());
//    }

    @Test
    public void testDemo() throws UiObjectNotFoundException {
        Log.d("msg", "UITest testDemo");
//        mDevice.findObject(new UiSelector()
//                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("btn_test")).click();
//        UiObject sample = new UiObject(new UiSelector().description("UItestSample"));
//        sample.clickAndWaitForNewWindow();
        UiObject2 btnTest = mDevice.findObject(By.res("com.example.uitest.hank.uitestsample:id/btn_test"));
//        UiObject2 btnTest = mDevice.wait(Until.findObject(By.res("com.example.uitest.hank.uitestsample", "btn_test")), 500);
        btnTest.click();
//        if(btnTest.)

//        UiObject testButton = mDevice.findObject(new UiSelector()
//                .text("test")
//                .className("android.widget.Button"));
//
//        UiObject cancelButton = mDevice.findObject(new UiSelector()
//                .text("Cancel")
//                .className("android.widget.Button"));
//
//        UiObject okButton = mDevice.findObject(new UiSelector()
//                .text("OK")
//                .className("android.widget.Button"));
//
//        if(testButton.exists()) {
//            testButton.click();
//        }
//
//        if(okButton.exists() && okButton.isEnabled()) {
//            Log.d("msg", "okButton exists");
//            okButton.click();
//        } else {
//            Log.d("msg", "okButton not exists");
//        }
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

}
