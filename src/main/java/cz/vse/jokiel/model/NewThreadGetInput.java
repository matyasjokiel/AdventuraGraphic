package cz.vse.jokiel.model;

import java.util.*;
import java.io.*;

/*******************************************************************************
 * Třída, která zajišťuje dostání inputu v poslední lokaci hry při přestřelce.
 * 
 * Implementuje interface Runnable - očekává se, že tato třída bude spuštěna pomocí pomocného vlákna.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class NewThreadGetInput implements Runnable
{
    
    /***************************************************************************
     * Prázdný konstruktor
     * 
     */
    public NewThreadGetInput()
    {
        //Prázdný konstruktor
    }

    /***************************************************************************
     * Metoda se používá v závěrečné lokaci, při přestřelce. Dostává input z klávesnice. Hráč má použít příkaz "pouzij Zbran".
     * Jedině poté se metoda ukončí. Jinak se musí ukončit z hlavního vlákna po uplynutí časového intervalu.
     * 
     */
    public void run()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(reader);
        
         try 
         {
            while(true)
            {
                while (!reader.ready()) 
                {
                    Thread.sleep(100);
                }
                    
                if(scanner.hasNext())
                {
                    String line = scanner.nextLine();
                    if(line.equals("pouzij Zbran"))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Musíš použít zbraň. Jinak umřeš !!!");
                    }
                }
                
            }
        } 
        catch (IOException | InterruptedException e) 
        { 
            System.out.println("");//Nepotřebujeme nic dělat
        }
        
    }
    
    
}
