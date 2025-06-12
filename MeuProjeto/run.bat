@echo off
echo Compilando o projeto...

:: Cria pasta bin se não existir
if not exist bin mkdir bin

:: Compila todos os arquivos .java de uma vez
javac -cp "lib/mysql-connector-java.jar" -d bin src\model\*.java src\controller\*.java src\utils\*.java

if errorlevel 1 (
    echo Erro na compilação.
    pause
    exit /b
)

echo Executando o projeto...
:: Substitua pelo nome da classe que tem o main()
java -cp "bin;lib/mysql-connector-java.jar" controller.App
pause
