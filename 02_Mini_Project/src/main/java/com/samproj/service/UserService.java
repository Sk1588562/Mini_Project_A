package com.samproj.service;

import com.samproj.binding.LoginForm;
import com.samproj.binding.SignUpForm;
import com.samproj.binding.UnlockForm;

public interface UserService {

	public boolean signUp(SignUpForm form);

	public boolean unlockAccount(UnlockForm form);

	public String login(LoginForm form);

	public String forgotPwd(String email);

}
