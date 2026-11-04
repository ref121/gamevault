<div dir="rtl" align="right">

# GameVault — סדר העבודה

מטרת הקובץ: לבנות מערכת מאפס, שלב אחר שלב, בלי לקפוץ קדימה.

**המערכת:** חנות וספריית משחקים. יש קטלוג של משחקים ותוספות (DLC), משתמשים עם ארנק, רכישות וספרייה אישית.

הקובץ מתעדכן לאורך הדרך. כשנסיים שלב, יתווסף אליו השלב הבא.

> **מצב הריפו:** שלבים 1–4 כבר ממומשים בריפו של המורה, כנקודת התחלה משותפת. עברו על הקוד, הריצו את <code dir="ltr">Main</code>, ובדקו שאתם מבינים כל מחלקה. העבודה שלכם מתחילה בשלב 5.

---

## דרך העבודה בכל שלב

1. **לומדים:** הנושא החדש נלמד בכיתה על דוגמה קטנה.
2. **מממשים:** כותבים את המשימה בפרויקט.
3. **בודקים:** עוברים על "בדיקת סיום" של השלב.
4. **שומרים:** מריצים את פקודות ה־Git מהנספח.

בסוף כל שלב המורה מסמן את הגרסה שלו בשם <code dir="ltr">stage-XX</code>. מי שהסתבך יכול להתחיל מחדש מהגרסה הזו, לפי הנספח.

---

## מבנה הפרויקט

כל הקוד נמצא תחת החבילה <code dir="ltr">com.adir.gamevault</code>. חבילה חדשה נפתחת רק בשלב שבו היא נדרשת.

<div dir="ltr" align="left">

```
com.adir.gamevault
├── Main.java        [0]
├── model            [1]
├── exceptions       [3]
├── service          [3]
├── comparator       [4]
└── ui               [5]
```

</div>

---

## שלב 0 — הקמת הסביבה

### מה לעשות?
1. לבצע fork לריפו של המורה ב־GitHub.
2. לבצע clone ולפתוח ב־IntelliJ.
3. להגדיר JDK: **Eclipse Temurin, גרסה 21**.
4. להריץ את <code dir="ltr">Main</code>.

### מה כבר מוכן בריפו?
- <code dir="ltr">Main.java</code>, שמדפיס למסוף.
- <code dir="ltr">pom.xml</code>, שמגדיר את גרסת Java של הפרויקט. אין צורך לשנות אותו.
- <code dir="ltr">.gitignore</code>, שאומר ל־Git אילו קבצים לא לשמור.

### תקלות נפוצות

| מה רואים | מה עושים |
|---|---|
| פס צהוב: *Project JDK is misconfigured* | **Setup SDK** ← Temurin 21. אם לא עוזר: **File** ← **Project Structure** ← **Project** ← SDK |
| הכול באדום, גם <code dir="ltr">String</code> | אותה תקלה: ה־JDK לא מוגדר |
| מודפסת גרסה שאינה 21 | בחרו Temurin 21 ב־Project Structure |

### בדיקת סיום
במסוף מופיע:

<div dir="ltr" align="left">

```
GameVault - stage 0
Java version: 21...
```

</div>

ובריפו שלכם ב־GitHub מופיע ה־commit הראשון שלכם.

---

## שלב 1 — <code dir="ltr">model</code>: משחק, ז׳אנר ומשתמש

### מה לממש?
1. <code dir="ltr">Genre</code> — <code dir="ltr">enum</code>: <code dir="ltr">ACTION</code>, <code dir="ltr">ADVENTURE</code>, <code dir="ltr">RPG</code>, <code dir="ltr">STRATEGY</code>, <code dir="ltr">SPORTS</code>, <code dir="ltr">PUZZLE</code>, <code dir="ltr">INDIE</code>
2. <code dir="ltr">Game</code> — <code dir="ltr">id</code>, <code dir="ltr">title</code>, <code dir="ltr">genre</code>, <code dir="ltr">price</code>, <code dir="ltr">releaseDate</code>, <code dir="ltr">rating</code>
3. <code dir="ltr">User</code> — <code dir="ltr">username</code>, <code dir="ltr">displayName</code>, <code dir="ltr">balance</code>
4. Constructor, Getters, <code dir="ltr">toString</code>
5. <code dir="ltr">equals</code> ו־<code dir="ltr">hashCode</code>
6. ב־<code dir="ltr">User</code>: הפקדה ומשיכה, ובדיקה האם יש מספיק כסף לסכום מסוים.

### דגשים
- הזהות של משחק היא לפי <code dir="ltr">id</code>. הזהות של משתמש היא לפי <code dir="ltr">username</code>.
- <code dir="ltr">price</code> ו־<code dir="ltr">balance</code> לא יכולים להיות שליליים. <code dir="ltr">rating</code> בין 0 ל־5.
- עבור קלט לא חוקי משתמשים ב־<code dir="ltr">IllegalArgumentException</code>.
- <code dir="ltr">releaseDate</code> הוא <code dir="ltr">LocalDate</code>.
- משיכה שתגרום ליתרה שלילית נכשלת, והיתרה לא משתנה.

### חזרה על
מחלקות, הכמסה, בנאים, <code dir="ltr">enum</code>, <code dir="ltr">Object</code>, <code dir="ltr">equals</code>, <code dir="ltr">hashCode</code>, <code dir="ltr">toString</code>.

### בדיקת סיום
- שני משחקים עם אותו <code dir="ltr">id</code> שווים.
- ניסיון ליצור משחק במחיר שלילי נכשל.
- משיכה מעבר ליתרה נכשלת, והיתרה נשארת כמו שהייתה.

---

## שלב 2 — ירושה: <code dir="ltr">StoreItem</code>, <code dir="ltr">Game</code>, <code dir="ltr">Dlc</code>

### הצורך
בחנות יש גם תוספות למשחקים. לתוספת יש שם, מחיר, תאריך ודירוג, כמו למשחק, אבל היא שייכת למשחק בסיס.

### מה לממש?
1. מחלקה אבסטרקטית <code dir="ltr">StoreItem</code> עם <code dir="ltr">id</code>, <code dir="ltr">title</code>, <code dir="ltr">price</code>, <code dir="ltr">releaseDate</code>, <code dir="ltr">rating</code>.
2. <code dir="ltr">Game</code> יורשת מ־<code dir="ltr">StoreItem</code>, ונשאר לה רק <code dir="ltr">genre</code>.
3. <code dir="ltr">Dlc</code> יורשת מ־<code dir="ltr">StoreItem</code>, ומוסיפה <code dir="ltr">baseGameId</code>.
4. מתודה אבסטרקטית <code dir="ltr">getTypeLabel()</code> שכל מחלקה מממשת.
5. העברת <code dir="ltr">equals</code>, <code dir="ltr">hashCode</code> והבדיקות המשותפות ל־<code dir="ltr">StoreItem</code>.

### דגשים
- <code dir="ltr">Game</code> משלב 1 משתנה. זה חלק מהתרגיל: לזהות מה משותף ולהעביר אותו למעלה.
- אין ליצור מופע של <code dir="ltr">StoreItem</code> ישירות.
- <code dir="ltr">baseGameId</code> לא ריק, ושונה מה־<code dir="ltr">id</code> של התוספת עצמה.

### חזרה על
ירושה, <code dir="ltr">super</code>, דריסה, מחלקה אבסטרקטית, פולימורפיזם, <code dir="ltr">instanceof</code>.

### בדיקת סיום
רשימה <code dir="ltr">List&lt;StoreItem&gt;</code> שמכילה גם משחקים וגם תוספות מודפסת, וכל פריט מופיע עם התווית הנכונה.

---

## שלב 3 — קטלוג, ספרייה וחריגות

### מה לממש?
1. <code dir="ltr">exceptions</code>:
   - <code dir="ltr">ItemNotFoundException</code> — Runtime Exception
   - <code dir="ltr">AlreadyOwnedException</code> — Checked Exception
   - <code dir="ltr">InsufficientFundsException</code> — Checked Exception
   - <code dir="ltr">MissingBaseGameException</code> — Checked Exception
2. <code dir="ltr">model.OwnedItem</code> — פריט שהמשתמש רכש: <code dir="ltr">item</code>, <code dir="ltr">purchaseDate</code>, <code dir="ltr">pricePaid</code>, <code dir="ltr">playtimeMinutes</code>.
3. <code dir="ltr">service.CatalogService</code>:
   - הוספת פריט, איתור פריט לפי מזהה, והחזרת כל הפריטים.
   - שמירה ב־<code dir="ltr">Map&lt;String, StoreItem&gt;</code>, המפתח הוא <code dir="ltr">id</code>.
   - תוספת מתווספת רק אם משחק הבסיס כבר בקטלוג.
4. <code dir="ltr">service.StoreService</code>:
   - <code dir="ltr">purchase(User user, String itemId)</code>
   - החזרת הספרייה של משתמש, ובדיקה האם הוא מחזיק בפריט.

### מה קורה ב־<code dir="ltr">purchase</code>?
1. מוצאים את הפריט בקטלוג. לא קיים: <code dir="ltr">ItemNotFoundException</code>.
2. בודקים שהמשתמש לא מחזיק בו כבר. מחזיק: <code dir="ltr">AlreadyOwnedException</code>.
3. אם זו תוספת, בודקים שהמשתמש מחזיק במשחק הבסיס. לא מחזיק: <code dir="ltr">MissingBaseGameException</code>.
4. בודקים יתרה. לא מספיקה: <code dir="ltr">InsufficientFundsException</code>.
5. **רק אחרי שכל הבדיקות עברו:** מורידים מהארנק ומוסיפים לספרייה.

### דגשים
- הסדר חשוב: רכישה שנכשלה לא משנה כלום, לא יתרה ולא ספרייה.
- משיכה מהארנק ב־<code dir="ltr">User</code> עוברת לזרוק <code dir="ltr">InsufficientFundsException</code>. שימו לב מה זה משנה אצל כל מי שקורא לה.
- <code dir="ltr">pricePaid</code> נשמר כי המחיר בחנות יכול להשתנות אחרי הרכישה.

### חזרה על
<code dir="ltr">List</code>, <code dir="ltr">Map</code>, <code dir="ltr">Set</code>, חריגות Checked מול Runtime, <code dir="ltr">throws</code>, <code dir="ltr">try-catch</code>.

### בדיקת סיום
<code dir="ltr">Main</code> במסוף מדגים ארבעה מקרים: רכישה תקינה, רכישה כפולה, רכישה בלי מספיק כסף, ותוספת בלי משחק בסיס. בכל כישלון היתרה והספרייה לא משתנות.

---

## שלב 4 — מיון: <code dir="ltr">Comparable</code>, <code dir="ltr">Comparator</code>, מחלקות מקוננות ו־Lambda

### הצורך
הקטלוג צריך להופיע לפי שם, מחיר, דירוג ותאריך יציאה.

### מה לממש?
1. <code dir="ltr">StoreItem</code> מממשת <code dir="ltr">Comparable&lt;StoreItem&gt;</code>. סדר טבעי לפי <code dir="ltr">id</code>.
2. מחלקה <code dir="ltr">comparator.ItemComparators</code> עם מחלקות סטטיות מקוננות: <code dir="ltr">ByPrice</code>, <code dir="ltr">ByTitle</code>.
3. מיון לפי דירוג, מהגבוה לנמוך, כמחלקה אנונימית.
4. המרת כל ההשוואות ל־Lambda ול־<code dir="ltr">Comparator.comparing</code>.
5. מיון "הכי חדשים קודם".

### דגשים
- בכל שוויון מכריעים לפי <code dir="ltr">id</code>, כדי שאותו קטלוג יוצג תמיד באותו סדר.
- <code dir="ltr">reversed()</code> הופך את כל השרשרת שנבנתה לפניו.
- <code dir="ltr">releaseDate</code> הוא <code dir="ltr">LocalDate</code>, והוא כבר <code dir="ltr">Comparable</code>.

### חזרה על
<code dir="ltr">Comparable</code>, <code dir="ltr">Comparator</code>, מחלקה סטטית מקוננת, מחלקה פנימית, מחלקה מקומית, מחלקה אנונימית, Lambda, Method Reference, <code dir="ltr">default</code> בממשק.

### בדיקת סיום
אותו קטלוג מודפס בארבעה סדרים שונים. שני פריטים באותו מחיר מופיעים לפי <code dir="ltr">id</code>.

---

## שלב 5 — חלון ראשון ב־JavaFX

### לומדים בנפרד
דפי העבודה של JavaFX, 1 עד 3, בפרויקט המעבדה <code dir="ltr">javafx-lab</code>: <code dir="ltr">Stage</code>, <code dir="ltr">Scene</code>, פריסות, רכיבים ואירועים.

### מה לממש?
1. להוסיף את <code dir="ltr">javafx-controls</code> ל־<code dir="ltr">pom.xml</code>, וליצור <code dir="ltr">module-info.java</code> ישירות תחת <code dir="ltr">src/main/java</code>.
2. להעביר את בדיקות המסוף מ־<code dir="ltr">Main</code> למחלקה חדשה, <code dir="ltr">ConsoleDemo</code>.
3. <code dir="ltr">Main</code> יורשת מ־<code dir="ltr">Application</code>, יוצרת את השירותים ואת המשתמש, ומציגה את מסך החנות.
4. <code dir="ltr">ui.StoreView</code>: למעלה שם המשתמש והיתרה, במרכז <code dir="ltr">ListView&lt;StoreItem&gt;</code> עם הקטלוג, למטה בחירת מיון וכפתור <code dir="ltr">Buy</code>.

### דגשים
- המסך לא בודק יתרה, בעלות או משחק בסיס. הוא קורא ל־<code dir="ltr">StoreService.purchase</code> ומציג את התשובה.
- כל אחת משלוש החריגות מוצגת ב־<code dir="ltr">Alert</code>. לחיצה על <code dir="ltr">Buy</code> בלי בחירה לא קורסת.
- המיון משתמש ב־<code dir="ltr">ItemComparators</code> משלב 4.
- הנתונים עדיין בזיכרון: כשסוגרים את החלון, הרכישות נעלמות. בשלב 7 זה ישתנה.

### בדיקת סיום
לפי הטבלה בדף העבודה של שלב 5. בקצרה: קנייה מעדכנת את היתרה במסך, וכל רכישה שנכשלת מציגה הודעה ולא משנה כלום.

---

## השלבים הבאים

יתווספו לקובץ הזה כשנגיע אליהם.

---

# נספח — פקודות Git

## הקמה חד־פעמית (שלב 0)

1. ב־GitHub, בריפו של המורה: כפתור **Fork** ← **Create fork**.
2. ב־IntelliJ, או בטרמינל עם <code dir="ltr">git clone</code>: **File** ← **New** ← **Project from Version Control**, והדביקו את הכתובת של **ה־fork שלכם**, לא של המורה.
3. בטרמינל של IntelliJ, פעם אחת:

<div dir="ltr" align="left">

```bash
git remote add upstream https://github.com/ref121/gamevault.git
```

</div>

## שמירה בסוף כל שלב

<div dir="ltr" align="left">

```bash
git add .
git commit -m "stage 01 - model classes"
git push
```

</div>

## קבלת עדכונים מהמורה

<div dir="ltr" align="left">

```bash
git fetch upstream --tags
git merge upstream/master
```

</div>

## הסתבכתי — להתחיל מחדש משלב מסוים

הפקודה יוצרת branch חדש מהגרסה של המורה. העבודה הקודמת שלכם לא נמחקת.

<div dir="ltr" align="left">

```bash
git fetch upstream --tags
git switch -c restart-stage-03 stage-03
```

</div>

</div>
