# Migrando da versão 2.5.5 do spring boot para a versão 3.4.1

## Mudanças gerais
Notei duas grandes questões nessa migração:

1. Várias classes mantém o mesmo nome, mas o pacote não é javax.persistence, passa a ser jakarta.persistence. Isso é uma dor de cabeça se passar algum despercebido porque não vai dar erro de compilação. Minha sugestão (na minha opinião a forma mais segura de fazer isso) é criar um novo projeto para a versão do spring boot que você deseja (no caso desse projeto foi a 3.1.4) e ir levando classe por classe, sem levar os imports (deixa que a IDE faça os imports corretos).
   
2. Por default, não é mais permitido ter referência cíclica. Eu tenho um UsuarioService que usa o JWTService e vice versa. Nesse quesito (por questões de tempo) fui pelo caminho mais fácil (mas não estou certa de que é o melhor). Simplesmente adicionei no application.properties a seguinte linha:

```
spring.main.allow-circular-references=true
```

A parte do filtro e da configuração do filtro funcionaram sem modificações (o que eu já tinha feito anteriormente para usar a versão 0.11.5 do jwt).

## Mudanças por conta do swagger

Infelizmente o swagger springfox não roda na versão 3 do spring boot. Então temos que migrar para springdoc-openapi. É preciso adicionar a seguinte dependência no pom.xml:

```
<dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.1.0</version>
        </dependency>
```

Em seguinda é preciso configurar o swagger. Eu simplesmente adicionei no pacote raiz da aplicação a seguinte classe:

```java
package sp3.com.swagger.docswagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration


@OpenAPIDefinition(info = @Info(title = "Minha API de estoque", version = "v1"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class ConfiguracaoSwagger { }
```
Depois foi preciso adicionar as anotações para customizar a documentação conforme desejado. Isso é feito nas classes marcadas com @RestController. Essa parte deixo para que cada um veja como fazer. A documentação pode ser acessada na URL http://localhost:8080/swagger-ui/index.html quando a aplicação estiver rodando em sua máquina.

Só faço aqui uma ressalva, se quiser que possa ser possível testar rotas privadas na própria documentação acrescentar dentro da anotação @Operation o atributo:

```
security = @SecurityRequirement(name = "bearerAuth"
```
Ainda sobre segurança, essa aplicação usa a versão mais atual do JWT (que é 0.11.5) e precisei mudar umas coisas também pra migrar de uma versão mais antiga para ela. Se você também tiver esse problema pode entender o que precisa ser mudado [aqui](https://github.com/raquelvl/psoft/blob/master/estoquej/jwt0115.md).

Boa sorte ;)
