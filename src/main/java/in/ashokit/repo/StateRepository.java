package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.State;

public interface StateRepository extends JpaRepository<State, Integer> {
 public List<State> findByCountryId(Integer CountryId);
}
