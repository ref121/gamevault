package com.adir.gamevault.model;

import java.time.LocalDate;

// כל דבר שאפשר לקנות בחנות: משחק או תוספת.
// המחלקה אבסטרקטית, כי בחנות אין "פריט סתם".
// היא מרכזת את מה שמשותף ל־Game ול־Dlc.
public abstract class StoreItem implements Comparable<StoreItem> {

    private final String id;              // מזהה ייחודי, לא משתנה אף פעם
    private final String title;
    private double price;                 // יכול להשתנות, למשל במבצע
    private final LocalDate releaseDate;
    private final double rating;          // בין 0 ל־5

    // protected: רק תתי־המחלקות קוראות לבנאי, דרך super(...)
    protected StoreItem(String id, String title, double price,
                        LocalDate releaseDate, double rating) {
        // בודקים את כל הנתונים לפני שמאתחלים שדה כלשהו
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id can not be empty");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title can not be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price can not be negative");
        }
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }
        if (releaseDate == null) {
            throw new IllegalArgumentException("release date can not be null");
        }

        this.id = id;
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    // שינוי מחיר עובר את אותה בדיקה כמו בבנאי
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("price can not be negative");
        }
        this.price = price;
    }

    // כל תת־מחלקה מחזירה את סוג הפריט שלה: "Game" או "DLC"
    public abstract String getTypeLabel();

    // שני פריטים שווים אם ורק אם יש להם אותו id
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        // אובייקט מסוג אחר, או null, אינו שווה לנו
        if (!(other instanceof StoreItem)) {
            return false;
        }
        StoreItem otherItem = (StoreItem) other;
        return id.equals(otherItem.id);
    }

    // חייב להתאים ל־equals: גם הוא לפי id בלבד
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    // הסדר הטבעי של פריטים: לפי id
    @Override
    public int compareTo(StoreItem other) {
        return id.compareTo(other.id);
    }

    // %.2f = מספר עם שתי ספרות אחרי הנקודה
    @Override
    public String toString() {
        return title + "  |  " + getTypeLabel() + "  |  " + String.format("%.2f", price);
    }
}
