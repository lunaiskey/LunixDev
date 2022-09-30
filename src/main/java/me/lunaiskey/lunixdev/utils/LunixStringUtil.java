package me.lunaiskey.lunixdev.utils;

import org.apache.commons.lang.WordUtils;
import org.jetbrains.annotations.NotNull;

public class LunixStringUtil {

    public static String formatMaterialName(@NotNull String input) {
        input = input.replace('_',' ');
        input = WordUtils.capitalizeFully(input);
        return input;
    }
}
