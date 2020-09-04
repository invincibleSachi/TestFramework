package pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterationData {
	String firstName;
	String lastName;
	String email;
	String password;
	String confirmPassword;
	String expectedErrorMsg;
	boolean isRegistrationSuccess;

}
