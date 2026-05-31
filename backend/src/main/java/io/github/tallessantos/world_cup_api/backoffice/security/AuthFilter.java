package io.github.tallessantos.world_cup_api.backoffice.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("*.xhtml")
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
                        session.getAttribute("authenticated") != null;

        String path =
                req.getRequestURI();

        boolean loginPage =
                path.contains("login.xhtml");

        if (!logged && !loginPage) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/backoffice/login.xhtml"
            );

            return;

        }

        chain.doFilter(
                request,
                response
        );

    }

}