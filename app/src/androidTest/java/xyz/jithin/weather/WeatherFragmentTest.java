package xyz.jithin.weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by JIM on 18/11/17.
 */


public class WeatherFragmentTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    CountDownLatch lock = new CountDownLatch(1);
    MockWebServer server;


    public WeatherFragmentTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public WeatherFragmentTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        offline(true);
        getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testFunctionality() throws InterruptedException {

        //switch off mobile data before starting the test. mobile data enabling will not succeed the case.

        lock.await(5 * 1000, TimeUnit.MILLISECONDS);

        onView(withId(R.id.retry)).check(matches(isDisplayed()));

        offline(false);
        lock.await(5 * 1000, TimeUnit.MILLISECONDS);

        onView(withId(R.id.button_for_retry)).perform(click());

        lock.await(5 * 1000, TimeUnit.MILLISECONDS);
    }



    private void offline(boolean enable) {
        WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(!enable);
        try {
            setMobileDataEnabled(getActivity().getApplicationContext(), !enable);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);
        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }
}
