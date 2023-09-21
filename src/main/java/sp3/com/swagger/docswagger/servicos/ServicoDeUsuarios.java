package sp3.com.swagger.docswagger.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sp3.com.swagger.docswagger.dtos.LoginDeUsuarioDTO;
import sp3.com.swagger.docswagger.dtos.UsuarioDTO;
import sp3.com.swagger.docswagger.entidades.Papel;
import sp3.com.swagger.docswagger.entidades.Usuario;
import sp3.com.swagger.docswagger.erros.OperacaoNaoAutorizadaException;
import sp3.com.swagger.docswagger.repositorios.UsuarioDAO;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServicoDeUsuarios {

    @Autowired
    private UsuarioDAO usuariosDAO;
    @Autowired
    private ServicoJWT jwtService;

    public UsuarioDTO adicionaUsuario(Usuario usuario) {
        return UsuarioDTO.from(this.usuariosDAO.save(usuario));
    }

    private Usuario getUsuario(String email) {
        Optional<Usuario> optUsuario = usuariosDAO.findByEmail(email);
        if (!optUsuario.isEmpty()) {
            return optUsuario.get();
        }
        throw new NoSuchElementException("Usuario nao existe: " + email + ".");
    }

    public UsuarioDTO recuperaUsuario(String email, String authHeader) {
        Optional<Usuario> optUsuario = usuariosDAO.findByEmail(email);
        if (!optUsuario.isEmpty() && usuarioTemPermissao(authHeader, email)) {
            return UsuarioDTO.from(optUsuario.get());
        }
        throw new OperacaoNaoAutorizadaException("Usuario nao tem permissao",
                "A operacao requerida nao pode ser realizada por este usuario: " + jwtService.getSujeitoDoToken(authHeader) + ".");
    }

    public UsuarioDTO removeUsuario(String email, String authHeader) {
        Usuario usuarioASerDeletado = this.getUsuario(email);
        Usuario usuarioLogado = this.getUsuario(jwtService.getSujeitoDoToken(authHeader));
        if(usuarioLogado.getPapel().equals(Papel.ADMIN) ||
                usuarioLogado.getEmail().equals(usuarioASerDeletado.getEmail()))
            usuariosDAO.delete(usuarioASerDeletado);
        else throw new OperacaoNaoAutorizadaException("Usuario nao tem permissao",
                "A operacao requerida nao pode ser realizada por este usuario: " + usuarioLogado.getEmail() + ".");
        return UsuarioDTO.from(usuarioASerDeletado);
    }

    private boolean usuarioTemPermissao(String authorizationHeader, String email) {
        String subject = jwtService.getSujeitoDoToken(authorizationHeader);
        Optional<Usuario> optUsuario = usuariosDAO.findByEmail(subject);
        return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
    }

    public boolean validaUsuarioSenha(LoginDeUsuarioDTO usuario) {
        Optional<Usuario> optUsuario = usuariosDAO.findByEmail(usuario.getEmail());
        if (optUsuario.isPresent() && optUsuario.get().getSenha().equals(usuario.getSenha()))
            return true;
        return false;
    }

}
