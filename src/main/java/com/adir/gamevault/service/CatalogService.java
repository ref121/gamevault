package com.adir.gamevault.service;

import com.adir.gamevault.exceptions.ItemNotFoundException;
import com.adir.gamevault.model.Dlc;
import com.adir.gamevault.model.Game;
import com.adir.gamevault.model.StoreItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// מנהל את כל הפריטים בחנות.
// כל מי שצריך פריט, או את רשימת הפריטים, פונה לכאן.
public class CatalogService {

    // המפתח הוא ה־id של הפריט, כדי למצוא פריט מהר
    private final Map<String, StoreItem> items = new HashMap<>();

    public void addItem(StoreItem item) {
        if (item == null) {
            throw new IllegalArgumentException("item can not be null");
        }
        if (items.containsKey(item.getId())) {
            throw new IllegalArgumentException("duplicate id: " + item.getId());
        }

        // תוספת נכנסת לקטלוג רק אם משחק הבסיס שלה כבר שם, והוא באמת משחק
        if (item instanceof Dlc) {
            Dlc dlc = (Dlc) item;
            StoreItem base = items.get(dlc.getBaseGameId());
            if (!(base instanceof Game)) {
                throw new IllegalArgumentException(
                        "base game " + dlc.getBaseGameId() + " is not in the catalog");
            }
        }

        items.put(item.getId(), item);
    }

    // מחזיר פריט לפי id. אם אין כזה, נזרקת ItemNotFoundException
    public StoreItem getItem(String id) {
        StoreItem item = items.get(id);
        if (item == null) {
            throw new ItemNotFoundException("No item with id " + id);
        }
        return item;
    }

    // בדיקה בלי חריגה
    public boolean exists(String id) {
        return items.containsKey(id);
    }

    // מחזירים עותק: מי שמקבל אותו יכול למיין או לשנות אותו בלי לפגוע בקטלוג
    public List<StoreItem> getAllItems() {
        return new ArrayList<>(items.values());
    }
}
