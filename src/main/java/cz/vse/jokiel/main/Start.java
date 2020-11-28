package cz.vse.jokiel.main;

import cz.vse.jokiel.MainController;
import cz.vse.jokiel.model.Game;
import cz.vse.jokiel.model.IGame;
import cz.vse.jokiel.textui.TextUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


/**
 * Hlavní třída určená pro spuštění hry. Obsahuje pouze statickou metodu
 * {@linkplain #main(String[]) main}, která vytvoří instance logiky hry
 * a uživatelského rozhraní, propojí je a zahájí hru.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public class Start extends Application
{

    public static Stage primaryStage;
    /**
     *
     * Metoda pro spuštění celé aplikace.
     *
     * @param args parametry aplikace z příkazového řádku
     */
    public static void main(String[] args)
    {
        List<String> flags = Arrays.asList(args);

        if(flags.contains("text")) {
            System.out.println(
                    " _____       _       __  __                          \n"
                            +"|  __ \\     | |     |  \\/  |                         \n"
                            +"| |  | | ___| |_    | \\  / | ___ _   _  ___ _ __ ___ \n"
                            +"| |  | |/ _ \\ __|   | |\\/| |/ _ \\ | | |/ _ \\ '__/ __|\n"
                            +"| |__| |  __/ |_ _  | |  | |  __/ |_| |  __/ |  \\__ \\\n"
                            +"|_____/ \\___|\\__(_) |_|  |_|\\___|\\__, |\\___|_|  |___/\n"
                            +"                                  __/ |              \n"
                            +"Author: Matyáš Jokiel            |___/               \n"
                            +"\nChoose difficulty:\n(1) Easy\n(2) Medium\n(3) Hard\n");

            String line = readLine();

            while(!(line.equals("1") || line.equals("2") || line.equals("3")))
            {
                System.out.println("Choose difficulty:\n(1) Easy\n(2) Medium\n(3) Hard\n");
                line = readLine();
            }
            IGame game = null;
            switch(line)
            {
                case "1": game = new Game(3);break;
                case "2": game = new Game(2);break;
                case "3": game = new Game(1);break;
                default: break;
            }

            TextUI ui = new TextUI(game);
            ui.play();
        } else {
            launch();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        int difficulty = 2;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Det. Meyers");
        alert.setHeaderText(null);
        alert.setContentText("Vyberte obtížnost:");

        ButtonType button1= new ButtonType("Lehká");
        ButtonType button2 = new ButtonType("Normální");
        ButtonType button3 = new ButtonType("Těžká");
        ButtonType buttonTypeCancel = new ButtonType("Zrušit", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(button1, button2, button3, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == button1){
            difficulty = 3;
        } else if (result.get() == button2) {
            difficulty = 2;
        } else if (result.get() == button3) {
            difficulty = 1;
        } else {
            System.exit(0);
        }


        this.primaryStage = primaryStage;

        primaryStage.setTitle("Det. Meyers");

        FXMLLoader loader = new FXMLLoader();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("scene.fxml");
        Parent root = loader.load(stream);

        Scene scene = new Scene(root, 1600,800);
        primaryStage.setScene(scene);

        MainController controller = loader.getController();
        IGame game = new Game(difficulty, controller);
        controller.init(game);
        primaryStage.show();
    }



    /**
     * Pomocná metoda pro čtení příkazů z konzole.
     *
     * @return řádek textu z konzole
     */
    private static String readLine()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }

}
