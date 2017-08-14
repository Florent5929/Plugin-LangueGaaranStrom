package fr.Florent59.LangueGaaranStrom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin{

	public static List<String> Listemots;
	public static List<String> ListemotsTrad;
	public static String pseudolistlgs = new String();
	
	@Override
	public void onEnable(){
		
		
	if (!this.getDataFolder().exists()) { 
		this.saveDefaultConfig();
	    this.getConfig().options().copyDefaults(true);
	    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Un dossier de configuration a été crée. Merci de mettre le csv à l'intérieur et de reload le serveur.");
	        							}
	
	     // S'il n'y a pas de dossier et de fichier de configuration, on crée ceux par défaut. 
	    
	    
	    		else {
	    
	    		FileReader monFichier = null;
	    		BufferedReader tampon = null;

	    			try {
	    			monFichier = new FileReader(this.getDataFolder() + "\\MotsGaaran.csv");
	    			tampon = new BufferedReader(monFichier);
		
	    			int i = -1; // On initialise i à -1 pour que la première ligne du csv ne soit pas sauvegardée (car elle contient les en-têtes.)
	    			Listemots = new ArrayList<String>();
	    			ListemotsTrad = new ArrayList<String>();
	    			// On initialise à nouveau les listes.
	    			
	    					while (true) {
	    					// Lit une ligne de MotsGaaran.csv
	    					String ligne = tampon.readLine();
	    					// Vérifie la fin de fichier
	    					if (ligne == null)
	    					break;
	    					
	    						if (i >= 0){
	    							
	    						String[] parts = ligne.split(";");
	    						Listemots.add(parts[0]);
	    						ListemotsTrad.add(parts[1]);
	    						
	    								   }
	    						i=i+1;
		
	    								} // Fin du while
	    					
	    			} catch (IOException exception) {
	    				exception.printStackTrace();
	    											} finally {
	    													try {
	    														tampon.close();
	    														monFichier.close();
	    														} catch(IOException exception1) {
	    														exception1.printStackTrace();
		
	    																						}
	    													  }
		
		
		List<String> JoueursLGS  = getConfig().getStringList("JoueursLGS");
		pseudolistlgs = "";
		
		
        	for (String pseudo : JoueursLGS){
        	pseudolistlgs = pseudolistlgs + pseudo + ", ";
        } // On récupère les JoueursLGS listés dans la configuration pour les ranger dans une chaîne de caractères.
         // Elle aura la forme suivante : Pseudo1, Pseudo2, Pseudo3, [etc]
		
        	
        	PluginManager pm = getServer().getPluginManager();
    		pm.registerEvents(new Langage(), this);
    		// Ces deux lignes servent à enregistrer des évènements que l'on pourra exploiter dans Langage.java.
    		// En l'occurence l'évènement qui nous intéresse est celui qui se déclenche quand un joueur parle.
    	
        	
        	
	    		}	
						}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
	Player player = (Player) sender;
	if(sender instanceof Player ){
		
		// On vérifie que l'entité qui exécute la commande est un joueur.
		
		 if(label.equalsIgnoreCase("lgs")){ // Si c'est la commande /lgs ...
			 List<String> JoueursLGS  = getConfig().getStringList("JoueursLGS");
			 
				 if(!Main.pseudolistlgs.contains(player.getName())){ 
					 JoueursLGS.add(player.getName());
				 getConfig().set("JoueursLGS", JoueursLGS);
				 saveConfig(); // Si le joueur n'est pas dans la chaîne de JoueursLGS "Pseudo1, Pseudo2, etc", on le rajoute à la config.
				 player.sendMessage(ChatColor.DARK_AQUA + "Votre personnage parle désormais en langue locale.");
				 	getServer().getPluginManager().disablePlugin(this);
		            getServer().getPluginManager().enablePlugin(this);
		            Bukkit.reload();
		            // Le disablePlugin/enablePlugin ne fonctionne pas, faute de mieux ou de correction pour l'instant j'utilise un reload.
				 } else {
						player.sendMessage(ChatColor.RED + "Votre personnage parle déjà en langue locale !");
						 } // Sinon, on envoie un message disant qu'il existe déjà.
				
			//getServer().getPluginManager().disablePlugin(this);
           // getServer().getPluginManager().enablePlugin(this);
            
		 								}
		 
		 // On relance le plugin pour qu'il recrée la chaîne de caractères en prenant en compte la modification.
		
		 
		 
		 else if(label.equalsIgnoreCase("lfr")){ // Si c'est la commande /lfr ...
			 List<String> JoueursLGS  = getConfig().getStringList("JoueursLGS");
			 
					 if(Main.pseudolistlgs.contains(player.getName())){ 
						 JoueursLGS.remove(player.getName());
					 getConfig().set("JoueursLGS", JoueursLGS);
					 saveConfig(); // Si le joueur est dans la chaîne de JoueursLGS "Pseudo1, Pseudo2, etc", on le supprime de la config.
					 player.sendMessage(ChatColor.DARK_AQUA  + "Votre personnage parle désormais en langue commune.");
					 getServer().getPluginManager().disablePlugin(this);
			         getServer().getPluginManager().enablePlugin(this);
					 } else {
					player.sendMessage(ChatColor.RED  + "Votre personnage parle déjà en langue commune !");
					 } // Sinon, on envoie un message disant qu'il n'existe pas.
					 
				//getServer().getPluginManager().disablePlugin(this);
                // getServer().getPluginManager().enablePlugin(this);	
                
		 									}
			 // On relance le plugin pour qu'il recrée la chaîne de caractères en prenant en compte la modification.
			
		 
		 
				}
		
		return false;
		

	}
}