package com.blog.BlogBackend.services.concretes;

import com.blog.BlogBackend.dto.response.LoginResponse;
import com.blog.BlogBackend.dto.response.UserResponse;
import com.blog.BlogBackend.entities.Authority;
import com.blog.BlogBackend.entities.ConfirmationToken;
import com.blog.BlogBackend.entities.Token;
import com.blog.BlogBackend.entities.User;
import com.blog.BlogBackend.repositories.AuthorityRepository;
import com.blog.BlogBackend.repositories.UserRepository;
import com.blog.BlogBackend.services.abstracts.EmailService;
import com.blog.BlogBackend.services.abstracts.ModelMapperService;
import com.blog.BlogBackend.services.abstracts.TokenService;
import com.blog.BlogBackend.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserService userService;
    private ConfirmationTokenService confirmationTokenService;
    private EmailService emailService;
    private ModelMapperService modelMapperService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthorityRepository authorityRepository,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                 TokenService tokenService, UserService userService,
                                 ConfirmationTokenService confirmationTokenService, EmailService emailService, ModelMapperService modelMapperService) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
        this.modelMapperService = modelMapperService;
    }
    @Transactional
    public UserResponse register(String name, String surname, String email, String password, String profilePicture){

        userService.getUserByEmail(email);

        String encodedPassword = passwordEncoder.encode(password);
        LocalDateTime date = LocalDateTime.now();
        String emailToken = UUID.randomUUID().toString();

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setProfilePicture(profilePicture);
        user.setRegistrationDate(date);
        Authority authority = authorityRepository.findByAuthority("USER");
        user.setAuthority(authority);
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setEmailToken(emailToken);
        confirmationToken.setCreatedAt(date);
        confirmationToken.setExpiresAt(date.plusWeeks(1));
        confirmationToken.setUser(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String text = "Congrats!\n"+
                "Your registration to E-Commerce is successful. The only thing you need to" +
                " do is to click the link below to activate your account and start shopping!\n"+
                "Activate your account: " + "http://localhost:9000/auth/confirm?emailToken="+emailToken;
        emailService.sendEmail(email, "Blog Activation Mail", text);

        return modelMapperService.forResponse().map(user, UserResponse.class);
    }
    @Transactional
    public String confirmationEmailToken(String emailToken){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getConfirmationToken(emailToken)
                .orElseThrow(() ->
                        new RuntimeException("Confirmation Token not found!")); //TODO Throw exception -> Token not found
        if (confirmationToken.getConfirmedAt() != null){
            throw new RuntimeException("Email already confirmed!"); //TODO Throw exception -> Email confirmed
        }
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();

        if(LocalDateTime.now().isAfter(expiresAt)){
            throw new RuntimeException("Token expired!"); //TODO Throw exception -> Token expired
        }
        confirmationTokenService.setConfirmedAt(emailToken);

        userService.enableUser(confirmationToken.getUser().getEmail());

        return "Confirmed !"; //TODO Constants must be added
    }
    @Transactional
    public LoginResponse login(String email, String password){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            String token = tokenService.generateJwtToken(authentication);

            User foundUser = setUserToken(email);
            LocalDateTime expiry = LocalDateTime.now().plusDays(1);
            Token userToken = new Token();
            userToken.setToken(token);
            userToken.setExpiryDate(expiry);
            userService.updateUser(userToken, foundUser);
            tokenService.saveToken(userToken);

            return new LoginResponse(token);
        } catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("A"); //TODO Throw exception -> User not found A
        }
    }
    public User setUserToken(String email){
        Optional<User> foundUser = userRepository.findUserByEmail(email);

        if(foundUser.isPresent()){
            return foundUser.get();
        }
        throw new RuntimeException("B"); //TODO Throw exception -> User not found B
    }
}
