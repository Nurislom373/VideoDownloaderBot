package org.khasanof.utils.baseUtils;

import org.khasanof.enums.language.Language;

public class BaseUtils {
    public static String translate(String lang, Language text) {
        for (Language value : Language.values()) {
            if (value.equals(text)) {
                if (lang.equalsIgnoreCase("uz")) return value.getUz();
                if (lang.equalsIgnoreCase("ru")) return value.getRu();
                if (lang.equalsIgnoreCase("en")) return value.getEn();
            }
        }
        return "";
    }
}
