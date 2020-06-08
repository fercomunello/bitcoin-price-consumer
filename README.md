Histórico do Bitcoin
=====================================

O objetivo deste projeto é gravar e manter atualizado o histórico de preços do Bitcoin, desde 12/06/2013 em um arquivo JSON.

API do MercadoBitcoin
----------------

Os dados são obtidos da API da corretora brasileira, MercadoBitcoin, para que os valores fiquem o mais próximo da realidade (em BRL).

São necessárias milhares de requisições HTTP pois a API não disponibiliza os dados históricos do Bitcoin em uma única requisição.

Lembrando que a API exige um "User-Agent" no header da requisição HTTP, pode ser "Chrome/83.0.4103.61" por exemplo.

Para maiores informações: https://www.mercadobitcoin.com.br/api-doc/

Como funciona?
----------------

Ao executar, o arquivo "cotacao.json" é gerado e então começa a ser atualizado. O processo somente é finalizado quando
os dados históricos do Bitcoin estão completos, até a data atual.

A ideia é consumir esses dados em algum projeto real, como um simulador de negociações por exemplo ...

Comando para executar: java -jar historico-bitcoin-1.0.jar
Diretório da jar: jar/historico-bitcoin-1.0.jar
