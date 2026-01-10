#  Sistema de Gerenciamento de Eventos Universitários

Esse sistema foi meu projeto final para a disciplina de Programção Orientada a Objetos ministrada pelo professor Alan Lopes.  
## Funcionalidades

### Aluno
- Pode se cadastrar no sistema
- Pode visualizar eventos disponíveis
- Pode se inscrever e cancelar inscrições em eventos
- Possui um histórico de eventos já participados

### Professor
- Pode se cadastrar no sistema
- Pode criar novos eventos
- Pode editar informações dos eventos que criou
- Pode visualizar a lista de inscritos em seus eventos

### Administrador
- Responsável por gerenciar todo o sistema
- Pode cadastrar, editar e remover qualquer evento
- Pode gerenciar usuários (ativar, desativar ou remover)
- Possui acesso a relatórios e estatísticas gerais da plataforma
### Obs
Professores e Administradores podem visualizar quem está inscrito, mas professor pode ver somente nos eventos que o próprio criou.

## Tecnologias Utilizadas

- **Java 8+**
- **Programação Orientada a Objetos (POO)**
- **Collections Framework**
- **Stream API**
- **LocalDateTime API**

## Como Executar

### Pré-requisitos
- Java JDK 8 ou superior
- IDE Java (IntelliJ IDEA, Eclipse, NetBeans) ou terminal

### Passos para execução

1. **Clone o repositório**
```bash
git clone https://github.com/anakenobi/trabalho-final-poo.git

```

2. **Compile o projeto**
```bash
javac -d bin src/**/*.java
```

3. **Execute o sistema**
```bash
java -cp bin Main
```

### Usando uma IDE

1. Importe o projeto na sua IDE
2. Configure o JDK
3. Execute a classe `Main.java`

## Credenciais de Teste

O sistema vem com usuários pré-cadastrados para testes:

| Tipo | Email | Senha |
|------|-------|-------|
| Aluno | joao@email.com | 123 |
| Aluno | maria@email.com | 123 |
| Professor | carlos@email.com | 123 |
| Professor | ana@email.com | 123 |
| Administrador | admin@email.com | 123 |

##  Relatórios Disponíveis

### 1. Eventos Mais Populares
Lista os 5 eventos com maior número de inscrições

### 2. Inscrições por Aluno
Mostra estatísticas de participação de cada aluno

### 3. Estatísticas Gerais
Painel completo com:
- Total de usuários (por tipo e status)
- Total de eventos (por categoria)
- Total de inscrições (ativas e canceladas)

## Armazenamento de Dados

O sistema utiliza **armazenamento em memória** (listas) durante a execução. Os dados são perdidos ao encerrar o programa. 


