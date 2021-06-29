package com.example.todo;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo.models.Item;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private String[] titles;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.todo", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void Initialize() {
        titles = new String[]{"title1", "title2"};
    }

    @Test
    public void changeText_sameActivity() {
        // initialize test
        onView(withId(R.id.btnAddItem)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAddItem)).check(matches(isEnabled()));
        onView(withId(R.id.editText_item)).check(matches(isDisplayed()));

        // add some data and test functionality button.
        onView(withId(R.id.editText_item)).perform(typeText(titles[0]), closeSoftKeyboard());
        onView(withId(R.id.editText_item)).check(matches(withText("title1")));
        onView(withId(R.id.btnAddItem)).perform(click());
        onView(withId(R.id.editText_item)).perform(typeText(titles[1]), closeSoftKeyboard());
        onView(withId(R.id.editText_item)).check(matches(withText("title2")));
        onView(withId(R.id.btnAddItem)).perform(click());

        // test size of listView
        onView(withId(R.id.listView_item)).check(matches(ListMatcher.withListSize(2)));

        // test to not to add item with no title
        onView(withId(R.id.editText_item)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.btnAddItem)).perform(click());
        onView(withId(R.id.listView_item)).check(matches(ListMatcher.withListSize(2)));

        // test existence of items
        onData(anything())
                .inAdapterView(withId(R.id.listView_item))
                .atPosition(0)
                .check(matches(withText(equalTo("title1"))));

        onData(anything())
                .inAdapterView(withId(R.id.listView_item))
                .atPosition(1)
                .check(matches(withText(equalTo("title2"))));
    }

    @Test
    public void addItemTest() {
        Item item = new Item();
        item.addItem("test1");
        item.addItem("");
        item.addItem("test2");
        assertEquals(item.getItemsList().size(), 2);
    }
}

class ListMatcher {
    public static Matcher<View> withListSize(final int size) {
        return new TypeSafeMatcher<View>() {
            int length;

            @Override
            public boolean matchesSafely(final View view) {
                length = ((ListView) view).getAdapter().getCount();
                return length == size;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("ListView should have " + size + " items, the actual size is " + length);
            }
        };
    }
}