package com.projectdgdx.game.model;

import com.projectdgdx.game.model.AI.BasicNode;
import com.projectdgdx.game.model.AI.WorkerNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hampus on 2017-03-24.
 */
public abstract class Map {
    private int gameTime = 300;
    private int playerSpeed = 1;
    private List<GameObject> gameObjects;


    /**
     * The constructor for Map just requires a list of GameObjects
     *
     * @param gameObjects A list of GameObjects
     */
    public Map(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * getGameObjects can be used to get all of the map's GameObjects
     *
     * @return A list of GameObjects
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * A method to get all player objects in the map
     * @return List of players
     */
    public List<PlayableCharacter> getPlayers() {
        List<PlayableCharacter> players = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof PlayableCharacter) {
                players.add((PlayableCharacter)gameObject);
            }
        }
        return players;
    }

    public List<Character> getCharacters() {
        List<Character> character = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof Character) {
                character.add((Character)gameObject);
            }
        }
        return character;
    }

    public List<Worker> getWorkers() {
        List<Worker> workers = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof Worker) {
                workers.add((Worker)gameObject);
            }
        }
        return workers;
    }

    public List<BasicNode> getAINodes() {
        List<BasicNode> nodes = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof BasicNode) {
                nodes.add((BasicNode)gameObject);
            }
        }
        return nodes;
    }

    public List<Machine> getMachines() {
        List<Machine> machines = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof Machine) {
                machines.add((Machine)gameObject);
            }
        }
        return machines;
    }

    public List<SpotlightControlBoard> getSpotlightControlBoard() {
        List<SpotlightControlBoard> SpotlightControlBoards = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof SpotlightControlBoard) {
                SpotlightControlBoards.add((SpotlightControlBoard)gameObject);
            }
        }
        return SpotlightControlBoards;
    }

    public List<WorkerNode> getWorkerNodes() {
        List<WorkerNode> nodes = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof WorkerNode) {
                nodes.add((WorkerNode)gameObject);
            }
        }
        return nodes;
    }

    public List<iHonestInteractable> getHonestInteractables() {
        List<iHonestInteractable> hi = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof iHonestInteractable) {
                hi.add((iHonestInteractable)gameObject);
            }
        }
        return hi;
    }

    public List<iDishonestInteractable> getDishonestInteractables() {
        List<iDishonestInteractable> di = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof iDishonestInteractable) {
                di.add((iDishonestInteractable)gameObject);
            }
        }
        return di;
    }

    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof Entity) {
                entities.add((Entity) gameObject);
            }
        }
        return entities;
    }

    public List<StaticObject> getStaticObjects() {
        List<StaticObject> staticObjects = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof StaticObject) {
                staticObjects.add((StaticObject) gameObject);
            }
        }
        return staticObjects;
    }


}
