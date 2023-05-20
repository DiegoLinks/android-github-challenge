# Android github challenge

In summary, the challenge is to create an application that consumes the GitHub API. The project should be completed within 3 days and developed using Kotlin and native Android. Third-party open-source libraries are allowed, and the code should be hosted in a public Git repository.

The application should fetch the list of users and provide the ability to search for users by their name. Upon selecting a user, their information and repository list should be displayed.

API Documentation: https://developer.github.com/v3/</br>
User List: https://api.github.com/users</br>
User Data: https://api.github.com/users/{username}</br>
User Repositories: https://api.github.com/users/{username}/repos

## Planning

The development was divided into several main parts, which were further separated into branches during the development process.

  **• feature/navigation:** The project was created and added to the repository, and the libraries planned in the initial planning were added. Fragments were created and initial navigation was implemented without data. Dependency injection was also performed.</br>
  **• feature/api-data:** The initial flow of API consumption was created, including data flow, basic architecture structure (MVVM), and error handling.</br>
  **• feature/user-details:** Fetching data of the selected user and their repositories, constructing the layout for this flow.</br>
  **• feature/home:** The layout of the Home Screen and the search component were builded.</br>
  **• feature/error:** Improved the error handling of the API, creating an error screen and a simple alert with an error message.</br>
  **• feature/unit-test:** Unit tests were performed for the view models, repository, and the class responsible for converting the API error JSON.</br>
  **• feature/improvement:** The shimmer effect was added for the loading screen. The application icons were also included.</br>

## About project

The project was based on the concept of Single Activity, using Fragments as the user interface. Navigation was implemented with Jetpack Navigation, which simplifies the flow and avoids issues with lifecycle management. The chosen architecture was MVVM, which is commonly found in large-scale enterprise applications. Hilt was used as the dependency injection library, and Retrofit was employed to handle network requests.

The app's appearance was inspired by the actual GitHub version and implemented using design patterns, such as spacing, colors, and font sizes. These values were centralized in a guideline file, contributing to standardization, particularly in larger projects. Regarding the app's interface, it was developed with compatibility for dark mode, which is considered essential in current projects.

![image1](https://github.com/DiegoLinks/android-github-challenge/assets/36086232/8409d438-0f17-42ab-93de-98332fdfb27e)


Error handling mechanisms were implemented to display specific error messages from the server when such errors occur. In addition to specific messages, a generic error message is also provided when necessary. Errors are displayed in a simple AlertDialog on the Home screen and through a Toast on the user details screen. Ideally, a consistent approach would be followed throughout a real application. However, in this case, both versions were included to accommodate different preferences. Additionally, an error screen was created on the Home screen, which is a useful and common feature in many apps to communicate with the user.

The search functionality on the Home screen is implemented using a standard Android component called SearchView, which is integrated with the Toolbar following the design of the original version. The entered value is sent to the ViewModel, which processes it and updates the list in real time using LiveData.

On the Home screen, a shimmer effect was implemented to indicate loading while the list is being loaded. On the user details screen, the standard Android component, a circular progress bar, was implemented. Ideally, it is recommended to follow a consistent pattern throughout the project. However, in this case, two different approaches were implemented to showcase and reuse both cases: shimmer effect for loading on the Home screen and circular progress bar on the user details screen.

![image2](https://github.com/DiegoLinks/android-github-challenge/assets/36086232/f8bb17e2-3d43-45b2-a62e-0c6baebf5000)

Regarding the API calls, coroutines were used to handle asynchronous processes. Here's a brief summary of the flow: When the app is opened, the user list call is triggered in the ViewModel, which accesses a repository interface. The repository, in turn, accesses the API interface with the respective endpoint. The API call is made and returns a response to the repository. This response is handled in the handleApi method, which returns a generic object that can contain the data or a handled error. This process is done using suspend methods, which perform asynchronous calls without blocking the main thread, allowing other tasks to be executed concurrently. The result reaches the ViewModel, which returns the generic object with the states of success, error, and loading. The ViewModel then passes this information to the View, which updates the state to "loading." After that, depending on whether the result is successful or an error, the data is displayed in the View or the error message is shown. From that point on, the process is the same for calling user details and their repository list.

Unit tests were also implemented for expected scenarios and error scenarios. The tests were performed for the ViewModels, Repository, and the API error handling class. The testing libraries used include Junit, AndroidX Core Testing, Kotlin Coroutines Test, and MockK.

## Important Notes

The application was developed using XML instead of Jetpack Compose. The recommendation for projects starting from this date or later is to use Compose instead of XML. However, since this is a technical challenge related to a selection process, the application was built using recent technologies and best practices while trying to closely resemble the reality of most ongoing projects, which often still use XML instead of Compose.

Another important observation is that in a real project, the lists can vary from 0 to N, and not just a few users as in this case. Therefore, it is recommended to implement pagination for lists that can potentially be large.

### Some topics covered in the project include:
 • Git, GitFlow</br>
 • Configuração de gradle</br>
 • JetPack Navigation, NavGraph, Single Activity</br>
 • Dependency injection</br>
 • API REST, Retrofit, OkHttp, Kotlin Serialization</br>
 • Material Design</br>
 • MVVM</br>
 • Image processing with Glide</br>
 • Coroutines</br>
 • Extension Functions</br>
 • Dark mode</br>
 • Standard English version and Portuguese translation</br>

### Some libraries added to the project include:
 • Jetpack Navigation</br>
 • Hilt</br>
 • Retrofit</br>
 • Kotlin serialization</br>
 • OkHttp</br>
 • Glide</br>
 • Moshi</br>
 • Shimmer</br>
 • AndroidX Core Testing</br>
 • Kotlin Coroutines Test</br>
 • MockK</br>

_Agora em português!_

# Android github challenge


Em resumo, o desafio é criar um aplicativo que consuma a api do github, o projeto deve ser feito em 3 dias. Deve ser desenvolvido com kotlin, android nativo, podendo usar bibliotecas open source de terceiros e deve ser disponibilizado em um repositório git público.

O aplicativo deve trazer a lista de usuários, e deve fornecer a possibilidade de buscar usuários pelo nome. Ao selecionar um usuário, devem ser exibidas suas informações e sua lista de repositórios.

Documentação da Api: https://developer.github.com/v3/</br>
Lista de usuários: https://api.github.com/users</br>
Dados do usuário: https://api.github.com/users/{username}</br>
Repositórios do usuário: https://api.github.com/users/{username}/repos</br>

## Planejamento

O desenvolvimento foi dividido em algumas partes principais, que por sua vez foram separadas em branchs na hora do desenvolvimento.

**• feature/navigation:** O projeto foi criado e adicionado ao repositório, foram adicionadas as bibliotecas pensadas no planejamento inicial. Foram criados os Fragments e implementada a navegação inicial ainda sem os dados. Feita a injeção de dependências.</br>
**• feature/api-data:** Foi criado o fluxo inicial do consumo da Api, com fluxo de dados, estrutura básica da arquitetura (MVVM) e tratamento de erros.</br>
**• feature/user-details:** Consumo dos dados do usuário selecionado e seus repositórios, construção do layout desse fluxo.</br>
**• feature/home:**  Construído o layout da tela inicial e o componente de busca.</br>
**• feature/error:** Aprimorado o tratamento de erros da Api, criando uma tela de erro e um alerta simples com mensagem de erro.</br>
**• feature/unit-test:** Feitos os testes unitários das viewmodels, do repository e da classe responsável por converter o JOSN de erro da Api.</br>
**• feature/improvement:**  Acrescentado o shimmer para loading da tela principal. Adicionados os ícones do aplicativo.</br>

## Sobre o projeto

O projeto foi feito baseado no conceito de Single Activity, usando Fragments como interface com o usuário. A navegação foi feita com Jetpack Navigation, o que simplifica o fluxo e evita problemas com gerenciamento do ciclo de vida. A arquitetura escolhida foi MVVM, pensando no que é mais comum de se encontrar nos aplicativos de grandes empresas. Foi usado Hilt como biblioteca de injeção de dependências, e Retrofit para lidar com as requisições de rede.

A aparência do app foi inspirada na versão real do github, foi implementada usando padrões de design, por exemplo, nos padrões de espaçamento, cores e tamanho de fonte. Esses valores foram centralizados num arquivo a ser usado como guideline, o que contribui para padronização em especial quando trabalhamos com projetos maiores. Ainda sobre a interface do aplicativo, ele foi desenvolvido tendo compatibilidade com o modo escuro, algo que pode ser considerado indispensável em projetos atuais.

![image1](https://github.com/DiegoLinks/android-github-challenge/assets/36086232/8409d438-0f17-42ab-93de-98332fdfb27e)

Foram criados tratamentos de erro para retornar mensagens de erro específicas vindo do servidor quando esse tipo de erro acontecer. Além de uma mensagem genérica, caso seja necessário. Os erros são exibidos em um Alertdialog simples na Home e por meio de um Toast na tela de detalhes do usuário, o ideal é que siga o mesmo padrão em um aplicativo real, mas no caso optei por exibir as duas versões para que possa ser aproveitada a que melhor atender. Também foi criada uma tela de erro na Home, que é um recurso interessante e comum em muitos apps para se comunicar com o usuário.

A busca feita na home acontece por meio de um componente padrão no android, o  SearchView que no caso foi acoplado a ToolBar seguindo o design da versão original. O valor digitado é enviado para a ViewModel que faz o tratamento e atualiza a lista em tempo real por meio de LiveData.

Na home foi implementado o shimmer para fazer o efeito de loading enquanto a lista é carregada, já na tela de detalhes do usuário foi implementado o componente padrão do android o progressbar circular. Como no caso das notificações de erro, o ideal é que se siga um único padrão em todo o projeto, mas nesse caso foram feitas duas abordagens diferentes para que ambos os casos possam ser demonstrados e reaproveitados.

![image2](https://github.com/DiegoLinks/android-github-challenge/assets/36086232/f8bb17e2-3d43-45b2-a62e-0c6baebf5000)

Sobre as chamadas da Api, foram utilizadas as corrotinas para lidar com os processos assíncronos. Um pequeno resumo do fluxo: Ao abrir o app a chamada da lista de usuários é disparada na ViewModel, que por sua vez acessa uma interface do repositório, que por meio do repositório acessa a interface da Api com o respectivo endPoint. A chamada é feita na Api e retorna uma resposta ao repositório. Essa resposta é tratada no método handleApi que retorna um objeto genérico que pode conter os dados ou um erro tratado. Esse processo é feito por meio de métodos suspend que fazem as chamadas assíncronas sem bloquear a thread principal o que permite que outras tarefas sejam executadas enquanto isso. O resultado chega a ViewModel, retornando esse o objeto genérico que tem o estado de sucesso, erro e carregamento. A viewmodel passa a informação para View que atualiza o estado para carregando/loading em seguida o resultado recebido é identificado como sucesso ou erro. Se sucesso, os dados são exibidos na View, se erro a mensagem de erro é exibida na View. Do ponto da chamada em diante o processo é o mesmo para a chamada dos detalhes do usuário e também da sua lista de repositórios.

Também foram implementados testes unitários para os cenários esperados e cenários de erro. Os testes foram feitos para as ViewModels, o Repositório e também para a classe de tratamento de erros da API. Para os testes foram utilizadas as bibliotecas Junit, AndroidX Core Testing, Kotlin Coroutines Test e MockK.

## Observações importantes

O aplicativo foi feito com XML e não com o Jetpack Compose, a recomendação de um projeto a ser iniciado nessa data ou em data posterior é usar compose e não XML. Porém, como se trata de um desafio técnico envolvendo um processo seletivo foi feito usando tecnologias recentes e boas práticas, mas tentando se aproximar ao máximo da realidade da maioria dos projetos atuais que já estão em andamento, que na maioria dos casos ainda usa XML e não Compose.

Outra observação importante, num projeto real as listas podem ser de 0 a N e não com poucos usuários como é o caso. Então é recomendável que listas que podem ser grandes sejam feitas com paginação.

### Alguns tópicos abrangidos no projeto
 • Git, GitFlow</br>
 • Gradle</br>
 • JetPack Navigation, Gráfico de navegação, Single Activity</br>
 • Injeção de dependências</br>
 • API REST, Retrofit, OkHttp, Kotlin Serialização</br>
 • Material Design</br>
 • Arquitetura MVVM</br>
 • Tratamento de imagens com Glide</br>
 • Corrotines</br>
 • Extension Functions</br>
 • Tema escuro</br>
 • Versão padrão inglês e tradução Portugûes</br>

### Some libraries added to the project include:
 • Jetpack Navigation</br>
 • Hilt</br>
 • Retrofit</br>
 • Kotlin serialization</br>
 • OkHttp</br>
 • Glide</br>
 • Moshi</br>
 • Shimmer</br>
 • AndroidX Core Testing</br>
 • Kotlin Coroutines Test</br>
 • MockK</br>

_É sempre bom ter informação disponível na nossa língua! Mas de qualquer forma... estude inglês my friend._
