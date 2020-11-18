package cz.vse.jokiel;

import cz.vse.jokiel.model.IGame;
import cz.vse.jokiel.model.Area;
import cz.vse.jokiel.model.Item;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.util.Collection;

public class MainController {


    public TextArea textOutput;
    public TextField textInput;
    private IGame game;

    public Label locationName;
    public Label locationDescription;

    public VBox exits;
    public VBox items;


    public void init(IGame hra) {
        this.game = game;
        update();

    }

    private void update() {
        String location = getAktualniProstor().getName();
        locationName.setText(location);

        String description = getAktualniProstor().getFullDescription();
        locationDescription.setText(description);

        updateExits();
        updateItems();
    }

    private Area getAktualniProstor() {
        return game.getGamePlan().getCurrentArea();
    }

}
