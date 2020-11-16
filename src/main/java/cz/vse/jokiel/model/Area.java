 package cz.vse.jokiel.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Třída představuje lokaci <i>(místo, místnost, prostor)</i> ve scénáři hry.
 * Každá lokace má název, který ji jednoznačně identifikuje. Lokace může mít
 * sousední lokace, do kterých z ní lze odejít. Odkazy na všechny sousední
 * lokace jsou uložené v kolekci.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public class Area
{
    private String name;
    private String description;
    
    private Set<Area> exits;  // Obsahuje sousední lokace, do kterých lze z této odejít
    private boolean isAccessible; // Jestli je lokace přístupná
    private boolean wasInHere; // Jestli tu už hráč byl
    private Set<NPC> npcs; // set lidí v lokaci
    private Set<Storage> storages; // v area bude víc storages;

    /**
     * Konstruktor třídy. Vytvoří lokaci se zadaným názvem a popisem a je standardně nastavena jako nepřístupná.
     *
     * @param name název lokace <i>(jednoznačný identifikátor, musí se jednat o text bez mezer)</i>
     * @param description podrobnější popis lokace
     */
    public Area(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.exits = new HashSet<>();
        isAccessible = false;
        wasInHere = false;
        this.npcs = new HashSet<>();
        this.storages = new HashSet<>();
        storages.add(new Storage("Hlavni", true));
    }
    
    /**
     * Konstruktor třídy. Vytvoří lokaci se zadaným názvem, popisem a přístupností. 
     *
     * @param name název lokace <i>(jednoznačný identifikátor, musí se jednat o text bez mezer)</i>
     * @param description podrobnější popis lokace
     * @param isAccessible nastavení přístupnosti
     */
    public Area(String name, String description, boolean isAccessible)
    {
        this.name = name;
        this.description = description;
        this.exits = new HashSet<>();        
        this.isAccessible = isAccessible;
        this.npcs = new HashSet<>();
        this.storages = new HashSet<>();
        storages.add(new Storage("Hlavni", true));
    }

    /**
     * Metoda vrací název lokace, který byl zadán při vytváření instance jako
     * parametr konstruktoru. Jedná se o jednoznačný identifikátor lokace <i>(ve
     * hře nemůže existovat více lokací se stejným názvem)</i>. Aby hra správně
     * fungovala, název lokace nesmí obsahovat mezery, v případě potřeby můžete
     * více slov oddělit pomlčkami, použít camel-case apod.
     *
     * @return název lokace
     */
    public String getName()
    {
        return name;
    }

    /**
     * Metoda přidá další východ z této lokace do lokace předané v parametru.
     * <p>
     * Vzhledem k tomu, že pro uložení sousedních lokací se používá {@linkplain Set},
     * může být přidán pouze jeden východ do každé lokace <i>(tzn. nelze mít dvoje
     * 'dveře' do stejné sousední lokace)</i>. Druhé volání metody se stejnou lokací
     * proto nebude mít žádný efekt.
     * <p>
     * Lze zadat též cestu do sebe sama.
     *
     * @param area lokace, do které bude vytvořen východ z aktuální lokace
     */
    public void addExit(Area area)
    {
        exits.add(area);
    }
    
    
    
    /**
     * Metoda přidává úložiště do lokace
     *
     * @param storage úložiště
     */
    public void addStorage(Storage storage)
    {
        storages.add(storage);
    }
    
    /**
     * Metoda přidává NPC do lokace
     *
     * @param npc NPC(non-player character)
     */
    public void addNPC(NPC npc)
    {
        npcs.add(npc);
    }
    
    /**
     * Metoda vrací set postav v lokaci
     *
     * @return set postav
     */
    public Set<NPC> getNpcsInArea()
    {
        return npcs;
    }
    
    /**
     * Metoda mění popis lokace
     *
     * @param newDescription nový popis lokace
     */
    public void changeDescription(String newDescription)
    {
        description = newDescription;
    }
    
    /**
     * Metoda přídá předmět do hlavního úložiště lokace
     *
     * @param item předmět
     */
    public void addItem(Item item)
    {
        for(Storage storage : storages)
        {
            if(storage.getName().equals("Hlavni"))
            {
                storage.addItem(item);
            }
        }
        
        
    }
    
    /**
     * Metoda prohledá všechny úložiště v lokaci a vrátí předmět se stejným jménem, který jí předáváme
     * Jestli neexistuje tak vráti null
     *
     * @param itemName jméno předmětu
     * @return předmět nebo null
     */
    public Item getItemFromAllStorages(String itemName)
    {
        for(Storage storage : storages)
        {
            if(storage.getItem(itemName) != null)
            {
                return storage.getItem(itemName);
            }
        }
        
        return null;
    }
    
    /**
     * Metoda odebere předmět v lokaci
     *
     * @param itemName jméno předmětu k odstranění
     */
    public void removeItemFromAllStorages(String itemName)
    {
        for(Storage storage : storages)
        {
            if(storage.getItem(itemName) != null)
            {
                storage.removeItem(storage.getItem(itemName));
            }
        }
        
    }
    
    /**
     * Metoda vrací názvy všech úložiští v lokaci
     *
     * @return názvy všech úložiští v lokaci
     */
    public String getStorageNames()
    {
        String result = "";
        for(Storage storage : storages)
        {
            if(!storage.getName().equals("Hlavni"))
            {
                result += storage.getName() + "\n";
            }
        }
        return result;
    }
    
    /**
     * Metoda vrací set úložiští v lokaci
     *
     * @return set úložiští v lokaci
     */
    public Set<Storage> getStorages()
    {
        return storages;
    }
    
    /**
     * Metoda vrací úložiště s takovým jménem, který jí předáme jinak null
     *
     * @param name název úložiště
     * @return úložiště/null
     */
    public Storage getStorage(String name)
    {
        for(Storage storage : storages)
        {
            if(storage.getName().equals(name))
            {
                return storage;
            }
        }
        return null;
    }
    
    /**
     * Metoda vrací jestli je lokace přístupná
     *
     * @return jestli je lokace přístupná
     */
    public boolean isAccessible()
    {
        return isAccessible;
    }
    
    /**
     * Metoda nastavuje status jestli hráč už navštívil lokaci
     *
     * @param newState nový stav
     */
    public void setWasInHere(boolean newState)
    {
        wasInHere = newState;
    }
    
    /**
     * Metoda vrací jestli už hráč byl v lokaci
     *
     * @return jestli už hráč byl v lokaci
     */
    public boolean getWasInHere()
    {
        return wasInHere;
    }
    
    /**
     * Metoda nastavuje přístupnost
     *
     * @param newAccessibility nový stav přístupnosti
     */
    public void setAccessibility(boolean newAccessibility)
    {
        isAccessible = newAccessibility;
    }
    
    /**
     * Metoda odebere předmět z hlavního úložiště
     *
     * @param item předmět
     */
    public void removeItem(Item item)
    {
        for(Storage storage : storages)
        {
            if(storage.getName().equals("Hlavni") && storage.isInStorage(item))
            {
                storage.removeItem(item);
            }
        }
        
    }
    
    /**
     * Metoda vrací jaké východy má aktuální lokace
     *
     * @return jaké východy má aktuální lokace
     */
    public String getExits()
    {
        String exitNames = "Východy:";
        for (Area exitArea : exits) 
        {
            if(exitArea.isAccessible())
            {
                exitNames += " " + exitArea.getName();
            }
        }
        
        return exitNames;
    }
    
    /**
     * Metoda vrací detailní informace o lokaci. Výsledek volání obsahuje název
     * lokace, podrobnější popis a seznam sousedních lokací, do kterých lze
     * odejít.
     *
     * @return detailní informace o lokaci
     */
    public String getFullDescription()
    {
        String exitNames = "Východy:";
        String npcsYouCanTalkWith = "Můžeš mluvit s:";
        for (Area exitArea : exits) 
        {
            if(exitArea.isAccessible() && !exitArea.getName().equals("Sklad_619"))
            {
                exitNames += " " + exitArea.getName();
            }
        }
        
        for (NPC clovek : npcs) 
            {
                if(clovek.isAccessible())
                {
                    npcsYouCanTalkWith += " " + clovek.getName();
                }
                
            }
        
        if(npcsYouCanTalkWith.length() > 15)
        {            
            
            return "Jsi v lokaci (místnosti) " + name + ".\n"
                + description + "\n\n"
                + npcsYouCanTalkWith + "\n\n"
                + exitNames;
        }
        
        return "Jsi v lokaci (místnosti) " + name + ".\n"
                + description + "\n\n"
                + exitNames;
    }
    
    /**
     * Metoda vrací speciální popis, který je potřeba vypsat po dialozich
     *
     * @return speciální popis
     */
    public String getAfterDialogDescription()
    {
        String exitNames = "Východy:";
        String npcsYouCanTalkWith = "";
        for (Area exitArea : exits) 
        {
            if(exitArea.isAccessible())
            {
                exitNames += " " + exitArea.getName();
            }
        }
        
        
        if(npcs.size() > 0)
        {
            npcsYouCanTalkWith += "Můžeš mluvit s:";
            boolean atleast1accs = false;
            for (NPC clovek : npcs) 
            {
                if(clovek.isAccessible())
                {
                    npcsYouCanTalkWith += " " + clovek.getName();
                    atleast1accs = true;
                }
            }
            if(atleast1accs)
            {
            return "Jsi v lokaci (místnosti) " + name + ".\n"
                + "\n"
                + npcsYouCanTalkWith + "\n\n"
                + exitNames;
            }
        }
        
        return "Jsi v lokaci (místnosti) " + name + ".\n"
                + "\n"
                + exitNames;
    }
    
    /**
     * Metoda vrací lokaci, která sousedí s aktuální lokací a jejíž název
     * je předán v parametru. Pokud lokace s daným jménem nesousedí s aktuální
     * lokací, vrací se hodnota {@code null}.
     * <p>
     * Metoda je implementována pomocí tzv. 'lambda výrazu'. Pokud bychom chtěli
     * metodu implementovat klasickým způsobem, kód by mohl vypadat např. tímto
     * způsobem:
     * <pre> for (Area exitArea : exits) {
     *     if (exitArea.getName().equals(areaName)) {
     *          return exitArea;
     *     }
     * }
     *
     * return null;</pre>
     *
     * @param areaName jméno sousední lokace <i>(východu)</i>
     * @return lokace, která se nachází za příslušným východem; {@code null}, pokud aktuální lokace s touto nesousedí
     */
    public Area getExitArea(String areaName)
    {
        return exits.stream()
                    .filter(exit -> exit.getName().equals(areaName))
                    .findAny().orElse(null);
    }

    /**
     * Metoda porovnává dvě lokace <i>(objekty)</i>. Lokace jsou shodné,
     * pokud mají stejný název <i>(atribut {@link #name})</i>. Tato metoda
     * je důležitá pro správné fungování seznamu východů do sousedních
     * lokací.
     * <p>
     * Podrobnější popis metody najdete v dokumentaci třídy {@linkplain Object}.
     *
     * @param o objekt, který bude porovnán s aktuálním
     * @return {@code true}, pokud mají obě lokace stejný název; jinak {@code false}
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o)
    {
        // Ověříme, že parametr není null
        if (o == null) {
            return false;
        }

        // Ověříme, že se nejedná o stejnou instanci (objekt)
        if (this == o) {
            return true;
        }

        // Ověříme, že parametr je typu (objekt třídy) Area
        if (!(o instanceof Area)) {
            return false;
        }

        // Provedeme 'tvrdé' přetypování
        Area area = (Area) o;

        // Provedeme porovnání názvů, statická metoda equals z pomocné třídy
        // java.util.Objects porovná hodnoty obou parametrů a vrátí true pro
        // stejné názvy a i v případě, že jsou oba názvy null; jinak vrátí
        // false
        return Objects.equals(this.name, area.name);
    }

    /**
     * Metoda vrací číselný identifikátor instance, který se používá
     * pro optimalizaci ukládání v dynamických datových strukturách
     * <i>(např.&nbsp;{@linkplain HashSet})</i>. Při překrytí metody
     * {@link #equals(Object) equals} je vždy nutné překrýt i tuto
     * metodu.
     * <p>
     * Podrobnější popis pravidel pro implementaci metody najdete
     * v dokumentaci třídy {@linkplain Object}.
     *
     * @return číselný identifikátor instance
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

}
