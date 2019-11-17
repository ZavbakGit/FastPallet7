package `fun`.gladkikh.fastpallet7.db.dao

import `fun`.gladkikh.fastpallet7.db.intity.createpallet.BoxCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.CreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.PalletCreatePalletDb
import `fun`.gladkikh.fastpallet7.db.intity.createpallet.ProductCreatePalletDb
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface BoxCreatePalletScreenDao {


    @Query(
        "SELECT * FROM PalletCreatePalletDb  " +
                "WHERE guid in (Select guidPallet from BoxCreatePalletDb where guid =:guidBox)"
    )
    fun getPallet(guidBox: String): LiveData<PalletCreatePalletDb>

    @Query("SELECT * FROM BoxCreatePalletDb WHERE guid = :guidBox")
    fun getBox(guidBox: String): LiveData<BoxCreatePalletDb>

    //TODO Переделать запрос
    @Query("SELECT * FROM ProductCreatePalletDb WHERE guid =:guidBox  ")
    fun getProduct(guidBox: String): LiveData<ProductCreatePalletDb>

    //TODO Переделать запрос
    @Query("SELECT * FROM CreatePalletDb WHERE guid =:guidBox  ")
    fun getDoc(guidBox: String): LiveData<CreatePalletDb>

}