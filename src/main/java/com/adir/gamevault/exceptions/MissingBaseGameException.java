package com.adir.gamevault.exceptions;

// מנסים לקנות תוספת בלי שמשחק הבסיס בספרייה.
// Checked: מצב צפוי בחנות, והקומפיילר מכריח את הקורא להחליט מה לעשות בו.
public class MissingBaseGameException extends Exception {

    public MissingBaseGameException(String message) {
        super(message);
    }
}
