package in.ashokit.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="usertb")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	private String email;
	private Integer phno;
	private String pwd;
	private String updatePwd;
	@CreationTimestamp
	@Column(name="created_Date", updatable=false)
	private LocalDate createdDate;
	@UpdateTimestamp
	@Column(name="updated_Date",insertable=false)
	private LocalDate updatedDate;
	@ManyToOne
	@JoinColumn(name="CountryId")
	private  Country country;
	
	@ManyToOne
	@JoinColumn(name="stateId")
	private  State state;
	
 @ManyToOne
 @JoinColumn(name="cityId")
 private City city;

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public Integer getPhno() {
	return phno;
}

public void setPhno(Integer phno) {
	this.phno = phno;
}

public String getPwd() {
	return pwd;
}

public void setPwd(String pwd) {
	this.pwd = pwd;
}

public String getUpdatePwd() {
	return updatePwd;
}

public void setUpdatePwd(String updatePwd) {
	this.updatePwd = updatePwd;
}

public LocalDate getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(LocalDate createdDate) {
	this.createdDate = createdDate;
}

public LocalDate getUpdatedDate() {
	return updatedDate;
}

public void setUpdatedDate(LocalDate updatedDate) {
	this.updatedDate = updatedDate;
}

public Country getCountry() {
	return country;
}

public void setCountry(Country country) {
	this.country = country;
}

public State getState() {
	return state;
}

public void setState(State state) {
	this.state = state;
}

public City getCity() {
	return city;
}

public void setCity(City city) {
	this.city = city;
}






}