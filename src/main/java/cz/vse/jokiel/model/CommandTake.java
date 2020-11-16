package cz.vse.jokiel.model;


/*******************************************************************************
 * Třída implementující příkaz sebrání předmětu hráčem.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandTake implements ICommand
{
    private static final String NAME = "vezmi";

    private GamePlan plan;
    
    /**
     * CommandTake Konstruktor
     *
     * @param plan herní plán
     */
    public CommandTake(GamePlan plan)
    {
        this.plan = plan;
    }
    
    /**
     * Metoda vezme předmět z úložiště a dá ho do hráčova inventáře
     *
     * @param parameters co chce hráč sebrat
     * @return výsledek příkazu jako String
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Musíš zadat co chceš vzít";  // Pokud hráč nezadá žádný parametr (cíl cesty)
        } else if (parameters.length > 1) {
            return "Musíš zadat jen jednu věc.";  // Pokud hráč zadá více parametrů
        }

        // Název itemu si uložíme do pomocné proměnné
        String itemName = parameters[0];
        
        if(plan.getCurrentArea().getItemFromAllStorages(itemName) == null)
        {
            return "To nemůžeš vzít";
        }
        else
        {
            if(plan.getBackpack().howManyItemsInStorage() < 5)
            {
                Item item = plan.getCurrentArea().getItemFromAllStorages(itemName);
                if(item.isAccesible())
                {
                    plan.getBackpack().addItem(item);
                    plan.getCurrentArea().removeItemFromAllStorages(itemName);
                    
                    return "Vzal jsi: " + item.getName();
                }
                else
                {
                    return "To nelze vzít";
                }
            }
            else
            {
                return "Bohužel víc věcí už neuneseš. Nějakou odhoď.";
            }
            
        }
        
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
