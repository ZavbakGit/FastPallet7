package `fun`.gladkikh.fastpallet7

import `fun`.gladkikh.fastpallet7.db.AppDatabase
import `fun`.gladkikh.fastpallet7.model.usecase.testdata.AddTestDataUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//
//        val dao = AppDatabase.getInMemoryDatabase(appContext).getCreatePalletUpdateDao()
//
//        val rep = CreatePalletRepositoryUpdate(dao)
//
//        AddTestDataUseCase(rep).save()

        assertEquals("fun.gladkikh.fastpallet7", appContext.packageName)
    }
}
