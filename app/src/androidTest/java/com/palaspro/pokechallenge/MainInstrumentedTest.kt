package com.palaspro.pokechallenge

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.base.IdlingResourceRegistry
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.palaspro.pokechallenge.presenter.features.main.view.activity.MainActivity
import com.palaspro.pokechallenge.util.LoadingIdlingResource
import com.palaspro.pokechallenge.util.WaitLapseIdlingResource
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun us01scrollItems() {
        activityRule.scenario.onActivity {
            IdlingRegistry.getInstance().register(LoadingIdlingResource(it))
        }

        Espresso.onView(withId(R.id.mainRecycler))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(16))


        Espresso.onView(withId(R.id.mainRecycler))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun us02navigationToDetailAndCheckPropertiesShowed() {
        IdlingRegistry.getInstance().register(WaitLapseIdlingResource(1000))

        Espresso.onView(withId(R.id.mainRecycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, ViewActions.click()))

        IdlingRegistry.getInstance().register(WaitLapseIdlingResource(2000))

        Espresso.onView(withId(R.id.detailHeader)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.detailInfo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.detailTypes)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.detailMoves)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.detailAbilities)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        IdlingRegistry.getInstance().register(WaitLapseIdlingResource(3000))

        Espresso.pressBack()

        IdlingRegistry.getInstance().register(WaitLapseIdlingResource(1000))
    }
}