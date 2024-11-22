package com.mdd.back.config;

import com.mdd.back.Model.User;
import com.mdd.back.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public String loginAndGenerateToken(String login, String password) {
        // Rechercher l'utilisateur par email
        User user = userService.getUserByEmail(login);
        if (user == null) {
            throw new RuntimeException("Invalid email");
        }

        // Vérifier que le mot de passe correspond à celui de l'utilisateur
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Si l'email et le mot de passe sont corrects, générer le token JWT
        return generateTokenForUser(user);
    }

    private String generateTokenForUser(User user) {
        // Début de la méthode, elle prend en paramètre un objet de type Users et retourne une chaîne de caractères (le token JWT).
        Instant now = Instant.now();
        // Création d'un objet Instant qui représente l'instant actuel (le moment où le token est généré).
        // Cela sera utilisé pour marquer le moment de l'émission du token et sa date d'expiration.
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                // Définit l'émetteur du token ("issuer").
                // Ici, "self" signifie que l'application elle-même est l'émetteur du token.
                .issuedAt(now)
                // Indique la date et l'heure auxquelles le token a été émis (basé sur l'instant actuel capturé par 'now').
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                // Définit la durée de validité du token.
                // Ici, le token est valide pour une durée d'un jour après son émission. 'now.plus(1, ChronoUnit.DAYS)' ajoute un jour à l'heure actuelle.
                .subject(user.getEmail())
                // Le "subject" du token est l'email de l'utilisateur, c'est l'information principale à propos de qui ce token représente.
                .build();
        // Construction finale du JwtClaimsSet avec toutes les informations précédentes (émetteur, date d'émission, expiration et sujet).
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                claims
        );
        // Création des paramètres pour l'encodage du JWT.
        // Ici, un en-tête JWS (JSON Web Signature) est construit avec l'algorithme de hachage "HS256" (HMAC avec SHA-256).
        // Ces paramètres incluent l'en-tête et les revendications (claims).
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        // Encodage des paramètres JWT avec l'objet 'jwtEncoder'.
        // Après encodage, le token JWT sous forme de chaîne de caractères est renvoyé via la méthode 'getTokenValue'.

    }

}
