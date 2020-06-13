//imports
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Die Klasse nimmt Objekte entgegen und gibt Objekte zurück.
 * 
 * @Vanessa Hartl, Hochschule für Gestaltung, IoT Semester 3
 * @29.06.2018
 */    
public class IO
{
    // variablen
    //array mit allen filenames
    private String[] files;
    //global data filetype
    private String fileType;
    //charset to write in
    private Charset charset;
    //path to store files in
    private String dataPath;
    //csv seperator
    private String seperator;
    /**
     * Konstruktor von IO
     */
    public IO()
    {
        //initialisiere instanzvariablen
        files = new String[]{"game", "country"};
        fileType = "csv";
        dataPath = "data";
        charset = Charset.forName("ISO-8859-1");
        seperator = ";;";
    }
    
    /**
     * Checkt die existens von files, erstellt files wenn sie nicht existieren
     * Gibt die daten der files in einem array aus und lädt diese
     * Ich hätte auch einen integer type benutzen können, um die informationen wie den amount der erstellten files auszugeben wenn benötigt
     * 
     * @return       Array of datablocks from all files
     */
    public List<List<String>> loadFiles()
    {
        //List to be returned
        List<List<String>> returnList = new ArrayList<List<String>>();
        //überschrieben mit false wenn ein file nicht erstellt werden kann bzw nicht gefunden
        boolean result = true;
        //wiederholen von Files array, wenn es leer ist, ist ein false positive, ist es aber nie
        for(int i = 0; i < files.length; i++){
            String fileName = files[i];
            //generiere filePath
            String filePath = dataPath + File.separator + fileName + "." + fileType;
            //erstelle file object
            File f = new File(filePath);
            //checkt ob der file schon existiert, wenn nicht dann schreib den csv header 
            if(!f.exists() || f.isDirectory()) {
                try{
                    //erstelle/checkt data directory
                    f.getParentFile().mkdirs();
                    //erstelle/checkt file
                    f.createNewFile();
                } catch(IOException e) {
                    System.out.println("Error 313: Couln't create "+fileName+":\n"+e.getMessage());
                    result = false;
                }
            }
            //ergebnis ist falsch wenn eine der dateien nicht exisitert und nicht erstellt werden konnte
            if(result){
                try {
                  returnList.add(i, Files.readAllLines(Paths.get(filePath), charset));
                } catch (IOException e) {
                  System.out.println("Error 131: Couln't load "+fileName+":\n"+e.getMessage());
                  result = false;
                }
            }
        }
        if(!result){
            System.out.println("Error 876");
            System.exit(1);
        }
        return returnList;
    }
    
    //speichere spiele zu file by argument
    public void saveGames(List<Game> data){
        String[] lineArr = new String[data.size()];
        for(int i = 0; i < data.size(); i++){
            Game game = data.get(i);
            lineArr[i] = game.getString(seperator);
        }
        writeFile(lineArr,"game");
    }
    
    //speichere spiele zu file by argument
    public void saveCountries(List<Country> data){
        String[] lineArr = new String[data.size()];
        for(int i = 0; i < data.size(); i++){
            Country country = data.get(i);
            lineArr[i] = country.getString(seperator);
        }
        writeFile(lineArr,"country");
    }
    
    //lade Spiele zu verwiesenem storage
    public void loadGames(List<String> data,Storage storage){
        for (String rowStr : data) {
                String[] rowArr = rowStr.split(";;");
                for(String col : rowArr){
                }
                storage.addGame(rowArr);
        }
    }
    
    //lade Länder zu verwiesenem storage
    public void loadCountries(List<String> data,Storage storage){
        for (String rowStr : data) {
                String[] rowArr = rowStr.split(";;");
                for(String col : rowArr){
                }
                storage.addCountry(rowArr);
        }
    }
    
    //schreibe einen array von zeilen zu einer datei
    public void writeFile(String[] data, String fileName){
        String filePath = this.dataPath + File.separator + fileName + "." + this.fileType;
        BufferedWriter outputWriter = null;
        try {
          outputWriter = new BufferedWriter(new FileWriter(filePath));
          for (int i = 0; i < data.length; i++) {
            // Maybe:
            outputWriter.write(data[i]+"");
            // Or:
            outputWriter.newLine();
          }
          outputWriter.flush();  
          outputWriter.close();  
        } catch (IOException e) {
          System.out.println("Error 574: Couln't load "+fileName+":\n"+e.getMessage());
        }
    }
}
