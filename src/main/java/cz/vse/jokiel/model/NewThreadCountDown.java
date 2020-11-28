package cz.vse.jokiel.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/*******************************************************************************
 * Třída, která zajišťuje dostání inputu v poslední lokaci hry při přestřelce.
 * 
 * Implementuje interface Runnable - očekává se, že tato třída bude spuštěna pomocí pomocného vlákna.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class NewThreadCountDown implements Runnable
{
    Game game;
    /***************************************************************************
     * Prázdný konstruktor
     *
     */
    public NewThreadCountDown(Game game)
    {
        this.game = game;
    }


    public void run()
    {
        game.getController().zbran.setOnMouseClicked(event -> {});

    }
    
    
}
