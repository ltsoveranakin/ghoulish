package me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.parser;

import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;

public class EnumParser {

    public <T extends Enum<?>> T parseFromClass(String str, Class<T> enumClass) throws ParseException {
        for(Enum<?> value : enumClass.getEnumConstants()) {
            if(value.name().equalsIgnoreCase(str)) {
                return (T) value;
            }
        }

        return null;
    }
}
