/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Moniteur extends Plongeur {

    public int numeroDiplome;
    
    public List<Embauche> mesEmbauches = new ArrayList<Embauche>();

    public Moniteur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, int niveau, GroupeSanguin gs, int numeroDiplome) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance, niveau, gs);
        this.numeroDiplome = numeroDiplome;
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * @return l'employeur actuel de ce moniteur sous la forme d'un Optional
     */
    public Optional<Club> employeurActuel() {
        Optional<Club> club = Optional.empty();
        if (!mesEmbauches.isEmpty()){
            Embauche last = mesEmbauches.get(mesEmbauches.size()-1);
            if (!last.estTerminee()){
                club = Optional.of(last.getEmployeur());
            }
        }
        return club;
    }
    
    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {   
        Embauche emb = new Embauche(debutNouvelle,this,employeur);
        if (!mesEmbauches.contains(emb)){
            mesEmbauches.add(emb);
        }	    
    }

    /**
     * @return la liste de toutes les embauches (terminées ou non) du Moniteur
     */
    public List<Embauche> emplois() {
        return mesEmbauches;
    }
    
    /**
     * Termine la dernière embauche du Moniteur
     * @param fin la date de fin de l'embauche
     */
    public void terminerEmbauche(LocalDate fin) {
        if (!mesEmbauches.isEmpty()){
            Embauche last = mesEmbauches.get(mesEmbauches.size()-1);
            if (!last.estTerminee()){
                last.terminer(fin);
            }
        }
    }
}
