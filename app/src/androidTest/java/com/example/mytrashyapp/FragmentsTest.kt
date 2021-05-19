package com.example.mytrashyapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mytrashyapp.ui.auth.LoginFragment
import com.example.mytrashyapp.ui.library.screens.songs.SongsFragment
import com.example.mytrashyapp.ui.profile.Profile


@RunWith(AndroidJUnit4::class)
class FragmentsTest {
    @Test
    fun test_profileFragment(){
        val scenario = launchFragmentInContainer<Profile>()
        Espresso.onView(withId(R.id.textProfile)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.textEmail)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.buttonGoToLogin)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.buttonGoToLogin)).perform(click())
        Espresso.onView(withId(R.id.loginFragment)).check(matches(isDisplayed()))

    }

    @Test
    fun test_loginFragment(){
        val scenario = launchFragmentInContainer<LoginFragment>()
        Espresso.onView(withId(R.id.textView)).check(matches(withText("email")))
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.textView2)).check(matches(withText("password")))
        Espresso.onView(withId(R.id.editTextTextPassword)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.buttonLogin)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.textViewForgotPassword)).check(matches(withText("Forgot your password")))
        Espresso.onView(withId(R.id.textViewRegisterNow)).check(matches(withText("don_t_have_an_account_register_now\">Dont't have an acc. Register now")))

    }

    @Test
    fun test_songsFragment(){
        val scenario = launchFragmentInContainer<SongsFragment>()
        Espresso.onView(withId(R.id.songRecycleView)).check(matches(ViewMatchers.isDisplayed()))
    }
}