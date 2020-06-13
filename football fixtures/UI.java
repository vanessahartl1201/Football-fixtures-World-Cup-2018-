import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * UI ist die main Klasse, welche dafür zuständig ist, mit dem User zu interagieren
 * 
 * 
 * @Vanessa Hartl, Hochschule für Gestaltung, IoT Semester 3
 * @29.06.2018
 */
public class UI
{
    //Instanzvariablen
    private JFrame  frame;
    private JPanel  panel;
    private JLabel  lbResult;
    private Storage storage;

    /**
     * Konstruktor für Objekte der Klasse UI
     * erstellt ein Window um die Eingabemöglichkeiten darzustellen
     * hier wird die Eingabe von html erlaubt, somit entsteht unnötige komlexität
     */
    public UI()
    {
        // erstellt label zum anzeigen des ergebnisses in html
        lbResult = new JLabel( "" );
        lbResult.setBounds( 10, 40, 320, 20);
        // erstellt den panel um das label zu halten
        panel = new JPanel( null );
        panel.add( lbResult );
        // erstellt den frame
        frame = new JFrame( "Spielplan" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add( panel );
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.pack();
    }

    /**
     * standardaktion, wird beim starten des programmes ausgeführt 
     * (Kann in einem loop laufen)
     * 
     * speichern & beenden bei Eingabe von 0 
     * spiel eingeben bei Eingabe von 1 
     * spielplan ausgeben bei Eingabe von 2
     * spiele ausgeben bei Eingabe von 3
     * speichern bei Eingabe von 4 
     * abbruch bei Eingabe von 10 
     * (ich habe mich für 10 entschieden, da man diese Zahlenkombi nicht ausversehen eingeben kann)
     * 
     */
    public void standardAction()
    {
        this.putInformation("<html>0=Speichern und Beenden<br>1=Spiel eingeben<br>2=Spielplan ausgeben<br>3=Spiele ausgeben<br>4=Speichern</html>");
        this.setLabelHeight(80);
        
        String information = this.getInformation("Siehe anderes Fenster, 0=Beenden");
        this.putInformation("");
        try {
            switch(Integer.parseInt(information)){
                case 0:
                    this.storage.saveData();
                    System.exit(0);
                    break;
                case 1:
                    this.putInformation("<html><b>Groß/Kleinschreibung wird ignoriert<br>ä = ae, ö = oe, ü = ue<br>Falscheingaben werden ungeprüft gespeichert!</b></html>");
                    //man fragt alle nötigen informationen ab
                    String firstCountry = this.getInformation("Land 1:");
                    String secondCountry = this.getInformation("Land 2:");
                    String firstGoals = this.getInformation("Tore Land 1:");
                    String secondGoals = this.getInformation("Tore Land 2:");
                    
                    //Spiel wird über den Storage der liste hinzugefügt
                    if(storage.addGameByStrings(new String[]{firstCountry, secondCountry, firstGoals, secondGoals})){
                        this.showWarning("Erfolgreich gespeichert!");
                    } else {
                        this.showWarning("Ungültige eingabe oder ähnliches!");
                    }
                    break;
                case 2:     
                    this.setLabelHeight(80);
                    this.putInformation("<html><b>Im anderen Fenster eine Der Gruppen eingeben.</b></html>");
                    String groupName = this.getInformation("Gruppe eingeben (A-H):");
                    //reguläre ausdrücke. Die abfrage ist nur bei a-h A-H und einem zeichen wahr
                    if(groupName.matches("[A-Ha-h]{1}")){
                        //Die möglichen spiele über die methode generieren, touppercase falls jemand klein schreibt
                        ArrayList<Country[]> matches = storage.getGroup(groupName.toUpperCase()).calculatePossibleMatches();
                        //ausgeben
                        String renderedList = renderMatches(matches);
                        this.setLabelHeight(100);
                        this.putInformation(renderedList);
                        this.showWarning("List rendered");
                    } else {
                        this.showWarning("Ungültige eingabe!");
                    }
                    break;
                case 3:
                    //rows = anzahl der zeilen
                    int rows = this.renderGameList();
                    //höhe anpassen an die liste (mit puffer)
                    this.setLabelHeight((rows * 20)+30);
                    this.showWarning("Game List has been rendered");
                    break;
                case 4:
                    this.storage.saveData();
                    break;
                case 10:
                    System.exit(0);
                    break;
            }
        } catch(NumberFormatException e) {
            this.showWarning("Ungültige eingabe!");
        }
    }
    
    public void putInformation(String message) {
        frame.setVisible( true );
        lbResult.setText(message);
    }
  
  //das label dem Text anpassen
  public void setLabelHeight(int height){
      lbResult.setBounds(0, 0, lbResult.getWidth(), height);
      this.updateWindowHeight();
  }
  
  public void updateWindowHeight(){
      panel.setPreferredSize( new Dimension(310, lbResult.getHeight()) );
      frame.pack();
  }
  
  public String getInformation(String message){
      return JOptionPane.showInputDialog(message);  
  }
  
  public String renderMatches(ArrayList<Country[]> countries){
      String result = "<html>";
      for(int i=0; i<countries.size(); i++){
          Country c1 = countries.get(i)[0];
          Country c2 = countries.get(i)[1];
          result += c1.getName() + " : " + c2.getName() + "<br>";
      }
      result += "</html>";
      return result;
  }
  
  public void showWarning(String message){
      JOptionPane.showMessageDialog(frame, message);
  }
  
  public int renderGameList(){
      String result = "<html>";
      result = result + "<b>Land1:Land2 Tore1:Tore2</b><br><br>";
      for(Game game : storage.getGames()){          
          result = result + storage.getCountry(game.getFirstCountry()).getName() + ":" + storage.getCountry(game.getSecondCountry()).getName() + " | " + game.getFirstGoals() + ":" + game.getSecondGoals() + "<br>";
      }
      result =  result + "</html>";
      this.putInformation(result);
      //return größe um später die Windowgröße zu setzen
      return storage.getGames().size();
    }
    
  public static void main(String[] args){
        //ui erstelle objekt 
        UI ui = new UI();
        //erstelle storage
        ui.storage = new Storage();
        //Output vom programm startet optionen in einem loop
        while(true){
            ui.standardAction();
        }
    }
}
