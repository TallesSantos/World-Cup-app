package io.github.tallessantos.world_cup_api.backoffice.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse res =
                (HttpServletResponse) response;

        HttpSession session =
                req.getSession(false);

        boolean logged =
                session != null &&
                        Boolean.TRUE.equals(
                                session.getAttribute("authenticated")
                        );

        String uri = req.getRequestURI();

        // Libera a página de login e os recursos estáticos do JSF
        // (ex: /javax.faces.resource/...) para não quebrar CSS/JS do login
        boolean isPublic =
                uri.contains("login.xhtml") ||
                        uri.contains("javax.faces.resource");

        if (!logged && !isPublic) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/backoffice/login.xhtml"
            );

            return;
        }

        chain.doFilter(request, response);
    }
}