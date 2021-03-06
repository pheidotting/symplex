package nl.dias.service;

import nl.dias.domein.LogIn;
import nl.dias.repository.LoginRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class LoginService {
    @Inject
    private LoginRepository loginRepository;

    public void nieuwToken(Long gebruiker, String token) {
        LogIn logIn = new LogIn(token, gebruiker);

        loginRepository.opslaan(logIn);
        loginRepository.opruimen();
    }

    public List<Long> getIngelogdeGebruikers() {
        return loginRepository.getIngelogdeGebruikers();
    }
}

