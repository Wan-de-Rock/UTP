package zad1;

import java.util.Locale;

public class CountryTranslator {
    static String translate(String country, Locale localeFrom, Locale localeTo) {
        Locale[] allLocales = Locale.getAvailableLocales();

        for (Locale locale : allLocales) {
            if (locale.getDisplayCountry(localeFrom).equals(country)) {
                return locale.getDisplayCountry(localeTo);
            }
        }

        return "";
    }
}