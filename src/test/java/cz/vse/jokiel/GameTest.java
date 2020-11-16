package cz.vse.jokiel;

import java.io.*;


import cz.vse.jokiel.model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testovací třída pro komplexní otestování herního příběhu.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class GameTest
{
    private Game game;
    
    @Before
    public void setUp()
    {
        
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        //System.setIn(new ByteArrayInputStream(("1" + System.lineSeparator() + "1" + System.lineSeparator() + "Juan Alvarez" + System.lineSeparator() + "exit" + System.lineSeparator() + "2" + System.lineSeparator() + "9badb16da5680244d928e1bd64e9157bbe063721158f0193ed29687ae565504f"+ System.lineSeparator() + "pouzij Zbran").getBytes()));
        game = new Game();
        
    }

    @Test
    public void testGameWinnable()
    {
        assertEquals("Domov", game.getGamePlan().getCurrentArea().getName());
        
        
        //System.setIn(new ByteArrayInputStream(("1\n" + System.lineSeparator() + "Juan Alvarez\n" + System.lineSeparator() +"exit\n").getBytes()));
        game.processCommand("vezmi Odznak");
        game.processCommand("vezmi Klic_ke_skrince");
        game.processCommand("jed Policejni_stanice");
        game.processCommand("promluv_s Kapitan");
        game.processCommand("pouzij Klic_ke_skrince");
        game.processCommand("vezmi Zbran");
        game.processCommand("vezmi Pouta");
        game.processCommand("jed Misto_cinu");
        game.processCommand("promluv_s Straznik_1");
        game.processCommand("promluv_s Straznik_2");
        game.processCommand("promluv_s Straznik_3");
        game.processCommand("promluv_s Hlavni_technik");
        game.processCommand("jed Nemocnice");
        game.processCommand("promluv_s Recepcni");
        game.processCommand("jed Marnice");
        game.processCommand("promluv_s Doktor");
        game.processCommand("jed Laborator");
        game.processCommand("promluv_s Technik");
        game.processCommand("vezmi Kopie_cipu");
        game.processCommand("jed Policejni_stanice");
        //System.setIn(new ByteArrayInputStream(("1" + System.lineSeparator() + "Juan Alvarez" + System.lineSeparator() + "exit").getBytes()));
        game.getGamePlan().getArea("Doky").setAccessibility(true);//Tato area se otevře po použití počítače a nepřišel jsem na to jak automatizovat vstup s vícero vstupy po sobě přes System.setIn
        game.processCommand("jed Doky");
        game.processCommand("jed Sklad_619");
        game.processCommand("pouzij Kopie_cipu");
        game.processCommand("jed Odemceny_sklad_619");
        game.processCommand("jed Policejni_stanice");
        //System.setIn(new ByteArrayInputStream(("2" + System.lineSeparator() + "9badb16da5680244d928e1bd64e9157bbe063721158f0193ed29687ae565504f").getBytes()));
        game.getGamePlan().getArea("Skrys").setAccessibility(true); //Stejný případ jako v přechozím případě 
        System.setIn(new ByteArrayInputStream("pouzij Zbran".getBytes()));
        game.processCommand("jed Skrys");
        game.processCommand("pouzij Pouta");
        game.processCommand("jed Policejni_stanice");
        assertTrue(game.isGameOver());
        
    }
    
    @Test
    public void testInventoryRestrictions()
    {
        game.processCommand("vezmi Odznak");
        game.processCommand("vezmi Klic_ke_skrince");
        game.processCommand("jed Policejni_stanice");
        game.processCommand("promluv_s Kapitan");
        game.processCommand("pouzij Klic_ke_skrince");
        game.processCommand("vezmi Zbran");
        game.processCommand("vezmi Pouta");
        game.processCommand("jed Misto_cinu");
        game.processCommand("promluv_s Straznik_1");
        game.processCommand("promluv_s Straznik_2");
        game.processCommand("promluv_s Straznik_3");
        game.processCommand("promluv_s Hlavni_technik");
        game.processCommand("jed Nemocnice");
        game.processCommand("promluv_s Recepcni");
        game.processCommand("jed Marnice");
        game.processCommand("promluv_s Doktor");
        game.processCommand("jed Laborator");
        game.processCommand("promluv_s Technik");
        game.processCommand("vezmi Kopie_cipu");
        game.processCommand("jed Policejni_stanice");
        
        assertEquals(game.getGamePlan().getBackpack().howManyItemsInStorage(),5);
        game.processCommand("pouzij Automat_na_kafe");
        assertEquals(game.getGamePlan().getBackpack().howManyItemsInStorage(),5);
        
        game.processCommand("vezmi Automat_na_kafe");
        assertEquals(game.getGamePlan().getBackpack().howManyItemsInStorage(),5);
        
        game.processCommand("odhod Pouta");
        assertEquals(game.getGamePlan().getBackpack().howManyItemsInStorage(),4);
        
        game.processCommand("vezmi Automat_na_kafe");
        assertEquals(game.getGamePlan().getBackpack().howManyItemsInStorage(),4);
    }
    
}
