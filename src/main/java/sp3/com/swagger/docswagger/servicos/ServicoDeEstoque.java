package sp3.com.swagger.docswagger.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sp3.com.swagger.docswagger.dtos.ItemDeAtualizacaoDeProdutoDTO;
import sp3.com.swagger.docswagger.dtos.ProdutoDTO;
import sp3.com.swagger.docswagger.entidades.Produto;
import sp3.com.swagger.docswagger.erros.CampoInvalidoException;
import sp3.com.swagger.docswagger.erros.NovoValorInvalidoException;
import sp3.com.swagger.docswagger.erros.ProdutoJaExisteException;
import sp3.com.swagger.docswagger.erros.ProdutoNaoEncontradoException;
import sp3.com.swagger.docswagger.repositorios.ProdutoDAO;

import java.util.Collection;
import java.util.Optional;

@Service
public class ServicoDeEstoque {

    @Autowired
    private ProdutoDAO repositorioDeProdutos;

    public ServicoDeEstoque() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Produto adicionaProdutos(ProdutoDTO produto) {

        Produto.validaProduto(produto);
        if (!repositorioDeProdutos.findByNome(produto.getNome()).isEmpty()) {
            throw new ProdutoJaExisteException("Produto ja existe no estoque",
                    "Este produto ja existe no estoque e pode apenas ser atualizado");
        }

        return repositorioDeProdutos.save(Produto.criaProduto(produto));
    }

    public Produto atualizaProduto(long id, ItemDeAtualizacaoDeProdutoDTO itemDeAtualizacao) {
        if (!repositorioDeProdutos.existsById(id)) // produto nao existe
            throw new ProdutoNaoEncontradoException("Produto nao existe no estoque",
                    "Este produto nao existe no estoque e precisa ser adicionado antes de ser atualizado");

        return atualizaItemDoProduto(id, itemDeAtualizacao);
    }

    public Collection<Produto> recuperaProdutos(Optional<String> padraoDeBusca) {
        if(padraoDeBusca.isEmpty())
            return repositorioDeProdutos.findAll();
        return repositorioDeProdutos.findByNomeContaining(padraoDeBusca.get());
    }

    public Produto recuperaProduto(long id) {
        if (!repositorioDeProdutos.existsById(id))
            throw new ProdutoNaoEncontradoException("Produto nao existe.",
                    "Um produto com este id nao foi encontrado no estoque.");
        return repositorioDeProdutos.findById(id).get();
    }

    private Produto atualizaItemDoProduto(long id, ItemDeAtualizacaoDeProdutoDTO itemDeAtualizacao) {
        Produto produto = recuperaProduto(id);
        switch (itemDeAtualizacao.getAtributo()) {
            case "nome":
                if (repositorioDeProdutos.existsByNome(itemDeAtualizacao.getNovoValor()))
                    throw new NovoValorInvalidoException("Produto deve ter nome unico.",
                            "Um produto com o novo nome a ser atualizado ja existe no estoque. Produtos devem ter nomes unicos.");

                produto.setNome(itemDeAtualizacao.getNovoValor());
                break;

            case "quantidade":
                int novaQuantidade;
                try {
                    novaQuantidade = Integer.parseInt(itemDeAtualizacao.getNovoValor());
                } catch (NumberFormatException nfe) {
                    throw new NovoValorInvalidoException("Quantidade invalida.",
                            "A quantidade do produto deve ser um numero inteiro maior que zero.");
                }
                if (novaQuantidade <= 0)
                    throw new NovoValorInvalidoException("Quantidade invalida.",
                            "A quantidade do produto deve ser um numero inteiro maior que zero.");

                produto.setQuantidade(novaQuantidade);
                break;

            case "valorUnitario":
                double novoValor;
                try {
                    novoValor = Double.parseDouble(itemDeAtualizacao.getNovoValor());
                } catch (NumberFormatException nfe) {
                    throw new NovoValorInvalidoException("Preco invalido.",
                            "O preco do produto deve ser um numero real maior que zero.");
                }
                if (novoValor <= 0)
                    throw new NovoValorInvalidoException("Preco invalido.",
                            "O preco do produto deve ser um numero real maior que zero.");

                produto.setValorUnitario(novoValor);
                break;

            case "descricao":
                if (itemDeAtualizacao.getNovoValor() == null || itemDeAtualizacao.getNovoValor().isEmpty()
                        || itemDeAtualizacao.getNovoValor().isBlank())
                    throw new NovoValorInvalidoException("Produto deve ter descricao unica.",
                            "Produtos devem ter descricoes vlidas, nao pode ser nula ou em branco.");

                produto.setDescricao(itemDeAtualizacao.getNovoValor());
                break;

            default:
                throw new CampoInvalidoException("Campo invalido.",
                        "O campo do item de atualizacao nao existe. Os campos para atualizacao sao nome, quantidade, descricao e valorUnitario.");
        }

        return repositorioDeProdutos.save(produto);
    }
}