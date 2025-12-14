@echo off
rem Запуск program.jar из этой же папки
set "DIR=%~dp0"
java -jar "%DIR%program.jar"
