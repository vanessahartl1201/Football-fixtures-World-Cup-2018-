//imports
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Die Storage Klasse verwaltet die Datensatz-Objekte (Game, Country, Group)
 * 
 * 
 * @Vanessa Hartl, Hochschule für Gestaltung, IoT Semester 3
 * @29.06.2018
 */
public class Storage
{
    //variablen
    //listen von allen objekten die gebraucht werden
    private HashMap<String,Group> groups;  
    private List<Country> countries;
    private List<Game> games;
    private IO io;
    /**
     * Konstruktor für Objekte der Klasse Storage
     */
    public Storage()
    {
        this.initialize();
    }

    /**
     * Initialisiere "Storage Objects" aka Lists of objects
     * 
     */
    public void initialize(){
        //set storages, ich hätte "reflection" nehmen können und einen general converter wenn die Datenstruktur noch komlexer gewesen wäre
        //(oder natürlich eine datenbank)
        
        this.games = new ArrayList<Game>();
        this.countries = new ArrayList<Country>();
        this.groups = new HashMap<String,Group>();
        this.io = new IO();
        List data = this.io.loadFiles();
        if(data.size()>0){
            io.loadGames((List<String>)data.get(0), this);
            io.loadCountries((List<String>)data.get(1), this);
            loadGroups();
        }
    }
    
    public void saveData(){
        this.io.saveGames(this.games);
        this.io.saveCountries(this.countries);
    }
    
    public void addGame(String[] argumentArr){
        this.games.add(new Game(Integer.parseInt(argumentArr[0]), Integer.parseInt(argumentArr[1]), Integer.parseInt(argumentArr[2]), Integer.parseInt(argumentArr[3]), Integer.parseInt(argumentArr[4]), argumentArr[5]));
    }
    
    public boolean addGameByStrings(String[] argumentArr){
        //über die nutzereingabe wird das land geholt 
        Country country1 = this.getCountryByName(argumentArr[0]);
        Country country2 = this.getCountryByName(argumentArr[1]);
        //Die ID wird generiert um dopplungen zu vermeiden. Sie wird nicht verwendet. Aber macht das Programm erweiterbar.
        //ID = Die anzahl der spiele in der liste + 1, "" hinten dran um einen string draus zu machen
        //die phase ist immer gruppe, später  könnte man das programm erweitern, oder herauschmeissen.
        String id = (this.getGames().size()+1)+"";
        if(country1 != null && country2 != null){
            this.addGame(new String[]{id, country1.getId()+"", country2.getId()+"", argumentArr[2], argumentArr[3], "gruppe"});
            return true;
        }
        return false;
    }
    
    public Game getGame(int id){
        //index ist id-1 weil die ids bei 1 starten, der listenindex aber bei null.
        return this.games.get(id-1);
    }
    
    public List<Game> getGames(){
        return this.games;
    }
    
    public void addCountry(String[] argumentArr){
        this.countries.add(new Country(Integer.parseInt(argumentArr[0]), argumentArr[1], argumentArr[2]));
    }
    
    public Country getCountry(int id){
        //index ist id-1
        return this.countries.get(id-1);
    }
    
    public List<Country> getCountries(){
        return this.countries;
    }
    
    public Country getCountryByName(String name){
        //hashmap um über den string das land zu bekommen. Ich hätte von anfang an hashmaps verwenden sollen.
        Map<String,Country>countryMap = new HashMap<String,Country>();
        name = name.toLowerCase();
        
        for (Country country : this.getCountries()){
            countryMap.put(country.getName().toLowerCase(), country);
        }
        return countryMap.get(name);
    }
    
    //fügt eine neue Gruppe hinzu 
    public Group addGroup(String name){
        Group group = new Group(name);
        //existierende werden überschrieben,man könnte dies ändern
        //indem man eine updateGroup Methode nimmt, welche erstellt wenn keines existiert und dann ein Land hinzufügt
        this.groups.put(name, group);
        return group;
    }
    
    public Group getGroup(String name){
        return this.groups.get(name);
    }
    
    public HashMap<String, Group> getGroups(){
        return this.groups;
    }

    
    public void loadGroups(){
       //gruppenlogik
       Group group;
       for(Country country : this.countries){
            String name = country.getGroup();
            group = getGroup(name);
            if(group==null){
                group = addGroup(name);
            }
            if(group!=null){
                group.addCountry(country);
            }       
       }
    }
}
