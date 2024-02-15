# kotlin-coroutines-v2
- site https://kotlinlang.org/docs/flow.html#processing-the-latest-value
- Uma coroutine é um tipo de função em algumas linguagens de programação que pode ter sua execução suspensa e retomada em outro momento.
- Aqui está um exemplo em Kotlin explicando o contexto de corrotinas, scopes e tipos de corrotinas:

```
import kotlinx.coroutines.*

fun main() = runBlocking<Unit> { // 1

    launch { // launch é um scope de coroutine  
        // 2
        delay(1000L) 
        println("Corrotina de escopo Global")
    }
    
    coroutineScope { // coroutineScope = novo escopo  
        launch {
            delay(500L)     
            println("Corrotina de escopo interno") 
        }
        
        // aguarda corrotinas terminarem antes de fechar esse scope
    }
    
    println("Código fora das corrotinas")
    
}

```
```
Explicando os números:

1. O `runBlocking` é um **scope especial** que aguarda todas as suas corrotinas filhas terminarem antes de finalizar.

2. O `launch` inicia uma nova **coroutine estruturada** (não precisa fechar manualmente). As corrotinas estruturadas fazem parte do **escopo principal** da thread, diferente de threads novas.

3. O `coroutineScope` cria um **novo escopo** onde podemos iniciar novas corrotinas. Ele aguarda essas corrotinas filhas terminarem antes de sair do escopo.

Então podemos ver que as corrotinas:

- Rodam dentro de um **contexto** e **escopo**
- Podem criar novos escopos como o `coroutineScope`
- Podem ser **estruturadas** (como o `launch`) ou precisa controlar manualmente (com `async`)

O contexto e escopos controlam o ciclo de vida das corrotinas.
```
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

A principal diferença entre async launch que async usado para iniciar um cálculo que não deve retornar um resultado específico. launch retorna uma job que representa a corrotina. É possível esperar até que seja concluído ligando para Job.join().

Deferred é um tipo genérico que estende Job. Uma chamada assincrona pode retornar a Deferred<Int>ou a Deferred<CustomType>, dependendo do que o lambda retorna (a última expressão dentro do lambda é o resultado).

Para obter o resultado de uma corrotina, você pode chamar await() na instância Deferred. Enquanto aguarda o resultado, a corrotina de onde é chamadoo await() é suspensa
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

launch async são declarados como extensões de CoroutineScope, portanto, um receptor implícito ou explícito sempre deve ser passado quando você os chama.

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

## Resumo entendimento
- a coroutines é executada dentro de um escopo (relação pai-filho)
- a execução coroutine pai é finalizada, quando as coroutines filhas são concluídas (simultaneidade estruturada)
- o contexto de uma coroutine é o nome ou a thread aonde ela é executada (fazendo isso atravéls dos Dispatchers)
- podemos criar novos contextos usando coroutineContext, mas ela se tornára filha da coroutine externa chamadora
- quando colocamos as coroutines como escopo Global, ela não é estruturada, ou seja, ela executa sozinha sem a relação pai-filho

## funcoa suspend
```
 Uma função com a função suspend em Kotlin é uma função assíncrona que pode suspender/pausar sua execução sem bloquear threads ou uso intensivo de recursos.

Algumas características de funções suspend:

- São declaradas com a keyword `suspend`. Ex:

suspend fun buscarDados() {

}

- Podem chamar outras funções `suspend`.

- Suspendem coroutines quando trabalham em tarefas assíncronas como E/S ou esperando por dados.

- Não bloqueiam threads - suspensão libera threads do pool.

- Resumem depois que dados ou eventos assíncronos estão disponíveis.

- Permitem que códigos assíncronos sejam escritos de forma sequencial, sem callbacks.

Em apps Android, funções suspend são muito úteis para tarefas como chamadas de rede, acesso a banco de dados, leitura de arquivos, entre outras operações E/S.

Elas simplificam muito códigos assíncronos em Kotlin!

Quando usamos alguma funcao de suspensao (delay por ex), é obrigatorio marcar a funcao como suspend
```

## construtores de corountine
- launch
- async

## Cancelamento uma coroutine

## Compondo funcoes de suspensao
- por padrão são sequencias, ou seja, podemos chamar uma funcao, e em seguida outra funcao, somar o resultado das 2
- podemos executar cada funcao de forma assincrona, usando o async, que retorna um Deferred (promisse/future, o launch retorna uma job), 
  - para pegar o valor async, que retorna um deferred, usamos o await. 

## iniciando coroutines de forma preguiçosa
- nesse caso a coroutine executa o procedimento, somente se for chamada, por start (manda iniciar o processo) ou await (aguarda até terminar)
- para isso precisamos colocar o seguinte
```
val time = measureTimeMillis {
    val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
    val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
    // some computation
    one.start() // start the first one
    two.start() // start the second one
    println("The answer is ${one.await() + two.await()}")
}
println("Completed in $time ms")

```

## coroutine scope
```
 Esta função utiliza o `coroutineScope` para executar duas tarefas assíncronas de forma concorrente usando coroutines, e depois soma os seus resultados.

O `coroutineScope`:

- É um escopo que garante que todas as coroutines iniciadas dentro dele serão canceladas/finalizadas quando sair do escopo.

- É útil para agrupar lógicas assíncronas relacionadas e garantir que elas terminem juntas.

Dentro do `coroutineScope` desta função:

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

- São iniciadas duas coroutines `async` para executar funções assíncronas em paralelo.

- Usando `await()`, a função principal espera pelos resultados.

- Soma os resultados retornados pelas duas coroutines.  

- Ao sair do escopo, ele garantirá o cancelamento e fechamento automático das duas coroutines filhas.

Então o `coroutineScope` é perfeito para gerenciar ciclos de vida e deixar o código mais limpo quando trabalhamos com coroutines, principalmente quando há múltiplas coroutines sendo executadas.
```
- ponto importante, quando usamos um runBlocking, ele já provê o escopo coroutines

## Simultaneidade estruturada com assíncrono
- quando temos a exeucao de varias coroutines dentro de um escopo, se uma der erro, todas sao canceladas, caso uma corountine filha der erro, o pai tambem é cancelado

## contexto de coroutines e dispatchers
- As corrotinas sempre são executadas em algum contexto representado por um valor do tipo CoroutineContext , definido na biblioteca padrão Kotlin.
- O contexto da corountine é um conjunto de vários elementos. Os elementos principais são o Job da corrotina, que vimos antes, e seu despachante, que é abordado nesta seção.


### dispatchers
- determina qual thread a corountine correspondente usa para sua execução

#### dispatcher unconfined vs confined
- unconfined -> inicia na thread do chamador, e muda na primeira funcao de suspensao (suspense, delay e etc), retornando para a thread dessa funcao
  - ideal para coroutines que nao usam muito tempo de cpu e não atualiza dados compartilhados
- confined -> inicia e termina usando a thread do chamador
```
launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
    println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
    delay(500)
    println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
}
launch { // context of the parent, main runBlocking coroutine
    println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    delay(1000)
    println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
}
```


### withContext
```
 O `withContext()` é uma função suspensa do Kotlin que é muito útil quando trabalhamos com coroutines.

Seu objetivo principal é mudar o contexto de execução das coroutines. 

Alguns dos principais usos:

- Alterar a thread onde a coroutine está executando, por exemplo para despachar um trecho de código para executar na main thread depois de fazer operações em background:

withContext(Dispatchers.Main) {
// atualizar UI na thread principal
}

- Alterar o contexto para um que já esteja configurado ou otimizado para uma determinada operação, como E/S com banco de dados ou chamadas de rede.

- Agrupar trechos de código que devem executar no mesmo contexto.

- Definir escopos customizados que controlam o ciclo de vida ou comportamentos das coroutines internas.

Então o `withContext()` permite que você mude temporariamente o contexto das suas coroutines para o contexto mais adequado àquela operação. Isso mantém o código mais simples, legível e performático.
```

### join()
- usado na coroutine pai, para aguardar a conclusao das coroutines filhas

### MainScope vs CoroutineScope
- mainscope usado para android ui
- coroutineScope de uso mais geral, dentro de funcoes

### Usando ThreadLocal
- o valor da threadlocal não muda entre coroutine
- se coroutine1 tem uma threadlocal com valor 100, passo ela para outra coroutine mudo para 200, dp quando volto para coroutine1, ela continua com 100

### flow()
- computar varios valores de forma assincrona
- é chamado quando chamamos o collect
- respeira o contexto do chamador, ou seja, nao vamos conseguir executar o flow usando um withContext para mudar de contexto

### flowOn()
- para executar o flow em um contexto diferente do chamador

### buffering
- executar o flux simultaneamente e nao sequencialmente

### conflation
- usado para ignorar valores intermediarios, quando o coletor e muito lento


### xxxLatest
- cancela o block de codigo quando vir um novo valor, que acaba sendo o ultimo

### combine vs zip flow
```
O combine e o zip são duas funções úteis para combinar múltiplos flows em Kotlin. Aqui estão as principais diferenças entre elas quando usadas com Coroutine Flows:

- combine: 
    - Combina valores de múltiplos flows em um único objeto do tipo especificado.
    - Os flows devem emitir sequencialmente. Ou seja, primeiro o flow1 emite, depois o flow2, e assim por diante.
    - Espera que todos os flows emitam um valor antes de emitir o objeto combinado.
    - Se algum flow parar de emitir, o flow resultante também para.

- zip:
    - Emite tuplas contendo os valores emitidos de cada flow. 
    - Os flows podem emitir valores em paralelo ou sequencialmente.
    - Se algum flow parar de emitir valores, os demais podem continuar emitindo.
    - O menor flow define o número de emissões do flow resultante.

Em resumo, o combine sincroniza a emissão de múltiplos flows, enquanto o zip intercala as emissões mas não as sincroniza. Escolha combine quando precisa de valores sincronizados, e zip para uma "mescla" mais flexível.
```

### flatMapConcat
- A concatenação de fluxos de fluxos é fornecida pelos operadores flatMapConcat e flattenConcat . 
- Eles são os análogos mais diretos dos operadores de sequência correspondentes.
- Eles esperam que o fluxo interno seja concluído antes de começar a coletar o próximo

### flatMapMerge
- Outra operação de nivelamento é coletar simultaneamente todos os fluxos recebidos e mesclar seus valores em um único fluxo 
- para que os valores sejam emitidos o mais rápido possível. É implementado pelos operadores flatMapMerge e flattenMerge .
- Ambos aceitam um concurrencyparâmetro opcional que limita o número de fluxos simultâneos coletados ao mesmo tempo (é igual a DEFAULT_CONCURRENCY por padrão).

### flatMapLatest
- De forma semelhante ao operador collectLatest , que foi descrito na seção "Processando o último valor" ,
- existe o modo de nivelamento "Latest" correspondente onde a coleta do fluxo anterior é cancelada assim que um novo fluxo é emitido. 
- É implementado pelo operador flatMapLatest .

### exception
- um fluxo pode ser concluido, quando o emissor ou o código dentro dos operadores, lançar uma exceção

### operador launchIn
- um operador terminal, que inicia a coleta em uma coroutine separada


## canais
- forma de transferir valores entre coroutines
- quando tenho um ProducerScope, posso usar um cancel, quando tenho um CoroutineScope, posso usar um cancelChildren
  - pois posso produzir dados infinitos, para interromper uso o cancel
  - na coroutine posso ficar up infinito (while(true)), para interromper, chamo na corountine pai o coroutineContext.cancelChildren()

#### fan-out
- varias coroutines podem receber do mesmo canal, distribuindo o trabalho entre elas

#### fant-in
- vrias corountines podem ser enviadas para o mesmo canal

#### canais com buffer
- podemos definir uma capacidade de buffer no canal, e essa capacidade armazenará dados ate que fique cheia,
- caso fique cheia, este será suspenso (capacidade 4 envio 5 mensagens, 4 fica no buffer na 5 suspende)
- o suspende seria, até que algum receber ligue para o canal receber os dados


#### canal ordem
- a ordem do canal, segue: primero a entrar, primeiro a sair
- no arquivo OrdemCanal, temos um exemplo, onde enviamos uma mensagem Ball(0), as 2 coroutines estão ouvindo o canal sem iniciadas, na ordem de declaracao

#### canal ticker
- produz um unit cada vez que um atraso passa desde o ultimo consumo deste canal
- tenta manter uma taxa fixa de elementos produzidos, por exemplo:
  - caso aja um atraso de 150ms para produzir um elemento, o proxímo  a ele também será atrasado automaticamente.

### Exeption coroutine

#### propagacao
- tempos dois tipos:
  - propagacao -> launch (quando chamamos o join para aguarda as threads filhas, nao conseguimos capturar no try cath)
  - expô-las ao usuário -> async (mostra a exception quando usamos o wait), producer

#### cancelamento
- o cancelamento de uma coroutine filha, não cancela o pai
- uma exception em uma coroutine filha, não interrompe o pai
- a coroutine pai pode capturar a exception e efetuar algum tratamento apropriado

#### yield
```
O yield é uma função importante quando se trabalha com corrotinas (coroutines) no Kotlin.

Quando chamado dentro de uma corrotina, o yield faz duas coisas:

Suspende (pauses) a execução corrente da corrotina
Retorna um valor para o caller da corrotina
Isso permite que a corrotina "pause" sua execução no meio do caminho, retorne algum dado para quem a chamou, e depois continue de onde parou quando for chamada novamente.

No contexto de corrotinas com suspensão (suspending coroutines), o yield é usado junto com a função suspend para definir pontos onde a corrotina deve pausar
```

#### agregando excecoes
- quando temos varios filhos e eles lançam exeptions, quando lançar a primeira exception vence, ou seja, a primeira exception lançada que será tratada, as demais serão 
- suprimidas

#### supervisao
```
O supervisorScope e o supervisorJob são formas de alterar esse comportamento padrão de cancelamento e exceções entre corrotinas pai e filhas no Kotlin.

O supervisorScope é um escopo de corrotina (coroutine scope) especial que tem um comportamento diferente de cancelamento de filhas:

- Se uma corrotina filha falhar com uma exceção, essa exceção NÃO é propagada para o pai automaticamente.

- Em vez disso, a filha falha silenciosamente sem afetar as outras filhas ou o pai.

- O pai pode ainda assim capturar exceções usando supervisedScope.children.map { it.exceptions }. Isso retorna exceções de TODAS as filhas.

Já o supervisorJob é um trabalho de corrotina (job) com comportamento similar:

- Filhas lançando exceções não afetam o pai.

- O pai pode capturar exceções de filhas mesmo assim com supervisorJob.childrenExceptionHandler { /* trata aqui */ }

- Cancelar o pai CANCELA automaticamente todas as filhas.

Então em resumo, supervisorScope e supervisorJob previnem a propagação automática de falhas e exceções entre corrotinas pai/filhas, permitindo mais controle e tratamento granular pelo pai.
```