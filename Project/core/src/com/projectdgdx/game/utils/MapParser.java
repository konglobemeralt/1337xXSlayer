package com.projectdgdx.game.utils;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
    List<GameObject> gameObjects;

    /**
     * This method loads the xml representation of the map into a Document folder which can be used
     * to access map data.
     *
     * @param mapName the name of the map to load. Map has to be located in assets/map
     */
    private void loadDocument(String mapName) {
        try {
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
//            System.out.println(node.getNodeName());
            GameObjectInit gameObjectInit = new GameObjectInit(node.getNodeName());
            for(int j = 0; j < node.getAttributes().getLength(); j++) {
                Node attribute = node.getAttributes().item(j);
                System.out.println(attribute.getNodeName() + " : " + attribute.getNodeValue());
                gameObjectInit.changeValue(attribute.getNodeName(), attribute.getNodeValue());
            }


        }

    }

    public void parse(String mapName) {
        loadDocument(mapName);
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
        loadElements(doc.getElementsByTagName("Mesh"));
    }


}
