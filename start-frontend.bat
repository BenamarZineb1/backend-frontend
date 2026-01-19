@echo off
REM E-Doctorat Frontend Startup Script for Windows

echo ==========================================
echo   E-Doctorat Frontend Startup
echo ==========================================
echo.

REM Navigate to frontend directory
set FRONTEND_DIR=PROJETEdoctorat-main\PROJETEdoctorat-main
if not exist "%FRONTEND_DIR%" (
    echo ERROR: Frontend directory not found: %FRONTEND_DIR%
    pause
    exit /b 1
)

cd "%FRONTEND_DIR%"

REM Check if node_modules exists
if not exist "node_modules" (
    echo Installing dependencies...
    call npm install
    
    if errorlevel 1 (
        echo ERROR: Installation failed!
        pause
        exit /b 1
    )
    echo Dependencies installed
    echo.
)

echo Starting React development server...
echo Frontend will be available at: http://localhost:3000
echo API endpoint: http://localhost:8085
echo.
echo Press Ctrl+C to stop the server
echo.

call npm start
