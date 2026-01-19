#!/bin/bash

# E-Doctorat Frontend Startup Script

echo "=========================================="
echo "  E-Doctorat Frontend Startup"
echo "=========================================="
echo ""

# Navigate to frontend directory
FRONTEND_DIR="PROJETEdoctorat-main/PROJETEdoctorat-main"
if [ ! -d "$FRONTEND_DIR" ]; then
    echo "❌ Frontend directory not found: $FRONTEND_DIR"
    exit 1
fi

cd "$FRONTEND_DIR"

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install
    
    if [ $? -ne 0 ]; then
        echo "❌ Installation failed!"
        exit 1
    fi
    echo "✅ Dependencies installed"
    echo ""
fi

echo "🚀 Starting React development server..."
echo "   Frontend will be available at: http://localhost:3000"
echo "   API endpoint: http://localhost:8085"
echo ""
echo "   Press Ctrl+C to stop the server"
echo ""

npm start
