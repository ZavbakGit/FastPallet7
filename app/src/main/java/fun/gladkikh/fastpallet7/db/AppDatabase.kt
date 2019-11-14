package `fun`.gladkikh.fastpallet7.db

import `fun`.gladkikh.fastpallet7.db.dao.CreatePalletUpdateDao
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.BoxCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.CreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.PalletCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.ProductCreatePalletDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        CreatePalletDb::class,
        ProductCreatePalletDb::class,
        PalletCreatePalletDb::class,
        BoxCreatePalletDb::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCreatePalletUpdateDao(): CreatePalletUpdateDao

//    companion object{
//        fun getInMemoryDatabase(context: Context): AppDatabase {
//
//            return Room.databaseBuilder(context,
//                AppDatabase::class.java, "app_database.db")
//                .addCallback(CALLBACK)
//                .build()
//
//        }
//    }
//
//
//
//    private val CALLBACK = object : RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//
//            //db.execSQL("CREATE TRIGGER ...")
//        }
//    }
}