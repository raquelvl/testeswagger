package sp3.com.swagger.docswagger.controladores;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp3.com.swagger.docswagger.dtos.UsuarioDTO;
import sp3.com.swagger.docswagger.entidades.Usuario;
import sp3.com.swagger.docswagger.servicos.ServicoDeUsuarios;

@RestController
public class UsuariosControlador {
    @Autowired
    private ServicoDeUsuarios usuariosService;

    @Operation(summary = "Cadastra um novo usuário no sistema. Os emails dos usuários devem ser únicos e são usados para login.")
    @RequestMapping(value = "/v1/api/usuarios", method = RequestMethod.POST, consumes="application/json", produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Retorna o usuario cadastrado com sucesso (sem senha).",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Usuário não foi cadastrado porque dados fornecidos para cadastro são inválidos.",
                    content = @Content)
    })
    public ResponseEntity<UsuarioDTO> cadastraUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content,
                    description = "Informações sobre o usuário a ser cadastrado.")
            @RequestBody Usuario usuario) {
        return new ResponseEntity<>(this.usuariosService.adicionaUsuario(usuario), HttpStatus.OK);
    }

    @Operation(summary = "Recupera os dados do usuário já cadastrado cujo email é passado na rota.",
            description = "Apenas o próprio usuário pode realizar esta operação.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequestMapping(value = "/v1/auth/usuarios/{email}", method = RequestMethod.GET,
            produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna os dados do usuario (sem senha).",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Usuário não tem permissão para acessar este recurso.",
                    content = @Content)
    })
    public ResponseEntity<UsuarioDTO> recuperaUsuario(
            @Parameter(name = "email", description = "ID do usuário (e-mail)", in = ParameterIn.PATH, example = "usuario@example.com", required = true)
            @PathVariable String email,
            @Parameter(description = "Autorização Bearer token", required = true, hidden = true , schema = @Schema(implementation = String.class))
            @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(usuariosService.recuperaUsuario(email, header), HttpStatus.OK);
    }

    @Operation(summary = "Recupera os dados do usuário cadastrado e identificado pelo e-mail passado. ",
            description = "Apenas o próprio usuário autenticado pode remover sua conta, " +
                    "ou um usuário que tenha papel " +
            "de ADMIN.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequestMapping(value = "/v1/auth/usuarios/{email}", method = RequestMethod.DELETE,
            produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Usuário removido com sucesso (retorna dados do usuário removido).",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Usuário não tem permissão para acessar este recurso.",
                    content = @Content)
    })
    public ResponseEntity<UsuarioDTO> removeUsuario(
            @Parameter(name = "email", description = "ID do usuário (e-mail)", in = ParameterIn.PATH, example = "usuario@example.com", required = true)
            @PathVariable String email,
            @Parameter(description = "Autorização Bearer token", required = true, hidden = true , schema = @Schema(implementation = String.class))
            @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(usuariosService.removeUsuario(email, header), HttpStatus.OK);
    }
}