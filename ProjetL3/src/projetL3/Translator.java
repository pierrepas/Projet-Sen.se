package projetL3;
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
 * Petre Rï¿½my
 * Charbonnier Jonathan
 * Priscoglio Florent
 * Ziadeh Mohamad
 */

/**
 * La classe Translator rï¿½cupï¿½re des messages du Mosquitto
 * et effectue une traduction afin de pouvoir controler la lampe
 * en lui envoyant les commandes dï¿½ja prï¿½dï¿½finies par PhilipsHue
 *
 */


public class Translator {
 
   

 
 
    public List<PhilipsHue> philipsHue; //liste des boitiers philips HUE connecté a l'application
	public int id; // id est le numero du boitier a considerer dans la liste de ponts
    
    
/*
    
 Le constructeur de la classe   Translator
    
 */ 
   
 

  
    public Translator(PhilipsHue p, int idd) {
    	this.philipsHue = new ArrayList<PhilipsHue>();
        this.id  = idd;
        philipsHue.add(p);
	}



	/*
    // -l = lampe
		on change de lampe
		
    // -i = ip
		on change l'adresse ip du pont 
	
    // -c = color
		on change la couleur en rvb
		
    // -t = topic
		on change le topic dont on recupere et traite les commandes
		
    // -b = brightness
		on change la luminosite de la lampe
		
    // -h = help 
		affiche la liste des commandes disponibles
		
	// -temp = temperature
		modifie la couleur de la lampe en fonction de la temperature captï¿½e par un cookie "capteur de temperature"
		
    // On utilise le format RGB pour choisir les couleurs 
    // 
	*/
    String lampe = null;
    String user = null ;
    String ip = null ;
    String color = null;
    String topic = null;
    String brightness = null ;
    String r = "";  
    String g = "";
    String b = "";
    String TEMP = null ;
    Color rgb= null ;
    
    
    void Translate(String msg) throws IOException{
        
    String indiceUtile[]=new String[msg.split("\\s").length ];
    indiceUtile = msg.split("\\s");

	/*
	on recoit une commande que l'on va dï¿½composer a chaque espace ,et stocker chaque morceau dans un tableau
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
        case "-u":
            user =indiceUtile[j+1];
            break;    
        case "-b":
            brightness =indiceUtile[j+1];
            break;    
        case "-temp":
            TEMP =indiceUtile[j+1];
            break;    
        case "-rgb":
            int z=0;
			
			/*on recupere chaque valeur rgb en concatenant chaque chiffre afin d'obtenir le nombre correspondant a la couleurs dï¿½siree
			exemple : 
			
			-rgb 255/45/65 
			
			tour de boucle : valeur
			
			
			1 - r = 255
			2 - r = 45
			3 - r = 65
			
			r rencontre un "/" element delimitateur des differentes couleurs on passe donc a l'element suivant G
			
			
			*/
			
            for (int i = 0; i <indiceUtile[j+1].length()  ; i++) {
                if (indiceUtile[j+1].charAt(i) != '/' ){
                    if (z==0)
                        r += indiceUtile[j+1].charAt(i);
                    if(z==1)
                        g += indiceUtile[j+1].charAt(i);
                    if(z==2)
                        b += indiceUtile[j+1].charAt(i);
                }
                else
                    z++;
            }
            
            rgb=new Color(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b));
              r="";g="";b="";  
        break;        
        default:
                     
            
        }
    }
    
	
	/* on regarde la premier argument du message recuperï¿½ qui va definir l'action a rï¿½aliser */
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
        case "setHue":
        	  System.out.println("met la couleur "+color+" a la lampe "+ lampe);
            this.philipsHue.get(this.id).setHue(Integer.parseInt(color),Integer.parseInt(lampe));
          
         
         
        break;    
    
        case "setBrightness":
         
           philipsHue.get(this.id).setBrightness(Integer.parseInt(brightness), Integer.parseInt(lampe)); /*si pas de lampe en parametre?*/
		   
           System.out.println("change la  luminosite en "+brightness+" de  la lampe "+ lampe);
      
        break;
        
        case "setBrightnessAndColor":
           philipsHue.get(this.id).setBrightnessAndColor(Integer.parseInt(color), Integer.parseInt(brightness), Integer.parseInt(lampe));
        break;   
        
        case "connectToLastKnownIP":
           
            philipsHue.get(this.id).connectToLastKnownIP() ;
        break;
        
        case "setRGB":
        	
            System.out.println(r +" "+ g + " "+ b);
        	philipsHue.get(this.id).setRGB(rgb,Integer.parseInt(lampe));
        break; 
        case "setTemperature" :
            philipsHue.get(this.id).setTemperature(Integer.parseInt(TEMP),Integer.parseInt(lampe));
            break ;
        case "connect":
        	
        	philipsHue.get(this.id).connect(ip,user);
        break ;
        default:
            System.err.println("message invalide ");             
    } 
               
   }
 
}
    
  
	
			
			
		