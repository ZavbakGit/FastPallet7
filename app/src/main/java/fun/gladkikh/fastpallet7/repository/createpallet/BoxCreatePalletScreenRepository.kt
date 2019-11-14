package `fun`.gladkikh.fastpallet7.repository.createpallet

import `fun`.gladkikh.fastpallet7.db.dao.BoxCreatePalletScreenDao
import `fun`.gladkikh.fastpallet7.map.toDb
import `fun`.gladkikh.fastpallet7.map.toObject
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class BoxCreatePalletScreenRepository(
    private val boxCreatePalletScreenDao: BoxCreatePalletScreenDao,
    private val createPalletRepositoryUpdate: CreatePalletRepositoryUpdate
) {

    fun getPallet(guidPallet: String): LiveData<PalletCreatePallet> {
        return Transformations.map(
            boxCreatePalletScreenDao.getPallet(guidPallet)
        ) {
            it.toObject()
        }
    }

    fun getBox(guidBox: String): LiveData<BoxCreatePallet> = Transformations.map(
        boxCreatePalletScreenDao.getBox(guidBox)
    ) {
        it.toObject()
    }

    fun saveBox(box: BoxCreatePallet) {
        createPalletRepositoryUpdate.save(box.toDb())
    }
}