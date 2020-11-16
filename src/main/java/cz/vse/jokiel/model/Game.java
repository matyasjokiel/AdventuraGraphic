package cz.vse.jokiel.model;

import java.util.*;
/**
 * Hlavní třída logiky aplikace. Třída vytváří instanci třídy {@link GamePlan},
 * která inicializuje lokace hry, a vytváří seznam platných příkazů a instance
 * tříd provádějících jednotlivé příkazy.
 *
 * Během hry třída vypisuje uvítací a ukončovací texty a vyhodnocuje jednotlivé
 * příkazy zadané uživatelem.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class Game implements IGame
{
    private ListOfCommands listOfCommands;
    private GamePlan gamePlan;
    private boolean gameOver;
    private int difficulty;

    /**
     * Konstruktor třídy. Vytvoří hru, inicializuje herní plán udržující
     * aktuální stav hry, seznam platných příkazů a také nastavuje obtížnost hry(Kolik vteřin má hráč v poslední lokaci).
     */
    public Game()
    {
        gameOver = false;
        gamePlan = new GamePlan();
        listOfCommands = new ListOfCommands();

        listOfCommands.addCommand(new CommandHelp(listOfCommands, gamePlan));
        listOfCommands.addCommand(new CommandTerminate(this));
        listOfCommands.addCommand(new CommandMove(gamePlan, this));
        listOfCommands.addCommand(new CommandTake(gamePlan));
        listOfCommands.addCommand(new CommandThrowAway(gamePlan));
        listOfCommands.addCommand(new CommandInventory(gamePlan));
        listOfCommands.addCommand(new CommandSearch(gamePlan));
        listOfCommands.addCommand(new CommandTalk(gamePlan));
        listOfCommands.addCommand(new CommandUse(gamePlan, this));
        
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
 
        switch(line)
        {
            case "1": difficulty = 3;break;
            case "2": difficulty = 2;break;
            case "3": difficulty = 1;break;
            default: break;
        }
    }

    /**
     * Pomocná metoda pro čtení příkazů z konzole.
     *
     * @return řádek textu z konzole
     */
    private String readLine()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }
    
    /**
     * Metoda vrací obtížnost hry.
     *
     * @return obtížnost hry
     */
    public int getDifficulty()
    {
        return difficulty;
    }
    
    /**
     * @return Prolog
     */
    @Override
    public String getPrologue()
    {
        return "6:00 am, neznámé město\n"
                +  "Právě jsi se probudil a vypnul budík\n"
                +  "Na nočním stolku je policejní odznak.\n"
                +  "John Meyers - 0564 - detektiv 1. třída\n\n\n"
                + "Napiš 'napoveda', pokud si nevíš rady, jak hrát dál.\n"
                + "\n"
                + gamePlan.getCurrentArea().getFullDescription();
    }

    /**
     * @return Epilog
     */
    @Override
    public String getEpilogue()
    {
        return "KONEC - Díky, že jste si zahráli.";
    }

    /**
     * @return Jestli už je konec hry.
     */
    @Override
    public boolean isGameOver()
    {
        return gameOver;
    }

    
    /**
     * Metoda zpracovává příkazy.
     *
     * @param line příkaz
     * @return výsledek příkazu
     */
    @Override
    public String processCommand(String line)
    {
        String[] words = line.split("[ \t]+");

        String cmdName = words[0];
        String[] cmdParameters = new String[words.length - 1];

        for (int i = 0; i < cmdParameters.length; i++) {
            cmdParameters[i] = words[i + 1];
        }

        String result = null;
        if (listOfCommands.checkCommand(cmdName)) {
            ICommand command = listOfCommands.getCommand(cmdName);
            result = command.process(cmdParameters);
        } else {
            result = "Tento příkaz neznám.";
        }

        return result;
    }

    /**
     * @return Plán hry
     */
    @Override
    public GamePlan getGamePlan()
    {
        return gamePlan;
    }

    /**
     * Metoda nastaví příznak indikující, že nastal konec hry. Metodu
     * využívá třída {@link CommandTerminate}, mohou ji ale použít
     * i další implementace rozhraní {@link ICommand}.
     *
     * @param gameOver příznak indikující, zda hra již skončila
     */
    void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

}