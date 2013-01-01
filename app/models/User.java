package models;

import org.apache.commons.lang.StringUtils;

import play.data.validation.Constraints.Required;

public class User {
	@Required
	public String email;
	public String password;

	public String validate() {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
			return "Invalid email or password";
		}
		return null;
	}
}