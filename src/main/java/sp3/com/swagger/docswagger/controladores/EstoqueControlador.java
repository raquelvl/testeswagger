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
import sp3.com.swagger.docswagger.dtos.ItemDeAtualizacaoDeProdutoDTO;
import sp3.com.swagger.docswagger.dtos.ProdutoDTO;
import sp3.com.swagger.docswagger.entidades.Produto;
import sp3.com.swagger.docswagger.servicos.ServicoDeEstoque;

import java.util.Collection;
import java.util.Optional;

@RestController
public class EstoqueControlador {
    @Autowired
    private ServicoDeEstoque servicoDeEstoque;

    /*
     * buscar produto pelo nome adicionar item de compra remover item de compra
     * atualizar quantidade de item de compra fechar pedido
     *
     */

    @Operation(
            summary = "Cadastra um novo produto no estoque.",
            description = "Produtos não podem ser repetidos. Aqpenas usuários autenticados podem " +
                    "cadastrar novos produtos.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/v1/auth/produtos", method = RequestMethod.POST, consumes="application/json", produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                        description = "Retorna o produto adicionado, incluindo seu código",
                        content = @Content),
            @ApiResponse(responseCode = "403",
                        description = "Usuário não tem permissão para acessar este recurso",
                        content = @Content),
            @ApiResponse(responseCode = "400",
                        description = "Produto já existe no estoque, atualize os dados do produto que já existe",
                        content = @Content)
    })
    public ResponseEntity<Produto> adicionaProdutosNoEstoque(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, content = @Content,
                    description = "Informações sobre o produto a ser adicionado.")
            @RequestBody ProdutoDTO produto) {
        return new ResponseEntity<Produto>(servicoDeEstoque.adicionaProdutos(produto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/v1/api/produtos", method = RequestMethod.GET, produces="application/json")
    @Operation(summary = "Recupera todos os produtos já cadastrados no estoque.",
            description = "É possível limitar a busca para produtos que contenham um certo padrão no nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os produtos cadastrados", content = @Content)
    })
    public ResponseEntity<Collection<Produto>> recuperaProdutosDoEstoque(
            @Parameter(name = "busca", description = "Filtra a busca por esse nome (ou parte de nome) do produto.", in = ParameterIn.QUERY)
            @RequestParam(value="busca", required=false) String padrao) {
        return new ResponseEntity<Collection<Produto>>(servicoDeEstoque.recuperaProdutos(Optional.ofNullable(padrao)), HttpStatus.OK);
    }

    @PatchMapping("/v1/auth/produtos/{id}")
    @Operation(summary = "Atualiza um produto do estoque.",
            description = "É preciso saber o identificador do produto para atualizá-lo. " +
            "Produtos não podem ser repetidos. Aqpenas usuários autenticados podem cadastrar novos produtos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "400", description = "Produto não existe no estoque")
    })
    public ResponseEntity<Produto> atualizaProduto(
            @Parameter(name = "id", description = "ID do produto (numérico)", in = ParameterIn.PATH, example = "1", required = true)
            @PathVariable long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content,
                    description = "Informações sobre o campo do produto a ser atualizado e o novo valor. " +
                            "Os atributos atualizáveis são nome, quantidade, valorUnitario e descricao; " +
                            "quaisquer outros valores para o atributo não serão aceitos.")
            @RequestBody ItemDeAtualizacaoDeProdutoDTO itemDeAtualizacao) {
        return new ResponseEntity<Produto>(servicoDeEstoque.atualizaProduto(id, itemDeAtualizacao), HttpStatus.OK);

    }

    @Operation(summary = "Recupera as informaçÕes sobre um produto no estoque.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna o produto cujo id foi informado no caminho da requisição.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "O produto com o id passado não foi encontrado.",
                    content = @Content)
    })
    @GetMapping("/v1/api/produtos/{id}")
    public ResponseEntity<Produto> recuperaProduto(
            @Parameter(name = "id", description = "ID do produto (numérico)", in = ParameterIn.PATH, example = "1", required = true)
            @PathVariable long id) {
        return new ResponseEntity<Produto>(servicoDeEstoque.recuperaProduto(id), HttpStatus.OK);

    }
}