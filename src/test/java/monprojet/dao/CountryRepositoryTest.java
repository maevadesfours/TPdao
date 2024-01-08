package monprojet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }
    @Test
    @Sql("test-data.sql")
    void verifComptePopulationFr(){
        log.info("On compte les habitants du pays qui a pour ID 1");
        assertEquals( 12 , countryDAO.comptePopulationSQL(1));
    }
    @Test
    @Sql    ("test-data.sql")
    void verifComptePopulationUk(){
        log.info("On compte les habitants du pays qui a pour ID 2");
        assertEquals( 18 , countryDAO.comptePopulationSQL(2));
    }
    @Test
    @Sql    ("test-data.sql")
    void verifComptePopulationUs(){
        log.info("On compte les habitants du pays qui a pour ID 3");
        assertEquals( 27 , countryDAO.comptePopulationSQL(3));
    }

    @Test
    @Sql("test-data.sql")
    void listePopulationTest(){
        log.info("On teste la listes de population");
        assertEquals(3, countryDAO.listePaysJPQL().size());
    }

    @Test
    @Sql("test-data.sql")
    void listePopulationTestFr(){
        log.info("On teste la liste pour la France");
        assertEquals(12, countryDAO.listePaysJPQL().get(0).getPop());
        assertEquals("France", countryDAO.listePaysJPQL().get(0).getNom());
    }

}
