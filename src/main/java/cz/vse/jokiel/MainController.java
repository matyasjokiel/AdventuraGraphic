package cz.vse.jokiel;


import cz.vse.jokiel.main.Start;
import cz.vse.jokiel.model.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class MainController {


    public TextArea textOutput;
    public TextField textInput;
    public String input;
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
    public VBox use;
    public VBox useBox;
    public Label zbran;

    public Button newGame;
    public Button help;
    public Button aboutGame;


    public void init(IGame game) {
        this.game = game;
        update();

    }

    public void update() {
        String location = getCurrentArea().getName();
        locationName.setText(location);

        String description = getCurrentArea().getFullDescription();
        locationDescription.setText(description);

        npcsBox.setVisible(false);
        backpackBox.setVisible(false);
        itemsBox.setVisible(false);
        searchBox.setVisible(false);
        useBox.setVisible(false);

        updateExits();
        updateItems();
        updateNpcs();
        updateBackpack();
        updateUse();
        toolBar();
        help();
        aboutGame();

        if(game.isGameOver())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Konec hry");
            alert.setHeaderText(null);
            alert.setContentText("Konec hry. Díky, že jste zahráli.");

            alert.showAndWait();
            System.exit(0);
        }
    }

    private void aboutGame() {
        aboutGame.setOnMouseClicked(event -> {
            Stage oHre = new Stage();
            oHre.setTitle("Det. Meyers - o hře");
            oHre.initModality(Modality.APPLICATION_MODAL);
            WebView browser = new WebView();

            WebEngine webEngine = browser.getEngine();
            webEngine.load(getClass().getResource("/aboutGame.html").toString());
            Scene scene = new Scene(browser);
            oHre.setScene(scene);
            oHre.show();

        });
    }

    private void help() {
        help.setOnMouseClicked(event -> {
            executeCommand("napoveda");
        });
    }

    private void toolBar() {

        newGame.setOnMouseClicked(event -> {
            Start.primaryStage.close();
            try {
                new Start().start( new Stage() );
            } catch (Exception e) {
            }
        }); ;
    }

    public void updateUse() {
        use.getChildren().clear();

        switch (getCurrentArea().getName()){
            case "Policejni_stanice":
                Label computer = new Label("Pocitac");
                computer.setCursor(Cursor.HAND);
                computer.setOnMouseClicked(event -> executeCommand("pouzijGraphic Pocitac"));
                use.getChildren().add(computer);

                if(game.getGamePlan().getBackpack().isInStorage("Klic_ke_skrince")) {
                    Label klic = new Label("Klic_ke_skrince");
                    klic.setCursor(Cursor.HAND);
                    klic.setOnMouseClicked(event -> executeCommand("pouzijGraphic Klic_ke_skrince"));
                    use.getChildren().add(klic);
                }
                Label automat = new Label("Automat_na_kafe");
                automat.setCursor(Cursor.HAND);
                automat.setOnMouseClicked(event -> executeCommand("pouzijGraphic Automat_na_kafe"));
                use.getChildren().add(automat);
                if(game.getGamePlan().getBackpack().isInStorage("Kelimek_s_kafem")) {
                    Label plnykafe = new Label("Kelimek_s_kafem");
                    plnykafe.setCursor(Cursor.HAND);
                    plnykafe.setOnMouseClicked(event -> executeCommand("pouzijGraphic Kelimek_s_kafem"));
                    use.getChildren().add(plnykafe);
                }

                useBox.setVisible(true);
                break;
            case "Sklad_619":
                if(game.getGamePlan().getBackpack().isInStorage("Kopie_cipu")){
                    Label cip = new Label("Kopie_cipu");
                    cip.setCursor(Cursor.HAND);
                    cip.setOnMouseClicked(event -> executeCommand("pouzijGraphic Kopie_cipu"));
                    use.getChildren().add(cip);
                    useBox.setVisible(true);
                    break;
                }
        }
        if(game.getGamePlan().getBackpack().isInStorage("Zbran")){
            zbran = new Label("Zbran");
            zbran.setCursor(Cursor.HAND);
            zbran.setOnMouseClicked(event -> executeCommand("pouzijGraphic Zbran"));
            use.getChildren().add(zbran);
            useBox.setVisible(true);

        }
        if(game.getGamePlan().getBackpack().isInStorage("Pouta")){
            Label pouta = new Label("Pouta");
            pouta.setCursor(Cursor.HAND);
            pouta.setOnMouseClicked(event -> executeCommand("pouzijGraphic Pouta"));
            use.getChildren().add(pouta);
            useBox.setVisible(true);

        }

    }

    private void updateBackpack() {
        Set<Item> itemSet = game.getGamePlan().getBackpack().getItemSet();
        backpack.getChildren().clear();

        for(Item item : itemSet) {
            String itemName = item.getName();
            Label itemLabel = new Label(itemName);
            itemLabel.setCursor(Cursor.HAND);

            InputStream stream = getClass().getClassLoader().getResourceAsStream(itemName + ".jpg");
            Image img = new Image(stream);
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(60);
            imageView.setFitHeight(40);
            itemLabel.setGraphic(imageView);

            itemLabel.setOnMouseClicked(event -> {
                executeCommand("odhod " + itemName);
            });

            backpack.getChildren().add(itemLabel);
            backpackBox.setVisible(true);

        }
        updateItems();
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
                String exitName;
                Label exitLabel;
                InputStream stream;
                if(area.getName().equals("Sklad_619")) {
                    exitName = "Sklad_XXX";
                    exitLabel = new Label(exitName);
                    exitLabel.setCursor(Cursor.HAND);
                    stream = getClass().getClassLoader().getResourceAsStream("Sklad_619" + ".jpg");
                    Image img = new Image(stream);
                    ImageView imageView = new ImageView(img);
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(40);
                    exitLabel.setGraphic(imageView);

                    exitLabel.setOnMouseClicked(event -> {
                        TextInputDialog dialog = new TextInputDialog("");
                        dialog.setTitle("Číslo skladu");
                        dialog.setHeaderText(null);
                        dialog.setGraphic(null);
                        dialog.setContentText("Zadej číslo skladu:");

                        dialog.showAndWait().ifPresent(name ->{
                            if(name.equals("619")){
                                game.getGamePlan().setCurrentArea(game.getGamePlan().getArea("Sklad_619"));
                                update();
                            }
                            else{
                                setTextOutput("Bohužel, zadal jsi špatné číslo skladu.");
                            }
                        });
                    });

                }
                else {
                    exitName = area.getName();
                    exitLabel= new Label(exitName);
                    exitLabel.setCursor(Cursor.HAND);
                    stream = getClass().getClassLoader().getResourceAsStream(exitName + ".jpg");
                    Image img = new Image(stream);
                    ImageView imageView = new ImageView(img);
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(40);
                    exitLabel.setGraphic(imageView);

                    exitLabel.setOnMouseClicked(event -> {
                        executeCommand("jedGraphic " + exitName);
                    });
                }

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
                    InputStream stream = getClass().getClassLoader().getResourceAsStream(itemName + ".jpg");
                    Image img = new Image(stream);
                    ImageView imageView = new ImageView(img);
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(40);
                    itemLabel.setGraphic(imageView);

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
                        getCurrentArea().addItem(item);

                    }

                    update();
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
            input = textInput.getText();
            executeCommand(input);
            textInput.setText("");
        }
    }

    public void setTextOutput(String text){
        textOutput.appendText(text + "\n");
        update();
    }



}
