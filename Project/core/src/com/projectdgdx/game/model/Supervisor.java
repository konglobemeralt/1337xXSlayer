package com.projectdgdx.game.model;

import com.projectdgdx.game.Config;
import com.projectdgdx.game.model.AI.WorkerNode;
import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * The Supervisors are the "honest" player controlled characters in the game that are supposed
 * catch the Saboteur before too much damage has been done.
 */
public class Supervisor extends PlayableCharacter {
    public Supervisor(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void dishonestInteract(List<iDishonestInteractable> dishonestInteractables) {
        // Should be empty since a supervisor can not sabotage the machines.
    }

    @Override
    public void useAbility(List<Character> characters) {
        this.catsch(characters);
    }

    /**
     * Gets all of the characters that are in a radius around the Supervisors. Used to determine
     * which character that the Supervisor is supposed to catch.
     * @param characters , a list of all Characters in the game.
     * @return a list containing all the characters that are in the specified radius.
     */
    // Maybe cone instead of radius when checking for the players that are interesting
    private List<Character> getCharactersInRadius(List<Character> characters){
        List<Character> charactersInRadius = new ArrayList<>();
        for(Character c : characters){
            if(canCatch(c)){
                charactersInRadius.add(c);
            }
        }
        return charactersInRadius;
    }

    /**
     * This method will determine the closest character of all character in the radius.
     * @param charactersInRadius , a list containing the characters that are inr adius.
     * @return the character closest to hte PlayableCharacter.
     */
    private Character getClosestCharacter(List<Character> charactersInRadius){ //TODO Checks the first character twice

        Character closestCharacter = new Worker(new Vector3d(0,0,0), new Vector3d(0,0,0), new Vector3d(0,0,0), "ss");
        float closestDistance = 10000;
        for(Character c : charactersInRadius){
            if (!(c == this)){
                float characterToThisCharacterDistance = c.getPosition().distanceTo(this.getPosition());
                if(characterToThisCharacterDistance < closestDistance){
                    closestCharacter = c;
                    closestDistance = characterToThisCharacterDistance;
                }
            }
        }
        return closestCharacter;
    }

    /**
     * Verifies if the character in the radius Character is catchable.
     * @param character , the character to verify if it is in radius.
     * @return true if the character is in the catching radius else false.
     */
    private boolean canCatch(Character character){
        return this.getPosition().isInRadius(character.getPosition(), Config.USE_ABILITY_ACT_DISTANCE);
    }

    /**
     * The actual method that actually catches the Character. Runs all methods that verifies if
     * a character is catchable and then catch the Character. (Misspelled because of the keyword catch in java)
     * @param characters , all Characters in the game.
     */
    public void catsch(List<Character> characters){
        List<Character> charactersInRadius = getCharactersInRadius(characters);
        Character closestCharacter = getClosestCharacter(charactersInRadius);
        closestCharacter.beenCaught();
    }

    @Override
    public void beenCaught() {
        this.setState(new CapturedPlayerState(this));
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }

	/**
	 * Created by Hampus on 2017-03-26.
	 */
	public static class GameObjectInit {
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
					return new Machine(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "machine.basic");
				case "SpotControl":
					Spotlight light = new Spotlight(new Vector3d(x, 30, z), new Vector3d(1, 1, 1), new Vector3d(1, 1, 1), 5, 300, "spotlight.controlboard") ;
					return new SpotlightControlBoard(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "control.basic", light);
				  case "Worker":
					  return new Worker(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "worker.basic");
				  case "Supervisor":
					  return new Supervisor(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "supervisor.basic");
				  case "Saboteur":
					  return new Saboteur(new Vector3d(x, y, z), new Vector3d(scaleX, scaleY, scaleZ), new Vector3d(rotationX, rotationY, rotationZ), "saboteur.basic");
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
}
