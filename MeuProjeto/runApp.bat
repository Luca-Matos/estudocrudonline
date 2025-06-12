@echo off
echo Compilando...
if not exist out mkdir out

rem Compila todos os .java da pasta src e subpastas, gera classes em out
javac -d out -cp "lib\mysql-connector-java.jar" src\controller\App.java src\model\*.java src\utils\*.java

if errorlevel 1 (
    echo Erro na compilacao, abortando.
    pause
    exit /b 1
)

echo Compilacao concluida.

echo Rodando o programa...
java -cp "out;lib\mysql-connector-java.jar" controller.App

pause
