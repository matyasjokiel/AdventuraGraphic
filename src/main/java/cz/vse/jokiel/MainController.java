package cz.vse.jokiel;

import com.sun.deploy.panel.RuleSetViewerDialog;
import cz.vse.jokiel.model.*;
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
import java.util.Set;

public class MainController {


    public TextArea textOutput;
    public TextField textInput;
    private IGame game;

    public Label locationName;
    public Label locationDescription;

    public VBox exits;
    public VBox items;
    public VBox itemsBox;
    public VBox npcs;
    public VBox npcsBox;
    public VBox backpack;
    public VBox backpackBox;
    public VBox search;
    public VBox searchBox;


    public void init(IGame game) {
        this.game = game;
        update();

    }

    private void update() {
        String location = getCurrentArea().getName();
        locationName.setText(location);

        String description = getCurrentArea().getFullDescription();
        locationDescription.setText(description);


        npcsBox.setVisible(false);
        backpackBox.setVisible(false);
        itemsBox.setVisible(false);
        searchBox.setVisible(false);

        updateExits();
        updateItems();
        updateNpcs();
        updateBackpack();
    }

    private void updateBackpack() {
        Set<Item> itemSet = game.getGamePlan().getBackpack().getItemSet();
        backpack.getChildren().clear();

        for(Item item : itemSet) {
            String itemName = item.getName();
            Label itemLabel = new Label(itemName);
            itemLabel.setCursor(Cursor.HAND);

            itemLabel.setOnMouseClicked(event -> {
                executeCommand("odhod " + itemName);
            });

            backpack.getChildren().add(itemLabel);
            backpackBox.setVisible(true);
        }
    }

    private void updateNpcs() {
        Set<NPC> npcList = getCurrentArea().getNpcsInArea();
        npcs.getChildren().clear();

        for(NPC npc : npcList)
        {
            if(npc.isAccessible()) {
                String npcName = npc.getName();
                Label npcLabel = new Label(npcName);
                npcLabel.setCursor(Cursor.HAND);

                npcLabel.setOnMouseClicked(event -> {
                    executeCommand("promluv_s " + npcName);
                });

                npcs.getChildren().add(npcLabel);
                npcsBox.setVisible(true);
            }
        }
    }

    private void updateExits() {
        Set<Area> exitList = getCurrentArea().getExitsSet();
        exits.getChildren().clear();

        for (Area area : exitList) {
            if(area.isAccessible()) {

                String exitName = area.getName();
                Label exitLabel = new Label(exitName);
                exitLabel.setCursor(Cursor.HAND);

                InputStream stream = getClass().getClassLoader().getResourceAsStream(exitName + ".jpg");
                Image img = new Image(stream);
                ImageView imageView = new ImageView(img);
                imageView.setFitWidth(60);
                imageView.setFitHeight(40);
                exitLabel.setGraphic(imageView);

                exitLabel.setOnMouseClicked(event -> {
                    executeCommand("jed " + exitName);
                });

                exits.getChildren().add(exitLabel);
            }
        }



    }

    private void updateItems() {
        Set<Storage> storageList = getCurrentArea().getStorages();
        items.getChildren().clear();
        search.getChildren().clear();

        for(Storage storage : storageList) {
            //hlavni uloziste
            if(storage.getName().equals("Hlavni")) {
                Set<Item> hlavniItems= storage.getItemSet();
                for(Item item : hlavniItems) {
                    String itemName = item.getName();
                    Label itemLabel = new Label(itemName);

                    if(item.isAccesible()) {
                        itemLabel.setCursor(Cursor.HAND);

                        itemLabel.setOnMouseClicked(event -> {
                            executeCommand("vezmi "+itemName);
                        });
                    }
                    items.getChildren().add(itemLabel);
                    itemsBox.setVisible(true);
                }
            } else {
                // vedlejsi storages
                if(storage.isAccessible()){
                String storageName = storage.getName();
                Label storageLabel = new Label(storageName);

                storageLabel.setCursor(Cursor.HAND);
                storageLabel.setOnMouseClicked(event -> {
                    storage.setAccessibility(false);


                    Set<Item> itemsInStorage = storage.getItemSet();

                    for(Item item : itemsInStorage){
                        String itemName = item.getName();
                        Label itemLabel = new Label(itemName);

                        if(item.isAccesible()) {
                            itemLabel.setCursor(Cursor.HAND);

                            itemLabel.setOnMouseClicked(event2 -> {
                                executeCommand("vezmi "+itemName);
                            });
                        }
                        getCurrentArea().removeItemFromAllStorages(item.getName());
                        getCurrentArea().addItem(item);
                        update();
                    }
                });

                search.getChildren().add(storageLabel);
                searchBox.setVisible(true);
                }
            }
        }

    }


    private Area getCurrentArea() {
        return game.getGamePlan().getCurrentArea();
    }

    private void executeCommand(String command) {
        String result = game.processCommand(command);
        textOutput.appendText(result + "\n\n");
        update();
    }

    public void onInputKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            executeCommand(textInput.getText());
            textInput.setText("");
        }
    }


}
