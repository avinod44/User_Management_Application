package in.ashokit.serviceimp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.dto.LoginFormDTO;
import in.ashokit.dto.RegisterFormDTO;
import in.ashokit.dto.ResetPwdFormDTO;
import in.ashokit.dto.UserDTO;
import in.ashokit.entity.City;
import in.ashokit.entity.Country;
import in.ashokit.entity.State;
import in.ashokit.entity.User;
import in.ashokit.repo.CityRepository;
import in.ashokit.repo.CountryRepository;
import in.ashokit.repo.StateRepository;
import in.ashokit.repo.UserRepository;
import in.ashokit.service.UserService;
import in.ashokit.util.EmailService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;

	@Override
	public Map<Integer, String> getCountries() {
		Map<Integer, String> countryMap = new HashMap<>();
		List<Country> countriesList = countryRepository.findAll();
		countriesList.forEach(c -> {
			countryMap.put(c.getCountryId(), c.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		Map<Integer, String> stateMap = new HashMap<>();
		List<State> statesList = stateRepository.findByCountryId(countryId);
		statesList.forEach(s -> {
			stateMap.put(s.getStateId(), s.getStateName());
		});
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		Map<Integer, String> cityMap = new HashMap<>();
		List<City> citiesList =cityRepository.findByStateId(stateId);
		citiesList.forEach(c -> {
			cityMap.put(c.getCityId(), c.getCityName());
		});
		return cityMap;
	}

	@Override
	public boolean duplicateEmailCheck(String email) {
		User byEmail = userRepository.findByEmail(email);
		if (byEmail != null) {
			return true;
		} else {
			return false;
		}
	}

	
	
	@Override 
	 public boolean saveUser(RegisterFormDTO regFormDTO) { 
		 User user=new User();
		 BeanUtils.copyProperties(regFormDTO, user); 
		     Country country=countryRepository.findById(regFormDTO.getCountryId()).orElse(null);
	          user.setCountry(country); 
	         
	        State  state=stateRepository.findById(regFormDTO.getStateId()).orElse(null);
	          user.setState(state); 
	          
	          City  city=cityRepository.findById(regFormDTO.getCityId()).orElse(null);
	        user.setCity(city);
	        
	        String randomPwd=generateRandomPwd();
	           user.setPwd(randomPwd); 
	           user.setUpdatePwd("No"); 
	           User savedUser=userRepository.save(user);
	            if(null!=savedUser.getUserId())
	            { 
	            String subject="your Accoun created";
	           	String body="your password to login:"+randomPwd; 
	           	String to=regFormDTO.getEmail(); 
	           emailService.sendEmail(subject,body ,to);
	            return true;
	            } else {
	  
	            return false; }
	 }
	@Override
	public UserDTO login(LoginFormDTO loginFormDTO) {
		User user = userRepository.findByEmailAndPwd(loginFormDTO.getEmail(), loginFormDTO.getPwd());
		if (user != null) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(user, userDTO);
			return userDTO;
		}
		return null;
	}

	@Override
	public boolean resetPwd(ResetPwdFormDTO resetPwdDTO) {
		String email = resetPwdDTO.getEmail();
		User user = userRepository.findByEmail(email);
		user.setPwd(resetPwdDTO.getNewPwd());
		user.setUpdatePwd("yes");
		userRepository.save(user);
		return true;
	}

	private String generateRandomPwd() {
		String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String alphabets = upperCaseLetters + lowerCaseLetters;
		Random random = new Random();
		StringBuffer generatePwd = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			int randomIndex = random.nextInt(alphabets.length());
			generatePwd.append(alphabets.charAt(randomIndex));
		}
		return generatePwd.toString();
	}

}
