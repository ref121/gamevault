package com.adir.gamevault.model;

import java.time.LocalDate;

// תוספת (DLC) למשחק קיים.
// התוספת שומרת רק את ה־id של משחק הבסיס, ולא את אובייקט המשחק עצמו.
public class Dlc extends StoreItem {

    private final String baseGameId;

    public Dlc(String id, String title, double price,
               LocalDate releaseDate, double rating, String baseGameId) {
        super(id, title, price, releaseDate, rating);

        if (baseGameId == null || baseGameId.isBlank()) {
            throw new IllegalArgumentException("base game id can not be empty");
        }
        if (baseGameId.equals(id)) {
            throw new IllegalArgumentException("a DLC can not be its own base game");
        }
        // שימו לב: לא בודקים כאן שמשחק הבסיס קיים.
        // המחלקה לא מכירה את הקטלוג, ולכן זה תפקיד של CatalogService.
        this.baseGameId = baseGameId;
    }

    public String getBaseGameId() {
        return baseGameId;
    }

    @Override
    public String getTypeLabel() {
        return "DLC";
    }
}
