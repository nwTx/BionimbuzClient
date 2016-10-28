package br.unb.cic.bionimbuz.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.rest.service.RestService;
import br.unb.cic.bionimbuz.security.PBKDF2;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class SignUpBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpBean.class);

    private User user = new User();
    private final RestService restService;

    public SignUpBean() {
        restService = new RestService();
    }

    /**
     * Calss RestService to perform a signUp request
     *
     * @return
     */
    public String signUp() {
        boolean result = false;

        try {            
            
            // Hashes user password using bCrypt algorithm.
            user.setPassword(PBKDF2.generatePassword(user.getPassword()));
            
            // Send to core
            result = restService.signUp(user);
            
        } catch (ServerNotReachableException e) {
            return "server_offline";
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
        }
        
         user = new User();
         
         return ((result)? "sign_up_success" : "sign_up_error");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
