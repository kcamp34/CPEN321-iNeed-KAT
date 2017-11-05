package com.kat.cpen321_ineed;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NewPostActivityTest {

    @Rule
    public ActivityTestRule<NewPostActivity> mActivityTestRule = new ActivityTestRule<>(NewPostActivity.class);

    @Test
    public void newPostActivityTest2() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText = onView(
                allOf(withId(R.id.NameField)));
        editText.check(matches(withHint("Name")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.DescriptionField)));
        editText2.check(matches(withHint("Description")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.PriceField)));
        editText3.check(matches(withHint("$")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.CreatePostButton), withText("Confirm"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.NameField)));
        editText4.check(matches(withHint("Must enter a name")));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.DescriptionField)));
        editText5.check(matches(withHint("Must enter a description")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.PriceField)));
        editText6.check(matches(withHint("Must enter a price")));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.NameField), isDisplayed()));
        appCompatEditText.perform(replaceText("Espresso"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.DescriptionField), isDisplayed()));
        appCompatEditText2.perform(replaceText("test\n"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.PriceField), isDisplayed()));
        appCompatEditText3.perform(replaceText("56"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.CreatePostButton), withText("Confirm"), isDisplayed()));
        appCompatButton2.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
