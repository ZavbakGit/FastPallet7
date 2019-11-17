package `fun`.gladkikh.fastpallet7.model.usecase.creatpallet

import `fun`.gladkikh.fastpallet7.model.usecase.check.CheckDocumentUseCase
import `fun`.gladkikh.fastpallet7.repository.CreatePalletRepositoryUpdate

class PalletCreatePalletUseCase(
    private val createPalletRepositoryUpdate: CreatePalletRepositoryUpdate,
    private val checkDocumentUseCase: CheckDocumentUseCase
) {
    //ToDo Доделать
}