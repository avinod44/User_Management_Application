package in.ashokit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="state_tb")
public class State {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Integer stateId;
   private String stateName;
   private Integer countryId;
public Integer getStateId() {
	return stateId;
}
public void setStateId(Integer stateId) {
	this.stateId = stateId;
}
public String getStateName() {
	return stateName;
}
public void setStateName(String stateName) {
	this.stateName = stateName;
}
public Integer getCountryId() {
	return countryId;
}
public void setCountryId(Integer countryId) {
	this.countryId = countryId;
}
   
   
}
