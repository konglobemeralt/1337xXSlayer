package com.projectdgdx.game.utils;

/**
 * Created by Hampus on 2017-03-26.
 */
public class GameObject {
    String className;
    public GameObject(String className) {
        this.className = className;
    }
    public void init() {
        System.out.println(className);
    }
}
