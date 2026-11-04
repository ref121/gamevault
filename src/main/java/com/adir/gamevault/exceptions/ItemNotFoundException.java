package com.adir.gamevault.exceptions;

// מבקשים פריט שלא קיים בקטלוג.
// Runtime: בדרך כלל זה באג בקוד הקורא, כי המזהה הגיע מהקטלוג עצמו.
// לכן לא מכריחים כל קורא לכתוב try-catch.
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }
}
