import java.util.List;
import java.util.ArrayList;

/**
 * Klasse Group, wird als speicher verwendet
 * 
 * @Vanessa Hartl, Hochschule für Gestaltung, IoT Semester 3
 * @29.06.2018
 */
public class Group
{
    // Instanzvariablen 
    private ArrayList countries;
    private String name;

    /**
     * Konstruktor für Objekte der Klasse Group
     */
    public Group(String name){
        this.name = name;
        this.countries = new ArrayList<Country>();
    }
    public void addCountry(Country country)
    {
        this.countries.add(country);
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Country> getCountries(){
        return this.countries;
    }
    public ArrayList<Country[]> calculatePossibleMatches(){
      ArrayList<Country> countries = this.countries;
      ArrayList<Country[]> pairs = new ArrayList();
      for(int a=0; a<countries.size(); a++){
              for(int b=a+1; b<countries.size(); b++){
                  pairs.add(new Country[]{countries.get(a), countries.get(b)});
              }
      }
      return pairs;
  }
}
