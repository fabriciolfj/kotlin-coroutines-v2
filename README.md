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

## Simultaineidade estruturada
```
O escopo da corrotina é responsável pela estrutura e pelos relacionamentos pai-filho entre as diferentes corrotinas. Novas corrotinas geralmente precisam ser iniciadas dentro de um escopo.

O contexto da corrotina armazena informações técnicas adicionais usadas para executar uma determinada corrotina, como o nome personalizado da corrotina ou o despachante que especifica os threads nos quais a corrotina deve ser agendada.

Quando launch, async, ou runBlockingsão usados para iniciar uma nova corrotina, eles criam automaticamente o escopo correspondente. Todas essas funções usam um lambda com um receptor como argumento e CoroutineScopesão do tipo de receptor implícito:

launch { /* this: CoroutineScope */ }
Novas corrotinas só podem ser iniciadas dentro de um escopo.

launche asyncsão declarados como extensões de CoroutineScope, portanto, um receptor implícito ou explícito sempre deve ser passado quando você os chama.

A corrotina iniciada por runBlockingé a única exceção porque runBlockingé definida como uma função de nível superior. Mas, como bloqueia o thread atual, destina-se principalmente ao uso em main()funções e testes como uma função de ponte.

Uma nova corrotina dentro de runBlocking, launchou asyncé iniciada automaticamente dentro do escopo:

import kotlinx.coroutines.*

fun main() = runBlocking { /* this: CoroutineScope */
    launch { /* ... */ }
    // the same as:
    this.launch { /* ... */ }
}
Quando você chama launchinside runBlocking, ele é chamado como uma extensão do receptor implícito do CoroutineScopetipo. Alternativamente, você poderia escrever explicitamente this.launch.

A corrotina aninhada (iniciada por launchneste exemplo) pode ser considerada filha da corrotina externa (iniciada por runBlocking). Esse relacionamento “pai-filho” funciona por meio de escopos; a corrotina filha é iniciada a partir do escopo correspondente à corrotina pai.

É possível criar um novo escopo sem iniciar uma nova corrotina, usando a coroutineScopefunção. Para iniciar novas corrotinas de forma estruturada dentro de uma suspendfunção sem acesso ao escopo externo, você pode criar um novo escopo de corrotina que automaticamente se torna filho do escopo externo a partir do qual esta suspendfunção é chamada. loadContributorsConcurrent()é um bom exemplo.

Você também pode iniciar uma nova corrotina no escopo global usando GlobalScope.asyncou GlobalScope.launch. Isso criará uma corrotina "independente" de nível superior.

O mecanismo por trás da estrutura das corrotinas é chamado de simultaneidade estruturada . Ele fornece os seguintes benefícios em relação aos escopos globais:

O escopo geralmente é responsável pelas corrotinas filhas, cujo tempo de vida está vinculado ao tempo de vida do escopo.

O escopo pode cancelar automaticamente as corrotinas filhas se algo der errado ou se um usuário mudar de ideia e decidir revogar a operação.

O escopo aguarda automaticamente a conclusão de todas as corrotinas filhas. Portanto, se o escopo corresponder a uma corrotina, a corrotina pai não será concluída até que todas as corrotinas lançadas em seu escopo sejam concluídas.

Ao usar GlobalScope.async, não há estrutura que vincule várias corrotinas a um escopo menor. As corrotinas iniciadas no escopo global são todas independentes – seu tempo de vida é limitado apenas pelo tempo de vida de todo o aplicativo. É possível armazenar uma referência à corrotina iniciada no escopo global e aguardar sua conclusão ou cancelá-la explicitamente, mas isso não acontecerá automaticamente como aconteceria com a simultaneidade estruturada.
```

## Channels
```
Escrever código com um estado mutável compartilhado é bastante difícil e sujeito a erros (como na solução que usa retornos de chamada). Uma maneira mais simples é compartilhar informações por comunicação, em vez de usar um estado mutável comum. As corrotinas podem se comunicar entre si por meio de canais .

Canais são primitivas de comunicação que permitem a passagem de dados entre corrotinas. Uma corrotina pode enviar algumas informações para um canal, enquanto outra pode receber essas informações dele:

Usando canais
Uma corrotina que envia (produz) informações costuma ser chamada de produtor, e uma corrotina que recebe (consome) informações é chamada de consumidor. Uma ou várias corrotinas podem enviar informações para o mesmo canal, e uma ou várias corrotinas podem receber dados dele:

Usando canais com muitas corrotinas
Quando muitas corrotinas recebem informações do mesmo canal, cada elemento é tratado apenas uma vez por um dos consumidores. Depois que um elemento é manipulado, ele é imediatamente removido do canal.

Você pode pensar em um canal como algo semelhante a uma coleção de elementos, ou mais precisamente, uma fila, na qual os elementos são adicionados em uma extremidade e recebidos na outra. Porém, há uma diferença importante: diferentemente das coleções, mesmo em suas versões sincronizadas, um canal pode suspender send() operações receive(). Isso acontece quando o canal está vazio ou cheio. O canal pode estar cheio se o tamanho do canal tiver um limite superior.

Channelé representado por três interfaces diferentes: SendChannel, ReceiveChannel, e Channel, com a última estendendo as duas primeiras. Normalmente você cria um canal e o entrega aos produtores como uma SendChannelinstância para que somente eles possam enviar informações ao canal. Você fornece um canal aos consumidores como uma ReceiveChannelinstância para que somente eles possam receber dele. Ambos os métodos sende receivesão declarados como suspend:

interface SendChannel<in E> {
    suspend fun send(element: E)
    fun close(): Boolean
}

interface ReceiveChannel<out E> {
    suspend fun receive(): E
}

interface Channel<E> : SendChannel<E>, ReceiveChannel<E>
O produtor pode fechar um canal para indicar que não há mais elementos chegando.

Vários tipos de canais são definidos na biblioteca. Eles diferem em quantos elementos podem armazenar internamente e se a send()chamada pode ser suspensa ou não. Para todos os tipos de canais, a receive()chamada se comporta de forma semelhante: recebe um elemento se o canal não estiver vazio; caso contrário, será suspenso.

Canal ilimitado

Canal em buffer

Canal de encontro



Canal combinado

Ao criar um canal, especifique seu tipo ou tamanho do buffer (se precisar de um buffer):

val rendezvousChannel = Channel<String>()
val bufferedChannel = Channel<String>(10)
val conflatedChannel = Channel<String>(CONFLATED)
val unlimitedChannel = Channel<String>(UNLIMITED)
Por padrão, um canal "Rendezvous" é criado.
```

## Cancelamento uma coroutine

## Compondo funcoes de suspensao
- por padrão são sequencias, ou seja, podemos chamar uma funcao, e em seguida outra funcao, somar o resultado das 2
- podemos executar cada funcao de forma assincrona, usando o async, que retorna um Deferred (promisse/future, o launch retorna uma job), 
  - para pegar o valor async, que retorna um deferred, usamos o await. 