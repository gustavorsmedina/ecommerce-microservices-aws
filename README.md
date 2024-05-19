# Ecommerce AWS

- Java
- Event-Driven Architecture
- AWS SQS
- AWS SNS
- Docker
- Spring Boot
- Spring Data JPA
- Spring Cloud OpenFeign
- Resilience4J
- MongoDB
- PostgreSQL

---

## Diagrama

![Diagrama AWS](/images/aws-ecommerce-diagram.jpg "Diagrama AWS")

#### Vídeo explicativo: <https://youtu.be/Hmt52wf_2uM>

---

## Passo a passo

#### 1 - Instale o docker em sua máquina <https://www.docker.com>

---

#### 2 - Abra o terminal na pasta *api-order* e execute o comando `docker compose up`

---

#### 3 - Abra o terminal na pasta *api-product* e execute o comando `docker compose up`

Após executar os dois comandos você terá disponível os seguintes serviços:

- PostgreSQL
Porta: 5432

- PgAdmin Web
Porta: 5050
Link de acesso: http://localhost:5050
Email: gustavo@postgres.com
Senha: gustavo

Para se conectar com PgAdmin, você deverá pegar o IP Adress do container que está rodando o PostgreSQL, use o comando `docker ps` no terminal e pegue o ID do container do PostgreSQL e em sequência use o comando `docker inspect <ID>`, lá você encontrará o IP Adress necessário para realizar a conexão..

- MongoDB
Porta: 27017

- Mongo Express
Porta: 8090
Link de acesso: http://localhost:8090
Usuário: mongo
Senha: gustavo

---

#### 4 - Crie os bancos de dados necessários para a aplicação

- MongoDB
Crie um banco chamado: db_products

- PostgreSQL
Crie um banco chamado: db_orders

---

#### 5 - Abra a seguinte pasta /src/main/resources/application.yml de api-order, service-email, service-order-consumer e service-payment

Aqui você terá que informar o acess-key e secret-key da sua conta AWS, e também a região que for utilizar.

---

#### 6 - Abra service-email/src/main/resources/application.yml

Informe o username (email) que será *responsável* por enviar o email para os usuários que estão fazendo o pedido, e a sua senha de aplicativo.

Gere sua senha de aplicativo do gmail: <https://support.google.com/accounts/answer/185833?hl=pt-BR>

---

#### 7 - Após terminar a configuração, inicie todas as aplicações e elas estarão disponíveis nas seguintes portas:

- api-order > 8081 | http://localhost:8081/v1/orders
- api-product > 8080 | http://localhost:8080/v1/products
- service-email > 8083 | http://localhost:8083
- service-order-consumer > 8085 | http://localhost:8085
- service-payment > 8084 | http://localhost:8084

---

#### 8 - Abra algum aplicativo para realizar suas requisições

- Crie primeiramente um produto:

POST > http://localhost:8080/v1/products

```json
{
	"name": "T-Shirt",
	"price": 20.00,
	"description": "Black t-shirt.",
	"stock": 20
}
```

- Pegue as informações do produto usando:

GET > http://localhost:8080/v1/products

- Crie um novo pedido 

POST > http://localhost:8080/v1/orders

```json
{
	"paymentType": "PIX",
	"userEmail": "user email",
	"products": [
		{
			"code": "product code",
			"name": "T-Shirt",
			"price": 20.00,
			"quantity": 1
		}
	]
}
```


---

## Localstack

#### Caso você queira rodar a aplicação utilizando o localstack, aqui estão os comandos a serem executados:


#### - Criando os tópicos:
```bash
aws sns create-topic --endpoint-url=http://localhost:4566 --region sa-east-1 --name=payment_processed_topic
```

```bash
aws sns create-topic --endpoint-url=http://localhost:4566 --region sa-east-1 --name=order_placed_topic
```

#### Criando as filas:
```bash
aws sqs create-queue --endpoint-url=http://localhost:4566 --region sa-east-1 --queue-name="order_payment_queue" --attributes DelaySeconds=5
```

```bash
aws sqs create-queue --endpoint-url=http://localhost:4566 --region sa-east-1 --queue-name="order_email_queue" --attributes DelaySeconds=5
```

```bash
aws sqs create-queue --endpoint-url=http://localhost:4566 --region sa-east-1 --queue-name="payment_email_queue" --attributes DelaySeconds=5
```

```bash
aws sqs create-queue --endpoint-url=http://localhost:4566 --region sa-east-1 --queue-name="payment_order_queue" --attributes DelaySeconds=5
```

#### Criando as assinaturas:
```bash
aws sns subscribe --endpoint-url=http://localhost:4566 --region sa-east-1 --topic-arn=arn:aws:sns:sa-east-1:000000000000:order_placed_topic --protocol=sqs --notification-endpoint={ORDER EMAIL QUEUE ARN}
```

```bash
aws sns subscribe --endpoint-url=http://localhost:4566 --region sa-east-1 --topic-arn=arn:aws:sns:sa-east-1:000000000000:order_placed_topic --protocol=sqs --notification-endpoint={ORDER PAYMENT QUEUE ARN}
```

```bash
aws sns subscribe --endpoint-url=http://localhost:4566 --region sa-east-1 --topic-arn=arn:aws:sns:sa-east-1:000000000000:payment_processed_topic --protocol=sqs --notification-endpoint={PAYMENT EMAIL QUEUE ARN}
```

```bash
aws sns subscribe --endpoint-url=http://localhost:4566 --region sa-east-1 --topic-arn=arn:aws:sns:sa-east-1:000000000000:payment_processed_topic --protocol=sqs --notification-endpoint={PAYMENT ORDER QUEUE ARN}
```

#### Alterando propriedades:
```bash
aws sns set-subscription-attributes --endpoint-url=http://localhost:4566 --region sa-east-1 --subscription-arn={SUBSCRIPTION ARN} --attribute-name=RawMessageDelivery --attribute-value=true
```
```bash
aws sns set-subscription-attributes --endpoint-url=http://localhost:4566 --region sa-east-1 --subscription-arn={SUBSCRIPTION ARN} --attribute-name=RawMessageDelivery --attribute-value=true
```
```bash
aws sns set-subscription-attributes --endpoint-url=http://localhost:4566 --region sa-east-1 --subscription-arn={SUBSCRIPTION ARN} --attribute-name=RawMessageDelivery --attribute-value=true
```
```bash
aws sns set-subscription-attributes --endpoint-url=http://localhost:4566 --region sa-east-1 --subscription-arn={SUBSCRIPTION ARN} --attribute-name=RawMessageDelivery --attribute-value=true
```


