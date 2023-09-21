package sp3.com.swagger.docswagger.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sp3.com.swagger.docswagger.entidades.Papel;
import sp3.com.swagger.docswagger.entidades.Usuario;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    private String email;

    private String nomeCompleto;

    private Papel papel = Papel.REGULAR;

    public static UsuarioDTO from(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPapel(usuario.getPapel());
        usuarioDTO.setNomeCompleto(usuario.getNomeCompleto());
        return usuarioDTO;
    }

}
