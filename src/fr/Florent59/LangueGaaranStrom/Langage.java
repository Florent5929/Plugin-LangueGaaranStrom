package fr.Florent59.LangueGaaranStrom;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class Langage implements Listener {
	
	
	@EventHandler (priority = EventPriority.LOW)
	public void QuandUnJoueurParle(AsyncPlayerChatEvent e){


	Player p = e.getPlayer();
	String Prefixe = e.getMessage().substring(0, 1); // 1er caractère du message que le joueur tente d'envoyer.
	String Prefixe2 = e.getMessage().substring(0, 2); // 2 premiers caractères du message que le joueur tente d'envoyer.
	String PrefixeNum2 = e.getMessage().substring(1, 2); // 2ème caractère du message que le joueur tente d'envoyer.
	Random r = new Random();
	float distanceRemove;
	int NumFirstCharMessageSansPrefixe;
		
		
		if(Main.pseudolistlgs.contains(p.getName()) &&
				!Prefixe.equals("$") && // channel aide
				!Prefixe.equals(":") && // télépathie
				!Prefixe.equals("*") && // action
				!Prefixe.equals("?") && // commande staff
				!Prefixe.equals("=") && // commande staff
				!Prefixe.equals("{") && // commande staff
				!Prefixe.equals("@") && // commande staff
				!Prefixe2.equals("->") && // télépathie individuelle
				!PrefixeNum2.equals("(") && // HRP de cri, de chucho, etc...
				!Prefixe.equals(">") && // télépathie individuelle HRP
				!Prefixe.equals("[") && // HRP global
				!Prefixe.equals("(") // HRP local
				
		){ // Si le joueur est LGS et seulement s'il n'écrit pas dans les channels ci-dessus, on agit.
	
		
			 if( Prefixe.equals("!")) 
				 
				 {distanceRemove = 100F;
				 NumFirstCharMessageSansPrefixe = 1;} 
			 
			 	else if (Prefixe.equals("+"))
			 		
				 {distanceRemove = 20F;
				 NumFirstCharMessageSansPrefixe = 1;} 
			 
			 		else if (Prefixe.equals("#"))
			 			
			 		{distanceRemove = 5F;
			 		NumFirstCharMessageSansPrefixe = 1;}
			 
			 			else if (Prefixe.equals("^"))
			 				
			 			{distanceRemove = 2F;
			 			NumFirstCharMessageSansPrefixe = 1;}
			 
			 				else if (Prefixe.equals("-"))
				 			
			 				{distanceRemove = 10F;
			 				NumFirstCharMessageSansPrefixe = 1;}
								
			 					else { 
			 						
			 					distanceRemove = 15F;
			 					NumFirstCharMessageSansPrefixe = 0;
			 					Prefixe = "";
			 					} 
			 
// En fonction du channel la distance limite de réception du message varie.
// Ainsi que le numéro du premier caractère du message sans préfixe.
// S'il n'y a pas de préfixe, c'est que l'on est dans le channel local.
// Le cas échéant, on réaffecte à Prefixe une chaîne vide.
				
		
			for (Player onlinePlayer : e.getPlayer().getServer().getOnlinePlayers()) {
			if (e.getPlayer().getLocation().distance(onlinePlayer.getLocation()) > distanceRemove) {
			e.getRecipients().remove(onlinePlayer);
			} // On vire de la liste des destinataires du message ceux qui sont trop loin.
			} 
			
			
			for (Player t : e.getRecipients()) {
				if(t.getName().equals(p.getName()) ||                          
				t.isOp()){
				t.sendMessage(ChatColor.GRAY  + "[Traduction de " + p.getName() + "] : " +  e.getMessage().substring(NumFirstCharMessageSansPrefixe));
				}
				// On envoie à chaque destinataire la traduction en privé, s'il est OP ou si c'est le joueur lui-même.
			}
			
			
			String message = e.getMessage().substring(NumFirstCharMessageSansPrefixe); // On enlève le préfixe du message, s'il y en a un.
			// "!J'ai faim" deviendra "J'ai faim"
			// "! J'ai faim" deviendra " J'ai faim"
			
			
			Pattern pattern = Pattern.compile("[A-Za-z]");  
			// On définit la classe des caractères que l'on recherche (alphanumérique).
            Matcher matcher = pattern.matcher(message.substring(0,1)); 
            // On definit la chaine dans laquelle on va faire notre recherche,
            // le 1er caractère du message donc, qui ne comporte donc plus de préfixe.
            
            
				if(!matcher.matches()){ 
					// Si ce 1er caractère n'est pas alphanumérique... 
					// (exemple: " J'ai faim !", le 1er caractère est un espace)
					message = message.substring(1); // Alors on le vire du message (" J'ai faim" deviendra "J'ai faim")
				}
				
			String[] parts = message.split(" "); // On coupe le message et on le range dans un tableau
			// La découpe se fait selon les espaces.
			// Si le message est "J'ai faim", parts[0] sera égal à "J'ai" et parts[1] égal à "faim".
			String listemots = "";
			int nbmots = parts.length;
			String ponctuation = ".";
					
					if( 
					   e.getMessage().substring(e.getMessage().length()-1).equals("!") || 
					   e.getMessage().substring(e.getMessage().length()-1).equals("?")
					){
					nbmots = nbmots -1;
					ponctuation = " " + e.getMessage().substring(e.getMessage().length()-1);
					} 
        // Si le message se terminait par un ! ou un ?, on range ce ! ou ce ? dans la variable ponctuation.
					// On ne compte pas cette ponctuation comme étant un mot, d'où le nbmots-1
			
			String fin = " ";
			int col = Main.Listemots.size();
			String motcompare = "";
			
			for(int j = 0; j <= nbmots-1; j++){ // Pour chaque mot du message...
				for(int i = 0; i <= col-1; i++){ // ... et pour chaque mot de français répertorié dans Listemots...
					
					// ... on va essayer de voir si le mot du message n'est pas répertorié dans Listemots
					
					if (i == 0){ // Si c'est la première fois que l'on fait une recherche pour tel mot du message,

					motcompare = parts[j];
					// on range le mot dans la variable motcompare car parts[j] ne le conservera pas.
					
						 String finmotcompare = motcompare.substring(motcompare.length()-1);
						 // On range dans finmotcompare le dernier caractère du mot.
						 // Exemple : si le mot est "ami," le dernier caractère sera ","
					
						if (finmotcompare.equals(".") || 
						    finmotcompare.equals(",") || 	
							finmotcompare.equals("!") || 
							finmotcompare.equals("?"))
						{
							// si le dernier caractère est un point, une virgule, un point d'exclamation ou d'interrogation...
							motcompare = motcompare.substring(0, motcompare.length()-1);	
							// ... on l'enlève du mot car cela va bloquer la traduction sinon.
							// "ami," deviendra donc "ami"
						}
							
							;}
					
					
					
					if(motcompare.equalsIgnoreCase(Main.Listemots.get(i))) 
					{	
						// Si on trouve le mot dans la liste de mots de français de Listemots...
					 parts[j] = Main.ListemotsTrad.get(i);
					// ... alors on prend sa traduction et on la range dans parts[j]
					break;} // et on arrête la recherche là, on passe au mot suivant.
					parts[j] = Main.ListemotsTrad.get(r.nextInt(col -1));
					// sinon, on met dans parts[j] un mot en langue locale de notre liste au hasard.
				}
			}
			
			
			
			for(int i = 0; i <= nbmots-1; i++){		
				if(i == nbmots-1){ fin = "";}
				listemots = listemots + parts[i] + fin;	
			// On ajoute tous les mots en langue locale dans listemots
			}
			
			e.setMessage(Prefixe +  listemots + ponctuation);
			// On affiche la liste des mots.
			// On ajoute le préfixe éventuel et la ponctuation éventuelle (par défaut, un point).
		
		
			
		} // fin du if
					
				
		} // fin du public void QuandUnJoueurParle(AsyncPlayerChatEvent e)
	
		
	} // fin du public class Langage implements Listener
