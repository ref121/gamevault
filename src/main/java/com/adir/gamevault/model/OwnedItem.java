package com.adir.gamevault.model;

import java.time.LocalDate;

// פריט שמשתמש כבר קנה.
// זה לא אותו דבר כמו פריט בחנות: יש לו מידע שנוצר רק ברגע הרכישה,
// והוא שונה בין משתמשים (תאריך, מחיר ששולם, זמן משחק).
public class OwnedItem {

    private final StoreItem item;
    private final LocalDate purchaseDate;
    private final double pricePaid;      // נשמר כי המחיר בחנות יכול להשתנות אחרי הרכישה
    private int playtimeMinutes;         // מתחיל ב־0

    public OwnedItem(StoreItem item, LocalDate purchaseDate, double pricePaid) {
        if (item == null || purchaseDate == null) {
            throw new IllegalArgumentException("item and purchase date are required");
        }
        this.item = item;
        this.purchaseDate = purchaseDate;
        this.pricePaid = pricePaid;
        this.playtimeMinutes = 0;
    }

    public StoreItem getItem() {
        return item;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public int getPlaytimeMinutes() {
        return playtimeMinutes;
    }

    // זמן משחק רק עולה
    public void addPlaytime(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("minutes must be positive");
        }
        playtimeMinutes = playtimeMinutes + minutes;
    }

    @Override
    public String toString() {
        return item.getTitle() + "  |  bought " + purchaseDate
                + "  |  " + playtimeMinutes + " min played";
    }
}
