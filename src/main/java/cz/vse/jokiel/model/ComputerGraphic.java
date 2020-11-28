package cz.vse.jokiel.model;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Scanner;

/*******************************************************************************
 * Třída Computer představuje Počítač, který hráč může použít k vyhledání pachatele a k dekódování tajné adresy.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class ComputerGraphic
{
    GamePlan plan;
    Game game;
    Stage computer;
    String text = "";
    TextArea textOutput;
    TextField textInput;
    VBox vBox;


    /**
     * Computer Konstruktor
     *
     * @param plan plán hry
     */
    public ComputerGraphic(GamePlan plan, Game game)
    {
        this.plan = plan;
        this.game = game;
    }
    
    /**
     * V metodě start si hráč vybere jaký program chce použít
     * 1 - Policejní databázi
     * 2 - Dekodér
     *
     * @return String "Done." když je výběr hotov
     */
    public String start()
    {
        computer = new Stage();
        computer.initModality(Modality.APPLICATION_MODAL);
        computer.setTitle("Computer");
        vBox = new VBox();
        textInput = new TextField();
        textOutput = new TextArea();
        textOutput.setPrefColumnCount(50);
        textOutput.setPrefRowCount(20);
        textOutput.setEditable(false);
        vBox.getChildren().add(textOutput);
        vBox.getChildren().add(textInput);
        Scene scene = new Scene(vBox);
        computer.setScene(scene);
        computer.show();

        Thread taskThread = new Thread(() -> {
            try{Thread.sleep(500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            Platform.runLater(() ->textOutput.appendText("Programs:" + "\n"));
            try{Thread.sleep(500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            Platform.runLater(() ->textOutput.appendText("(1)  Police Database"+ "\n"));
            try{Thread.sleep(500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            Platform.runLater(() -> textOutput.appendText("(2)  Address decoder"+ "\n"));
            try{Thread.sleep(500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            Platform.runLater(() -> textOutput.appendText("Which program do you want to use ? (Use appropriate numbers or \"exit\")\n\n"));

    textInput.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
            text = textInput.getText();
            textInput.setText("");
            switcheroo(text);}
    });
        });
        taskThread.start();

        return "Done";
    }
    
    private void switcheroo(String text)
    {

        switch (text){
            case "1":policeDatabaseStart();break;
            case "2":addressDecoderStart();break;
        }

       // computer.close();

    }
    
    /**
     * Metoda představuje program "Police Database", ve kterém hráč může vyhledávat jména
     *
     * @return String "Done." když je vyhledávání dokončeno 
     */
    public void policeDatabaseStart()
    {
        Thread taskThread = new Thread(() -> {
    try{Thread.sleep(1000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
    Platform.runLater(() -> textOutput.appendText("Police Database v1.0"+ "\n"));
    try{Thread.sleep(1000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
    Platform.runLater(() ->textOutput.appendText("Status: Loading"+ "\n"));
    try{Thread.sleep(1500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
    Platform.runLater(() -> textOutput.appendText("Status: Connected\n\n"));
    Platform.runLater(() -> textOutput.appendText("Name of suspect: (or \"exit\")\n"));

        });
        taskThread.start();


                textInput.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        text = textInput.getText();
                        textInput.setText("");
                        policeDatabase(text);
                    }
                });

    }

    private void policeDatabase(String text)
    {
        if(text.equals("Juan Alvarez"))
        {
            Thread taskThread = new Thread(() -> {
            try{Thread.sleep(1500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            Platform.runLater(() -> {
                        textOutput.appendText("Name: Juan Alvarez" + "\n");
                        textOutput.appendText("Date of birth: 16.08.2010" + "\n");
                        textOutput.appendText("Known aliases: ---" + "\n");
                        textOutput.appendText("Address: ---" + "\n");
                        textOutput.appendText("Relatives: ---\n");
                        textOutput.appendText("Last bank statement:" + "\n");
                        textOutput.appendText("Jim's storages - Ocean drive 308 - storage 619----------" + "\n");
                        textOutput.appendText("Jul 15----------------------------------------------150đ" + "\n");
                        textOutput.appendText("--------------------------------------------------------" + "\n");
                        textOutput.appendText("Total-----------------------------------------------150đ\n");
                        textOutput.appendText("Name of suspect: (or \"exit\")\n");
                    });
            });
            taskThread.start();
            plan.getArea("Doky").setAccessibility(true);
            game.getController().update();
        }
        else if(text.equals("John Meyers"))
        {
            textOutput.appendText("              XXXXXX\n"
                    +"       XXX             XXX\n"
                    +"      XX                   XX\n"
                    +"     XX                     XX\n"
                    +"    XX                       XX\n"
                    +"   XX                         XX\n"
                    +"   X                             X\n"
                    +"   X       EASTER           X\n"
                    +"   X                             X\n"
                    +"   X                             X\n"
                    +"   XX                          X\n"
                    +"    X                          X\n"
                    +"    XX                        XX\n"
                    +"     XXX                    XX\n"
                    +"       XXXXXXXXXXXX\n\n");
            textOutput.appendText("Yay you found it."+"\n");
            textOutput.appendText("Name of suspect: (or \"exit\")\n");
        }
        else if(text.equals("exit"))
        {
            game.getController().update();
            computer.close();
        }
        else
        {
            textOutput.appendText("Unfortunetely this name is not in the database."+ "\n");
        }
    }
    
    /**
     * Metoda představuje program "Address decoder", ve kterém hráč může dekódovat tajný vzkaz.
     *
     * @return String "Done." když je dekódování dokončeno
     */
    public void addressDecoderStart()
    {

        textOutput.appendText("Address Decoder v1.0"+ "\n");
        Thread taskThread = new Thread(() -> {
        try{Thread.sleep(1000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            Platform.runLater(() -> {textOutput.appendText("Enter encrypted data: (or \"exit\")"+ "\n");
            });
        });
        taskThread.start();

        textInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                text = textInput.getText();
                textInput.setText("");
                addressDecoder(text);
            }
        });
            

    }

    private void addressDecoder(String text)
    {
        if(text.equals("9badb16da5680244d928e1bd64e9157bbe063721158f0193ed29687ae565504f"))
        {
            try{Thread.sleep(1500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            plan.getArea("Skrys").setAccessibility(true);
            textOutput.appendText( "Result:\nWillow st 256"+ "\n");
            textOutput.appendText("Enter encrypted data: (or \"exit\")"+ "\n");
            game.getController().update();
        }
        else if(text.equals("exit"))
        {

            computer.close();
        }
        else
        {
             try{Thread.sleep(1500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            textOutput.appendText( "No result."+ "\n");
            textOutput.appendText("Enter encrypted data: (or \"exit\")"+ "\n");
        }
    }


}
