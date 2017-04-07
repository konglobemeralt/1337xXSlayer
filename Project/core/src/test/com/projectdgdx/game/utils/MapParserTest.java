package com.projectdgdx.game.utils;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.gameobjects.GameObject;
import com.projectdgdx.game.gameobjects.Supervisor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2017-04-07.
 */
public class MapParserTest {
    private MapParser mapParser;

    @Before
    public void setup(){
        mapParser = new MapParser();
    }

    @Test
    public void testParse() {
        assertTrue(mapParser.parse("Somethingcoool") != null);
    }


}