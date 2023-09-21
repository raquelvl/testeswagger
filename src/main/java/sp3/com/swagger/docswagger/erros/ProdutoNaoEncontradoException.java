package sp3.com.swagger.docswagger.erros;

public class ProdutoNaoEncontradoException extends RuntimeException {
    private String titulo;
    private String detalhes;

    public ProdutoNaoEncontradoException(String titulo, String detalhes) {
        super();
        this.detalhes = detalhes;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDetalhes() {
        return detalhes;
    }
}