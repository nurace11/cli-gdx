package com.nurace11.cligdx.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseScreen implements Screen, InputProcessor {
    protected Stage mainStage;
    protected Stage uiStage;

    static boolean pause;

    public BaseScreen(){
        mainStage = new Stage();
        uiStage = new Stage();

        pause = false;

        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        initialize();
    }

    public abstract void initialize();
    public abstract void update(float dt);

    @Override
    public void render(float delta) {
        uiStage.act(delta);

        if(!pause){
            mainStage.act(delta);
            update(delta);
        }

        Gdx.gl.glClearColor(0.0f,0.00f,0.00f,0.1f);//
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        mainStage.draw();
        uiStage.draw();
    }

    public static void setPaused(){
        pause = !pause;
    }

    @Override
    public void show() {
        System.out.println("BaseScreen show()");
        System.out.println(Gdx.input.getInputProcessor());
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
    }

    @Override
    public void resize(int width, int height) {
        mainStage.getViewport().update(width, height);
        mainStage.getCamera().update();
        mainStage.getBatch().setProjectionMatrix(mainStage.getCamera().combined);

        uiStage.getViewport().update(width, height);
        uiStage.getCamera().update();
        uiStage.getBatch().setProjectionMatrix(uiStage.getCamera().combined);
        System.out.println("resized");
    }

    @Override
    public void pause() {
        System.out.println("BaseScreen pause()");
        pause = true;
    }

    @Override
    public void resume() {
        System.out.println("BaseScreen resume()");
        pause = false;
    }

    @Override
    public void hide() {
        System.out.println("BaseScreen hide()");
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }

    @Override
    public void dispose() {
        System.out.println("BaseScreen dispose()");
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

