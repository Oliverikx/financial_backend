package com.oliver.financial.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.oliver.financial.domain.Card;
import com.oliver.financial.service.CardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CardService cardService;

    private final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) {
      
        Integer username = Integer.parseInt(authentication.getName());
        String password = authentication.getCredentials().toString();

        log.debug("CustomAuthenticationProvider: card number " + username + " pin: " + password);

        Optional<Card> card = cardService.findOneByNumber(username);

        if (card.isPresent()) {

            if (!cardService.checkCardPin(password, card.get().getPin())) {
               return null;
            }

            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            return new UsernamePasswordAuthenticationToken(username, password,
            mappedAuthorities);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
