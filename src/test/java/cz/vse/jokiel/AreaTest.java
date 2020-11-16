package cz.vse.jokiel;

import cz.vse.jokiel.model.Area;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Testovací třída pro komplexní otestování třídy {@link Area}.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public class AreaTest
{
    @Test
    public void testAreaExits() {
        Area area1 = new Area("hala", "Toto je vstupní hala budovy VŠE na Jižním městě.");
        Area area2 = new Area("bufet", "Toto je bufet, kam si můžete zajít na svačinu.");

        area1.addExit(area2);
        area2.addExit(area1);

        assertEquals(area1, area2.getExitArea(area1.getName()));
        assertEquals(area2, area1.getExitArea(area2.getName()));

        assertNull(area1.getExitArea("pokoj"));
        assertNull(area2.getExitArea("pokoj"));
    }

}
