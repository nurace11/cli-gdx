package com.nurace11.cligdx.screen;

import com.kotcrab.vis.ui.VisUI;
import com.nurace11.cligdx.GameUI;

public class MyScreen extends BaseScreen {
    GameUI gameUI;

    @Override
    public void initialize() {
        gameUI = new GameUI(uiStage);
    }


    // TODO:
    //   + 1. Code refcatoring
    //   + 2. Make tree scrollable
    //   3. Show detailed information of every drone when clicked


    @Override
    public void update(float dt) {
        gameUI.act(dt);
    }
}
