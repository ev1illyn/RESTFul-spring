package br.com.springrestful.services;

import br.com.springrestful.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {

    //ao invés de usar log4j
    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired
    UserRepository repository;

    public UserServices(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("buscando usuário por nome: " + username);
        var user = repository.findByUsername(username);
        if (user!=null) {
            return user;
        } else {
            throw new UsernameNotFoundException("usuário " + username + " não encontrado.");
        }
    }
}
