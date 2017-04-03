package com.projectdgdx.game.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.projectdgdx.game.gameobjects.GameObject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.List;

/**
 * Created by Hampus on 2017-03-24.
 */
public class MapParser {

    private Document doc;
    List<GameObject> gameObjects = new ArrayList();

    /**
     * This method loads the xml representation of the map into a Document folder which can be used
     * to access map data.
     *
     * @param mapName the name of the map to load. Map has to be located in assets/map
     */
    private void loadDocument(String mapName) {
        try {
            System.out.println("map/" + mapName + ".txt");
            FileHandle handle = Gdx.files.internal("map/" + mapName + ".txt");
            File inputFile = handle.file();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
        }catch(ParserConfigurationException | IOException | SAXException exception) {
            exception.printStackTrace();
        }
    }


    /**
     *
     * @param list a list of all Node items which should be loaded into the gameObjects list
     */
    private void loadElements(NodeList list) {
        for(int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            GameObjectInit gameObjectInit = new GameObjectInit(node.getNodeName());
            for(int j = 0; j < node.getAttributes().getLength(); j++) {
                Node attribute = node.getAttributes().item(j);
                gameObjectInit.changeValue(attribute.getNodeName(), attribute.getNodeValue());
            }
            gameObjects.add(gameObjectInit.convert());
        }

    }

    public Map parse(String mapName) {
        loadDocument(mapName);
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
        loadElements(doc.getElementsByTagName("Machine"));

//        for(GameObject gameObject : gameObjects) {
//            System.out.println(gameObject);
//        }
        System.out.println("GameObjects: " + gameObjects.size());

        return new BasicMap(gameObjects);

    }


}
