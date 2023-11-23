package me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.parser;

import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.IParser;

public interface IStdParser<K> extends IParser {
    K parse(String string) throws ParseException;
}
