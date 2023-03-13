package com.nurace11.cligdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.nurace11.cligdx.actor.ui.DroneTreeLabel;
import com.nurace11.cligdx.actor.ui.TestTree;
import com.nurace11.cligdx.entity.Drone;
import com.nurace11.cligdx.util.HttpRequestSender;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import java.util.Arrays;
import java.util.List;

public class GameUI {
    Label label;
    HttpRequestSender httpRequestSender;

    ScrollableTextArea scrollableTextArea;

    VisTextField visTextField;
    ScrollPane scrollPane;

    TypingLabel typingLabel;

    VisTree<Tree.Node, Drone> visTree;

    VisScrollPane visScrollPane;

    TestTree.TestNode dronesItem;

    ObjectMapper objectMapper;

    public GameUI(Stage uiStage) {
        VisUI.load();

        label = new Label("Amogus", CliGdx.getUiSkin());
        httpRequestSender = new HttpRequestSender();

        float textFieldHeight = label.getStyle().font.getCapHeight() * 2f;

        label.setPosition(0, Gdx.graphics.getHeight() - label.getStyle().font.getCapHeight());

        scrollableTextArea = new ScrollableTextArea("");
        scrollPane = scrollableTextArea.createCompatibleScrollPane();
        scrollPane.setSize(Gdx.graphics.getWidth() * 0.75f, Gdx.graphics.getHeight() / 2f);
        scrollPane.setPosition(0, textFieldHeight);
        scrollPane.setScrollPercentY(0);
        scrollableTextArea.setReadOnly(true);

        visTextField = new VisTextField();
        visTextField.setSize(Gdx.graphics.getWidth(), textFieldHeight);
        visTextField.setPosition(0, 0);
        visTextField.setColor(0.85f,0.8f,0.8f,1);
        visTextField.setFocusBorderEnabled(false);

        typingLabel = new TypingLabel("{COLOR=GREEN}{SPEED=0.5}CursonY: ", CliGdx.getUiSkin());
        typingLabel.setPosition(scrollPane.getWidth(), Gdx.graphics.getHeight() - typingLabel.getStyle().font.getLineHeight());

        visTree = new VisTree<>();
        dronesItem = new TestTree.TestNode(new VisLabel("Drones"));
        visTree.add(dronesItem);
        visTree.setSize(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() - textFieldHeight);
        visTree.setPosition(scrollPane.getWidth(), textFieldHeight);

        System.out.println(visTree.getSelectedNode());

        visScrollPane = new VisScrollPane(visTree);
        visScrollPane.setSize(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() - textFieldHeight);
        visScrollPane.setPosition(scrollPane.getWidth(), textFieldHeight);
        visScrollPane.setOverscroll(false, false);
        visScrollPane.setScrollingDisabled(true, false);

        VisTable table = new VisTable();
        VisScrollPane newVisSP = new VisScrollPane(table);
        newVisSP.setPosition(0, Gdx.graphics.getHeight() / 2f + textFieldHeight);
        newVisSP.setDebug(true);
        newVisSP.setSize(Gdx.graphics.getWidth() * 0.75f, Gdx.graphics.getHeight() / 2f - textFieldHeight);

        uiStage.addActor(visScrollPane);
        uiStage.addActor(visTextField);
        uiStage.addActor(label);
        uiStage.addActor(scrollPane);
        uiStage.addActor(newVisSP);

        visTree.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (drone == null) {
                    drone = (Drone) ((VisTree<?, ?>) actor).getSelectedNode().getValue();
                    if(drone != null) {
                        while (table.getChildren().size > 0) {
                            table.removeActorAt(0,true);
                        }
                        table.add(new VisLabel("" + drone.getId())).row();
                        table.add(drone.getName()).row();
                    }
                } else if (!drone.equals((Drone) ((VisTree<?, ?>) actor).getSelectedNode().getValue())) {
                    drone = (Drone) ((VisTree<?, ?>) actor).getSelectedNode().getValue();
                    if (drone != null) {
                        while (table.getChildren().size > 0) {
                            table.removeActorAt(0,true);
                        }
                        table.add(new VisLabel("" + drone.getId())).row();
                        table.add(drone.getName()).row();
                    }
                }
                newVisSP.scrollTo(0,0,0,0);
            }
        });

        objectMapper = new ObjectMapper();
    }
    static Drone drone;

    public void act(float dt) {
        cliInput();
        debugOutput();
        controls();
    }

    public void cliInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
            || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_ENTER)) {

            switch (visTextField.getText()) {
                case "cls", "clr" -> scrollableTextArea.setText("");
                case "whoami" -> scrollableTextArea.setText(scrollableTextArea.getText() + "ADMIN\n");

                default -> {
                    String text = visTextField.getText();
                    if(text.startsWith("request ")) {
                        String uri = text.substring("request ".length());
                        sendHttpRequest(uri);
                        scrollableTextArea.setText(scrollableTextArea.getText() + "");
                    } else {
                        scrollableTextArea.setText(scrollableTextArea.getText() + "Command not recognized: " + visTextField.getText() + "\n");
                    }
                }
            }

            scrollPane.scrollTo(0,0,0,0);
            scrollableTextArea.setCursorAtTextEnd();
            visTextField.clearText();
        }
    }

    public void sendHttpRequest(String uri) {
        try {
            List<Drone> dronesArray = Arrays.asList(objectMapper.readValue(httpRequestSender.sendHttpRequest(uri), Drone[].class));
            System.out.println(dronesArray);

            if (dronesItem.getChildren().size == 0) {
                for (Drone d : dronesArray) {
                    TestTree.TestNode node = new TestTree.TestNode(new DroneTreeLabel(d));
                    node.setValue(d);
                    dronesItem.add(node);
                }
            } else {
                for (Drone d : dronesArray) {
                    Drone notDuplicateDrone = null;
                    for (TestTree.TestNode node : dronesItem.getChildren()) {
                        notDuplicateDrone = d;
                        if (notDuplicateDrone.equals(node.getValue())) {
                            notDuplicateDrone = null;
                            break;
                        }
                    }
                    if (notDuplicateDrone != null) {
                        dronesItem.add(new TestTree.TestNode(new DroneTreeLabel(notDuplicateDrone)));
                    }
                }
            }
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
    }

    public void controls() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            visTextField.appendText(HttpRequestSender.DEFAULT_API_URI);
        }
    }

    public void debugOutput() {

        if(typingLabel.hasEnded()) {
            typingLabel.skipToTheEnd();
        }

        if (typingLabel.isSkipping()) {
            typingLabel.setText("{RAINBOW}{SPEED=30}CursonY: " + scrollableTextArea.getCursorY());
        }

//        if (typingLabel.isSkipping()) {
////            typingLabel.setText("{EASE=1;0,001;1}{COLOR=GREEN}{SPEED=0.75}CursonY: " + scrollableTextArea.getCursorY());
//            typingLabel.setText("{RAINBOW}{SPEED=faster}CursonY: " + scrollableTextArea.getCursorY());
////            typingLabel.skipToTheEnd();
//        }
//
//        if(typingLabel.hasEnded()) {
//            typingLabel.skipToTheEnd();
//        }
    }
}
