package `fun`.gladkikh.fastpallet7

import `fun`.gladkikh.fastpallet7.db.AppDatabase
import `fun`.gladkikh.fastpallet7.db.dao.CreatePalletUpdateDao
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.*
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private var db: AppDatabase? = null
    private var createPalletUpdateDao: CreatePalletUpdateDao? = null

    val docTest = CreatePalletDb(
        guid = "0",
        dateChanged = null,
        barcode = null,
        date = null,
        description = "0",
        status = 1,
        number = "1",
        guidServer = "0",
        isLastLoad = false
    )

    fun getListProduct(guidDoc: String): List<ProductCreatePalletDb> {
        return (0..3).map {
            ProductCreatePalletDb(
                guidDoc = guidDoc,
                guid = guidDoc + "_" + it,
                countRow = null,
                nameProduct = guidDoc + "_" + it,
                count = null,
                countBox = null,
                dateChanged = null,
                barcode = null,
                number = guidDoc + "_" + it,
                isLastLoad = false,
                weightEndProduct = null,
                weightStartProduct = null,
                weightCoffProduct = null,
                countPallet = null,
                weightBarcode = null,
                edCoff = null,
                ed = null,
                codeProduct = null,
                countBack = null,
                countBoxBack = null,
                guidProductBack = guidDoc + "_" + it
            )
        }
    }

    fun getListPallet(guidProduct: String): List<PalletCreatePalletDb> {
        return (0..3).map {
            PalletCreatePalletDb(
                guid = guidProduct + "_" + it,
                guidProduct = guidProduct,
                number = guidProduct + "_" + it,
                barcode = null,
                dateChanged = null,
                countBox = null,
                count = null,
                nameProduct = guidProduct + "_" + it,
                sclad = null,
                countRow = null,
                state = null
            )
        }
    }

    fun getListBox(guidPallet: String): List<BoxCreatePalletDb> {
        return (0..10).map {
            BoxCreatePalletDb(
                guid = guidPallet + "_" + it,
                barcode = null,
                dateChanged = null,
                countBox = 2,
                count = 10f,
                guidPallet = guidPallet
            )
        }
    }


    @Before
    @Throws(Exception::class)
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    getListTriggerCreatePallet().forEach {
                        db.execSQL(it)
                    }
                }
            }

        )
            .build()
        createPalletUpdateDao = db!!.getCreatePalletUpdateDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db!!.close()
    }

    @Test
    fun addCreatePalletTest() {
        createPalletUpdateDao!!.insertOrUpdate(docTest)
        getListProduct(docTest.guid).forEach { prod ->
            createPalletUpdateDao!!.insertOrUpdate(prod)
            getListPallet(prod.guid).forEach { pal ->
                createPalletUpdateDao!!.insertOrUpdate(pal)
                getListBox(pal.guid).forEach { box ->
                    createPalletUpdateDao!!.insertOrUpdate(box)
                }
            }
        }


        testDoc(docTest, getListProduct(docTest.guid))
        getListProduct(docTest.guid).forEach { prod ->
            //testProduct(prod, getListPallet(prod.guid))
            getListPallet(prod.guid).forEach { pall ->
                testPallet(pall, getListBox(pall.guid))
            }
        }
    }


    fun testDoc(doc: CreatePalletDb, listProduct: List<ProductCreatePalletDb>) {
        val docDb = createPalletUpdateDao!!.getDocByGuid(doc.guid)

        if (docDb != doc) {
            assertFalse("Документы не равны!", true)
        }

        val listProductDb = createPalletUpdateDao!!.getProductListByGuidDoc(doc.guid)

        if (listProductDb.size != listProduct.size) {
            assertFalse("Документ Список продуктов не равены!", true)
        }
    }

    fun testPallet(pallet: PalletCreatePalletDb, listBox: List<BoxCreatePalletDb>) {
        val palletDb = createPalletUpdateDao!!.getPalletByGuid(pallet.guid)

        if (palletDb.guid != pallet.guid) {
            assertFalse("Паллеты не равны!", true)
        }

        val listBoxDb = createPalletUpdateDao!!.getListBoxByGuidPallet(pallet.guid)

        val count = listBox
            .fold(0f) { total, next -> total + next.count!! }

        val countBox = listBox
            .fold(0) { total, next -> total + next.countBox!! }


        val countDb = listBoxDb.fold(0f) { total, next -> total + next.count!! }

        val countBoxDb = listBoxDb.fold(0) { total, next -> total + next.countBox!! }

        if (count != countDb) {
            assertFalse("Паллета Количество в списке не равно!", true)
        }
        if (countBox != countBoxDb) {
            assertFalse("Паллета Места в списке не равно!", true)
        }

        //Тригер
        if (palletDb.count != countDb) {
            assertFalse("Паллета Количество не равно c паллетой!", true)
        }
        if (palletDb.countBox != countBoxDb) {
            assertFalse("Паллета Мест не равно c паллетой!", true)
        }
        if (palletDb.countRow != listBoxDb.size) {
            assertFalse("Паллета Строк не равно c паллетой!", true)
        }


        if (listBoxDb.size != listBox.size) {
            assertFalse("Паллета Списоки коробок не равены!", true)
        }

    }

    fun testProduct(product: ProductCreatePalletDb, listPallet: List<PalletCreatePalletDb>) {
        val productDb = createPalletUpdateDao!!.getProductByGuid(product.guid)

        if (productDb.guid != product.guid) {
            assertFalse("Продукты не равны!", true)
        }

        val listPalletDb = createPalletUpdateDao!!.getListPalletByGuidProduct(product.guid)


        val count = listPallet
            .fold(0f) { total, next -> total + next.count!! }

        val countBox = listPallet
            .fold(0) { total, next -> total + next.countBox!! }

        val countRow = listPallet
            .fold(0) { total, next -> total + next.countRow!! }


        val countDb = listPalletDb
            .fold(0f) { total, next -> total + next.count!! }

        val countBoxDb = listPalletDb
            .fold(0) { total, next -> total + next.countBox!! }

        val countRowDb = listPalletDb
            .fold(0) { total, next -> total + next.countRow!! }

        if (count != countDb) {
            assertFalse("Продукт Количество в списке не равно!", true)
        }
        if (countBox != countBoxDb) {
            assertFalse("Продукт Места в списке не равно!", true)
        }

        if (countRow != countRowDb) {
            assertFalse("Продукт Строк кол. в списке не равно!", true)
        }


        //Тригер
        if (product.count != countDb) {
            assertFalse("Продукт Количество не равно c паллетой!", true)
        }
        if (product.countBox != countBoxDb) {
            assertFalse("Продукт Мест не равно c паллетой!", true)
        }
        if (product.countRow != countRowDb) {
            assertFalse("Продукт Строк не равно c паллетой!", true)
        }


        if (listPalletDb.size != listPallet.size) {
            assertFalse("Продукт Списоки коробок не равены!", true)
        }
    }


    fun testDoc1() {
        val docDb = createPalletUpdateDao!!.getDocByGuid(docTest.guid)

        if (docTest != docDb) {
            assertFalse("Документы не равны!", true)
        }
        val listProdactDb =
            createPalletUpdateDao!!.getProductListByGuidDoc(docTest.guid)

        if (listProdactDb.size != getListProduct(docTest.guid).size) {
            assertFalse("Список продуктов не равен!", true)
        }

        listProdactDb.forEach { prod ->


            val listPalletDb =
                createPalletUpdateDao!!.getListPalletByGuidProduct(prod.guid)

            if (listPalletDb.size != getListPallet(prod.guid).size) {
                assertFalse("Список пеллет не равен!", true)
            }

            listPalletDb.forEach { pall ->
                val listBoxDb = createPalletUpdateDao!!.getListBoxByGuidPallet(pall.guid)

                val listBox = getListBox(pall.guid)

                val count = listBox.fold(0f) { total, next -> total + next.count!! }
                val countDb = listBoxDb.fold(0f) { total, next -> total + next.count!! }

                val countBox = listBox.fold(0) { total, next -> total + next.countBox!! }
                val countBoxDb = listBoxDb.fold(0) { total, next -> total + next.countBox!! }

                if (count != countDb) {
                    assertFalse("Количество не равно!", true)
                }
                if (countBox != countBoxDb) {
                    assertFalse("Места не равно!", true)
                }

                //Тригер
                if (pall.count != countDb) {
                    assertFalse("Количество не равно c паллетой!", true)
                }
                if (pall.countBox != countBoxDb) {
                    assertFalse("Мест не равно c паллетой!", true)
                }
                if (pall.countRow != listBoxDb.size) {
                    assertFalse("Строк не равно c паллетой!", true)
                }


                if (listBoxDb.size != listBox.size) {
                    assertFalse("Список коробок не равен!", true)
                }

            }
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext


        assertEquals("fun.gladkikh.fastpallet7", appContext.packageName)
    }
}
