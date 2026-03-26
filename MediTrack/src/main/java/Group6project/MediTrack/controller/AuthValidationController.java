package Group6project.MediTrack.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Intercepts every request and redirects unauthenticated users to /login.
 * Register this in WebMvcConfig so it applies to all routes except
 * /login, /register, and static assets.
 */
@Component
public class AuthValidationController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("loggedInUser") != null;

        if (!loggedIn) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
