package com.example.herbar

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasCategories
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is
import org.hamcrest.number.OrderingComparison
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestS2 {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.CAMERA)

    @Test
    fun testValidacijeNaziva(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)

        onView(withId(R.id.nazivET)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.nazivET)).perform(scrollTo())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")))

        onView(withId(R.id.nazivET)).perform(clearText(), typeText("abcdefghijklmnoprstuvz"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.nazivET)).perform(scrollTo())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")))

        onView(withId(R.id.nazivET)).perform(clearText(), typeText("grah"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.nazivET)).perform(scrollTo())
        onView(withId(R.id.nazivET)).check(matches(not(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera"))))

    }

    @Test
    fun testValidacijePorodice(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)

        onView(withId(R.id.porodicaET)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.porodicaET)).perform(scrollTo())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")))

        onView(withId(R.id.porodicaET)).perform(clearText(), typeText("abcdefghijklmnoprstuvz"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.porodicaET)).perform(scrollTo())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")))

        onView(withId(R.id.porodicaET)).perform(clearText(), typeText("grah"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.porodicaET)).perform(scrollTo())
        onView(withId(R.id.porodicaET)).check(matches(not(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera"))))
    }

    @Test
    fun testValidacijeUpozorenja(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)

        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(scrollTo())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")))

        onView(withId(R.id.medicinskoUpozorenjeET)).perform(clearText(), typeText("abcdefghijklmnoprstuvz"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(scrollTo())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")))

        onView(withId(R.id.medicinskoUpozorenjeET)).perform(clearText(), typeText("grah"), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(scrollTo())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(not(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera"))))
    }

    @Test
    fun testValidacijeJela(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)

        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")))

        onView(withId(R.id.jeloET)).perform(clearText(), typeText("abcdefghijklomnprstuvz"), closeSoftKeyboard())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")))

        onView(withId(R.id.jeloET)).perform(clearText(), typeText("Buranija"), closeSoftKeyboard())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(not(hasErrorText("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera"))))

        onView(withId(R.id.jeloET)).perform(clearText(), typeText("burAnIja"), closeSoftKeyboard())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Ne mogu se dodati dva ista jela")))
    }

    @Test
    fun testValidacijaMedicinskihKoristi(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withText("Izaberite barem jednu medicinsku korist"))
            .inRoot(ToastMatcher().apply {
                (matches(isDisplayed()))
            })
    }

    @Test
    fun testValidacijaKlimatskogTipa(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo(),click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withText("Izaberite barem jedan klimatski tip"))
            .inRoot(ToastMatcher().apply {
                (matches(isDisplayed()))
            })
    }

    @Test
    fun testValidacijaZemljisniTip(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo(),click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo(),click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withText("Izaberite barem jedan zemljišni tip"))
            .inRoot(ToastMatcher().apply {
                (matches(isDisplayed()))
            })
    }

    @Test
    fun testValidacijaProfilOkusa(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo(),click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo(),click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo(),click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withText("Izaberite profil okusa"))
            .inRoot(ToastMatcher().apply {
                (matches(isDisplayed()))
            })
    }

    @Test
    fun testValidacijaJela(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo(),click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo(),click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo(),click())
        onView(withId(R.id.profilOkusaLV)).perform(scrollTo(),click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withText("Dodajte barem jedno jelo"))
            .inRoot(ToastMatcher().apply {
                (matches(isDisplayed()))
            })
    }

    private lateinit var mockImageBitmap : Bitmap
    private fun createMockImageBitmap(): Bitmap {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        return BitmapFactory.decodeResource(context.resources, R.drawable.bosiljak)
    }

    private fun createMockImageBitmapFalse(): Bitmap {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        return BitmapFactory.decodeResource(context.resources, R.drawable.ruzmarin)
    }

    @Test
    fun testSlike(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.uslikajBiljkuBtn)).perform(scrollTo())

        mockImageBitmap=createMockImageBitmap()
        Intents.init()
        val resultIntent = Intent()
        resultIntent.putExtra("data", mockImageBitmap)
        val result = Activity.RESULT_OK
        Intents.intending(allOf(
            hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        )).respondWith(
            Instrumentation.ActivityResult(result, resultIntent)
        )
        //mockImageBitmap=createMockImageBitmapFalse()
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())
        onView(withId(R.id.slikaIV)).perform(scrollTo()).check { view, _ ->
            val imageView = view as ImageView
            val displayedBitmap = (imageView.drawable as BitmapDrawable).bitmap
            assertEquals(mockImageBitmap.width, displayedBitmap.width)
            assertEquals(mockImageBitmap.height, displayedBitmap.height)

            for (x in 0 until mockImageBitmap.width) {
                for (y in 0 until mockImageBitmap.height) {
                    assertEquals(
                        "Pixel mismatch at x=$x, y=$y",
                        mockImageBitmap.getPixel(x, y),
                        displayedBitmap.getPixel(x, y)
                    )
                }
            }
        }
        Intents.release()
    }

}