package com.egonzalez.simpleredditclient.base;

import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import com.egonzalez.simpleredditclient.service.ServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import org.junit.Before;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspressoTest {

    public static final Gson GSON = new GsonBuilder().setLenient().create();

    protected final List<Uri> mEspressoMockResponses = new ArrayList<>();

    @Before
    public void setUp() {
        // Mock responses
        final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(
                new MockInterceptor(mEspressoMockResponses, InstrumentationRegistry.getContext()))
            .build();

        // Mock retrofit
        final Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .baseUrl(ServiceFactory.URL)
            .client(client)
            .build();

        ServiceFactory.getInstance().setRetrofit(retrofit);
    }

    protected static boolean checkDisplayedText(final String text) {
        waitForText(text);
        onView(withText(text))
            .check(matches(isDisplayed()));
        return true;
    }

    private static void waitForText(final String text) {
        Long waitedTime = 0L;
        final Long timeIncrement = 100L;
        while (true) {
            try {
                try {
                    onView(withText(text)).perform(scrollTo());
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                onView(withText(text)).check(matches(isDisplayed()));
                return;
            } catch (final Throwable t) {
                Thread.yield();
                if (waitedTime >= 10000L) {
                    throw new AssertionError("String not found: " + text);
                }
                android.os.SystemClock.sleep(timeIncrement);
                waitedTime += timeIncrement;
            }
        }
    }

    protected static void checkDisplayed(final int elementId) {
        waitForElement(elementId);
        try {
            onView(withId(elementId)).perform(scrollTo()).check(matches(isDisplayed()));
        } catch (Exception e) {
            e.printStackTrace();
            onView(withId(elementId)).check(matches(isDisplayed()));
        }
        closeSoftKeyboardByElement(elementId);
    }

    private static void waitForElement(final int elementId) {
        Long waitedTime = 0L;
        final Long timeIncrement = 100L;
        while (true) {
            try {
                try {
                    onView(withId(elementId)).perform(scrollTo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onView(withId(elementId)).check(matches(isDisplayed()));
                return;
            } catch (Throwable t) {
                Thread.yield();
                if (waitedTime >= 10000L) {
                    throw new AssertionError("Element not found: " + elementId);
                }
                android.os.SystemClock.sleep(timeIncrement);
                waitedTime += timeIncrement;
            }
        }
    }

    private static void closeSoftKeyboardByElement(final int elementId){
        try {
            onView(withId(elementId)).perform(closeSoftKeyboard());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
