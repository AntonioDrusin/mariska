package com.mygdx.game.systems;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.components.ControllerComponent;
import com.mygdx.game.systems.support.IUIStage;
import com.mygdx.loader.AssetLoader;


public class TouchpadSystem extends IteratingSystem {

    private Stage stage;
    private Skin touchpadSkin;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Touchpad touchpad;
    private AssetLoader loader;

    public TouchpadSystem(AssetLoader loader, IUIStage stage) {
        super(Family.all(ControllerComponent.class).get());
        this.loader = loader;
        this.stage = stage.getStage();

        createTouchpad();
    }

    private void createTouchpad() {
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", loader.getTextureRegion("TouchBackground"));

        //Set knob image
        touchpadSkin.add("touchKnob", loader.getTextureRegion("TouchKnob"));
        //create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        Drawable touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 180, 180);

        stage.addActor(touchpad);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

}
