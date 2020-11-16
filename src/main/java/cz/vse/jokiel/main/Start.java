package cz.vse.jokiel.main;

import cz.vse.jokiel.model.Game;
import cz.vse.jokiel.model.IGame;
import cz.vse.jokiel.textui.TextUI;
import javafx.*;

/**
 * Hlavní třída určená pro spuštění hry. Obsahuje pouze statickou metodu
 * {@linkplain #main(String[]) main}, která vytvoří instance logiky hry
 * a uživatelského rozhraní, propojí je a zahájí hru.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public class Start
{
    /**
     *
     * Metoda pro spuštění celé aplikace.
     *
     * @param args parametry aplikace z příkazového řádku
     */
    public static void main(String[] args)
    {
        IGame game = new Game();
        TextUI ui = new TextUI(game);
        ui.play();
    }


    private Start() {}

}
