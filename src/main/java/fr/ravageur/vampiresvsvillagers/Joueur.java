package fr.ravageur.vampiresvsvillagers;

import java.awt.Color;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import fr.ravageur.vampiresvsvillagers.Map.PointRegion;
import fr.ravageur.vampiresvsvillagers.Map.Region;

public class Joueur
{
    private Player player;
    private int mana;
    private int manaMax;
    private int sang;
    private RoleJoueur roleJoueur;
    
    private static final double MOUVEMENT_BASE = 0.10000000149011612;

    public Joueur(Player player, RoleJoueur roleJoueur, Scoreboard scoreboard)
    {
        
        //player.setScoreboard(scoreboard);
        this.player = player;
        this.roleJoueur = roleJoueur;
        switch(roleJoueur)
        {
            case FILLETTE:
                initialiserFillette();
            break;
            case VAMPIRE_LOYAL:
                initialiserVampireLoyal();
            break;
            case VAMPIRE:
                initialiserVampire();
            break;
            case GARDE:
                initialiserGarde();
            break;
            case PRETRE:
                initialiserPretre();
            break;
            case CHASSEUR:
                initialiserChasseur();
            break;
            case VILLAGEOIS:
                initialiserVillageois();
            break;
        }
    }

    public Player getJoueurRegarder()
    {
        // float yaw = player.getLocation().getYaw();
        // Location localisationYeuxJoueur = player.getEyeLocation();
        // Vector vecteurAVerifier;
        // for (Entity entitee : player.getNearbyEntities(10, 10, 10)) 
        // {
        //     vecteurAVerifier = entitee.getLocation().toVector().subtract(localisationYeuxJoueur.toVector());
        //     player.sendMessage("Degrée regard joueur: " + localisationYeuxJoueur.getYaw());
        //     player.sendMessage("Degrée regard" + entitee.getName() + ": " + entitee.getLocation().getYaw());
        //     player.sendMessage("Degrée total: " + Math.toDegrees(localisationYeuxJoueur.getDirection().angle(vecteurAVerifier)));
        //     player.sendMessage("VecteurRegardJoueur: " + localisationYeuxJoueur.getDirection().toString());
        //     player.sendMessage("VecteurEntitee: " + vecteurAVerifier.toString());
        // }

        Location localisationYeux = player.getEyeLocation();
        Location localisationEntitee; 
        for(Entity entitee : player.getNearbyEntities(10, 10, 10)) 
        {
            //angle = arccos[
            //(xa * xb + ya * yb + za * zb) / (√(xa2 + ya2 + za2) * √(xb2 + yb2 + zb2))
            //]
            localisationEntitee = entitee.getLocation();
            double resultat = Math.acos((localisationYeux.getX() * localisationEntitee.getX() +
            localisationYeux.getY() * localisationEntitee.getY() +
            localisationYeux.getZ() * localisationEntitee.getZ()) /
            (Math.sqrt(Math.pow(localisationYeux.getX(), 2) + Math.pow(localisationYeux.getY(), 2) + Math.pow(localisationYeux.getZ(), 2))
            * Math.sqrt(Math.pow(localisationEntitee.getX(), 2) + Math.pow(localisationEntitee.getY(), 2) + Math.pow(localisationEntitee.getZ(), 2))));
            System.out.println("L'angle (En radians) entre vos yeux et la localisation de votre entitée est de " + resultat);
            System.out.println("L'angle (En degrée) entre vos yeux et la localisation de votre entitée est de " + Math.toDegrees(resultat));
            System.out.println("L'angle de vos yeux sur cosinus est de " + localisationYeux.getYaw());
            System.out.println("L'angle de vos yeux sur sinus est de " + localisationYeux.getPitch());
        }

        

        // Vector playerLookDir = player.getEyeLocation().getDirection();
        // Vector playerEyeLoc = player.getEyeLocation().toVector();
        // Vector entityLoc;
        // for (Entity entitee : player.getNearbyEntities(10, 10, 10)) 
        // {
        //     entityLoc = entitee.getLocation().toVector();
        //     Vector playerEntityVec = entityLoc.subtract(playerEyeLoc);
        //     float angle = playerLookDir.angle(playerEntityVec);
        //     player.sendMessage("Degrée total: " + Math.toDegrees(angle));
        // }

        return null;
    }

    public String getStats(boolean toutLesStats)
    {
        String stats = "====================\n";
        AttributeInstance attribut = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        Color couleur;
        
        stats += "Max health: ";
        if(player.getHealth() / attribut.getValue() <= 0.20)
        {
            couleur = new Color(255, 0, 0);
        }
        else if(player.getHealth() / attribut.getValue() <= 0.5)
        {
            couleur = new Color(223, 112, 0);
        }
        else
        {
            couleur = new Color(0, 255, 0);
        }
        stats += "[" + couleur + player.getHealth() + new Color(255,255,255) + " / " + attribut.getValue() + "]\n";
        stats += "Dégats: " + player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() + "\n";
        stats += "Armure: " + player.getAttribute(Attribute.GENERIC_ARMOR).getValue() + "\n";
        stats += "Vitesse de déplacement: " + player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() + "\n";

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if(toutLesStats)
        {

        }

        return stats;
    }

    /**
     * Permet de récupérer le nom du joueur.
     * @return
     */
    public String getNomJoueur()
    {
        return player.getName();
    }

    /**
     * Permet d'obtenir l'objet "player".
     * @return
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Permet d'obtenir le role du joueur.
     * @return
     */
    public RoleJoueur getRoleJoueur()
    {
        return roleJoueur;
    }

    public void envoyerUnMessage(String message)
    {
        player.sendMessage("[Mentor]: " + message);
    }

    /**
     * Cette méthode représente le sort "heal" du role "Prêtre".
     * @param nomDuJoueur
    */
    public void soin(Joueur joueurCible)
    {
        if(mana > 20)
        {
            mana -= 20;
            Player player = joueurCible.getPlayer();
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2);
            player.sendMessage("[Mentor]: Le joueur à été soigné");
        }
        else
        {
            player.sendMessage("[Mentor]: Il vous manque " + (20 - mana) + " mana pour pouvoir lancer le sort de soin !");
        }
    }

    /**
     * Permet pour les vampires de désactiver ou activer leur vitesse pour bien se cacher.
     * @param etat
     */
    public void cacherOuMontrerVitesse(int etat)
    {
        if(roleJoueur.equals(RoleJoueur.VAMPIRE) || roleJoueur.equals(RoleJoueur.VAMPIRE_LOYAL) || roleJoueur.equals(RoleJoueur.FILLETTE))
        {
            AttributeInstance mouvementSpeed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            if(etat == 0)
            {
                if(mouvementSpeed.getBaseValue() != MOUVEMENT_BASE)
                {
                    mouvementSpeed.setBaseValue(MOUVEMENT_BASE);
                    envoyerUnMessage("Votre vitesse de mouvement est celle d'un humain.");
                }
                else if(roleJoueur.equals(RoleJoueur.FILLETTE))
                {
                    mouvementSpeed.setBaseValue(mouvementSpeed.getBaseValue() * 1.3);
                    envoyerUnMessage("Votre vitesse de mouvement est celle d'une vampire de haut rang.");
                }
                else
                {
                    mouvementSpeed.setBaseValue(mouvementSpeed.getBaseValue() * 1.2);
                    envoyerUnMessage("Votre vitesse de mouvement est celle d'un vampire normal.");
                }
            }
            else
            {
                if(etat == 1)
                {
                    if(mouvementSpeed.getBaseValue() == MOUVEMENT_BASE)
                    {
                        if(roleJoueur.equals(RoleJoueur.FILLETTE))
                        {
                            mouvementSpeed.setBaseValue(mouvementSpeed.getBaseValue() * 1.3);
                            envoyerUnMessage("Votre vitesse de mouvement est celle d'une vampire de haut rang.");
                            
                        }
                        else
                        {
                            mouvementSpeed.setBaseValue(mouvementSpeed.getBaseValue() * 1.2);
                            envoyerUnMessage("Votre vitesse de mouvement est celle d'un vampire normal.");
                        }
                    }
                }
                else
                {
                    mouvementSpeed.setBaseValue(MOUVEMENT_BASE);
                    envoyerUnMessage("Votre vitesse de mouvement est celle d'un humain.");
                }
            }
        }
    }

    /**
     * Permet de téléporter aléatoirement le joueur dans la région spécifiée.
     * @param region
     */
    public void teleporterLeJoueurAleatoirement(Region region)
    {
        PointRegion pointRegion = region.obtenirUnPointAleatoirementEnLargeur();
        player.teleport(new Location(player.getWorld(), pointRegion.x, pointRegion.y, pointRegion.z));
    }

    /**
     * Permet d'initialiser tout les stats du role FILLETTE au joueur.
     */
    private void initialiserFillette()
    {
        sang = 100;
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 1.3);
        player.setHealthScale(2);
    }

    /**
     * Permet d'initialiser le rôle Vampire Loyal
     */
    private void initialiserVampireLoyal()
    {
        sang = 100;
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 1.2);
    }

    /**
     * Permet d'initialiser le rôle Vampire
     */
    private void initialiserVampire()
    {
        sang = 100;
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(24);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 1.2);
    }

    /**
     * Permet d'initialiser le rôle Garde.
     */
    private void initialiserGarde()
    {
        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD, 1));
        player.getInventory().addItem(new ItemStack(Material.IRON_HELMET, 1));
        player.getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE, 1));
        player.getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS, 1));
        player.getInventory().addItem(new ItemStack(Material.IRON_BOOTS, 1));
    }

    /**
     * Permet d'initialiser le rôle Chasseur.
     */
    private void initialiserChasseur()
    {
        player.getInventory().addItem(new ItemStack(Material.BOW, 1));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketAdapter packetAdapter = new PacketAdapter(VampiresVSVillagers.getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT) 
        {
            public void onPacketSending(PacketEvent event) 
            {
                Joueur joueur = VampiresVSVillagers.getJoueur(event.getPlayer().getName());
                if(joueur != null && joueur.getRoleJoueur() == RoleJoueur.CHASSEUR 
                && event.getPacket().getStrings().read(0).startsWith("step."))
                {
                    event.setCancelled(true);
                }
            }
        };
        protocolManager.addPacketListener(packetAdapter);
    }

    /**
     * Permet d'initialiser le rôle Prêtre
     */
    private void initialiserPretre()
    {
        AttributeInstance armure = player.getAttribute(Attribute.GENERIC_ARMOR);
        armure.setBaseValue(armure.getBaseValue() * 0.80);
    }

    /**
     * Permet d'initialiser le rôle Villageois.
     */
    private void initialiserVillageois()
    {
        player.getInventory().addItem(new ItemStack(Material.WOODEN_HOE, 1));
    }



    /**
     * @category Test Experiemental
     */
    public void testExperimental()
    {
        Location regard = player.getEyeLocation();
        int directionHorizontal = (int) regard.getYaw() / 90;
        if(directionHorizontal < 0)
        {
            directionHorizontal *= -1;
        }
        double degree = regard.getYaw();
    
        if(degree < 0)
        {
            degree *= -1;
        }
        while(degree > 90)
        {
            degree -= 90;
        }
        degree /= 90;
    
        Vector regardMax = new Vector(); // On détermine le deuxième point maximale par rapport à la distance.
        switch(directionHorizontal)
        {
            case 0: // Entre le nord et l'est (Z négatif et X négatif)
                regardMax = new Vector(positifOuNegatif(regard.getX(), degree, true), regard.getY() - 1, positifOuNegatif(regard.getZ(), degree, false));
            break;
            case 1: // Entre l'est et le sud (Z positif et X négatif)
                regardMax = new Vector(positifOuNegatif(regard.getX(), degree, false), regard.getY() - 1, positifOuNegatif(regard.getZ(), degree, true));
            break;
            case 2: // Entre le sud et l'ouest (Z positif et X positif)
                regardMax = new Vector(positifOuNegatif(regard.getX(), degree, true), regard.getY() - 1, positifOuNegatif(regard.getZ(), degree, false));
            break;
            case 3: // Entre l'ouest et le nord (Z négatif et X positif)
                regardMax = new Vector(positifOuNegatif(regard.getX(), degree, false), regard.getY() - 1, positifOuNegatif(regard.getZ(), degree, true));
            break;
            case 4: // Entre le nord et l'est (Z négatif et X négatif)
                regardMax = new Vector(positifOuNegatif(regard.getX(), degree, true), regard.getY() - 1, positifOuNegatif(regard.getZ(), degree, false));
            break;
        }
    
        Location location = new Location(player.getWorld(), regardMax.getX(), regardMax.getY(), regardMax.getZ());
        Block block = location.getBlock();
        block.setType(Material.DIAMOND_BLOCK);
        // System.out.println("directionHorizontal: " + directionHorizontal);
        // System.out.println("Degree: " + (degree * 40));
        // System.out.println("Degree restant: " + (1 - degree) * 40);
        System.out.println(location.getX() + "," + location.getY() + "," + location.getZ());

        
        // ArrayList<Entity> entitees = new ArrayList<>(player.getNearbyEntities(80,80,80));

    }

    private double positifOuNegatif(double nombre, double degree, boolean estDegreeRestant)
    {
        // regardMax = new Vector(regard.getX() + ((1 - degree) * 40), regard.getY() - 1, regard.getZ() + (degree * 40));
        System.out.println("PositifOuNegatif(" + nombre + "," + degree + "," + estDegreeRestant + ");");
        if(estDegreeRestant)
        {
            degree = 1 - degree;
            System.out.println("DegreeRestant: " + degree);
        }
        if(nombre < 0)
        {
            System.out.println("Retourn dun nombre negatif:   " + (degree * -1 * 40 + nombre));
            return degree * -1 * 40 + nombre;
        }
        System.out.println("Retourn dun nombre positif:   " + (degree * 40 + nombre));
        return degree * 40 + nombre;
    }
}
