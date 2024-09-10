package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Country;
import in.ashokit.entity.User;

public interface CountryRepository extends JpaRepository<Country, Integer> {
	

}
