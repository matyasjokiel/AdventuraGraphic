package cz.vse.jokiel.model;

import java.util.Iterator;

/*******************************************************************************
 * Třída implementující příkaz "Pouzij".
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandUseGraphic implements ICommand
{
    private static final String NAME = "pouzijGraphic";

    private GamePlan plan;
    private Game game;
    private String itemName;

    /**
     * CommandUse Konstruktor
     *
     * @param plan herní plán
     * @param game hra
     */
    public CommandUseGraphic(GamePlan plan, Game game)
    {
        this.plan = plan;
        this.game = game;
    }
    
    
    /**
     * Metoda zjistí z parametru jaký předmět chce hráč použít a následně vykoná všechny operace, které jsou s tí předmětem spojeny.
     *
     * @param //jaký předmět chce hráč použít
     * @return výsledek příkazu jako String
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
        return "Musíš zadat co chceš pouzit";  // Pokud hráč nezadá žádný parametr
        } else if (parameters.length > 1) {
        return "Nelze pouzit vic veci naraz";  // Pokud hráč zadá více parametrů
        }
        
        itemName = parameters[0];
        
        switch(itemName)
        {
            case "Zbran":
                                        return zbran();
                            
            case "Klic_ke_skrince":
                                        return klicKeSkrince();
                                        
            case "Pocitac":
                                        return pocitac();
                                        
            case "Kopie_cipu":
                                        return kopieCipu();
                                            
            case "Automat_na_kafe":
                                        return automatNaKafe();
    
            case "Kelimek_s_kafem": 
                                        return kelimekSKafem();
                                        
            case "Pouta":
                                        return pouta();
            
            default:                    return "To nelze použít.";
            }
    }
    
    /**
     * Metoda udělá to co je potřeba když chce hráč použít zbraň
     *
     * @return výsledek metody
     */
    private String zbran()
    {
        if(!game.getGamePlan().getCurrentArea().getNpcsInArea().isEmpty()) {
            Iterator<NPC> iter = plan.getCurrentArea().getNpcsInArea().iterator();
            NPC npc = iter.next();

            game.setGameOver(true);
            return "Zastřelil jsi " + npc.getName();
        }
        else{
            return "Vystřelil jsi.";
        }
    }
    
    /**
     * Metoda udělá to co je potřeba když chce hráč použít klíč ke skříňce
     *
     * @return výsledek metody
     */
    private String klicKeSkrince()
    {
        if(plan.getBackpack().isInStorage(itemName))
        {
            if(plan.getCurrentArea().getName().equals("Policejni_stanice"))
            {
               if(!plan.getCurrentArea().getStorage("Skrinka").isAccessible()) {

                   plan.getCurrentArea().getStorage("Skrinka").setAccessibility(true);
                   plan.getItem("Zbran").setAccesibility(true);
                   plan.getItem("Pouta").setAccesibility(true);
                   return "Odemkl jsi svou skříňku.";
               }
               else
               {
                   return "Už je odemčená";
               }

                
            }
            else
            {
                return itemName + " zde nelze použít.";
            }
        }
        else
        {
            return "Klíč nemáš u sebe";
        }
    }
    
    /**
     * Metoda udělá to co je potřeba když chce hráč použít počítač
     *
     * @return výsledek metody
     */
    private String pocitac()
    {
        if(plan.getCurrentArea().getName().equals("Policejni_stanice"))
        {
            ComputerGraphic pocitac = new ComputerGraphic(plan,game);
            pocitac.start();
            return plan.getCurrentArea().getExits();
        }
        else
        {
            return "Pocitac lze použít jen na Policejní stanici";
        }
    }
    
    /**
     * Metoda udělá to co je potřeba když chce hráč použít kopii čipu
     *
     * @return výsledek metody
     */
    private String kopieCipu()
    {
        if(plan.getBackpack().isInStorage(itemName))
        {
            if(plan.getCurrentArea().getName().equals("Sklad_619"))
            {
                if(plan.getArea("Odemceny_sklad_619").isAccessible())
                {
                    plan.getArea("Odemceny_sklad_619").setAccessibility(false);
                    return "Zase si ho zamkl ?";
                }
                else
                {
                    plan.getArea("Odemceny_sklad_619").setAccessibility(true);
                    return "Odemkl jsi sklad.\n" + plan.getCurrentArea().getExits();
                }
            }
            else
            {
                return itemName + " zde nelze použít.";
            }
        }
        else
        {
            return "Nemáš ho u sebe.";
        }
    }
    
    /**
     * Metoda udělá to co je potřeba když chce hráč použít automat na kafe
     *
     * @return výsledek metody
     */
    private String automatNaKafe()
    {
        if(plan.getCurrentArea().getName().equals("Policejni_stanice"))
        {
            if(plan.getBackpack().isInStorage("Kelimek_s_kafem"))
            {return "Už jedno kafe máš.";}
            else {
                if (plan.getBackpack().howManyItemsInStorage() < 5) {
                    if (plan.getBackpack().isInStorage("Prazdny_kelimek")) {
                        game.getController().setTextOutput("Použil jsi Prazdny_kelimek");
                        plan.getBackpack().removeItem(plan.getBackpack().getItem("Prazdny_kelimek"));
                    }
                    game.getController().setTextOutput("Please stand by. Your coffee is being made.");
                    // try{waitSec(2000);}catch(InterruptedException e){game.getController().setTextOutput("");}
                    plan.getBackpack().addItem(new Item("Kelimek_s_kafem"));
                    return "Vzal jsi Kelimek_s_kafem";
                } else {
                    return "Bohužel víc věcí už neuneseš. Nějakou odhoď.";
                }
            }
        }
        else
        {
            return "Kafe si můžeš dát jen na stanici.";
        }
    }
    
    /**
     * Metoda udělá to co je potřeba když chce hráč použít kelimek s kafem
     *
     * @return výsledek metody
     */
    private String kelimekSKafem()
    {
        if(plan.getBackpack().isInStorage(itemName))
        {
            plan.getBackpack().addItem(new Item("Prazdny_kelimek"));
            plan.getBackpack().removeItem(plan.getBackpack().getItem("Kelimek_s_kafem"));
            return "Vypil jsi kafe.";
        }
        return "To nelze použít.";
    }
    
    /**
     * Metoda udělá to co je potřeba když chce hráč použít pouta
     *
     * @return výsledek metody
     */
    private String pouta()
    {
        if(plan.getBackpack().isInStorage(itemName))
        {
            if(plan.getCurrentArea().getName().equals("Skrys"))
            {
                plan.getBackpack().removeItem(plan.getBackpack().getItem("Pouta"));
                game.getController().setTextOutput(plan.getNPC("Pachatel").getStringDialog(1));
                return "Zpoutal jsi pachatele. Můžeš jet zpět na stanici.";
            }
            else
            {
                return "Pouta se používají jen na podezřelé.";
            }
        }
        return "To nelze použít.";
    }
    
    /**
     * Metoda čeká tolik milisekund, kolik jí předáme
     * 
     * @param mili kolik milisekund má vlákno spát
     */
    private void waitSec(int mili) throws InterruptedException
    {
        Thread.sleep(mili);  
    }
    
    /**
     * Metoda vrací název příkazu
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return NAME;
    }
}
