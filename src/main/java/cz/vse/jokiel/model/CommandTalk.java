package cz.vse.jokiel.model;

/*******************************************************************************
 * Třída implementující příkaz "Promluv_s". Když hráč pouzije tento příkaz tak si může promluvit s postavami ve hře.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class CommandTalk implements ICommand
{
    private static final String NAME = "promluv_s";

    private GamePlan plan;

    /**
     * CommandTalk Konstruktor
     *
     * @param plan herní plán
     */
    public CommandTalk(GamePlan plan)
    {
        this.plan = plan;
    }

    /**
     * Metoda vrací dialog, který má postava předurčený.
     *
     * @param s kým chce hráč mluvit
     * @return dialog
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Musíš zadat s kým chceš mluvit";  // Pokud hráč nezadá žádný parametr
        } else if (parameters.length > 1) {
            return "Nejde mluvit s více lidmi naráz";  // Pokud hráč zadá více parametrů
        }

        // Název místa k prohledání si uložíme do pomocné proměnné
        String npc = parameters[0];
       
        for(NPC clovek : plan.getCurrentArea().getNpcsInArea())
        {
            if(clovek.getName().equals(npc) && clovek.isAccessible())
            {
                
                return clovek.getStringDialog(plan.whichDialog(clovek)) + "\n\n" + plan.getCurrentArea().getAfterDialogDescription();
            }
            
        }
        
        
        return "Tato osoba s tebou teď nemůže mluvit.";
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
