package com.projectdgdx.game.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.projectdgdx.game.model.*;
import com.projectdgdx.game.model.ModelStructure.GameObject;
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
    private List<GameObject> gameObjects = new ArrayList<GameObject>();

    /**
     * This method loads the xml representation of the map into a Document variable which can be used
     * to access map data.
     *
     * @param mapName the name of the map to load. Map has to be located in assets/map
     */
    private void loadDocument(String mapName) {
        try {
            if(Vector2d.Config.DEBUG) {
                System.out.println(Gdx.files.getLocalStoragePath());
            }
            FileHandle handle = Gdx.files.internal("map/" + mapName);
            com.badlogic.gdx.assets.AssetManager manager = new AssetManager();
            FileHandleResolver resolver = new InternalFileHandleResolver();
            manager.setLoader(File.class, new TextFileLoader(resolver));
            manager.load("map/" + mapName, File.class);
            manager.finishLoading();
            File inputFile = manager.get("map/" + mapName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
        }catch(ParserConfigurationException | IOException | SAXException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * loadElements can be used to convert a NodeList into a GameObject list.
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

    /**
     * loadNode can be used for loading attributes of a node onto a gameObjectInit. This method is not immutable.
     *
     * @param node Node to load data from
     * @param gameObjectInit GameObjectInit to add data from the node upon
     * @return A GameObjectInit
     */
    private GameObjectInit loadNode(Node node, GameObjectInit gameObjectInit) {
        for(int i = 0; i < node.getAttributes().getLength(); i++) {
            Node attribute = node.getAttributes().item(i);
            gameObjectInit.changeValue(attribute.getNodeName(), attribute.getNodeValue());
        }
        if(Vector2d.Config.DEBUG) {
            System.out.println(gameObjectInit);
        }
        return gameObjectInit;
    }

    /**
     * gameObjectInit converts a gameObjectInit and adds it to the GameObject list.
     *
     * @param gameObjectInit A GameObjectInit that will be convert into a GameObject
     */
    private void addGameObject(GameObjectInit gameObjectInit) {
        GameObject gameObject = gameObjectInit.convert();
        if(gameObject != null) {
            gameObjects.add(gameObject);
        }
    }

    /**
     * parse will convert a xml file to a Map object
     *
     * @param mapName String
     * @return Returns a map containing all the information provided in the xml file
     */
    public Map parse(String mapName) {
        loadDocument(mapName);
        loadElements(doc.getDocumentElement().getChildNodes());
        return new BasicMap(gameObjects);

    }


}
