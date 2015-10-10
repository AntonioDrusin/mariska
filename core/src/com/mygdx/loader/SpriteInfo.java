package com.mygdx.loader;
import java.util.ArrayList;
import java.util.List;

public class SpriteInfo {
    public String name;
    public List<CelInfo> cells;

    public SpriteInfo() {
        cells = new ArrayList<>();
    }
}
