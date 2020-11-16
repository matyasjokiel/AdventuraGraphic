package cz.vse.jokiel.model;
import java.util.*;

/*******************************************************************************
 * Třída NPC(non-player character) představuje postavy ve hře, které nějak interagují s hráčem.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class NPC
{
    private String name;
    private Set<Dialog> dialogy;
    private boolean isAccessible;
    
    /**
     * NPC Konstruktor default je přístupná
     *
     * @param name jméno postavy
     */
    public NPC(String name)
    {
        this.name = name;
        dialogy = new HashSet<Dialog>();
        isAccessible = true;
    }
    
    /**
     * NPC Konstruktor
     *
     * @param name jméno postavy
     * @param accessibility přístupnost postavy
     */
    public NPC(String name, boolean accessibility)
    {
        this.name = name;
        dialogy = new HashSet<Dialog>();
        isAccessible = accessibility;
    }
    
    
    /**
     * Metoda vrací jméno postavy
     *
     * @return jméno postavy
     */
    public String getName()
    {
        return name;
    }

    /**
     * Metoda nastavuje přístupnost
     *
     * @param newAccess nová přístupnost
     */
    public void setAccessibility(boolean newAccess)
    {
        isAccessible = newAccess;
    }
    
    /**
     * Metoda vrací přístupnost
     *
     * @return přístupnost
     */
    public boolean isAccessible()
    {
        return isAccessible;
    }
    
    /**
     * Metoda přidá postavě dialog
     *
     * @param cisloDialogu 
     * @param dialog 
     */
    public void addDialog(int cisloDialogu, String dialog)
    {
        dialogy.add(new Dialog(cisloDialogu,dialog));
        
    }
    
    /**
     * Metoda vrací dialog podle čísla dialogu
     *
     * @param cisloDialogu 
     * @return dialog/null
     */
    public Dialog getDialog(int cisloDialogu)
    {
        
        if(dialogy.size() >= cisloDialogu)
        {
            for(Dialog dialog : dialogy)
            {
                if(dialog.getCisloDialogu() == cisloDialogu)
                {
                    return dialog;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Metoda vrací dialog podle čísla dialogu
     *
     * @param cisloDialogu 
     * @return dialog jako String
     */
    public String getStringDialog(int cisloDialogu)
    {
        
        if(dialogy.size() >= cisloDialogu)
        {
            for(Dialog dialog : dialogy)
            {
                if(dialog.getCisloDialogu() == cisloDialogu)
                {
                    return dialog.getDialog();
                }
            }
        }
        
        return "Teď nemohu mluvit.";
    }
    
    
}
