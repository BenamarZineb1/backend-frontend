import axios from 'axios';

// Configuration de l'URL de base du Backend Spring Boot
const api = axios.create({
    baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8085', // Backend runs on port 8085
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: true // Important for sessions/cookies
});

// Interceptor to include JWT Token automatically
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('user_token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Interceptor for response error handling
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response) {
            // Server responded with error status
            switch (error.response.status) {
                case 401:
                    // Unauthorized - clear token and redirect to login
                    localStorage.removeItem('user_token');
                    localStorage.removeItem('user_role');
                    localStorage.removeItem('user_info');
                    window.location.href = '/login';
                    break;
                case 403:
                    console.error('Access forbidden:', error.response.data);
                    break;
                case 404:
                    console.error('Resource not found:', error.response.data);
                    break;
                case 500:
                    console.error('Server error:', error.response.data);
                    break;
                default:
                    console.error('Error:', error.response.data);
            }
        } else if (error.request) {
            // Request made but no response received
            console.error('Network error: No response from server');
        } else {
            // Error setting up request
            console.error('Error:', error.message);
        }
        return Promise.reject(error);
    }
);

export default api;