package sp3.com.swagger.docswagger.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import sp3.com.swagger.docswagger.dtos.ProdutoDTO;
import sp3.com.swagger.docswagger.erros.ProdutoInvalidoException;

import java.util.Objects;

@Entity
public class Produto {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    private int quantidade;

    private double valorUnitario;

    private String descricao;

    public Produto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Produto(String nome, int quantidade, double valorUnitario, String descricao) {
        super();
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoDaUnidade() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Produto other = (Produto) obj;
        return Objects.equals(id, other.id);
    }

    public static Produto criaProduto(ProdutoDTO produto) {
        return new Produto(produto.getNome(), produto.getQuantidade(), produto.getValorUnitario(),
                produto.getDescricao());
    }

    @Override
    public String toString() {
        return "Produto [id=" + id + ", nome=" + nome + ", quantidade=" + quantidade + ", valorUnitario="
                + valorUnitario + ", descricao=" + descricao + "]";
    }

    public static boolean validaProduto(ProdutoDTO produto) {
        if (produto.getNome() == null || produto.getNome().isBlank() || produto.getNome().isEmpty())
            throw new ProdutoInvalidoException("Nome invalido.", "O nome do produto é um campo de texto obrigatorio.");

        if (produto.getQuantidade() <= 0)
            throw new ProdutoInvalidoException("Quantidade invalida.", "A quantidade do produto deve ser maior que zero.");

        if (produto.getValorUnitario() <= 0)
            throw new ProdutoInvalidoException("Preco invalido.", "O preco da unidade do produto deve ser maior que zero.");

        if (produto.getDescricao() == null || produto.getDescricao().isBlank() || produto.getDescricao().isEmpty())
            throw new ProdutoInvalidoException("Descricao invalida.", "O produto deve ter uma descricao mais detalhada sobre ele.");

        return true;
    }

}
