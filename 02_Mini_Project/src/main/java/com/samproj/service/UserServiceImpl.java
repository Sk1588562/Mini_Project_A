package com.samproj.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samproj.binding.LoginForm;
import com.samproj.binding.SignUpForm;
import com.samproj.binding.UnlockForm;
import com.samproj.entity.UserDtlsEntity;
import com.samproj.repo.UserDtlsRepo;
import com.samproj.util.EmailUtils;
import com.samproj.util.PwdUtils;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private HttpSession session;



	@Override
	public boolean signUp(SignUpForm form) {

		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());

		if(user!=null) {
			return false;
		}
		//Copy Data from binding obj to entity Obj

		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		//Generate random pwd and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);

		//Set account status as locked

		entity.setAccStatus("LOCKED");

		//Insert record

		userDtlsRepo.save(entity);

		//Send Email to user Unlock the Account

		String to = form.getEmail();
		String subject = "Unlock Your Account | Sam Project";

		StringBuffer body = new StringBuffer("");
		body.append("<h1> Use below temporary pwd to unlock your Account</h1>");
		body.append("Temporary pwd : "+tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click Here To Unlock Your Account</a>");

		emailUtils.sendEmail(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if(entity.getPwd().equals(form.getTempPwd())) {

			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UnLocked");
			userDtlsRepo.save(entity);
			return true;

		}else {
			return false;
		}

	}


	@Override
	public String login(LoginForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if(entity == null) {
			return "Invalid Credentials";
		}

		if(entity.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}


		//Create Session and store user data in session  for login
		session.setAttribute("userId", entity.getUserId());

		return "success";
	}

	@Override
	public String forgotPwd(String email) {
		//check record present in db  with given email

		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);

		//if record not available send error msg

		if(entity == null) {
			return "Invalid Email Id";
		}

		// if record available send pwd to email and send succs msg

		String Subject = "Recover Password";
		String body = "Your Pwd :: "+entity.getPwd();

		emailUtils.sendEmail(email, Subject, body);


		return "Password sent to your mail";
	}

}
