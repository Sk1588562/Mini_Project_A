package com.samproj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samproj.binding.LoginForm;
import com.samproj.binding.SignUpForm;
import com.samproj.binding.UnlockForm;
import com.samproj.service.UserService;


@Controller
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handelSignUp(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = userService.signUp(form);

		if(status) {
			model.addAttribute("succMsg", "Account Created, Check Your Email");
		}else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);

		model.addAttribute("unlock", unlockFormObj);

		return "unlock";
	}


	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {

		if(unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = userService.unlockAccount(unlock);
			if(status) {
				model.addAttribute("succMsg", "Account Unlocked Successfully");
			}else {
				model.addAttribute("errMsg", "Temporary Password Incorrect !!!, Check Your Email");
			}

		}else {
			model.addAttribute("errMsg", "New Pwd and Confirm Pwd should be Same");
		}

		return "unlock";
	}


	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}


	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginform, Model model) {

		String status = userService.login(loginform);

		if(status.contains("success")) {
			//redirect req to Display Dashboard method

			//Create Session and store user data in session  for login




			return "redirect:/dashboard";
		}

		model.addAttribute("errMsg", status);


		return "login";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {

		return "forgotPwd";
	}


	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email, Model model) {

		String status = userService.forgotPwd(email);

		if(status.contains("Password sent to your mail")) {
			model.addAttribute("succMsg", status);
			return "forgotPwd";
		}
		model.addAttribute("errMsg", status);
		return "forgotPwd";
	}








}
