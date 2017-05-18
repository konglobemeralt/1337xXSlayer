package com.projectdgdx.game.utils;

import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.io.File;

/**
 * Created by Hampus on 2017-05-19.
 */
public class TextFileLoader extends AsynchronousAssetLoader<File, TextFileLoader.TextParameter> {

    private File file;

    public TextFileLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextParameter parameter) {
        this.file = file.file();
    }

    @Override
    public File loadSync(AssetManager manager, String fileName, FileHandle file, TextParameter parameter) {
        return file.file();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TextParameter parameter) {
        return null;
    }

    static public class TextParameter extends AssetLoaderParameters<File> {
    }
}
