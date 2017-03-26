package com.projectdgdx.game.utils;

import java.util.Iterator;

/**
 * Created by Hampus on 2017-03-24.
 */
public class MapLoader {
    public MapLoader(String mapName) {
        MapParser parser = new MapParser();
        parser.parse(mapName);
    }
}
