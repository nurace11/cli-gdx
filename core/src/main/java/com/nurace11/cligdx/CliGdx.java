package com.nurace11.cligdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nurace11.cligdx.screen.MyScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class CliGdx extends BaseGame {

    private static Skin uiSkin;

    @Override
    public void create() {
        super.create();
        uiSkin = new Skin(Gdx.files.internal("stuff/uiskin.json"));
        setScreen(new MyScreen());
    }

    public static Skin getUiSkin() {
        return uiSkin;
    }
}
