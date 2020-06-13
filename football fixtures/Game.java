
/**
 * Klasse Game, wird als speicher benutzt
 * 
 * @Vanessa Hartl, Hochschule für Gestaltung, IoT Semester 3
 * @29.06.2018
 */

public class Game
{
    private int id;
    private int firstCountry;
    private int secondCountry;
    private int firstGoals;
    private int secondGoals;
    private String phase;

    /**
     * Konstruktor für Objekte der Klasse Game
     */
    public Game(int id, int firstCountry, int secondCountry, int firstGoals, int secondGoals, String phase)
    {
        this.id = id;
        this.firstCountry = firstCountry;
        this.secondCountry = secondCountry;
        this.firstGoals = firstGoals;
        this.secondGoals = secondGoals;
        this.phase = phase;
    }
    
    public int getFirstCountry(){
        return this.firstCountry;
    }
    
    public int getSecondCountry(){
        return this.secondCountry;
    }
    
    public int getFirstGoals(){
        return this.firstGoals;
    }
    
     public int getSecondGoals(){
        return this.secondGoals;
    }
    
    //gibt eine Zeichenfolge aus, die in der Textdatei gespeichert werden soll 
    public String getString(String spacer){ 
        return this.id + spacer + this.firstCountry + spacer +  this.secondCountry + spacer +  this.firstGoals + spacer +  this.secondGoals + spacer +  this.phase;
    }
}
