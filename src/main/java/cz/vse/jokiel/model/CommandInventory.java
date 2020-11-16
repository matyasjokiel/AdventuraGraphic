package cz.vse.jokiel.model;

/*******************************************************************************
 * Třída implementující příkaz "Inventář". Tento příkaz ukáže co má hráč u sebe za předměty.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandInventory implements ICommand
{
    private static final String NAME = "inventar";

    private GamePlan plan;

    
    /**
     * CommandInventory Konstruktor
     *
     * @param plan herní plán
     */
    public CommandInventory(GamePlan plan)
    {
        this.plan = plan;
    }

    
    /**
     * Metoda vrací předměty, které má hráč u sebe.
     *
     * @param ignoruje se
     * @return předměty, které má hráč u sebe
     */
    @Override
    public String process(String... parameters)
    {
        return "U sebe máš: \n"
                + "\n"
                + plan.getBackpack().getItems();
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
