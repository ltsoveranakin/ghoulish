package me.ltsoveranakin.ghoulish.client.util.parser;

import me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.parser.EnumParser;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.parser.MethodParser;

import java.awt.Color;

public class ParserUtil {
    /**
     * This parser does nothing, it returns the string without any modification.
     */
    public static final MethodParser<String> STRING_PARSER = new MethodParser<>(ParserUtil::passBack);
    public static final MethodParser<Byte> BYTE_PARSER = new MethodParser<>(Byte::parseByte);
    public static final MethodParser<Short> SHORT_PARSER = new MethodParser<>(Short::parseShort);
    public static final MethodParser<Integer> INTEGER_PARSER = new MethodParser<>(Integer::parseInt);
    public static final MethodParser<Long> LONG_PARSER = new MethodParser<>(Long::parseLong);
    public static final MethodParser<Float> FLOAT_PARSER = new MethodParser<>(Float::parseFloat);
    public static final MethodParser<Double> DOUBLE_PARSER = new MethodParser<>(Double::parseDouble);
    public static final MethodParser<Boolean> BOOLEAN_PARSER = new MethodParser<>(Boolean::parseBoolean);
    public static final MethodParser<Color> COLOR_PARSER = new MethodParser<>(ParserUtil::parseColor);

    public static final EnumParser ENUM_PARSER = new EnumParser();


    private static <T> T passBack(T string) {
        return string;
    }

    private static Color parseColor(String string) {
        String[] spl = string.split(",");
        int r = Integer.parseInt(spl[0]);
        int g = Integer.parseInt(spl[1]);
        int b = Integer.parseInt(spl[2]);
        int a = Integer.parseInt(spl[3]);
        return new Color(r, g, b, a);
    }
}
