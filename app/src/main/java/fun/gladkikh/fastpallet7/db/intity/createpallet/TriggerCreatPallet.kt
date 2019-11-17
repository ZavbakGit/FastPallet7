package `fun`.gladkikh.fastpallet7.db.intity.createpallet

fun getListTriggerCreatePallet(): List<String> {
    val listTrigger = mutableListOf<String>()

    //Insert Box
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
                "    UPDATE ProductCreatePalletDb " +
                "       SET countRow = IFNULL(countRow, 0) + 1, " +
                "           count = IFNULL(count, 0) + IFNULL(NEW.count, 0), " +
                "           countBox = IFNULL(countBox, 0) + IFNULL(NEW.countBox, 0)  " +
                "     WHERE guid = ( " +
                "                      SELECT guidProduct " +
                "                        FROM PalletCreatePalletDb " +
                "                       WHERE guid = NEW.guidPallet " +
                "                  ); " +
                "END;"
    )

    //Update Box
    listTrigger.add(
        "CREATE TRIGGER OnBoxUpdate " +
                "         AFTER UPDATE " +
                "            ON BoxCreatePalletDb " +
                "      FOR EACH ROW " +
                "BEGIN " +
                "    UPDATE PalletCreatePalletDb " +
                "       SET count = IFNULL(count, 0) + IFNULL(NEW.count, 0) - IFNULL(OLD.count, 0), " +
                "           countBox = IFNULL(countBox, 0) + IFNULL(NEW.countBox, 0) - IFNULL(OLD.countBox, 0)  " +
                "     WHERE guid = NEW.guidPallet; " +
                "    UPDATE ProductCreatePalletDb " +
                "       SET count = IFNULL(count, 0) + IFNULL(NEW.count, 0) - IFNULL(OLD.count, 0), " +
                "           countBox = IFNULL(countBox, 0) + IFNULL(NEW.countBox, 0) - IFNULL(OLD.countBox, 0)  " +
                "     WHERE guid = ( " +
                "                      SELECT guidProduct " +
                "                        FROM PalletCreatePalletDb " +
                "                       WHERE guid = NEW.guidPallet " +
                "                  ); " +
                "END; "
    )

    //Delete Box
    listTrigger.add(
        "CREATE TRIGGER OnBoxDelete " +
                "         AFTER DELETE " +
                "            ON BoxCreatePalletDb " +
                "      FOR EACH ROW " +
                "BEGIN " +
                "    UPDATE PalletCreatePalletDb " +
                "       SET countRow = IFNULL(countRow, 0) - 1, " +
                "           count = IFNULL(count, 0) - IFNULL(OLD.count, 0), " +
                "           countBox = IFNULL(countBox, 0) - IFNULL(OLD.countBox, 0)  " +
                "     WHERE guid = OLD.guidPallet; " +
                "    UPDATE ProductCreatePalletDb " +
                "       SET countRow = IFNULL(countRow, 0) - 1, " +
                "           count = IFNULL(count, 0) - IFNULL(OLD.count, 0), " +
                "           countBox = IFNULL(countBox, 0) - IFNULL(OLD.countBox, 0)  " +
                "     WHERE guid = ( " +
                "                      SELECT guidProduct " +
                "                        FROM PalletCreatePalletDb " +
                "                       WHERE guid = OLD.guidPallet " +
                "                  ); " +
                "END;"
    )

    //Insert Pallet
    listTrigger.add(
        "CREATE TRIGGER OnPalInsert " +
                "         AFTER INSERT " +
                "            ON PalletCreatePalletDb " +
                "      FOR EACH ROW " +
                "BEGIN " +
                "    UPDATE ProductCreatePalletDb " +
                "       SET countPallet = IFNULL(countPallet, 0) + 1, " +
                "           countBox = IFNULL(countBox, 0) + ifnull(NEW.countBox, 0), " +
                "           count = IFNULL(count, 0) + ifnull(NEW.count, 0), " +
                "           countRow = IFNULL(countRow, 0) + ifnull(NEW.countRow, 0)  " +
                "     WHERE guid = NEW.guidProduct; " +
                "END;"
    )

    //Delete Pallet
    listTrigger.add(
        "CREATE TRIGGER OnPalDelete " +
                "         AFTER DELETE " +
                "            ON PalletCreatePalletDb " +
                "      FOR EACH ROW " +
                "BEGIN " +
                "    UPDATE ProductCreatePalletDb " +
                "       SET countPallet = IFNULL(countPallet, 0) - 1, " +
                "           countBox = IFNULL(countBox, 0) - ifnull(OLD.countBox, 0), " +
                "           count = IFNULL(count, 0) - ifnull(OLD.count, 0), " +
                "           countRow = IFNULL(countRow, 0) - ifnull(OLD.countRow, 0)  " +
                "     WHERE guid = OLD.guidProduct; " +
                "END;"
    )


    return listTrigger
}