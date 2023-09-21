package sp3.com.swagger.docswagger.controladores;

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

    @PostMapping("v1/api/usuarios")
    public ResponseEntity<UsuarioDTO> cadastraUsuario(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(this.usuariosService.adicionaUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping("/auth/usuarios/{email}")
    public ResponseEntity<UsuarioDTO> recuperaUsuario(@PathVariable String email,
                                                      @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(usuariosService.recuperaUsuario(email, header), HttpStatus.OK);
    }

    @DeleteMapping("/auth/usuarios/{email}")
    public ResponseEntity<UsuarioDTO> removeUsuario(@PathVariable String email,
                                                    @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(usuariosService.removeUsuario(email, header), HttpStatus.OK);
    }
}