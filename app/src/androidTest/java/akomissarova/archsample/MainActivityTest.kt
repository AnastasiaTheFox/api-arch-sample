package akomissarova.archsample

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    public var activityRule = ActivityTestRule(
            MainActivity::class.java)

    @Test
    fun showsList() {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()))
    }
}
