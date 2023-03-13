package com.nurace11.cligdx.actor.ui;

import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.*;

public class TextAreaAndScroll extends VisWindow {
    public TextAreaAndScroll() {
        super("textare / scrollpane");
        TableUtils.setSpacingDefaults(this);
        columnDefaults(0).left();

        ScrollableTextArea textArea = new ScrollableTextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec iaculis odio.\nFun thing: This text area supports scrolling.");

        // ---

        VisTable table = new VisTable();

        for (int i = 0; i < 20; i++)
            table.add(new VisLabel("Label #" + (i + 1))).expand().fill().row();

        VisScrollPane scrollPane = new VisScrollPane(table);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);

        // ---

        add(textArea.createCompatibleScrollPane()).growX().height(100).row();
        add(scrollPane).spaceTop(8).growX().row();


        setResizable(true);
        setSize(180, 380);
        setPosition(28, 300);
    }
}
