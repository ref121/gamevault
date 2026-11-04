package com.adir.gamevault;

import com.adir.gamevault.comparator.ItemComparators;
import com.adir.gamevault.exceptions.AlreadyOwnedException;
import com.adir.gamevault.exceptions.InsufficientFundsException;
import com.adir.gamevault.exceptions.MissingBaseGameException;
import com.adir.gamevault.model.Dlc;
import com.adir.gamevault.model.Game;
import com.adir.gamevault.model.Genre;
import com.adir.gamevault.model.StoreItem;
import com.adir.gamevault.model.User;
import com.adir.gamevault.service.CatalogService;
import com.adir.gamevault.service.StoreService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

// בדיקות במסוף לשלבים 1–4.
// בשלב 5 הקוד הזה עובר למחלקה ConsoleDemo, ו־Main הופכת לתוכנית JavaFX.
public class Main {

    public static void main(String[] args) {

        // ----- הקטלוג -----
        CatalogService catalog = new CatalogService();
        catalog.addItem(new Game("G1", "Space Run", 49.90,
                LocalDate.of(2024, 3, 1), 4.5, Genre.ACTION));
        catalog.addItem(new Game("G2", "Castle Builder", 89.00,
                LocalDate.of(2023, 11, 15), 4.1, Genre.STRATEGY));
        catalog.addItem(new Game("G3", "Puzzle Island", 19.90,
                LocalDate.of(2025, 1, 20), 3.8, Genre.PUZZLE));
        // התוספת נכנסת אחרי G1, כי משחק הבסיס חייב להיות בקטלוג קודם
        catalog.addItem(new Dlc("D1", "Space Run: Moon Pack", 19.90,
                LocalDate.of(2024, 9, 1), 4.2, "G1"));

        // ----- שלב 4: מיונים -----
        System.out.println("=== Stage 4: sorting ===");
        printSorted(catalog, "by title", ItemComparators.byTitle());
        printSorted(catalog, "by price", ItemComparators.byPrice());
        printSorted(catalog, "by rating", ItemComparators.byRatingDesc());
        printSorted(catalog, "newest first", ItemComparators.newestFirst());

        // ----- שלב 3: רכישות -----
        System.out.println();
        System.out.println("=== Stage 3: purchases ===");
        StoreService store = new StoreService(catalog);
        User dana = new User("dana", "Dana", 150);

        tryPurchase(store, dana, "D1");   // נכשל: אין משחק בסיס
        tryPurchase(store, dana, "G1");   // מצליח
        tryPurchase(store, dana, "G1");   // נכשל: כבר בבעלות
        tryPurchase(store, dana, "D1");   // מצליח עכשיו
        tryPurchase(store, dana, "G2");   // נכשל: אין מספיק כסף

        System.out.println();
        System.out.println(dana);
        System.out.println("Library: " + store.getLibrary(dana));
    }

    // מדפיסה את הקטלוג בסדר שמתקבל כפרמטר
    private static void printSorted(CatalogService catalog, String name,
                                    Comparator<StoreItem> order) {
        List<StoreItem> items = catalog.getAllItems();   // עותק, אז מותר למיין
        items.sort(order);
        System.out.println(name + ":");
        for (StoreItem item : items) {
            System.out.println("   " + item);
        }
    }

    // מנסה לקנות, ומדפיסה אם הצליח או למה נכשל
    private static void tryPurchase(StoreService store, User user, String itemId) {
        try {
            store.purchase(user, itemId);
            System.out.println("OK      " + itemId + "   balance " + String.format("%.2f", user.getBalance()));
        } catch (AlreadyOwnedException e) {
            printFailure(itemId, e.getMessage(), user);
        } catch (MissingBaseGameException e) {
            printFailure(itemId, e.getMessage(), user);
        } catch (InsufficientFundsException e) {
            printFailure(itemId, e.getMessage(), user);
        }
    }

    private static void printFailure(String itemId, String reason, User user) {
        System.out.println("FAILED  " + itemId + "   " + reason
                + "   balance " + String.format("%.2f", user.getBalance()));
    }
}
