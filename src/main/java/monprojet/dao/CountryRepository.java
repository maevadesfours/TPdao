package monprojet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.City;
import monprojet.entity.Country;
import monprojet.dao.LesPays;
// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query(value="SELECT SUM(population) "
            +"FROM City "
            +"WHERE country_id = :codePays ", nativeQuery = true)
    public Integer comptePopulationSQL(Integer codePays);


    //Une méthode sans paramètre, qui renvoie une liste (nom du pays, population).
    @Query(value="SELECT Country.name as name, Sum(City.population) as population "
            +"FROM Country "
            +"INNER JOIN City ON Country.id=City.country_id "
            + "Group by Country.name, Country.id ", nativeQuery = true)
    public List<LesPays> listePaysSQL();
}


