package sp3.com.swagger.docswagger.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProdutoDTO {
    private String nome;

    private int quantidade;

    private double valorUnitario;

    private String descricao;
}
