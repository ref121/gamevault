package com.adir.gamevault.exceptions;

// מנסים לקנות פריט שכבר נמצא בספרייה של המשתמש.
// Checked: מצב צפוי בחנות, והקומפיילר מכריח את הקורא להחליט מה לעשות בו.
public class AlreadyOwnedException extends Exception {

    public AlreadyOwnedException(String message) {
        super(message);
    }
}
