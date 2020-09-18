<h1 align = center>API Transacional</h1>

Trata-se de uma API em Java e Spring Framework para gerenciamento de transações financeiras.

<h2 align = center>Como funciona?</h2>

A API deve criar, apagar e calcular as estatísticas sobre as transações criadas nos últimos 60 segundos. Com isso, a API tem os seguintes endpoints:

---

`POST/transacao`: cria uma transação.

**Body**

 <code>
{
"valor" : "123.45",
"dataHora" : "2020-09-11T09:59:51.312Z" 
}
</code>

**Em que:**

`valor`: valor da transação. Deve ser um BigDecimal que pode ser parseado como um double;
`dataHora`: data da transação no formato ISO8601 YYYY-MM-DDThh:mm:ss:sssZ no timezone local.

**Deve:** 

Ter retorno com body vazio com um dos códigos a seguir:

- 201 CREATED: caso de sucesso.
- 422 UNPROCESSABLE_ENTITY: caso o JSON seja inválido ou se qualquer um dos campos não for parseável ou a data da transação está no futuro.

---

`DELETE/transacao`: remove todas as transações.

**Deve:**

Aceitar uma requisição com body vazio e retornar um status 204 NO_CONTENT.

---

`GET/estatistica`: retorna estatísticas básicas sobre as transações criadas nos últimos 60 segundos.

<code>
{
	"count": 10, 
	"sum": 1234.56, 
	"avg": 123.456, 
	"min": 12.34, 
	"max": 123.56
}
</code>

**Em que:**

`count`: um long especificando a quantidade de transações criadas nos últimos 60 segundos.
`sum`:  um BigDecimal especificando a soma total do valor transactionado nos últimos 60 segundos.
`avg`: um BigDecimal especificando a média do valor transacionado nos últimos 60 segundos.
`min`: um BigDecimal especificando o menor valor transacionado nos últimos 60 segundos.
`max`: um BigDecimal especificando o maior valor transacionado nos últimos 60 segundos.
 
 **Deve:** 

Ter retorno com as estatísticas das transações dos últimos 60 segundos no  body com o código a seguir:

- 200 OK: caso de sucesso.

---

<h3 align = center>Testes</h3>

- Para executar o teste unitário, basta rodar o seguinte comando dentro do diretório da aplicação:

~~~
mvnw test
~~~

- Para executar todos os testes (incluindo o teste de Integração), basta rodar o seguinte comando dentro do diretório da aplicação:

~~~
mvnw integration-test
~~~

---

<h3 align = center>Execução</h3>

Para rodar a API, basta executar o seguinte comando dentro do diretório da aplicação:

~~~
./mvnw spring-boot:run
~~~

Por default, a API estará disponível no endereço: http://localhost:8080/.

---

#### Feito com ❤️ por [Magno](https://www.linkedin.com/in/magnoneves/).
