package com.adir.gamevault.model;

import com.adir.gamevault.exceptions.InsufficientFundsException;

// משתמש בחנות, עם ארנק.
// הכלל החשוב: היתרה לעולם לא שלילית.
// אין setter ליתרה, ולכן אפשר לשנות אותה רק דרך deposit ו־withdraw, שבודקות את הכלל.
public class User {

    private final String username;     // מזהה ייחודי, לא משתנה
    private String displayName;        // השם שמוצג. מותר לשנות
    private double balance;

    public User(String username, String displayName, double initialBalance) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username can not be empty");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("balance can not be negative");
        }
        this.username = username;
        this.displayName = displayName;
        this.balance = initialBalance;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getBalance() {
        return balance;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    // הפקדה לארנק
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        balance = balance + amount;
    }

    // משיכה מהארנק.
    // אם אין מספיק כסף, נזרקת חריגה והיתרה לא משתנה.
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (!canAfford(amount)) {
            throw new InsufficientFundsException("Not enough money: need "
                    + String.format("%.2f", amount) + ", have " + String.format("%.2f", balance));
        }
        balance = balance - amount;
    }

    // שאלה בלי שינוי: האם יש מספיק כסף?
    // השירות שואל אותה לפני שהוא מתחיל לשנות נתונים.
    public boolean canAfford(double amount) {
        return balance >= amount;
    }

    // שני משתמשים שווים אם ורק אם יש להם אותו username
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }
        User otherUser = (User) other;
        return username.equals(otherUser.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return displayName + " (" + username + "), balance " + String.format("%.2f", balance);
    }
}
