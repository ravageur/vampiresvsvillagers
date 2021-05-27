package fr.ravageur.vampiresvsvillagers.Map;

import org.bukkit.Location;

/**
 * Cette classe représente un point dans l'espace du monde de Minecraft sur les axes x,y,z.
 */
public class PointRegion 
{
    public double x;
    public double y;
    public double z;

    public PointRegion(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PointRegion(Location location)
    {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public int getXEntier()
    {
        return getNombreEntier(x);
    }

    public int getYEntier()
    {
        return getNombreEntier(y);
    }

    public int getZEntier()
    {
        return getNombreEntier(z);
    }

    private int getNombreEntier(double nombre)
    {
        char[] nombreEnCaracteres = Double.toString(x).toCharArray();
        char[] nouveauNombre = new char[nombreEnCaracteres.length];
        for(int i = 0; i < nombreEnCaracteres.length; i++)
        {
            if(nombreEnCaracteres[i] == ',')
            {
                return Integer.parseInt(new String(nouveauNombre));
            }
            else
            {
                nouveauNombre[i] = nombreEnCaracteres[i];
            }
        }
        return 0;
    }
}
