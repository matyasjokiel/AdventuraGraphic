package cz.vse.jokiel.model;


/*******************************************************************************
 * Třída implementující příkaz odhození předmětu.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandThrowAway implements ICommand
{
    private static final String NAME = "odhod";

    private GamePlan plan;
    
    /**
     * CommandThrowAway Konstruktor
     *
     * @param plan herní plán
     */
    public CommandThrowAway(GamePlan plan)
    {
        this.plan = plan;
    }
    
    /**
     * Metoda vezme předmět, který chce hráč odhodit a dá ho do hlavního úložiště v lokaci
     *
     * @param to co chce hráč odhodit
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
        
        if(plan.getBackpack().isInStorage(itemName))
        {
        
        Item item = plan.getBackpack().getItem(itemName);
        
        plan.getBackpack().removeItem(item);
        plan.getCurrentArea().addItem(item);
        return "Odhodil si " + item.getName();
        }

        return "To nemůžeš odhodit.";
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
