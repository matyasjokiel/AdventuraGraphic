package cz.vse.jokiel.model;

/**
 * Třída implementující příkaz pro zobrazení nápovědy ke hře.
 *
 * @author Jarmila Pavlíčková
 * @author Luboš Pavlíček
 * @author Jan Říha
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandHelp implements ICommand
{
    private static final String NAME = "napoveda";

    private ListOfCommands listOfCommands;
    private GamePlan plan;

    /**
     * Konstruktor třídy.
     *
     * @param listOfCommands odkaz na seznam příkazů, které je možné ve hře použít
     */
    public CommandHelp(ListOfCommands listOfCommands, GamePlan plan)
    {
        this.listOfCommands = listOfCommands;
        this.plan = plan;
    }

    /**
     * Metoda vrací nápovědu ke hře. Vypisuje
     * zprávu o herním příběhu a seznam dostupných příkazů, které může hráč
     * používat.
     *
     * @param parameters parametry příkazu <i>(aktuálně se ignorují)</i>
     * @return nápověda, která se vypíše na konzoli
     */
    @Override
    public String process(String... parameters)
    {
        String pouzitelnePrikazy = "Ve hře můžeš používat tyto příkazy:\n";
        if(!plan.getArea("Policejni_stanice").getWasInHere())
        {
            return "Měl by si jet do práce.\n"                    
                    + "pracuješ jako detektiv tudíž asi na Policejní stanici\n"
                    + "\n"
                    + pouzitelnePrikazy
                    + listOfCommands.getNames();
        }
        
        if(!plan.getNPC("Kapitan").getDialog(1).wasUsed() && plan.getCurrentArea().getName().equals("Policejni_stanice"))
        {
            return "Měl by sis promluvit s Kapitánem.\n"                    
                    + "\n"
                    + pouzitelnePrikazy
                    + listOfCommands.getNames();
        }
        
        if(plan.getCurrentArea().getName().equals("Misto_cinu") && !plan.getNPC("Hlavni_technik").getDialog(1).wasUsed())
        {
            return "Promluv se strážníky a zjisti co se tu stalo.\n"                    
                    + "\n"
                    + pouzitelnePrikazy
                    + listOfCommands.getNames();
        }
        
        if(plan.getCurrentArea().getName().equals("Doky"))
        {
            return "Musíš najít číslo skladu, který hledáš.\n"                    
                    + "Pokud ho znáš - můžeš použít jed Sklad_XXX\n"
                    + "XXX - číslo skladu\n\n"
                    + pouzitelnePrikazy
                    + listOfCommands.getNames();
        }
        
        if(plan.getCurrentArea().getName().equals("Laborator") && plan.getNPC("Technik").getDialog(1).wasUsed())
        { 
            return "Už znáš jméno oběti.\n"                    
                    + "Třeba něco najdeš v policejní databázi.\n\n"
                    + pouzitelnePrikazy
                    + listOfCommands.getNames();
        }
        
        
        return "Tvým úkolem je vyřešit vraždu.\n"
                + "\n"
                + pouzitelnePrikazy
                + listOfCommands.getNames();
    
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo {@value NAME}.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return NAME;
    }

}
