package sp3.com.swagger.docswagger.filtros;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import static sp3.com.swagger.docswagger.servicos.ServicoJWT.TOKEN_KEY;

public class FiltroDeTokensJWT extends GenericFilterBean {

    public final static int TOKEN_INDEX = 7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException, java.io.IOException {

        HttpServletRequest req = (HttpServletRequest) request;

        //if(req.getMethod().equals("GET")) {
        //	chain.doFilter(request, response);
        //}

        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Token inexistente ou mal formatado!");
            return;
            // throw new ServletException("Token inexistente ou mal formatado!");
        }

        // Extraindo apenas o token do cabecalho (removendo o prefixo "Bearer ").
        String token = header.substring(TOKEN_INDEX);
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(TOKEN_KEY).build();
            parser.parseClaimsJws(token).getBody();

            //Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody();
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException
                 | UnsupportedJwtException | IllegalArgumentException e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;// a requisição nem precisa passar adiante, retornar já para o cliente pois não
            // pode prosseguir daqui pra frente
            // por falta de autorização
        }

        chain.doFilter(request, response);
    }
}

