package com.meridian.auth;


import com.meridian.auth.domain.*;
import com.meridian.exceptions.ErrorMessageResponse;
import com.meridian.exceptions.InvalidRefreshTokenException;
import com.meridian.exceptions.UserAlreadyRegisteredException;
import com.meridian.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;




    @PostMapping("/login")
    public TokenResponse auth(@RequestBody @Valid LoginRequest request){
        return authService.authUser(request.getUsername(), request.getPassword());
    }


    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request){
        return authService.refreshToken(request.getRefreshToken());
    }



    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorMessageResponse> handleInvalidRefreshTokenException(InvalidRefreshTokenException e){
        return new ResponseEntity<>(new ErrorMessageResponse(e.getLocalizedMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ErrorMessageResponse> handleAlreadyRegisteredException(UserAlreadyRegisteredException e){
        return new ResponseEntity<>(new ErrorMessageResponse(e.getLocalizedMessage()), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessageResponse> handleBadCredentialException (BadCredentialsException e){
        return new ResponseEntity<>(new ErrorMessageResponse("Пароль или почта введены неверно"), HttpStatus.FORBIDDEN);
    }


}
