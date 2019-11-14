package `fun`.gladkikh.fastpallet7.db.intity.createpallet

fun getListTriggerCreatePallet():List<String>{
    val listTrigger = mutableListOf<String>()

    listTrigger.add(
        "CREATE TRIGGER OnBoxInsert " +
                "         AFTER INSERT " +
                "            ON BoxCreatePalletDb " +
                "      FOR EACH ROW " +
                "BEGIN " +
                "    UPDATE PalletCreatePalletDb " +
                "       SET countRow = IFNULL(countRow, 0) + 1, " +
                "           count = IFNULL(count, 0) + IFNULL(NEW.count, 0), " +
                "           countBox = IFNULL(countBox, 0) + IFNULL(NEW.countBox, 0)  " +
                "     WHERE guid = NEW.guidPallet; " +
                "END; "
    )


    return listTrigger
}