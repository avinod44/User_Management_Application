package in.ashokit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.ashokit.dto.LoginFormDTO;
import in.ashokit.dto.QuoteApiResponseDTO;
import in.ashokit.dto.RegisterFormDTO;
import in.ashokit.dto.ResetPwdFormDTO;
import in.ashokit.dto.UserDTO;
import in.ashokit.service.DashboardService;
import in.ashokit.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/register")
	public String loadRegisterPage(Model model) {
		Map<Integer, String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);
		RegisterFormDTO registerFormDTO = new RegisterFormDTO();
		model.addAttribute("registerForm", registerFormDTO);
		return "register";

	}

	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId, Model model) {
		Map<Integer, String> statesMap = userService.getStates(countryId);
		model.addAttribute("states", statesMap);
		return statesMap;
	}

	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable Integer stateId, Model model) {
		Map<Integer, String> citiesMap = userService.getCities(stateId);
		model.addAttribute("cities", citiesMap);
		return citiesMap;
	}

	@PostMapping("/register")
	public String handleRegisteration(@ModelAttribute("registerForm") RegisterFormDTO registerFormDTO, Model model) {
		boolean status = userService.duplicateEmailCheck(registerFormDTO.getEmail());
		if (status) {
			model.addAttribute("emsg", "Duplicate email found");
		} else {
			boolean saveUser = userService.saveUser(registerFormDTO);
			if (saveUser) {
				model.addAttribute("smsg", "Registration successfull,please  check you Email...!!");
			} else {
				model.addAttribute("emsg", "Duplicate Email found");
			}
		}
		model.addAttribute("registerFormDTO", new RegisterFormDTO());
		model.addAttribute("countries", userService.getCountries());
		return "register";
	}

	@GetMapping("/")
	public String index(Model model) {
		LoginFormDTO loginFormDTO = new LoginFormDTO();
		model.addAttribute("loginForm", loginFormDTO);
		return "login";
	}

	@PostMapping("/login")
	public String handleUserLogin(@ModelAttribute("loginForm") LoginFormDTO loginFormDTO, Model model) {
		UserDTO userDTO = userService.login(loginFormDTO);
		if (userDTO == null) {
			model.addAttribute("emsg", "Invalid Credentials");
		} else {
			String upatePwd = userDTO.getUpdatePwd();
			if ("yes".equals(upatePwd)) {
				return "redirect:dashboard";
			} else {
				return "redirect:reset-pwd-page?email=" + userDTO.getEmail();
			}
		}

		return "login";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		QuoteApiResponseDTO quoteApiResponseDTO = dashboardService.getQuote();
		model.addAttribute("quote", quoteApiResponseDTO);
		return "dashboard";
	}

	@GetMapping("/reset-pwd-page")
	public String loadRestPwdPage(@RequestParam("email") String email, Model model) {
		ResetPwdFormDTO resetPwdFormDTO = new ResetPwdFormDTO();
		resetPwdFormDTO.setEmail(email);
		model.addAttribute("resetPwd", resetPwdFormDTO);
		return "resetPwd";
	}

	@PostMapping("/setPwd")
	public String handlePwdReset(ResetPwdFormDTO resetPwdFormDTO, Model model) {
		boolean resetPwd = userService.resetPwd(resetPwdFormDTO);
		if (resetPwd) {
			return "redirect:dashboard";
		}
		return "resetPwd";

	}

}
