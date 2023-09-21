package sp3.com.swagger.docswagger.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sp3.com.swagger.docswagger.entidades.Produto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Long> {
    public Optional<Produto> findByNome(String nome);
    public List<Produto> findByNomeContaining(String padrao);
    public boolean existsByNome(String nome);
}
