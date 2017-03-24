package com.projectdgdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hampus on 2017-03-24.
 */
public class FileLoader {
    private ArrayList<String> words = new ArrayList<>();
    public FileLoader(String fileName) {
        FileHandle handle = Gdx.files.internal(fileName);
        String text = handle.readString();
        String wordsArray[] = text.split("\\r?\\n");
        for(String word : wordsArray) {
            words.add(word);
        }

    }

    public Iterator<String> getIterator() {
        return words.iterator();
    }
}
