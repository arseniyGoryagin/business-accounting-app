package com.meridian.auth;


import com.meridian.auth.domain.TokenResponse;
import com.meridian.exceptions.InvalidRefreshTokenException;
import com.meridian.jwt.JwtService;
import com.meridian.jwt.TokenType;
import com.meridian.user.UserService;
import com.meridian.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;




    /**
     * Аутентификация пользователя
     *
     * @param username email пользователя
     * @param password пароль пользователя
     * @return токен
     */
    public TokenResponse authUser (String username, String password){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                password
        ));

        return TokenResponse.builder()
                .token(jwtService.generateToken(username, TokenType.ACCESS))
                .refreshToken(jwtService.generateToken(username, TokenType.REFRESH))
                .build();
    }


    public TokenResponse refreshToken(String refreshToken){

            String username = jwtService.getUsername(refreshToken, TokenType.REFRESH);


            userService.getUserByUsername(username)
                    .orElseThrow(() -> new InvalidRefreshTokenException("Нет пользователя с таким токеном"));

            // TODO add valid by email
            if(jwtService.validateToken(refreshToken,TokenType.REFRESH)){

                var newToken = jwtService.generateToken(username, TokenType.ACCESS);

                return TokenResponse.builder()
                        .token(newToken)
                        .refreshToken(refreshToken)
                        .build();

            }

            throw new InvalidRefreshTokenException("Невалидный refresh token");

    }



}
