package cz.vse.jokiel.model;

import java.util.*;

/*******************************************************************************
 * Třída představuje úložný prostor, do kterého jdou dávat různé předměty.
 * 
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class Storage
{
    private Set<Item> items; // obsahuje predmety v storage
    private String name; // jmeno storage
    private boolean isAccessible; //pristupnost

    /***************************************************************************
     * Konstruktor třídy Storage - inicializuje Set předmětů, jméno, a jestli je úložiště přístupné.
     * 
     * @param   storageName - jméno úložiště
     *          isAccesible - přístupnost úložiště    
     */
    public Storage(String storageName, boolean isAccessible)
    {
        this.items = new HashSet<>();
        this.name = storageName;
        this.isAccessible = isAccessible;
    }
    
    /***************************************************************************
     * Metoda vrací přístupnost úložiště.
     * 
     * @return Přistupnost úložiště.
     */
    public boolean isAccessible()
    {
        return isAccessible;
    }
    
    /***************************************************************************
     * Metoda nastavuje přístupnost úložiště.
     * 
     * @param newValue - nová hodnota přístupnosti
     */
    public void setAccessibility(boolean newValue)
    {
        isAccessible = newValue;
    }
    
    /***************************************************************************
     * Metoda přidává předmět do úložiště.
     * 
     * @param item - předmět
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /***************************************************************************
     * Metoda vrací jméno úložiště.
     * 
     * @return Jméno úložiště.
     */
    public String getName()
    {
        return name;
    }
    
    /***************************************************************************
     * Metoda zjišťuje jestli se předmět nachází v úložišti.
     * 
     * @param   item - předmět
     * @return  true    - nachází
     *          false   - nenachází
     */
    public boolean isInStorage(Item item)
    {
        if(items.contains(item))
        {
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Metoda zjišťuje jestli se předmět nachází v úložišti.
     * 
     * @param   itemName - jméno předmětu
     * @return  true    - nachází
     *          false   - nenachází
     */
    public boolean isInStorage(String itemName)
    {
        for(Item item : items)
        {
            if(item.getName().equals(itemName))
            {
                return true;
            }
        }
        return false;
    }
    
    /***************************************************************************
     * Metoda odebere předmět z úložiště.
     * 
     * @param   item - předmět
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }

    /***************************************************************************
     * Metoda vyhledá a vrátí item v úložišti.
     * 
     * @param   itemName - jméno předmětu
     * @return  item - předmět
     */
    public Item getItem(String itemName)
    {
        Item result = null;
        for(Item item : items)
        {
            if(item.getName().equals(itemName))
            {
                result =  item;
                
            }
        }
        
        return result;
    }
    
    /***************************************************************************
     * Metoda vrátí jména předmětů v úložišti.
     * 
     * @return  jména předmětů
     */
    public String getItems()
    {
        String result = "";
        for(Item item : items)
        {
            result += item.getName() + "\n";
        }
        
        return result;
    }
    
    /***************************************************************************
     * Metoda vrátí kolik je předmětů v úložišti.
     * 
     * @return  kolik je předmětů v úložišti
     */
    public int howManyItemsInStorage()
    {
        return items.size();
    }
}



