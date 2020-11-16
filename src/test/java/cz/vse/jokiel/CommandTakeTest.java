package cz.vse.jokiel;

import cz.vse.jokiel.model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída slouží k otestování příkazu "vezmi"
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandTakeTest
{
    private Game game;
    
    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        game = new Game();
    }


    /***************************************************************************
     * Test příkazu vezmi
     */
    @Test
    public void testCommandTake()
    {
        assertFalse(game.getGamePlan().getBackpack().isInStorage("Odznak"));
        game.processCommand("vezmi Odznak");
        game.processCommand("vezmi Klic_ke_skrince");
        assertTrue(game.getGamePlan().getBackpack().isInStorage("Odznak"));
        assertTrue(game.getGamePlan().getBackpack().isInStorage("Klic_ke_skrince"));
        
        game.processCommand("jed Policejni_stanice");
        game.processCommand("pouzij Klic_ke_skrince");
        game.processCommand("vezmi Zbran");
        assertTrue(game.getGamePlan().getBackpack().isInStorage("Zbran"));
        
    }

}
