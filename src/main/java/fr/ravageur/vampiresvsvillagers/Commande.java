package fr.ravageur.vampiresvsvillagers;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commande implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender expediteur, Command commande, String label, String[] arguments) 
    {
        arguments = definirArgumentsEnMinuscules(arguments);
        if(arguments.length > 0 && expediteur instanceof Player)
        {
            Joueur joueur = VampiresVSVillagers.getJoueur(expediteur.getName());
            
            if(arguments[0].equals("debug"))
            {
                if(arguments[1].equals("set") && arguments.length == 4)
                {
                    Player player = Bukkit.getPlayer(arguments[2]);
                    double valeur = 1;
                    try 
                    {
                        valeur = Double.valueOf(arguments[4]);
                    } 
                    catch (NumberFormatException exception) 
                    {
                        expediteur.sendMessage("[VampiresVSVillagers]: La valeur ne doit être composé que de chiffres ! (Virgules autorisées");
                    }
                    if(player != null)
                    {
                        switch(arguments[3])
                        {
                            case "mh":
                                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(valeur);
                            break;
                            case "fr":
                                player.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(valeur);
                            break;
                            case "kr":
                                player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(valeur);
                            break;
                            case "ms":
                                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(valeur);
                            break;
                            case "fs":
                                player.getAttribute(Attribute.GENERIC_FLYING_SPEED).setBaseValue(valeur);
                            break;
                            case "ad":
                                player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(valeur);
                            break;
                            case "ak":
                                player.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(valeur);
                            break;
                            case "as":
                                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(valeur);
                            break;
                            case "ar":
                                player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(valeur);
                            break;
                            case "at":
                                player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(valeur);
                            break;
                            case "lu":
                                player.getAttribute(Attribute.GENERIC_LUCK).setBaseValue(valeur);
                            break;
                        }
                        expediteur.sendMessage("Liste d'attributs disponible: \n[GENERIC_MAX_HEALTH]: mh\n[GENERIC_FOLLOW_RANGE]: fr\n[GENERIC_KNOCKBACK_RESISTANCE]: kr\n[GENERIC_MOVEMENT_SPEED]: ms\n[GENERIC_FLYING_SPEED]: fs\n[GENERIC_ATTACK_DAMAGE]: ad\n[GENERIC_ATTACK_KNOCKBACK]: ak\n[GENERIC_ATTACK_SPEED]: as\n[GENERIC_ARMOR]: ar\n[GENERIC_ARMOR_TOUGHNESS]: at\n[GENERIC_LUCK]: lu");
                    }
                }
                else if(arguments[1].equals("get"))
                {
                    Player player = Bukkit.getPlayer(arguments[2]);
                    switch(arguments[3])
                    {
                        case "mh":
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                        break;
                        case "fr":
                            player.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getBaseValue();
                        break;
                        case "kr":
                            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getBaseValue();
                        break;
                        case "ms":
                            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                        break;
                        case "fs":
                            player.getAttribute(Attribute.GENERIC_FLYING_SPEED).getBaseValue();
                        break;
                        case "ad":
                            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
                        break;
                        case "ak":
                            player.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).getBaseValue();
                        break;
                        case "as":
                            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getBaseValue();
                        break;
                        case "ar":
                            player.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue();
                        break;
                        case "at":
                            player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getBaseValue();
                        break;
                        case "lu":
                            player.getAttribute(Attribute.GENERIC_LUCK).getBaseValue();
                        break;
                    }
                    expediteur.sendMessage("Liste d'attributs disponible: \n[GENERIC_MAX_HEALTH]: mh\n[GENERIC_FOLLOW_RANGE]: fr\n[GENERIC_KNOCKBACK_RESISTANCE]: kr\n[GENERIC_MOVEMENT_SPEED]: ms\n[GENERIC_FLYING_SPEED]: fs\n[GENERIC_ATTACK_DAMAGE]: ad\n[GENERIC_ATTACK_KNOCKBACK]: ak\n[GENERIC_ATTACK_SPEED]: as\n[GENERIC_ARMOR]: ar\n[GENERIC_ARMOR_TOUGHNESS]: at\n[GENERIC_LUCK]: lu");
                }
                else if(arguments[1].equals("test"))
                {
                    if(expediteur instanceof Player)
                    {
                        Player player = (Player) expediteur;
                        joueur = new Joueur(player, RoleJoueur.VILLAGEOIS, null);
                        joueur.getJoueurRegarder();
                    }
                }
                return true;
            }
            if(arguments.length == 2)
            {
                deuxArguments(joueur, arguments);
            }
            else if(arguments.length == 3)
            {

            }
            return true;
        }
        return false;
    }

    /**
     * Cette méthode est appelé pour deux arguments (Ainsi pour les yeux ce sera plus pratique)
     * @param joueur
     * @param arguments
    */
    private static void deuxArguments(Joueur joueur, String[] arguments)
    {
        if(joueur.getRoleJoueur().equals(RoleJoueur.PRETRE) && arguments[0].equals("heal"))
        {
            commandeSoinPretre(joueur, arguments[1]);
        }
        else if(arguments[0].equals("enable"))
        {
            activer(joueur, arguments);
        }
        else if(arguments[0].equals("disable"))
        {
            desactiver(joueur, arguments);
        }

    }

    /**
     * Permet d'éxécuter le sort soin du prêtre si c'est possible par rapport à son mana.
     * @param joueur
     * @param argument
     */
    private static void commandeSoinPretre(Joueur joueur, String argument)
    {
        Joueur joueurCible = VampiresVSVillagers.getJoueur(argument);
        if(joueurCible != null)
        {
            joueur.soin(joueurCible);
            joueur.envoyerUnMessage("Vous avez guéri le joueur " + joueurCible.getNomJoueur() + ".");
        }
    }

    /**
     * Toutes les commandes en relation avec l'état activer sont dans cette méthode.
     * @param joueur
     * @param arguments
     */
    private static void activer(Joueur joueur, String[] arguments)
    {
        if(arguments[1].equals("mv") || arguments[1].equals("ms") || arguments[1].equals("mouvement"))
        {
            joueur.cacherOuMontrerVitesse(1);
        }
    }

    /**
     * Toutes les commandes en relation avec l'état désactiver sont dans cette méthode.
     * @param joueur
     * @param arguments
     */
    private static void desactiver(Joueur joueur, String[] arguments)
    {
        if(arguments[1].equals("mv") || arguments[1].equals("ms") || arguments[1].equals("mouvement"))
        {
            joueur.cacherOuMontrerVitesse(-1);
        }
    }

    /**
     * Permet de mettre les lettres des arguments en minuscules.
     * @param arguments
     * @return String[]
     */
    private static String[] definirArgumentsEnMinuscules(String[] arguments)
    {
        for(String argument : arguments) 
        {
            argument.toLowerCase();
        }
        return arguments;
    }
}