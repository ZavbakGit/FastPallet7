package `fun`.gladkikh.fastpallet7.db.dao

import `fun`.gladkikh.fastpallet7.db.intity.createpallet.BoxCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.CreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.PalletCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.ProductCreatePalletDb
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface PalletCreatePalletScreenDao {
    @Query("SELECT * FROM PalletCreatePalletDb WHERE guid = :guidPallet")
    fun getPallet(guidPallet: String): LiveData<PalletCreatePalletDb>

    @Query("SELECT * FROM BoxCreatePalletDb WHERE guidPallet = :guidPallet")
    fun getListBox(guidPallet: String): LiveData<List<BoxCreatePalletDb>>

    //TODO Доделать с документом

    //TODO Переделать запрос
    @Query("SELECT * FROM ProductCreatePalletDb WHERE guid =:guidPallet  ")
    fun getProduct(guidPallet: String): LiveData<ProductCreatePalletDb>

    //TODO Переделать запрос
    @Query("SELECT * FROM CreatePalletDb WHERE guid =:guidPallet  ")
    fun getDoc(guidPallet: String): LiveData<CreatePalletDb>
}