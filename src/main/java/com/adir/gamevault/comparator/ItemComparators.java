package com.adir.gamevault.comparator;

import com.adir.gamevault.model.StoreItem;

import java.util.Comparator;

// כל דרכי המיון של פריטים, במקום אחד.
// המחלקה מדגימה את ארבע הדרכים לכתוב Comparator שלמדנו:
// מחלקה סטטית מקוננת, מחלקה אנונימית, Lambda, ו־Comparator.comparing.
//
// כלל בכל המיונים: כשהשדה הראשי שווה, מכריעים לפי id,
// כדי שאותו קטלוג יוצג תמיד באותו סדר.
public class ItemComparators {

    // בנאי פרטי: אין טעם ליצור מופע של מחלקה שכל המתודות שלה סטטיות
    private ItemComparators() {
    }

    // ===== דרך 1: מחלקה סטטית מקוננת =====
    // static: היא שייכת ל־ItemComparators, אבל לא צריכה מופע שלה.

    // מהזול ליקר
    public static class ByPrice implements Comparator<StoreItem> {
        @Override
        public int compare(StoreItem a, StoreItem b) {
            int result = Double.compare(a.getPrice(), b.getPrice());
            if (result != 0) {
                return result;
            }
            return a.getId().compareTo(b.getId());   // שוויון במחיר: לפי id
        }
    }

    // לפי שם, בסדר אלפביתי
    public static class ByTitle implements Comparator<StoreItem> {
        @Override
        public int compare(StoreItem a, StoreItem b) {
            int result = a.getTitle().compareTo(b.getTitle());
            if (result != 0) {
                return result;
            }
            return a.getId().compareTo(b.getId());   // שוויון בשם: לפי id
        }
    }

    public static Comparator<StoreItem> byPrice() {
        return new ByPrice();
    }

    public static Comparator<StoreItem> byTitle() {
        return new ByTitle();
    }

    // ===== דרך 2: מחלקה אנונימית =====
    // מהדירוג הגבוה לנמוך.
    // b לפני a: הפיכת הסדר הופכת את המיון ליורד.
    public static Comparator<StoreItem> byRatingDesc() {
        return new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem a, StoreItem b) {
                int result = Double.compare(b.getRating(), a.getRating());
                if (result != 0) {
                    return result;
                }
                return a.getId().compareTo(b.getId());
            }
        };
    }

    // ===== דרך 3: Lambda =====
    // אותו מיון כמו byRatingDesc, בלי כל התחביר שמסביב.
    // הקומפיילר יודע מטיפוס ההחזרה ש־a ו־b הם StoreItem.
    public static Comparator<StoreItem> byRatingDescLambda() {
        return (a, b) -> {
            int result = Double.compare(b.getRating(), a.getRating());
            if (result != 0) {
                return result;
            }
            return a.getId().compareTo(b.getId());
        };
    }

    // ===== דרך 4: Comparator.comparing והפניה למתודה =====
    // מהחדש לישן.
    // reversed() הופכת רק את מה שנבנה לפניה (התאריך),
    // ולכן thenComparing שבא אחריה לא מתהפך: השוויון מוכרע לפי id בסדר עולה.
    public static Comparator<StoreItem> newestFirst() {
        return Comparator.comparing(StoreItem::getReleaseDate)
                .reversed()
                .thenComparing(StoreItem::getId);
    }
}
