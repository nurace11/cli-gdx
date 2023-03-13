package com.nurace11.cligdx.actor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.kotcrab.vis.ui.widget.VisTree;
import com.nurace11.cligdx.entity.Drone;

public class TestTree extends VisTree<TestTree.TestNode, Drone> {


    public static class TestNode extends Tree.Node<TestNode, Drone, Actor> {
        public TestNode(Actor actor) {
            super(actor);
        }
    }
}
