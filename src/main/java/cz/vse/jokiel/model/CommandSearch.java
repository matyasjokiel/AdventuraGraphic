package cz.vse.jokiel.model;

/*******************************************************************************
 * Třída implementující příkaz prohledání lokace hráčem.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandSearch implements ICommand
{
    private static final String NAME = "prohledej";

    private GamePlan plan;

    /**
     * CommandSearch Konstruktor
     *
     * @param plan herní plán
     */
    public CommandSearch(GamePlan plan)
    {
        this.plan = plan;
    }

    /**
     * Metoda prohledá úložiště v lokaci a vrací předměty a další úložiště, které se nachází v lokaci
     *
     * @param parameters co chce hráč prohledat
     * @return výsledek příkazu jako String
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Musíš zadat co chceš prohledat";  // Pokud hráč nezadá žádný parametr
        } else if (parameters.length > 1) {
            return "Nejde prohledat víc věcí naráz";  // Pokud hráč zadá více parametrů
        }

        // Název místa k prohledání si uložíme do pomocné proměnné
        String searchArea = parameters[0];
        String result = "";
       
        if(searchArea.equals(plan.getCurrentArea().getName()))
        {
            if(searchArea.equals("Policejni_stanice"))
            {
                result = plan.getCurrentArea().getStorage("Hlavni").getItems() + plan.getCurrentArea().getStorageNames() + "Pocitac\n" + "Automat_na_kafe";
            }
            else
            {
                result = plan.getCurrentArea().getStorage("Hlavni").getItems() + plan.getCurrentArea().getStorageNames();
            }
        }
        else
        {
            if(plan.getCurrentArea().getStorage(searchArea) == null)
            {
                result =  "To prohledat nemůžes!";
            }
            else
            {
                if(plan.getCurrentArea().getStorage(searchArea).isAccessible())
                {
                    result = plan.getCurrentArea().getStorage(searchArea).getItems();
                }
                else
                {
                    result =  "To prohledat nemůžeš, asi je to zamčené.";
                }
            }
        }
        
        if(result.length() < 1)
        {
            return "Nic si nenašel.";
        }
        
        return result;
    }
    
    /**
     * Metoda čeká tolik milisekund, kolik jí předáme
     * 
     * @param mili kolik milisekund má vlákno spát
     */
    @Override
    public String getName()
    {
        return NAME;
    }
}
