package com.adir.gamevault.service;

import com.adir.gamevault.exceptions.AlreadyOwnedException;
import com.adir.gamevault.exceptions.InsufficientFundsException;
import com.adir.gamevault.exceptions.MissingBaseGameException;
import com.adir.gamevault.model.Dlc;
import com.adir.gamevault.model.OwnedItem;
import com.adir.gamevault.model.StoreItem;
import com.adir.gamevault.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// הלב של המערכת: רכישות וספריות של משתמשים.
// כל כלל שמערב כמה אובייקטים יחד (משתמש, פריט, ספרייה, ארנק) נבדק כאן.
public class StoreService {

    private final CatalogService catalog;

    // הספרייה של כל משתמש. המפתח הוא ה־username
    private final Map<String, List<OwnedItem>> libraries = new HashMap<>();

    // השירות לא יוצר קטלוג בעצמו. הוא מקבל אותו מבחוץ
    public StoreService(CatalogService catalog) {
        this.catalog = catalog;
    }

    // רכישה של פריט אחד.
    // סדר הבדיקות חשוב: שום דבר לא משתנה עד שכל הבדיקות עברו.
    // כך רכישה שנכשלה לא משאירה נזק: לא ביתרה ולא בספרייה.
    public void purchase(User user, String itemId)
            throws AlreadyOwnedException, InsufficientFundsException, MissingBaseGameException {

        // 1. הפריט קיים. אם לא, getItem זורקת ItemNotFoundException
        StoreItem item = catalog.getItem(itemId);

        // 2. המשתמש עוד לא מחזיק בו
        if (owns(user, itemId)) {
            throw new AlreadyOwnedException("You already own " + item.getTitle());
        }

        // 3. אם זו תוספת, המשתמש חייב להחזיק במשחק הבסיס
        if (item instanceof Dlc) {
            Dlc dlc = (Dlc) item;
            if (!owns(user, dlc.getBaseGameId())) {
                throw new MissingBaseGameException(
                        "You need the base game before buying " + item.getTitle());
            }
        }

        // 4. יש מספיק כסף
        if (!user.canAfford(item.getPrice())) {
            throw new InsufficientFundsException("Not enough money for " + item.getTitle()
                    + ": need " + String.format("%.2f", item.getPrice())
                    + ", have " + String.format("%.2f", user.getBalance()));
        }

        // 5. רק עכשיו משנים: מורידים כסף ומוסיפים לספרייה
        user.withdraw(item.getPrice());
        OwnedItem owned = new OwnedItem(item, LocalDate.now(), item.getPrice());
        libraryOf(user).add(owned);
    }

    // הספרייה של המשתמש, כעותק
    public List<OwnedItem> getLibrary(User user) {
        return new ArrayList<>(libraryOf(user));
    }

    // האם הפריט כבר בספרייה של המשתמש?
    public boolean owns(User user, String itemId) {
        for (OwnedItem owned : libraryOf(user)) {
            if (owned.getItem().getId().equals(itemId)) {
                return true;
            }
        }
        return false;
    }

    // מחזירה את רשימת הספרייה של המשתמש.
    // אם זו הפעם הראשונה שהמשתמש מופיע, יוצרת לו רשימה ריקה.
    private List<OwnedItem> libraryOf(User user) {
        List<OwnedItem> library = libraries.get(user.getUsername());
        if (library == null) {
            library = new ArrayList<>();
            libraries.put(user.getUsername(), library);
        }
        return library;
    }
}
