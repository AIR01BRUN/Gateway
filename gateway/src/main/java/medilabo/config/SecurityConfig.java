package medilabo.config;

import java.net.URI;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.*;
import org.springframework.security.web.server.authorization.*;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    // Nom de l'en-tête attendu pour l'autorisation
    private static final String AUTH_HEADER = "X-Gateway-Token";
    // Valeur attendue du token
    private static final String EXPECTED_TOKEN = "MY_SECRET_TOKEN";

    // Déclare un encodeur de mot de passe avec BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Définition des règles de sécurité
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .csrf().disable() // Désactive la protection CSRF
                .httpBasic().disable() // Désactive l'authentification basique
                .formLogin().disable() // Désactive le formulaire de login
                .authorizeExchange(exchanges -> exchanges
                        // Applique le filtre de token sur ces routes
                        .pathMatchers("/patient/**").access(this::hasValidToken)
                        .pathMatchers("/history/**").access(this::hasValidToken)
                        .pathMatchers("/risk-level/**").access(this::hasValidToken)
                        .anyExchange().permitAll()) // Autorise le reste sans restriction
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()) // Gestion des erreurs d'accès refusé
                .authenticationEntryPoint(authenticationEntryPoint()) // Gestion des erreurs d'authentification
                .and()
                .build();
    }

    // Vérifie si le token est valide dans l'en-tête
    private Mono<AuthorizationDecision> hasValidToken(Mono<Authentication> authentication,
            AuthorizationContext context) {
        String token = context.getExchange().getRequest().getHeaders().getFirst(AUTH_HEADER);
        boolean isAuthorized = EXPECTED_TOKEN.equals(token);
        return Mono.just(new AuthorizationDecision(isAuthorized));
    }

    // Définit des utilisateurs en mémoire avec rôles
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW") // Mot de passe encodé
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW") // Même mot de passe
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    // Gère les accès refusés : redirige vers "/"
    @Bean
    public ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> {
            exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER); // Code 303
            exchange.getResponse().getHeaders().setLocation(URI.create("/"));
            return exchange.getResponse().setComplete();
        };
    }

    // Gère les erreurs d'authentification : redirige vers "/"
    @Bean
    public ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, ex) -> {
            exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER); // Code 303
            exchange.getResponse().getHeaders().setLocation(URI.create("/"));
            return exchange.getResponse().setComplete();
        };
    }

}
