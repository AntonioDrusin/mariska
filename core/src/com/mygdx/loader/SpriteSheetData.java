package com.mygdx.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Collection;
import java.util.HashMap;

public class SpriteSheetData {
    private JsonValue json;
    private String jsonFileName;

    public SpriteSheetData(String jsonFileName) {

        this.jsonFileName = jsonFileName;
    }

    public Collection<SpriteInfo> Load() {
        json = new JsonReader().parse(new FileHandle(jsonFileName));
        json = json.get("frames");

        HashMap<String, SpriteInfo> sprites = new HashMap<>();

        for( JsonValue value : json.iterator()){
            String name = value.name().split("(\\.|\\s+)")[0];
            SpriteInfo sprite;
            if ( sprites.containsKey(name) ) {
                sprite = sprites.get(name);
            }
            else {
                sprite = new SpriteInfo();
                sprite.name =  name;
                sprites.put(name, sprite);
            }
            CelInfo celInfo = new CelInfo();
            celInfo.height = value.get("sourceSize").get("h").asInt();
            celInfo.width = value.get("sourceSize").get("w").asInt();
            celInfo.sourceX = value.get("frame").get("x").asInt();
            celInfo.sourceY = value.get("frame").get("y").asInt();
            celInfo.duration = value.get("duration").asFloat();
            sprite.cells.add(celInfo);
        }
        return sprites.values();
    }
}

