@echo off
REM E-Doctorat Backend Startup Script for Windows

echo ==========================================
echo   E-Doctorat Backend Startup
echo ==========================================
echo.

REM Navigate to backend directory
set BACKEND_DIR=Edoctorat-F\Edoctorat
if not exist "%BACKEND_DIR%" (
    echo ERROR: Backend directory not found: %BACKEND_DIR%
    pause
    exit /b 1
)

cd "%BACKEND_DIR%"

echo Building backend...
call mvn clean install -DskipTests

if errorlevel 1 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo Build successful
echo.

echo Starting Spring Boot application on port 8085...
echo Backend will be available at: http://localhost:8085
echo.
echo Press Ctrl+C to stop the server
echo.

call mvn spring-boot:run
