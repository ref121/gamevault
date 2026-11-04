package com.adir.gamevault.exceptions;

// אין למשתמש מספיק כסף.
// Checked: מצב צפוי בחנות, והקומפיילר מכריח את הקורא להחליט מה לעשות בו.
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
