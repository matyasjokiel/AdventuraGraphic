package cz.vse.jokiel.model;

import java.util.*;

/**
 * Třída představující aktuální stav hry.
 * 
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Matyáš Jokiel
 * @version LS 2020
 *
 */
public class GamePlan
{
    private Area currentArea;
    private Area domov;
    private Area stanice;
    private Area mistoCinu;
    private Area nemocnice;
    private Area marnice;
    private Area laborator;
    private Area doky;
    private Area sklad;
    private Area skladIn;
    private Area hideOut;
    
    private final static String sestrickaString = "Sestricka";//kvůli pmd
    
    private NPC sef; 
    private NPC straznik1;
    private NPC straznik2;
    private NPC straznik3;
    private NPC technikMistoCinu;
    private NPC sestricka;
    private NPC recepcni;  
    private NPC doktor;
    private NPC technik;
    private NPC vrah;
    
    private boolean shootout;
    
    private Storage backpack;
    private Storage stul;
    
    private Item zbran;
    private Item pouta;
    
    /**
     * Konstruktor třídy. Pomocí metody {@link #prepareWorldMap() prepareWorldMap}
     * vytvoří jednotlivé lokace a propojí je pomocí východů.
     */
    public GamePlan()
    {
        prepareWorldMap();
    }

    /**
     * Metoda vytvoří herní plán. Vytvoří lokace a propojí je východy, vytvoří také umístění a vloží do nich předměty.
     * Vytvoří postavy, přidá jim dialogy a přídá je do lokací.
     * 
     * Nastaví výchozí lokaci na domov.
     */
    private void prepareWorldMap()
    {
       
        // Vytvoříme jednotlivé lokace
        domov = new Area("Domov","Tvůj byt.",true);
        stanice = new Area("Policejni_stanice","Zde trávíš většinu času. Můžeš se zde kouknout na počítač, vybavit se do akce nebo si dát třeba kafe.",true);
        mistoCinu = new Area("Misto_cinu","Přijedeš na místo činu a už z dálky vidíš žlutou policejní pásku, plno strážníků a techniků.\nNa zemi leží mladý muž, okolo 25ti let se střelným poraněním hlavy.");
        nemocnice = new Area("Nemocnice","Musíš jít do márnice");
        marnice = new Area("Marnice", "Na stole leží postřelený mladík");
        laborator = new Area("Laborator","Policejni laborator");
        doky = new Area("Doky","Je tu dost skladů. Jestli nevíš číslo skladu, který hledáš pak se tam nedostaneš.");
        sklad = new Area("Sklad_619","Tohle by měl být sklad našeho zloděje, ale je zamknutý.");
        skladIn = new Area("Odemceny_sklad_619","Vejdeš dovnitř a vidíš hromadu ukradených šperků. Asi už víme, kdo vykrádal ty klenotnictví.\nVzadu na stole je kartička a na ní:\n\nIn case of need:\n9badb16da5680244d928e1bd64e9157bbe063721158f0193ed29687ae565504f");
        hideOut = new Area("Skrys","Na zemi leží postřelený pachatel. Zpoutej ho a odvez na stanici.",true);
        
       
        // Vytvoříme storages
        backpack = new Storage("Inventar", true);
        Storage nocniStolek = new Storage("Nocni_stolek", true);
        Storage skrinka = new Storage("Skrinka", false);
        stul = new Storage("Stul", false);
        
        
        //Pridame storages do lokací
        domov.addStorage(nocniStolek);
        stanice.addStorage(skrinka);
        laborator.addStorage(stul);
        
        // Vytvoříme itemy
        Item klicKeSkrince = new Item("Klic_ke_skrince");
        Item odznak = new Item("Odznak");
        zbran = new Item("Zbran",false);
        pouta = new Item("Pouta",false);
        Item nfcCip = new Item("Kopie_cipu");
        
        //Přidáme itemy do lokací
        nocniStolek.addItem(klicKeSkrince);
        domov.addItem(odznak); 
        skrinka.addItem(zbran);
        skrinka.addItem(pouta);
        stul.addItem(nfcCip); 
        
        // Nastavíme průchody mezi lokacemi (sousední lokace)
        domov.addExit(stanice);
        domov.addExit(mistoCinu);
        
        stanice.addExit(domov);
        stanice.addExit(mistoCinu);
        stanice.addExit(hideOut);
        stanice.addExit(nemocnice);
        stanice.addExit(marnice);
        stanice.addExit(laborator);
        stanice.addExit(doky);
        stanice.addExit(sklad);
        stanice.addExit(skladIn);
        
        mistoCinu.addExit(stanice);
        mistoCinu.addExit(domov);
        mistoCinu.addExit(nemocnice);
        
        nemocnice.addExit(stanice);
        nemocnice.addExit(domov);
        nemocnice.addExit(marnice);
        
        marnice.addExit(stanice);
        marnice.addExit(domov);
        marnice.addExit(laborator);
        
        laborator.addExit(stanice);
        laborator.addExit(domov);
        
        stanice.addExit(doky);
        
        doky.addExit(domov);
        doky.addExit(stanice);
        doky.addExit(sklad);
        
        sklad.addExit(domov);
        sklad.addExit(stanice);
        sklad.addExit(skladIn);
        
        skladIn.addExit(domov);
        skladIn.addExit(stanice);
       
        hideOut.addExit(stanice);
        
        
        //nastavime triggery was in area
        domov.setWasInHere(true);
        
        
        
        
        //vyvoříme postavy
        sef                 = new NPC("Kapitan");
        straznik1           = new NPC("Straznik_1");
        straznik2           = new NPC("Straznik_2");
        straznik3           = new NPC("Straznik_3");
        technikMistoCinu    = new NPC("Hlavni_technik",false);
        sestricka           = new NPC(sestrickaString);
        recepcni            = new NPC("Recepcni");        
        doktor              = new NPC("Doktor");
        technik             = new NPC("Technik");
        vrah                = new NPC("Pachatel",false); 
        
        //nastavíme dialogy
        sef.addDialog(1,"Ahhh konečně jsi se ukázal. Mám pro tebe zajímavý případ.\n"
        +"Právě mi volali že někde v centru leží mrtvej kluk. Jeď to vyšetřit");
        straznik1.addDialog(1,"Zdravim detektive, teď jsem přijel, zatím nic nevim.");
        straznik2.addDialog(1,"Detektive, pár svědků říkalo, že projelo černý auto a pak už ten mladík ležel mrtvej.");
        straznik3.addDialog(1,"Vyslýchal jsem pána, který bydlí přes ulici, celý to viděl. Projelo černý auto, z okýnka\nbyla vidět jen ruka se zbraní. Seběhlo se to prej strašně rychle.");
        technikMistoCinu.addDialog(1,"Šéfe, my už to tady balíme. Tělo si už odvážejí do márnice.");
        sestricka.addDialog(1,"Teď nemám čas, jděte se zeptat recepční.");
        sestricka.addDialog(2,"Recepční vám to snad řekla jasně ne !? Sejdete schody a nalevo je obří nápis Márnice.");
        recepcni.addDialog(1,"Márnice je v suterénu, nalevo od schodiště.");
        doktor.addDialog(1,"Obětí je mladý muž ve věku okolo 25ti let. Střelné poranění hlavy. Kulku schytal z pár metrů. Neměl šanci.\n" 
        +"V ruce jsme mu našli nějakej čip. Už by ho měli analyzovat v laboratoři.");
        technik.addDialog(1,"Podle otisku prstů a vzorku DNA jsme zjistili o koho se jedná. Obětí je Juan Alvarez.\nByl už jednou ve vězení za ozbrojenou loupež a drobné krádeže.\n"
        +"Ten čip,co mu vyndali z ruky je NFC čip a kód na něm vypadá, že otvírá nějaký zámek, ale víc nezjistím.\nUdělal jsem vám kopii, když najdete zámek tak by ho měl otevřít. Je támhle na stole.");
        vrah.addDialog(1,"Ahhh ty jsi mě střelil !!! Ten šmejd, co to schytal si to zasloužil.\nChtěl všechno pro sebe, chtěl všechno sebrat a zmizet.");
        
        // přidáme postavy do lokací
        stanice.addNPC(     sef);
        mistoCinu.addNPC(   straznik1);
        mistoCinu.addNPC(   straznik2);
        mistoCinu.addNPC(   straznik3);
        mistoCinu.addNPC(   technikMistoCinu);
        nemocnice.addNPC(   sestricka);
        nemocnice.addNPC(   recepcni);
        marnice.addNPC(     doktor);
        laborator.addNPC(   technik);       
        hideOut.addNPC(     vrah);
        

        // Hru začneme doma
        currentArea = domov;
    }

    /**
     * Metoda vrací jestli už měl hráč přestřelku.
     *
     * @return jestli už měl hráč přestřelku
     */
    public boolean getShootout()
    {
        return shootout;
    }
    
    /**
     * Metoda nastavuje jestli už proběhla přestřelka
     *
     * @param newState nový stav
     */
    public void setShootout(boolean newState)
    {
        shootout = newState;
    }
    
    /**
     * Metoda vrací odkaz na aktuální lokaci, ve které se hráč právě nachází.
     *
     * @return aktuální lokace
     */
    public Area getCurrentArea()
    {
        return currentArea;
    }

    /**
     * Metoda vrací odkaz na lokaci, která má stejné jméno, jako to, co jí předáváme v parametru
     *
     * @param area jméno lokace
     * @return odkaz na lokaci
     */
    public Area getArea(String area)
    {
        switch(area){
            case "Domov": return domov;
            case "Policejni_stanice": return stanice;
            case "Misto_cinu": return mistoCinu;
            case "Nemocnice": return nemocnice;
            case "Marnice": return marnice;
            case "Laborator": return laborator;
            case "Doky": return doky;
            case "Sklad_619": return sklad;
            case "Odemceny_sklad_619": return skladIn;
            case "Skrys": return hideOut;
            default: return null;
        }
    }
    
    /**
     * Metoda vrací odkaz na postavu, která má stejné jméno, jako to, co jí předáváme v parametru
     *
     * @param npc jméno postavy
     * @return odkaz na postavu
     */
    public NPC getNPC(String npc)
    {
        switch(npc){
            case "Kapitan": return sef;
            case "Straznik_1": return straznik1;
            case "Straznik_2": return straznik2;
            case "Straznik_3": return straznik3;
            case "Hlavni_technik": return technikMistoCinu;
            case sestrickaString: return sestricka;
            case "Recepcni": return recepcni;
            case "Doktor": return doktor;
            case "Technik": return technik;
            case "Pachatel": return vrah;
            default: return null;
        }
    }
    
    public Item getItem(String item)
    {
        switch(item){
            case "Zbran": return zbran;
            case "Pouta": return pouta;
            default: return null;
        }
    }
    
    /**
     * Metoda nastavuje u lokace, že už v ní hráč byl.
     *
     * @param area lokace
     */
    public void setTrueWasInArea(Area area)
    {
        if(!area.getWasInHere())
        {    
            area.setWasInHere(true);
        }
        checkWasInArea(area);
    }
    
    /**
     * Metoda kontroluje lokace, ve kterých hráč je a podle toho mění hru.
     *
     * @param area lokace
     */
    public void checkWasInArea(Area area)
    {
        if(mistoCinu.getWasInHere() && technikMistoCinu.getDialog(1).wasUsed()) // Když odejdeš z místa činu tak se tělo přesune do nemocnice
        {
            mistoCinu.changeDescription("Zde se stala ta vražda. Tělo mladíka je v márnici");
        }
        
        if(area.getName().equals("Doky"))
        {
            sklad.setAccessibility(true);
        }
        
    }
    
    /**
     * Tato metoda rozhoduje o tom jaký dialog použít.
     *
     * @param npc postava
     * @return jaké číslo dialogu použít
     */
    public int whichDialog(NPC npc)
    {
        if(!npc.isAccessible())
        {
            return 0;
        }
        
        if(npc.getName().equals("Kapitan") && !mistoCinu.isAccessible()) // když mluvíš s šéfem poprvé v policejní stanici
        {
            mistoCinu.setAccessibility(true);  
            npc.setAccessibility(false);
            return 1;
            
        }
        
        if(npc.getName().equals("Hlavni_technik")) //technik otevre hraci nemocnici
        {     
            nemocnice.setAccessibility(true);
            npc.setAccessibility(false);
            return 1;
        }
        
        if(npc.getName().equals(sestrickaString) && !recepcni.getDialog(1).wasUsed()) //jestli hráč nemluvil ještě s recepční a mluví se sestrou
        {   
            return 1;
        }
        
        if(npc.getName().equals(sestrickaString) && recepcni.getDialog(1).wasUsed())
        {
            npc.setAccessibility(false);
            return 2;
        }
        
        if(npc.getName().equals("Recepcni")) //recepcni otevre hraci pitevnu
        {     
            marnice.setAccessibility(true);
            npc.setAccessibility(false);
            return 1;
        }
        
        
        if(npc.getName().equals("Doktor")) //doktor otevre hraci laborator
        {     
            laborator.setAccessibility(true);
            npc.setAccessibility(false);
            return 1;
        }
        
        if(npc.getName().equals("Technik")) 
        {     
            stul.setAccessibility(true);
            return 1;
        }
        
        npc.setAccessibility(false);
        
        if(!straznik1.isAccessible() && !straznik2.isAccessible() && !straznik3.isAccessible())
        {
            technikMistoCinu.setAccessibility(true);
        }
        
        return 1;      
    }
    
    
    
    /**
     * Metoda vrací hráčův inventář
     *
     * @return inventář
     */
    public Storage getBackpack()
    {
        return backpack;
    }
    
    /**
     * Metoda nastaví aktuální lokaci, používá ji příkaz {@link CommandMove}
     * při přechodu mezi lokacemi.
     *
     * @param area lokace, která bude nastavena jako aktuální
     */
    public void setCurrentArea(Area area)
    {
       currentArea = area;
    }

}
