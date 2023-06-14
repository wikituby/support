package support.ticket.domains.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;
import support.ticket.domains.User;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User login(String username, String password){
        return find("username = ?1 OR email = ?2", username).firstResultOptional()
                .map(user -> {
                    if (Boolean.TRUE.equals(verifyPassword(password,user.password))){
                        return user;
                    }else {
                        return null;
                    }
                }).orElse(null);
    }

    public Boolean verifyPassword(String plainTextPwd, String encryptedPwd) {

        Logger logger = LoggerFactory.getLogger(User.class);

        try {
            // convert encrypted password string to a password key
            Password rawPassword = ModularCrypt.decode(encryptedPwd);

            // create the password factory based on the bcrypt algorithm
            PasswordFactory factory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT);

            // create encrypted password based on stored string
            BCryptPassword restored = (BCryptPassword) factory.translate(rawPassword);

            // verify restored password against original
            return factory.verify(restored, plainTextPwd.toCharArray());

        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("Invalid key: {}", e.getMessage());

        }

        return false;

    }
}
