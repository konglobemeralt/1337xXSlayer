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
    List<GameObject> gameObjects = new ArrayList<GameObject>();

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

            //Make sure that node is not a text element
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                //Load node
                GameObjectInit gameObjectInit = loadNode(node, new GameObjectInit(node.getNodeName()));

                //Check for child nodes
                if(node.hasChildNodes()) {
                    for(int j = 0; j < node.getChildNodes().getLength(); j++) {
                        Node deepNode = node.getChildNodes().item(j);
                        if(deepNode.getNodeType() == Node.ELEMENT_NODE) {
                            GameObjectInit deepGameObjectInit = loadNode(deepNode, gameObjectInit.clone());
                            addGameObject(deepGameObjectInit);
                        }

                    }
                }else {
                    addGameObject(gameObjectInit);
                }
            }
        }
    }

    //GameObject to add attributes to from xml
    private GameObjectInit loadNode(Node node, GameObjectInit gameObjectInit) {
        for(int i = 0; i < node.getAttributes().getLength(); i++) {
            Node attribute = node.getAttributes().item(i);
            gameObjectInit.changeValue(attribute.getNodeName(), attribute.getNodeValue());
        }
        System.out.println(gameObjectInit);
        return gameObjectInit;
    }

    private void addGameObject(GameObjectInit gameObjectInit) {
        GameObject gameObject = gameObjectInit.convert();
        if(gameObject != null) {
            gameObjects.add(gameObject);
        }
    }

    public Map parse(String mapName) {
        loadDocument(mapName);
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
//        loadElements(doc.getElementsByTagName("Machine"));
//        for(int i = 0; i < doc.getDocumentElement().getChildNodes().getLength(); i++) {
//            System.out.println(doc.getDocumentElement().getChildNodes().item(i).toString());
//        }
        loadElements(doc.getDocumentElement().getChildNodes());

//        for(GameObject gameObject : gameObjects) {
//            System.out.println(gameObject);
//        }
        System.out.println("GameObjects: " + gameObjects.size());

        return new BasicMap(gameObjects);

    }


}
