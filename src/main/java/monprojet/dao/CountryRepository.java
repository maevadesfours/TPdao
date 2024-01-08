package monprojet.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.City;
import monprojet.entity.Country;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query (value = "SELECT SUM(POPULATION) AS POPULATION" +
            "FROM COUNTRY INNER JOIN CITY ON COUNTRY.ID = CITY.COUNTRY_ID" +
            "WHERE COUNTRY.ID = : numID",
            nativeQuery = true)
    public int comptePopulationSQL(int numID);

    //Une méthode sans paramètre, qui renvoie une liste (nom du pays, population).
    @Query ("SELECT COUNTRY.NAME, SUM(POPULATION) AS POPULATION"+
            "FROM COUNTRY"+
            "INNER JOIN CITY ON COUNTRY.ID = CITY.COUNTRY_ID"+
            "GROUP BY COUNTRY.NAME")
    public HashMap<String, Integer> listePaysJPQL();
}
