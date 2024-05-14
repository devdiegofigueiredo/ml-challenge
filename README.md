# Bem vindo ao ML Challenge!

Bem-vindo(a) ao **ML Challenge**! Aqui você encontrará um facilitador de busca de produtos do Mercado Livre BR!

## O que você encontra aqui?

Uma listagem de um produto buscado, com seus detalhes e também a possibilidade de abrir no app ou site do Mercado Livre para efetuar a compra.

## Informações técnicas 🔥

Na criação desse projeto, nos importamos em trazer um layout simplificado e de fácil entendimento para os nossos usuários! Tratamos as possibilidades de nenhum produto encontrado, usuário com erro ou sem conexão com a internet, e um redirecionamento para site ou app do Mercado Livre para comprar o produto em específico.

## O que não levamos em consideração?

Não pensamos em um layout complexo, ou animações! Optamos por investir tempo em uma boa arquitetura de código, tratando seus possíveis erros, utilizando o que há de mais interessante no mercado relacionado a técnologias do Android!

## E cadê o Compose?️

Como queríamos algo que fosse performático em tempo de construção e que tivéssemos grande certeza de seu funcionamento e padrões corretos, optamos por utilizar o XML padrão. E sim.. ainda não posso dizer que dominamos 100% do compose a ponto de utilizar e justificar tudo o que foi implementado.

## Posso reutilizar?

É bem comum falarmos sobre reutilização de código, portanto nossa arquitetura foi pensada em camadas.. podendo utilizar da listagem para frente, ou apenas a tela de detalhes.. se fossemos construir algo pensando em atender uma grande quantidade de desenvolvedores, faríamos algo multi-modular entregando callbacks para que as features decidirem o que fazer com as informações recuperadas e qual seria a próxima tela.


## Poderíamos ter utilizado o padrão One Activity Multiples Fragments?

Sim.. poderíamos ter utilizado e teríamos (neste caso) a mesma experiência de usuário, por termos telas simples e pouca navegação sem customizações.

## Se esse projeto fosse a diante, quais seriam as próximas features sugeridas aos nossos stakeholders?

Poderíamos criar caches de items buscados, caches nos resultados, e uma API que trouxesse mais detalhes dos produtos.

## Considerações finais (ao time ML)

Não encontramos na documentação as APIs para listarmos as imagens em um view pager e o restante dos detalhes dos produtos.. por isso trabalhamos apenas com a thumbnail, caso vocês achem que faça sentido, poderiam me dizer essas APIs e eu finalizo a construção de forma mais completa, utilizando Parallax para trazer um efeito confortável ao scrollar a tela de detalhes.
