
/**
 * Klasse Country, wird als Speicher benutzt
 * 
 * @Vanessa Hartl, Hochschule für Gestaltung, IoT Semester 3
 * @29.06.2018
 */
public class Country
{
    // Instanzvariablen 
    private int id;
    private String name;
    private String group;

    /**
     * Konstruktor für Objekte der Klasse Country
     */
    public Country(int id, String name, String group)
    {
        // Instanzvariablen initialisieren
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public String getGroup(){
        return this.group;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getString(String spacer)
    {
        return this.id + spacer + this.name + spacer +  this.group;
    }
}
