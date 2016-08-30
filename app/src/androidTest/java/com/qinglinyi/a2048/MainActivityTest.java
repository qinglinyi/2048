package com.qinglinyi.a2048;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBegin() throws Exception {

        Matcher<View> gameGl = withId(R.id.gameGl);
        onView(gameGl).check(matches(not(isEnabled())));
        onView(withId(R.id.startBtn)).perform(click()).check(matches(withText("RESTART")));
        onView(gameGl).check(matches(isEnabled()));

        onView(gameGl).check(matches(withChildSize(withText(isOneOf("2", "4")), 2)));

    }

    @Test
    public void testGameOver() throws Exception {

        onView(withId(R.id.startBtn)).perform(click());

        Matcher<View> gameGl = withId(R.id.gameGl);

        Random random = new Random();

        View gameGlView = rule.getActivity().findViewById(R.id.gameGl);

        while (gameGlView.isEnabled()) {
            int direction = random.nextInt(4);
            switch (direction) {
                case 0:
                    onView(gameGl).perform(swipeLeft());
                    break;
                case 1:
                    onView(gameGl).perform(swipeUp());
                    break;
                case 2:
                    onView(gameGl).perform(swipeDown());
                    break;
                default:
                    onView(gameGl).perform(swipeRight());
                    break;
            }
        }

        checkToast(R.string.game_over);

    }

    public static Matcher<View> withChildSize(final Matcher<View> childMatcher, final int size) {
        return new TypeSafeMatcher<View>() {
            private int num;

            @Override
            public void describeTo(Description description) {
                description.appendText("has child size: ");
                childMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof ViewGroup)) {
                    return false;
                }

                ViewGroup group = (ViewGroup) view;
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (childMatcher.matches(group.getChildAt(i))) {
                        num++;
                    }
                }

                return size == num;
            }
        };
    }

    public void checkToast(int msgRes) {
//        onView(withText(msgRes))
//                .inRoot(RootMatchers.withDecorView(not(is(rule.getActivity().getWindow().getDecorView()))))
//                .check(matches(isDisplayed()));

        onView(withText(msgRes))
                .inRoot(isToast());
    }


    /**
     * Matcher that is Toast window.
     */
    public static Matcher<Root> isToast() {
        return new TypeSafeMatcher<Root>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            @Override
            public boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                    IBinder windowToken = root.getDecorView().getWindowToken();
                    IBinder appToken = root.getDecorView().getApplicationWindowToken();
                    if (windowToken == appToken) {
                        // windowToken == appToken means this window isn't contained by any other windows.
                        // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                        return true;
                    }
                }
                return false;
            }
        };
    }
}