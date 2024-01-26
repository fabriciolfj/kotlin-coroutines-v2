# kotlin-coroutines-v2
```
Quando uma coroutine suspende a execução, ela libera o thread que estava sendo usada mas não fica totalmente parada, ela aguarda pelo evento que irá disparar o seu resumo.

Por exemplo, no caso de uma requisição HTTP dentro de uma coroutine:

- A coroutine será executada na thread de um thread pool até o ponto de fazer a requisição HTTP;

- Nesse ponto, ela irá registrar callbacks para receber a resposta e será suspensa; 

- A thread do pool é liberada para outras coroutines utilizarem;

- Quando a resposta HTTP chegar, o framework de coroutines (como o Kotlin Flow ou canais) irá disparar o callback registrado;

- Uma thread do pool irá resumir a execução da coroutine a partir do ponto que ela parou;

- A coroutine terá a resposta disponível e poderá fazer as próximas operações ou requisições necessárias.

Então a coroutine fica em estado de suspensão aguardando os eventos que serão seus triggers para retomar a execução, mas não fica totalmente parada. Isso permite reutilizar as threads com eficiência.
```
```

As corrotinas Kotlin consomem muito menos recursos do que os threads. Cada vez que você quiser iniciar um novo cálculo de forma assíncrona, poderá criar uma nova corrotina.

Para iniciar uma nova corrotina, use um dos principais construtores de corrotina : launch, async, ou runBlocking. Bibliotecas diferentes podem definir construtores de corrotinas adicionais.

asyncinicia uma nova corrotina e retorna um Deferredobjeto. Deferredrepresenta um conceito conhecido por outros nomes, como Futureou Promise. Armazena um cálculo, mas adia o momento em que você obtém o resultado final; promete o resultado em algum momento no futuro .

A principal diferença entre async launch que launch usado para iniciar um cálculo que não deve retornar um resultado específico. launchretorna um Jobque representa a corrotina. É possível esperar até que seja concluído ligando para Job.join().

Deferredé um tipo genérico que estende Job. Uma asyncchamada pode retornar a Deferred<Int>ou a Deferred<CustomType>, dependendo do que o lambda retorna (a última expressão dentro do lambda é o resultado).

Para obter o resultado de uma corrotina, você pode chamar await()a Deferredinstância. Enquanto aguarda o resultado, a corrotina de onde isso await()é chamado é suspensa
```

## Contexto de threads
- com corountines podemos mudar o contexto, por exemplo
  - recebo uma requisição rest (thread main)
  - vou fazer um processamento paralelo, mando executar em threads diferentes, para isso marco o launch com launch(Dispatchers.Default)
  - ao concluir, provavelmente terei uma variável de retorno,  e esta envolvo com withContext(Dispatchers.Main) { aqui } 
  - para que o retorno seja processado pela thread main, ou seja, que iniciou a requisição