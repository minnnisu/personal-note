package minnnisu.personalnote.config;

import minnnisu.personalnote.constant.AuthenticationType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {

        AuthenticationType authenticationType = AuthenticationType.UnknownException;
        if (exception instanceof BadCredentialsException) {
            authenticationType = AuthenticationType.BadCredentialsException;
        }
        if (exception instanceof InternalAuthenticationServiceException) {
            authenticationType = AuthenticationType.InternalAuthenticationServiceException;
        }
        if (exception instanceof UsernameNotFoundException) {
            authenticationType = AuthenticationType.UsernameNotFoundException;
        }
        if (exception instanceof AuthenticationCredentialsNotFoundException) {
            authenticationType = AuthenticationType.AuthenticationCredentialsNotFoundException;
        }

        setDefaultFailureUrl("/login?failure=" + authenticationType.name());

        super.onAuthenticationFailure(request, response, exception);
    }
}