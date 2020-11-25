package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Plongeur extends Personne {
    
    public int niveau;
    
    public Licence lic;
    
    public GroupeSanguin gs;
    
    public List<Licence> mesLicences = new ArrayList<>();
    
    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, int niveau, GroupeSanguin gs){
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.niveau = niveau;
        this.gs = gs;
    }
    
    public void ajouteLicence(String numero, LocalDate delivrance, Club club){
        Licence lic = new Licence(this, numero, delivrance, this.niveau, club);
        if (!mesLicences.contains(lic)){
            mesLicences.add(lic);
        }
    }
   
    public int getNiveau() {
        return niveau;
    }
    
    public void setNiveau(int niveau){
        this.niveau = niveau;
    }
}
