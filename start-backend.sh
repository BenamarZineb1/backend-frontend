#!/bin/bash

# E-Doctorat Backend Startup Script

echo "=========================================="
echo "  E-Doctorat Backend Startup"
echo "=========================================="
echo ""

# Check if PostgreSQL is running
echo "📊 Checking PostgreSQL..."
if ! pg_isready -h localhost -p 5432 > /dev/null 2>&1; then
    echo "⚠️  PostgreSQL is not running!"
    echo "   Please start PostgreSQL first."
    exit 1
fi
echo "✅ PostgreSQL is running"
echo ""

# Navigate to backend directory
BACKEND_DIR="Edoctorat-F/Edoctorat"
if [ ! -d "$BACKEND_DIR" ]; then
    echo "❌ Backend directory not found: $BACKEND_DIR"
    exit 1
fi

cd "$BACKEND_DIR"

echo "📦 Building backend..."
mvn clean install -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed!"
    exit 1
fi

echo "✅ Build successful"
echo ""

echo "🚀 Starting Spring Boot application on port 8085..."
echo "   Backend will be available at: http://localhost:8085"
echo ""
echo "   Press Ctrl+C to stop the server"
echo ""

mvn spring-boot:run
