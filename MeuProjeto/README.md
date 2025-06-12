📂 MeuProjeto/
 ├── 📂 src/
 │    ├── 📂 model/
 │    │    ├── Usuario.java
 │    │    ├── UsuarioDAO.java
 │    │    ├── Materia.java          ✅
 │    │    ├── MateriaDAO.java       ✅
 │    │    ├── Conteudo.java         ✅ NOVO
 │    │    ├── ConteudoDAO.java      ✅ NOVO
 │    ├── 📂 view/
 │    │    ├── index.html            ✅ Atualizado
 │    │    ├── style.css
 │    │    ├── script.js             ✅ Atualizado
 │    ├── 📂 controller/
 │    │    ├── App.java
 │    │    ├── ConteudoController.java ✅ NOVO (ou dentro de App.java por enquanto)
 │    ├── 📂 utils/
 │    │    ├── Conexao.java
 ├── 📂 lib/
 │    ├── mysql-connector-java.jar
 ├── 📂 sql/
 │    ├── script.sql                 ✅ Atualizado com novas tabelas
 ├── 📜 README.md

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
