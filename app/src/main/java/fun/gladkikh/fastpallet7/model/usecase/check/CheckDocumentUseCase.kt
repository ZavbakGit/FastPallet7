package `fun`.gladkikh.fastpallet7.model.usecase.check

import `fun`.gladkikh.fastpallet7.model.Status


class CheckDocumentUseCase {
    fun checkEditDocByStatus(status: Status?): Boolean {
        return status in listOf(Status.LOADED, Status.NEW)
    }
}