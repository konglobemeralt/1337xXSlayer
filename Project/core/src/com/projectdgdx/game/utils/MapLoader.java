package com.projectdgdx.game.utils;

import java.util.Iterator;

/**
 * Created by Hampus on 2017-03-24.
 */
public class MapLoader {
    FileLoader loader;
    public MapLoader(String mapName) {
        loader = new FileLoader("map/" + mapName + ".txt");
        Iterator<String> mapIterator = loader.getIterator();
        MapParser parser = new MapParser();
        parser.parse(mapIterator);
    }
}
