package cz.vse.jokiel.model;

import java.util.*;
/*******************************************************************************
 * Třída Computer představuje Počítač, který hráč může použít k vyhledání pachatele a k dekódování tajné adresy.
 *
 * @author Matyáš Jokiel
 * @version LS 2020
 */
public class Computer
{
    GamePlan plan;

    
    /**
     * Computer Konstruktor
     *
     * @param plan plán hry
     */
    public Computer(GamePlan plan)
    {
        this.plan = plan;
    }
    
    /**
     * V metodě start si hráč vybere jaký program chce použít
     * 1 - Policejní databázi
     * 2 - Dekodér
     *
     * @return String "Done." když je výběr hotov
     */
    public String start()
    {
        String text;
        
        
        try{waitSec(500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
        System.out.println("Programs:");
        try{waitSec(1000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
        System.out.println("(1)  Police Database");
        try{waitSec(1000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
        System.out.println("(2)  Address decoder");
        try{waitSec(1000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
        System.out.println("Which program do you want to use ? (Use appropriate numbers or \"exit\")\n\n");
        do
        {
            text = readLine();
            
        }while(!text.equals("1") && !text.equals("2") && !text.equals("exit"));
        
        switch(text)
        {
            case "1": return policeDatabase();
            case "2": return addressDecoder();
            default: return "Done.";
        }
        
    }
    
    /**
     * Metoda představuje program "Police Database", ve kterém hráč může vyhledávat jména
     *
     * @return String "Done." když je vyhledávání dokončeno 
     */
    public String policeDatabase()
    {
        
            try{waitSec(1500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            System.out.println("Police Database v1.0");
            try{waitSec(1500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            System.out.println("Status: Loading");
            try{waitSec(1500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
            System.out.println("Status: Connected\n\n");
            System.out.println("Name of suspect: (or \"exit\")\n");
            
            String line;
            
            while(true)
            {
                line = readLine();
                
                if(line.equals("Juan Alvarez"))
                {
                    try{waitSec(2000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
                    System.out.println("Name: Juan Alvarez");
                    System.out.println("Date of birth: 16.08.2010");
                    System.out.println("Known aliases: ---");
                    System.out.println("Address: ---");
                    System.out.println("Relatives: ---\n");
                    System.out.println("Last bank statement:");
                    System.out.println("Jim's storages - Ocean drive 308 - storage 619----------");
                    System.out.println("Jul 15----------------------------------------------150đ");
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Total-----------------------------------------------150đ\n");
                    
                    plan.getArea("Doky").setAccessibility(true);
                }
                else if(line.equals("John Meyers"))
                {
                    System.out.println("         XXXXXXXX\n"
                                      +"       XXX      XXX\n"
                                      +"      XX            XX\n"
                                      +"     XX              XX\n"
                                      +"    XX                XX\n"
                                      +"   XX                  XX\n"
                                      +"   X                    X\n"
                                      +"   X       EASTER       X\n"
                                      +"   X                    X\n"
                                      +"   X                    X\n"
                                      +"   XX                  XX\n"
                                      +"    X                  X\n"
                                      +"    XX                XX\n"
                                      +"     XXX             XX\n"
                                      +"       XXXXXXXXXXXXXXX\n\n");
                    System.out.println("Yay you found it.");
                }
                else if(line.equals("exit"))
                {
                    break;
                }
                else
                {
                    System.out.println("Unfortunetely this name is not in the database.");
                }
            
            }
            
            return "Done.";
        
    }
    
    /**
     * Metoda představuje program "Address decoder", ve kterém hráč může dekódovat tajný vzkaz.
     *
     * @return String "Done." když je dekódování dokončeno
     */
    public String addressDecoder()
    {
        System.out.println("Address Decoder v1.0");
        try{waitSec(1500);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
        
        String hash;
        
        while(true){
            System.out.println("Enter encrypted data: (or \"exit\")");
            
            hash = readLine();
            
            
            
            if(hash.equals("9badb16da5680244d928e1bd64e9157bbe063721158f0193ed29687ae565504f"))
            {
                try{waitSec(3000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
                plan.getArea("Skrys").setAccessibility(true);
                return "Result:\nWillow st 256";
            }
            else if(hash.equals("exit"))
            {
                break;
            }
            else
            {
                try{waitSec(3000);}catch(InterruptedException e){System.out.println("");}//Nepotřebujeme nic dělat
                return "No result.";
            }
        }
        return "Done.";
    }

    /**
     * Pomocná metoda pro čtení příkazů z konzole.
     *
     * @return řádek textu z konzole
     */
    private String readLine()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("john_meyers_0564>>> ");
        return scanner.nextLine();
    }
    
    /**
     * Metoda čeká tolik milisekund, kolik jí předáme
     * 
     *
     * @param mili kolik milisekund má vlákno spát
     */
    private void waitSec(int mili) throws InterruptedException
    {
        Thread.sleep(mili);  
    }
    
}
