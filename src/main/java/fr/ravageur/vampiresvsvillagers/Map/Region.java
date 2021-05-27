package fr.ravageur.vampiresvsvillagers.Map;

import java.util.Random;

/**
 * Cette classe représente une région défini par deux points.
 */
public class Region 
{
    public PointRegion pointRegion1;
    public PointRegion pointRegion2;

    public Region(PointRegion pointRegion1, PointRegion pointRegion2)
    {
        this.pointRegion1 = pointRegion1;
        this.pointRegion2 = pointRegion2;
    }

    /**
     * Permet de changer le deuxième point par un autre.
     * @param pointRegion2
     */
    public void changerPointRegion2(PointRegion pointRegion2)
    {
        this.pointRegion2 = pointRegion2;
    }

    /**
     * Permet d'obtenir un point aléatoirement dans la région défini par les deux points précédemment.
     * @return
     */
    public PointRegion obtenirUnPointAleatoirementEnLargeur()
    {
        Random random = new Random();
        return new PointRegion(random.nextInt(Math.max(pointRegion1.getXEntier(), pointRegion2.getXEntier()) - Math.min(pointRegion1.getXEntier(), pointRegion2.getXEntier())), random.nextInt(Math.max(pointRegion1.getYEntier(), pointRegion2.getYEntier()) - Math.min(pointRegion1.getYEntier(), pointRegion2.getYEntier())), random.nextInt(Math.max(pointRegion1.getZEntier(), pointRegion2.getZEntier()) - Math.min(pointRegion1.getZEntier(), pointRegion2.getZEntier())));
    }

}
