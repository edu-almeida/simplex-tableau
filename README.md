# simplex-tableau

A partir de um tableau simplex inicial, busca uma solução básica viável se possível.

## Description

O usuário é indagado a responder qual a proporção de seu simplex em formato tableau:
Número de linhas e colunas que o compõem.
As primeiras linhas correspondem às restrições e a última corresponde ao vetor Z ou sua MAX ou MIN. O programa não espera a coluna de valores Z no tableau. Espera-se os valores para X, Folgas e Igualdades (b).
Exemplo:

\ | x1 | x2 | f1 | f2 | f3 | b
 ------ |---|---| ------ | ------ | ------ | ------
 Z  | -1 | -2 | 0 | 0 | 0 | 0
 r2  | 2 | 3 | 1 | 0 | 0 | 12
 r3  | 3 | 4 | 0 | 1 | 0 | 5
 r4  | 4 | 5 | 0 | 0 | 1 | 8

Ao final ele retorna uma matriz com uma solução basica, como por exemplo:

````
1,00    0,00    0,50    0,00    -0,50   1,00
0,00    0,00    -1,50   1,00    0,50    3,00
0,00    1,00    0,50    0,00    0,50    5,00
0,00    0,00    2,50    0,00    0,50    17,00
````


### Dependencies

* Java OpenJDK Versão 18


## Help

O programa espera uma matriz que assimila um tableau, o preenchimento correto é necessário. 

## Authors

 [Eduardo Almeida](https://instagram.com/duu_almeida_)

 [Clayver Alves](https://www.instagram.com/clayveralves/)

## Version History

* 0.1
    * Initial Release

## License
GNU General Public License v3.0 (GNU GPLv3)

