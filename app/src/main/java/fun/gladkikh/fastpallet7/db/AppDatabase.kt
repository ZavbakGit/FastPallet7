package `fun`.gladkikh.fastpallet7.db

import `fun`.gladkikh.fastpallet7.db.dao.BoxCreatePalletScreenDao
import `fun`.gladkikh.fastpallet7.db.dao.CreatePalletUpdateDao
import `fun`.gladkikh.fastpallet7.db.dao.PalletCreatePalletScreenDao
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.BoxCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.CreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.PalletCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.ProductCreatePalletDb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CreatePalletDb::class,
        ProductCreatePalletDb::class,
        PalletCreatePalletDb::class,
        BoxCreatePalletDb::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCreatePalletUpdateDao(): CreatePalletUpdateDao
    abstract fun getBoxCreatePalletScreen(): BoxCreatePalletScreenDao
    abstract fun getPalletCreatePalletScreenDao(): PalletCreatePalletScreenDao
}