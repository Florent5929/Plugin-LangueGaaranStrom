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
	    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Un dossier de configuration a �t� cr�e. Merci de mettre le csv � l'int�rieur et de reload le serveur.");
	        							}
	
	     // S'il n'y a pas de dossier et de fichier de configuration, on cr�e ceux par d�faut. 
	    
	    
	    		else {
	    
	    		FileReader monFichier = null;
	    		BufferedReader tampon = null;

	    			try {
	    			monFichier = new FileReader(this.getDataFolder() + "\\MotsGaaran.csv");
	    			tampon = new BufferedReader(monFichier);
		
	    			int i = -1; // On initialise i � -1 pour que la premi�re ligne du csv ne soit pas sauvegard�e (car elle contient les en-t�tes.)
	    			Listemots = new ArrayList<String>();
	    			ListemotsTrad = new ArrayList<String>();
	    			// On initialise � nouveau les listes.
	    			
	    					while (true) {
	    					// Lit une ligne de MotsGaaran.csv
	    					String ligne = tampon.readLine();
	    					// V�rifie la fin de fichier
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
        } // On r�cup�re les JoueursLGS list�s dans la configuration pour les ranger dans une cha�ne de caract�res.
         // Elle aura la forme suivante : Pseudo1, Pseudo2, Pseudo3, [etc]
		
        	
        	PluginManager pm = getServer().getPluginManager();
    		pm.registerEvents(new Langage(), this);
    		// Ces deux lignes servent � enregistrer des �v�nements que l'on pourra exploiter dans Langage.java.
    		// En l'occurence l'�v�nement qui nous int�resse est celui qui se d�clenche quand un joueur parle.
    	
        	
        	
	    		}	
						}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
	Player player = (Player) sender;
	if(sender instanceof Player ){
		
		// On v�rifie que l'entit� qui ex�cute la commande est un joueur.
		
		 if(label.equalsIgnoreCase("lgs")){ // Si c'est la commande /lgs ...
			 List<String> JoueursLGS  = getConfig().getStringList("JoueursLGS");
			 
				 if(!Main.pseudolistlgs.contains(player.getName())){ 
					 JoueursLGS.add(player.getName());
				 getConfig().set("JoueursLGS", JoueursLGS);
				 saveConfig(); // Si le joueur n'est pas dans la cha�ne de JoueursLGS "Pseudo1, Pseudo2, etc", on le rajoute � la config.
				 player.sendMessage(ChatColor.DARK_AQUA + "Votre personnage parle d�sormais en langue locale.");
				 	getServer().getPluginManager().disablePlugin(this);
		            getServer().getPluginManager().enablePlugin(this);
		            Bukkit.reload();
		            // Le disablePlugin/enablePlugin ne fonctionne pas, faute de mieux ou de correction pour l'instant j'utilise un reload.
				 } else {
						player.sendMessage(ChatColor.RED + "Votre personnage parle d�j� en langue locale !");
						 } // Sinon, on envoie un message disant qu'il existe d�j�.
				
			//getServer().getPluginManager().disablePlugin(this);
           // getServer().getPluginManager().enablePlugin(this);
            
		 								}
		 
		 // On relance le plugin pour qu'il recr�e la cha�ne de caract�res en prenant en compte la modification.
		
		 
		 
		 else if(label.equalsIgnoreCase("lfr")){ // Si c'est la commande /lfr ...
			 List<String> JoueursLGS  = getConfig().getStringList("JoueursLGS");
			 
					 if(Main.pseudolistlgs.contains(player.getName())){ 
						 JoueursLGS.remove(player.getName());
					 getConfig().set("JoueursLGS", JoueursLGS);
					 saveConfig(); // Si le joueur est dans la cha�ne de JoueursLGS "Pseudo1, Pseudo2, etc", on le supprime de la config.
					 player.sendMessage(ChatColor.DARK_AQUA  + "Votre personnage parle d�sormais en langue commune.");
					 getServer().getPluginManager().disablePlugin(this);
			         getServer().getPluginManager().enablePlugin(this);
					 } else {
					player.sendMessage(ChatColor.RED  + "Votre personnage parle d�j� en langue commune !");
					 } // Sinon, on envoie un message disant qu'il n'existe pas.
					 
				//getServer().getPluginManager().disablePlugin(this);
                // getServer().getPluginManager().enablePlugin(this);	
                
		 									}
			 // On relance le plugin pour qu'il recr�e la cha�ne de caract�res en prenant en compte la modification.
			
		 
		 
				}
		
		return false;
		

	}
}