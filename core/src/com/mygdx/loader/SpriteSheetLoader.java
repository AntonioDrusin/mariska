package com.mygdx.loader;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SpriteSheetLoader {
    private SpriteSheetData spriteData;
    private String pictureFileName;
    private HashMap<String, Animation> animations;

    public SpriteSheetLoader(String jsonFileName, String pictureFileName) {
        this.pictureFileName = pictureFileName;
        spriteData = new SpriteSheetData(jsonFileName);
        animations = new HashMap<>();
    }

    public Animation getAnimation(String name) {
        return animations.get(name);
    }

    public void Load() {
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
        }

    }
}
