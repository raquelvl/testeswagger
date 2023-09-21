package sp3.com.swagger.docswagger.controladores;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @PostMapping("/v1/api/login")
    @Operation(summary = "Autentica o usuário devidamente cadastrado.",
            description = "É preciso informar o e-mail de um usuário cadastrado no sistema e a senha " +
                    "correspondente para que um usuário seja devidamente autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado/autenticado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Login inválido, usuário ou senha são inválidos.")
    })
    public ResponseEntity<RespostaDeLogin> autentica(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content,
                    description = "Informações de login (email e senha do usuário).")
            @RequestBody LoginDeUsuarioDTO usuario) throws ServletException {
        return new ResponseEntity<RespostaDeLogin>(servicoJwt.autentica(usuario), HttpStatus.OK);
    }

}