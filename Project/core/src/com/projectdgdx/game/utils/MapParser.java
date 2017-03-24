package com.projectdgdx.game.utils;

import java.util.Iterator;

/**
 * Created by Hampus on 2017-03-24.
 */
public class MapParser {
    public void parse(Iterator<String> mapIterator) {
        while(mapIterator.hasNext()) {
            load(mapIterator.next());
        }
    }

    private void load(String rawLine) {
        String line = rawLine.substring(rawLine.indexOf("<") + 1, rawLine.indexOf(">"));
        String[] tags  = line.split(" ");

        System.out.println(tags[0]);
        System.out.println(tags[1]);
    }

}
