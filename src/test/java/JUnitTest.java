/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import FFSSM.Club;
import FFSSM.GroupeSanguin;
import static FFSSM.GroupeSanguin.AMOINS;
import static FFSSM.GroupeSanguin.APLUS;
import FFSSM.Licence;
import FFSSM.Moniteur;
import FFSSM.Personne;
import FFSSM.Plongee;
import FFSSM.Plongeur;
import FFSSM.Site;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ALEX
 */
public class JUnitTest {
        public Moniteur m1 = new Moniteur("0","Dupont","Jean","f","06.05.12.12.12",LocalDate.of(1990, 4, 28),0,APLUS,0);
        public Moniteur m2 = new Moniteur("1","Nirfdog","Xela","g","06.04.12.12.12",LocalDate.of(1980, 4, 28),0,AMOINS,0);
        public Plongeur p1 = new Plongeur("2","Godfrin","Alex","h","06.03.12.12.12",LocalDate.of(1998, 4, 28),0,AMOINS);
        public Plongeur p2 = new Plongeur("3","Tnopud","Naej","h","06.02.12.12.12",LocalDate.of(1997, 4, 28),1,AMOINS);
        public Club c1 = new Club(m1, "Dupont Plongee", "06.05.11.11.11");
    
    public JUnitTest() {
        
    }
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test //J'ajoute une licence à p1 et je vérifie 
          //qu'elle est valide après 11 mois mais pas après 12 
    public void plongeurAjoutLicenceEtLicenceValide(){
        assertTrue(p1.mesLicences.isEmpty());
        p1.ajouteLicence("0", LocalDate.now(), c1);
        assertFalse(p1.mesLicences.isEmpty());
        assertTrue(p1.mesLicences.get(0).estValide(LocalDate.now().plusMonths(11)));
        assertFalse(p1.mesLicences.get(0).estValide(LocalDate.now().plusMonths(12)));
    }
    
    @Test
    public void clubAjoutPlongeeEtConforme(){
        Site s1 = new Site("Fosse","assez profond");
        Plongee plongee1 = new Plongee(s1, m1, LocalDate.of(2020, 12, 24),6,2);
        assertTrue(c1.mesPlongees.isEmpty()); // les plongées de c1 sont vides
        c1.organisePlongee(plongee1); // on ajoute une plongée à c1
        assertFalse(c1.mesPlongees.isEmpty()); // les plongées de c1 ne sont plus vides
        p1.ajouteLicence("0", LocalDate.now(), c1); //Licence Valide
        p2.ajouteLicence("0", LocalDate.of(2019,11,25), c1); //Licence Non Valide
        plongee1.ajouteParticipant(p1); // on ajoute p1 qui est conforme
        assertTrue(!plongee1.mesPlongeurs.isEmpty()); // plongee1 possède 1 plongeur
        assertTrue(plongee1.estConforme()); // plongee1 possède 1 plongeur conforme donc est conforme
        plongee1.ajouteParticipant(p2); // on ajoute p2 qui n'est pas conforme
        assertFalse(plongee1.estConforme()); // plongee1 n'est plus conforme
    }
    
    @Test
    public void listPlongeeNonConforme(){
        Site s1 = new Site("Fosse","assez profond");
        Plongee plongee1 = new Plongee(s1, m1, LocalDate.of(2020, 12, 24),6,2);
        Plongee plongee2 = new Plongee(s1, m1, LocalDate.of(2020, 12, 24),6,2);
        p1.ajouteLicence("0", LocalDate.now(), c1); //Licence Valide
        p2.ajouteLicence("0", LocalDate.of(2019,11,25), c1); //Licence Non Valide
        plongee1.ajouteParticipant(p1); // Ajout de p1 à plongee1
        plongee2.ajouteParticipant(p2); // Ajout de p2 à plongee2
        c1.organisePlongee(plongee1); // On ajoute plongee1 à c1
        c1.organisePlongee(plongee2); // On ajoute plongee2 à c1
        assertTrue(c1.plongeesNonConformes().contains(plongee2) &&
                  !c1.plongeesNonConformes().contains(plongee1));
        // On vérifie que plongeesNonConformes() renvoit une HashSet contenant plongee2 mais pas plongee 1
    }
    
    @Test
    public void moniteur(){
        assertTrue(m1.mesEmbauches.isEmpty()); // m1 n'a pas d'embauche
        m1.nouvelleEmbauche(c1, LocalDate.now()); // On ajoute une embauche à m1
        assertTrue(!m1.mesEmbauches.isEmpty()); // m1 possède une embauche
        int last = m1.mesEmbauches.size()-1; // Variable auxiliaire pour avoir la dernière embauche
        Optional<Club> employeur = Optional.of(m1.mesEmbauches.get(last).getEmployeur()); // On crée un Optional de l'employeur de la dernière embauche
        assertEquals(m1.employeurActuel(), employeur); // La méthode renvoit la même valeur que l'employeur dans la variable
        assertTrue(m1.emplois() == m1.mesEmbauches); // la méthode emplois() renvoit bien les embauches
        m1.terminerEmbauche(LocalDate.now()); // On termine la dernière embauche
        assertTrue(m1.mesEmbauches.get(last).estTerminee()); // On vérifie que celle-ci se termine bien
    }
    
}
