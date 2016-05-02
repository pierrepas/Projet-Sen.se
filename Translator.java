package sen.se;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.philips.lighting.hue.sdk.utilities.impl.PHLog.i;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** @author 
 * Hassainia Mohamed
 * Souissi Oussama
 * Jommetti Leevan
 * Vouillamoz Fred
 * Ouali-Alami Mohamed
 * Pasquier Pierre
 * Petre R�my
 * Charbonnier Jonathan
 * Priscoglio Florent
 * Ziadeh Mohamad
 */

/**
 * La classe Translator r�cup�re des messages du Mosquitto
 * et effectue une traduction afin de pouvoir controler la lampe
 * en lui envoyant les commandes d�ja pr�d�finies par PhilipsHue
 *
 */


public class Translator {
 
   

 
 
    public List<PhilipsHue> philipsHue;
    public Mqtt_pub mqtt_pub ;
    public Mqtt_sub mqtt_sub ;
    public int id;
    
    
/*
    
 Le constructeur de la classe   Translator
    
 */ 
    public Translator(int idd)
    {
      this.philipsHue = new ArrayList<PhilipsHue>();
      this.mqtt_pub =new Mqtt_pub();
      this.mqtt_sub =new Mqtt_sub();
      this.id  = idd;
      
    }
 

    Translator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Translator(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
    // -l = lampe
		on change de lampe
		
    // -i = ip
		on change de pont 
	
    // -c = color
		on change la couleur en rvb
		
    // -t = topic
		on change le topic dont on recupere et traite les commandes
		
    // -b = brightness
		on change la luminosite de la lampe
		
    // -h = help 
		affiche la liste des commandes disponibles
		
	// -temp = temperature
		modifie la couleur de la lampe en fonction de la temperature capt�e par un cookie "capteur de temperature"
		
    // On utilise le format RGB pour choisir les couleurs 
    // 
	*/
 String lampe = null;
    String ip = null ;
    String color = null;
    String topic = null;
    String brightness = null ;
    String r = null;  
    String g = null;
    String b = null;
    String TEMP = null ;
    
    
    void Translate(String msg) throws IOException{
    
    Color co = new Color(Integer.parseInt(r), Integer.parseInt(g), Integer.parseInt(b));;
    String help= null ;
        
    String indiceUtile[]=new String[msg.split("\\s").length -1];
    indiceUtile = msg.split("\\s");
	/*
	on recoit une commande que l'on va d�composer a chaque espace ,et stocker chaque morceau dans un tableau
	*/
	
    for (int j = 1; j < indiceUtile.length; j++) {
        System.out.println(indiceUtile[j]);
        


	
        switch (indiceUtile[j]) {
		/* on associe a la variable concerne par la commande sa valeur ( par exempe temp = 25% )	*/
        case "-l":
            lampe =indiceUtile[j+1];
            break;    
        
        case "-i":
            ip =indiceUtile[j+1];
            break;    
        case "-c":
            color =indiceUtile[j+1];
            break;    
        case "-t":
            topic =indiceUtile[j+1];
            break;    
        case "-b":
            brightness =indiceUtile[j+1];
            break;    
        case "-Temp":
            TEMP =indiceUtile[j+1];
            break;    
        case "-rgb":
            int z=0;
			
			/*on recupere chaque valeur rgb en concatenant chaque chiffre afin d'obtenir le nombre correspondant a la couleurs d�siree
			exemple : 
			
			-rgb 255/45/65 
			
			tour de boucle : valeur
			
			
			1 - r = 2
			2 - r = 25
			3 - r = 255
			
			r rencontre un "/" element delimitateur des differentes couleurs on passe donc a l'element suivant G
			
			
			*/
			
            for (int i = 0; i <indiceUtile[j+1].length() -1 ; i++) {
                if (indiceUtile[j+1].charAt(i) != '/' ){
                    if (z==0)
                        r = indiceUtile[j+1].charAt(i);
                    if(z==1)
                        g = indiceUtile[j+1].charAt(i);
                    if(z==2)
                        b = indiceUtile[j+1].charAt(i);
                }
                else
                    z++;
            }
                
        break;        
        default:
            j=j+1;          
            
        }
    }
    
	
	/* on regarde la premier argument du message recuper� qui va definir l'action a r�aliser */
    switch (indiceUtile[0]) {
        
        case "searchBridge":
          
             this.philipsHue.get(this.id).searchBridge();
             System.out.println("recherche Bridge effectuer" );
           
            ;   
        break;    
        case "setIpAdress":
       
            this.philipsHue.get(id).setIpAdresse(ip);
            System.out.println("mettre adresse Ip "+ip);
            
        break;    
        case "selectHue":
           
            philipsHue.get(this.id).setHue(this.id,Integer.parseInt(lampe));
          
           System.out.println("met la couleur "+color+" a la lampe"+lampe);
         
        break;    
    
        case "setBrightness":
         
           philipsHue.get(this.id).setBrightness(Integer.parseInt(brightness), Integer.parseInt(lampe)); /*si pas de lampe en parametre?*/
		   
           System.out.println("change la  luminosite en "+brightness+" de  la lampe "+ lampe);
      
        break;
        
        case "setBrightnessAndColor":
           philipsHue.get(this.id).setBrightnessAndColor(this.id, Integer.parseInt(brightness), Integer.parseInt(lampe));
        break;   
        
        case "connectToLastKnownIP":
            philipsHue.get(this.id).connectToLastKnownIP() ;
        break;
        
        case "setRGB":
            philipsHue.get(this.id).setRGB(co,Integer.parseInt(lampe));
        break; 
        case "changerCouleurSelonTemp" :
            philipsHue.get(this.id).changerCouleurSelonTemp(TEMP,lampe);
        
        default:
            System.err.println("message invalide ");             
    } 
               
   }
 
}
    
  
	
			
			
		