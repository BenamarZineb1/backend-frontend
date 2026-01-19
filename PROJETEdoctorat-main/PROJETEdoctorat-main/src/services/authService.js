import api from '../api/axios';

const AuthService = {
    // 1. Inscription (Candidat)
    register: async (userData) => {
        try {
            console.log('📥 Service inscription appelé avec:', userData.email);
            
            // Si userData est déjà un objet simple, l'envoyer directement
            if (userData.email && userData.password) {
                // Envoi JSON simple
                const response = await api.post('/auth/register', userData, {
                    headers: { 'Content-Type': 'application/json' }
                });
                console.log('✅ Inscription service réussie');
                return response.data;
            } else {
                // Ancienne méthode avec FormData (pour compatibilité)
                const formData = new FormData();
                Object.keys(userData).forEach(key => {
                    if (key === 'photoProfil' && userData[key]) {
                        formData.append('photo', userData[key]);
                    } else {
                        formData.append(key, userData[key]);
                    }
                });

                const response = await api.post('/auth/register', formData, {
                    headers: { 'Content-Type': 'multipart/form-data' }
                });
                return response.data;
            }
        } catch (error) {
            console.error('❌ Erreur service inscription:', error);
            throw error;
        }
    },

    // 2. Login
    login: async (email, password) => {
        try {
            // Validation des paramètres
            if (!email || !password) {
                throw new Error('Email et mot de passe requis');
            }

            const response = await api.post('/auth/login', { 
                username: email, // Backend expects 'username' field
                password 
            });
            
            if (response.data?.token) {
                // Store token
                localStorage.setItem('user_token', response.data.token);
                
                // Store user role
                if (response.data.role) {
                    localStorage.setItem('user_role', response.data.role);
                }
                
                // Store user info
                if (response.data.user) {
                    localStorage.setItem('user_info', JSON.stringify(response.data.user));
                }
                
                console.log('✅ Token stocké, utilisateur connecté');
            } else {
                throw new Error('Réponse invalide du serveur');
            }
            
            return response.data;
        } catch (error) {
            console.error('❌ Erreur login service:', error);
            // Relancer l'erreur pour que le composant la traite
            throw error;
        }
    },

    // 3. Logout
    logout: () => {
        localStorage.removeItem('user_token');
        localStorage.removeItem('user_role');
        localStorage.removeItem('user_info');
        // Optional: Call backend to invalidate token server-side
    },

    // 4. Check if token exists
    getCurrentUserToken: () => {
        return localStorage.getItem('user_token');
    },

    // 5. Get current user role
    getCurrentUserRole: () => {
        return localStorage.getItem('user_role');
    },

    // 6. Get current user info
    getCurrentUserInfo: () => {
        const userInfo = localStorage.getItem('user_info');
        return userInfo ? JSON.parse(userInfo) : null;
    },

    // 7. Check if user is authenticated
    isAuthenticated: () => {
        return !!localStorage.getItem('user_token');
    }
};

export default AuthService;