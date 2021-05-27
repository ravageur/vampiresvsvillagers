package fr.ravageur.vampiresvsvillagers;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Events implements Listener
{
    @EventHandler
    public void entiteeAttaque(EntityDamageByEntityEvent event)
    {
        Entity attaquant = event.getDamager();
        if(attaquant instanceof Player)
        {
            Player attaquantPlayer = (Player) attaquant;
            double degats = getDegats(attaquantPlayer.getInventory().getItemInMainHand().getType());
            if(degats != -1)
            {
                event.setDamage(attaquantPlayer.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() + degats);
            }
        }
    }

    private static double getDegats(Material typeItem)
    {
        switch(typeItem) 
        {
            case WOODEN_SWORD:
            return 4;

            case STONE_SWORD:
            return 5;

            case IRON_SWORD:
            return 6;

            case GOLDEN_SWORD:
            return 4;

            case DIAMOND_SWORD:
            return 7;

            case NETHERITE_SWORD:
            return 8;
        
            default:
            return -1;
        }
    }
}
