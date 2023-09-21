package sp3.com.swagger.docswagger.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sp3.com.swagger.docswagger.entidades.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

}
