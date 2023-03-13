package com.nurace11.cligdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.nurace11.cligdx.screen.BaseScreen;

abstract public class BaseGame extends Game {
    private static BaseGame game;

    public BaseGame() {
        game = this;
    }

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor( im );
    }

    public static void setActiveScreen(BaseScreen screen){
        game.setScreen(screen);
    };

    @Override
    public void dispose() {
        super.dispose();
    }
}
