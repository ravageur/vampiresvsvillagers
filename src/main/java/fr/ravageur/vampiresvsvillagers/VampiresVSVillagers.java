package fr.ravageur.vampiresvsvillagers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.ravageur.vampiresvsvillagers.Map.PointRegion;
import fr.ravageur.vampiresvsvillagers.Map.Region;

public class VampiresVSVillagers extends JavaPlugin
{
    private static VampiresVSVillagers instance;
    private static ArrayList<Joueur> listeDesJoueurs = new ArrayList<>();

    @Override
    public void onEnable()
    {
        getCommand("vvp").setExecutor(new Commande());
        getServer().getPluginManager().registerEvents(new Events(), this);
        instance = this;
    }

    @Override
    public void onDisable()
    {

    }

    /**
     * Permet d'obtenir l'instance du plugin.
     * @return VampiresVSVillagers
     */
    public static VampiresVSVillagers getPlugin()
    {
        return instance;
    }

    /**
     * Debug à enlever dans la mise en production
     * @param joueur
     * @param roleJoueur
     */
    public static void ajouterJoueur(Joueur joueur, RoleJoueur roleJoueur)
    {
        listeDesJoueurs.add(joueur);
    }

    public static void enleverJoueur(Joueur joueur)
    {
        listeDesJoueurs.remove(joueur);
    }

    /**
     * Permet d'obtenir un joueur, si aucun joueur n'est trouvé alors la méthode renvoie null.
     * @param uuidJoueur
     * @return Joueur
     */
    public static Joueur getJoueur(String nomDuJoueur)
    {
        for(Joueur joueur : listeDesJoueurs) 
        {
            if(joueur.getNomJoueur().equals(nomDuJoueur))
            {
                return joueur;
            }
        }
        return null;
    }

    public static void initialisation()
    {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        scoreboard.registerNewObjective("Titre", "dummy", Color.blue + "Vampires VS Villagers");

        ArrayList<Player> listeDesPlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        ArrayList<RoleJoueur> roles = new ArrayList<>(Arrays.asList(RoleJoueur.values()));

        Random aleatoire = new Random();
        int nbaleatoireRole = aleatoire.nextInt(roles.size());
        int nbAleatoireJoueur = aleatoire.nextInt(listeDesPlayers.size());

        while(listeDesPlayers.size() != 0)
        {
            if(roles.get(0).equals(RoleJoueur.FILLETTE))
            {
                listeDesJoueurs.add(new Joueur(listeDesPlayers.get(nbAleatoireJoueur), RoleJoueur.FILLETTE, scoreboard));
                listeDesPlayers.remove(nbAleatoireJoueur);
                roles.remove(0);
            }
            else if(roles.get(0).equals(RoleJoueur.VAMPIRE_LOYAL))
            {
                listeDesJoueurs.add(new Joueur(listeDesPlayers.get(nbAleatoireJoueur), RoleJoueur.FILLETTE, scoreboard));
                listeDesPlayers.remove(nbAleatoireJoueur);
                roles.remove(0);
            }
            else
            {
                listeDesJoueurs.add(new Joueur(listeDesPlayers.get(nbAleatoireJoueur), roles.get(nbaleatoireRole), scoreboard));
                if(!roles.get(nbaleatoireRole).equals(RoleJoueur.VAMPIRE))
                {
                    roles.remove(nbaleatoireRole);
                }
                listeDesPlayers.remove(nbAleatoireJoueur);
            }
            nbaleatoireRole = aleatoire.nextInt(roles.size());
            nbAleatoireJoueur = aleatoire.nextInt(listeDesPlayers.size());
        }
        teleporterToutLesJoueurs();
    }

    /**
     * Permet de téléporter tout les joueurs dans une région par rapport au centre de la map
     */
    private static void teleporterToutLesJoueurs()
    {
        for(Joueur joueur : listeDesJoueurs)
        {
            joueur.teleporterLeJoueurAleatoirement(new Region(new PointRegion(2000, 5000, 2000), new PointRegion(-2000, 5000, -2000)));

        }
    }
}
