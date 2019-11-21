package `fun`.gladkikh.fastpallet7

import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
       val r = listOf<Float?>(null,null).filterNotNull().fold(0f){total, next -> total + next}

        println(BigDecimal("100.523") + (100f).toBigDecimal())

        assertEquals(4, 2 + 2)
    }
}
