package passwordtest.system;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import passwordtest.password.PasswordUtil;

/**
 * A demo to show off hashing and password verification
 *
 * @author Jacob Boicken
 */

@RestController
class PasswordTestController {

    @GetMapping("/passwordtest")
    public String passwordTest() throws Exception {
        String msg = "<p>---------------Hashing Demo----------------</p>\n";
		
		String passwd = "SuperSecret1234";
		byte[] hash = PasswordUtil.hash(passwd);

		msg += "<p>Password: " + passwd + "</p>\n";
		msg += "<p>Hashed Response: " + new String(hash, "UTF-8") + "</p>\n";

		msg += "<p>-----------------Verification Tests------------------</p>\n";
		msg += "<p>Correct Password: " + String.valueOf(PasswordUtil.verify(passwd, hash)) + "</p>\n";
		msg += "<p>Incorrect Password: " + String.valueOf(PasswordUtil.verify("NotQuiteSoSecret5678", hash)) + "</p>\n";

		return msg;
    }
}
