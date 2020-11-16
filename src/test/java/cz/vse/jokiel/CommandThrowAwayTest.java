package cz.vse.jokiel;

import java.io.*;

import cz.vse.jokiel.model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída slouží k otestování příkazu "odhod"
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandThrowAwayTest
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
     * Test příkazu odhod
     */
    @Test
    public void testSetUp()
    {
        assertFalse(game.getGamePlan().getBackpack().isInStorage("Odznak"));
        game.processCommand("vezmi Odznak");
        game.processCommand("vezmi Klic_ke_skrince");
        assertTrue(game.getGamePlan().getBackpack().isInStorage("Odznak"));
        assertTrue(game.getGamePlan().getBackpack().isInStorage("Klic_ke_skrince"));
        
        game.processCommand("odhod Odznak");
        game.processCommand("odhod Klic_ke_skrince");
        
        assertFalse(game.getGamePlan().getBackpack().isInStorage("Odznak"));
        assertFalse(game.getGamePlan().getBackpack().isInStorage("Klic_ke_skrince"));
    }

}
