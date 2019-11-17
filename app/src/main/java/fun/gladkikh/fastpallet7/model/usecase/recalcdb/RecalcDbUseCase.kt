package `fun`.gladkikh.fastpallet7.model.usecase.recalcdb

import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate

class RecalcDbUseCase(private val createPalletRepositoryUpdate: CreatePalletRepositoryUpdate) {
    fun recalc() {
        createPalletRepositoryUpdate.recalcPallet()
        createPalletRepositoryUpdate.recalcProduct()
    }
}