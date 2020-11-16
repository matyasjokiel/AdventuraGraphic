package cz.vse.jokiel.model;

import java.util.*;

/*******************************************************************************
 * Třída představuje předmět.
 * 
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class Item
{
    private String name;
    private boolean isAccesible;
    
    /***************************************************************************
     * Konstruktor třídy Item - inicializuje jméno předmětu a default je přístupný.
     */
    public Item(String name)
    {
        this.name = name;
        isAccesible = true;
    }
    
    /**
     * Item Konstruktor
     *
     * @param name jméno
     * @param newAcc přístupnost
     */
    public Item(String name, boolean newAcc)
    {
        this.name = name;
        isAccesible = newAcc;
    }
    
    /**
     * Metoda nastavuje isAccesible
     *
     * @param newState nový stav
     */
    public void setAccesibility(boolean newState)
    {
        isAccesible = newState;
    }
    
    
    /**
     * Metoda vrací isAccesible
     *
     * @return isAccesible
     */
    public boolean isAccesible()
    {
        return isAccesible;
    }

    /***************************************************************************
     * Metoda vrací jméno předmětu.
     * 
     * @return jméno předmětu
     */
    public String getName()
    {
        return name;
    }
    
    /***************************************************************************
     * Metoda porovnává dva předměty a vračí true/false podle toho jestli se shodují.
     * 
     * @return jestli se dva předměty shodují
     */
    @Override
    public boolean equals(Object object)
    {
        
        if (object == null) {
            return false;
        }
    
        if (this == object) {
            return true;
        }
    
        if (!(object instanceof Item)) {
            return false;
        }
    
        Item item = (Item) object;
        return Objects.equals(this.name, item.name);
    }
    
    /**
     * Metoda vrací číselný identifikátor instance, který se používá
     * pro optimalizaci ukládání v dynamických datových strukturách
     * 
     * @return číselný identifikátor instance
     *
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}
