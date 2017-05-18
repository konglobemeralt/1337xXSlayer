package com.projectdgdx.game.model;

import com.projectdgdx.game.model.Decorations.Floor;
import com.projectdgdx.game.model.MachineStates.UnusedMachineState;
import com.projectdgdx.game.model.ModelStructure.GameObject;
import com.projectdgdx.game.model.Playables.NormalPlayerState;
import com.projectdgdx.game.model.Playables.Saboteur;
import com.projectdgdx.game.model.Playables.Supervisor;
import com.projectdgdx.game.model.StaticInteractable.Machine;
import com.projectdgdx.game.model.StaticInteractable.Spotlight;
import com.projectdgdx.game.model.StaticInteractable.SpotlightControlBoard;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.model.AI.WorkerNode;
import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Jansson on 2017-05-17.
 */
public class GameObjectInit {
        private String type;

        //Different values with defaults:
        private float x = 0;
        private float y = 0;
        private float z = 0;

        private float scaleX = 1;
        private float scaleY = 1;
        private float scaleZ = 1;

        private float rotationX = 0;
        private float rotationY = 0;
        private float rotationZ = 0;

        private float spawnRate = 1;
        private boolean spawnRateRandom = false;
        private float spawnDelay = 0;
        private float aliveLimit = 10;

        //Node
        private int nodeId = 0;
        private List<Integer> nodeFriends = new ArrayList<>();


        private String id = null;

        /**
         * The constructor for GameObjectInit requires a type
         *
         * @param type Type of GameObject that the data should be converted into
         */
        public GameObjectInit(String type) {
            this.type = type;
        }

        private GameObjectInit(GameObjectInit gameObjectInit) {
            type = gameObjectInit.type;

            x = gameObjectInit.x;
            y = gameObjectInit.y;
            z = gameObjectInit.z;

            scaleX = gameObjectInit.scaleX;
            scaleY = gameObjectInit.scaleY;
            scaleZ = gameObjectInit.scaleZ;

            rotationX = gameObjectInit.rotationX;
            rotationY = gameObjectInit.rotationY;
            rotationZ = gameObjectInit.rotationZ;

            spawnRate = gameObjectInit.spawnRate;
            spawnRateRandom = gameObjectInit.spawnRateRandom;
            spawnDelay = gameObjectInit.spawnDelay;
            aliveLimit = gameObjectInit.aliveLimit;
            id = gameObjectInit.id;
        }


        /**
         * convert can be used to convert a git sGameObjectInit into a GameObject
         *
         * @return A GameObject that has the data within GameObjectInit
         */
        public GameObject convert() {
            switch (type) {
                case "Machine":
                    return new Machine(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "machine.basic", new UnusedMachineState());
                case "SpotControl":
                    Spotlight light = new Spotlight(new Vector3d(x,30,  z), new Vector3d(1, 1, 1), new Vector3d(1, 1, 1), 30, Config.MACHINELIGHT_BASE_INTENSITY, "spotlight.controlboard");
                    return new SpotlightControlBoard(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "control.basic", light);
                case "Worker":
                    return new Worker(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "worker.basic");
                case "Supervisor":
                    Supervisor supervisor = new Supervisor(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "supervisor.basic");
                    supervisor.setState(new NormalPlayerState(supervisor));
                    return supervisor;
                case "Saboteur":
                    Saboteur saboteur = new Saboteur(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "saboteur.basic");
                    saboteur.setState(new NormalPlayerState(saboteur));
                    return saboteur;
                case "WorkerNode":
                    return new WorkerNode(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "node.worker", nodeId, nodeFriends) {
                    };
                case "Floor":
                    return new Floor(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "floor.basic");
                default:
                    System.out.println("TAG OF TYPE: " + type + " not supported");
                    return null;
            }
        }

        public void changeValue(String key, String value) {
            switch(key) {
                case "x":
                    x = Float.parseFloat(value);
                    break;
                case "y":
                    y = Float.parseFloat(value);
                    break;
                case "z":
                    z = Float.parseFloat(value);
                    break;
                case "scaleX":
                    scaleX = Float.parseFloat(value);
                    break;
                case "scaleY":
                    scaleY = Float.parseFloat(value);
                    break;
                case "scaleZ":
                    scaleZ = Float.parseFloat(value);
                    break;
                case "rotationX":
                    rotationX = Float.parseFloat(value);
                    break;
                case "rotationY":
                    rotationY = Float.parseFloat(value);
                    break;
                case "rotationZ":
                    rotationZ = Float.parseFloat(value);
                    break;
                case "nodeId":
                    nodeId = Integer.parseInt(value);
                    break;
                case "nodeFriends":
                    for(String friend : value.split(",")) {
                        nodeFriends.add(Integer.parseInt(friend));
                    }
                    //                className = value;
                    break;
                case "className":
                    //                className = value;
                    break;
                default:
                    System.out.println("Value does not exist   " + key);
                    break;
            }
        }

        /**
         * Clone provides a way to do a deep copy of a GameObjectInit
         *
         * @return A clone of GameObjectInit
         */
        @Override
        public GameObjectInit clone() {
            return new GameObjectInit(this);
        }
    }

