package com.dicoding.courseschedule.ui.list
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class TaskActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(ListActivity::class.java)
    }

    @Test
    fun displayAddHabit() {
        onView(withId(R.id.fab)).perform(click())

        //checking ed_course_name in activity_add_course  displayed
        onView(withId(R.id.ed_course_name))
            .check(matches(isDisplayed()))
    }
}