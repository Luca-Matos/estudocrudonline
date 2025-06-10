📂 MeuProjeto/
 ├── 📂 src/                # Código-fonte Java
 │    ├── 📂 model/         # Camada de modelo (dados e acesso ao banco)
 │    │    ├── Usuario.java         # Classe do usuário (entidade)
 │    │    ├── UsuarioDAO.java      # Classe DAO para acessar o banco
 │    ├── 📂 view/          # Interface do usuário (HTML, CSS, JavaScript)
 │    │    ├── index.html         # Página principal
 │    │    ├── style.css          # Estilos da página
 │    │    ├── script.js          # Lógica do front-end
 │    ├── 📂 controller/    # Lógica de controle e execução
 │    │    ├── App.java            # Classe principal para execução do sistema
 │    ├── 📂 utils/         # Utilitários e conexão com banco de dados
 │    │    ├── Conexao.java        # Classe para conectar ao MySQL
 ├── 📂 lib/                # Dependências externas
 │    ├── mysql-connector-java.jar # Driver JDBC do MySQL
 ├── 📂 sql/                # Scripts SQL para banco de dados
 │    ├── script.sql        # Criação das tabelas no MySQL
 ├── 📜 README.md           # Documentação do projeto

📂 Descrição dos Arquivos
1️⃣ Model (Camada de Dados)

    Usuario.java → Classe que representa um usuário do sistema.
    Materia.java
    MateriaDAO.java

    UsuarioDAO.java → Classe para interagir com o banco de dados (CRUD).

2️⃣ View (Interface do Usuário)

    index.html → Página web principal.

    style.css → Estilos visuais da aplicação.

    script.js → Lógica do front-end para interações.

3️⃣ Controller (Lógica de Controle)

    App.java → Classe principal que inicia a aplicação e gerencia as operações.

4️⃣ Utils (Utilitários e Conexão)

    Conexao.java → Classe que estabelece a conexão com MySQL.

5️⃣ Lib (Bibliotecas Externas)

    mysql-connector-java.jar → Dependência necessária para conectar com MySQL.

6️⃣ SQL (Banco de Dados)

    script.sql → Script para criação de tabelas no MySQL.
