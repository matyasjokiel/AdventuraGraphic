package cz.vse.jokiel.model;

/*******************************************************************************
 * Třídu Dialog využívají NPC, když s nimi mluví hráč. Ukládá v sobě číslo, a to co NPC říká hráči.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class Dialog
{
    private int cisloDialogu;
    private String dialog;
    private boolean used;

    
    /**
     * Konstruktor inicializuje všechny atributy a nastavuje, že dialog ještě nebyl použit.
     *
     * @param cisloDialogu jaké je číslo dialogu
     * @param dialog samotný dialog
     */
    public Dialog(int cisloDialogu, String dialog)
    {
        this.cisloDialogu = cisloDialogu;
        this.dialog = dialog;
        used = false;
    }

    /**
     * Metoda vrací číslo dialogu
     *
     * @return číslo dialogu
     */
    public int getCisloDialogu()
    {
        return cisloDialogu;
    }
    
    /**
     * Metoda vrací to co říká NPC
     *
     * @return to co říká NPC
     */
    public String getDialog()
    {
        setUsed();
        return dialog;
    }
    
    /**
     * Metoda vrací jestli dialog byl použit
     *
     * @return jestli dialog byl použit
     */
    public boolean wasUsed()
    {
        return used;
    }
    
    /**
     * Metoda nastavuje dialog jako použitý
     *
     */
    public void setUsed()
    {
        used = true;
    }
}
