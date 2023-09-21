package sp3.com.swagger.docswagger.controladores;

import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sp3.com.swagger.docswagger.dtos.LoginDeUsuarioDTO;
import sp3.com.swagger.docswagger.dtos.RespostaDeLogin;
import sp3.com.swagger.docswagger.servicos.ServicoJWT;

@RestController
public class LoginControlador {
    @Autowired
    private ServicoJWT servicoJwt;

    @PostMapping("auth/login")
    public ResponseEntity<RespostaDeLogin> autentica(@RequestBody LoginDeUsuarioDTO usuario) throws ServletException {
        return new ResponseEntity<RespostaDeLogin>(servicoJwt.autentica(usuario), HttpStatus.OK);
    }

}