package com.mygdx.loader;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class AssetLoader {
    private SpriteSheetData spriteData;
    private String pictureFileName;
    private String audioFolder;
    private HashMap<String, Animation> animations;
    private HashMap<String, Sound> sounds;
    private HashMap<String, TextureRegion> textures;

    public AssetLoader(String jsonFileName, String pictureFileName, String audioFolder) {
        this.pictureFileName = pictureFileName;
        this.audioFolder = audioFolder;
        spriteData = new SpriteSheetData(jsonFileName);
        animations = new HashMap<>();
        sounds = new HashMap<>();
        textures = new HashMap<>();
    }

    public Animation getAnimation(String name) {
        return animations.get(name);
    }
    public Sound getSound(String name) {return sounds.get(name);}
    public TextureRegion getTextureRegion(String name) {return textures.get(name);}

    public void load() {
        loadAnimations();
        loadSounds();
    }

    private void loadSounds() {
        sounds.put("click", Gdx.audio.newSound(Gdx.files.internal(audioFolder + "click.mp3")));
    }

    private void loadAnimations() {
        Collection<SpriteInfo> sprites = spriteData.Load();
        Texture sheet = new Texture(Gdx.files.internal(pictureFileName));

        for( SpriteInfo sprite : sprites) {
            ArrayList<TextureRegion> regionList = new ArrayList<>();
            float frameTime = 0.1f;
            boolean first = true;
            for ( CelInfo celInfo : sprite.cells) {
                if ( first ) {
                    frameTime = celInfo.duration / 1000;
                    first = false;
                }
                TextureRegion newRegion = new TextureRegion(sheet, celInfo.sourceX, celInfo.sourceY, celInfo.width, celInfo.height);
                regionList.add(newRegion);
            }

            TextureRegion[] regionArray = new TextureRegion[regionList.size()];
            regionArray = regionList.toArray(regionArray);
            Animation animation = new Animation(frameTime,  regionArray );
            animations.put(sprite.name, animation);

            if ( regionList.size()==1) {
                textures.put(sprite.name, regionList.get(0));
            }
        }
    }
}
