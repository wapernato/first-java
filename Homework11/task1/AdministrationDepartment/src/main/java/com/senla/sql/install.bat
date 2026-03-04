@echo off
setlocal

set DB_NAME=%1
if "%DB_NAME%"=="" set DB_NAME=hotel_db

set DB_USER=%2
if "%DB_USER%"=="" set DB_USER=postgres

set DB_HOST=%3
if "%DB_HOST%"=="" set DB_HOST=localhost

set DB_PORT=%4
if "%DB_PORT%"=="" set DB_PORT=5432

createdb -h %DB_HOST% -p %DB_PORT% -U %DB_USER% %DB_NAME% 2>nul

psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f "Script-1.sql"
if errorlevel 1 exit /b 1

psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f "Script-2.sql"
if errorlevel 1 exit /b 1

echo OK: tables created and data inserted
endlocal
