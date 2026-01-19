import api from '../api/axios';

/**
 * Test utilities to verify backend-frontend connection
 */
const ConnectionTest = {

  /**
   * Test if backend is reachable
   */
  testBackendConnection: async () => {
    try {
      const response = await api.get('/actuator/health').catch(() => {
        // If actuator not available, try auth endpoint
        return api.options('/auth/login');
      });
      console.log('✅ Backend connection successful');
      return { success: true, message: 'Backend is reachable' };
    } catch (error) {
      console.error('❌ Backend connection failed:', error.message);
      return { 
        success: false, 
        message: 'Cannot connect to backend',
        error: error.message 
      };
    }
  },

  /**
   * Test authentication endpoints
   */
  testAuthEndpoints: async () => {
    const results = {
      login: false,
      register: false
    };

    try {
      // Test login endpoint (expect 401 or 400 with invalid credentials)
      await api.post('/auth/login', { username: 'test', password: 'test' });
    } catch (error) {
      // 401 or 400 means endpoint is working
      if (error.response && [400, 401].includes(error.response.status)) {
        results.login = true;
        console.log('✅ Login endpoint is accessible');
      } else {
        console.error('❌ Login endpoint error:', error.message);
      }
    }

    try {
      // Test register endpoint (expect 400 with empty data)
      await api.post('/auth/register', {});
    } catch (error) {
      // 400 means endpoint is working
      if (error.response && error.response.status === 400) {
        results.register = true;
        console.log('✅ Register endpoint is accessible');
      } else {
        console.error('❌ Register endpoint error:', error.message);
      }
    }

    return results;
  },

  /**
   * Test CORS configuration
   */
  testCORS: async () => {
    try {
      const response = await api.options('/auth/login');
      console.log('✅ CORS is properly configured');
      return { success: true, message: 'CORS configured correctly' };
    } catch (error) {
      console.error('❌ CORS test failed:', error.message);
      return { 
        success: false, 
        message: 'CORS might not be configured',
        error: error.message 
      };
    }
  },

  /**
   * Check if JWT token is valid
   */
  testTokenValidity: async () => {
    const token = localStorage.getItem('user_token');
    
    if (!token) {
      console.log('ℹ️ No token found in localStorage');
      return { success: false, message: 'No token found' };
    }

    try {
      // Try to access a protected endpoint
      const response = await api.get('/candidat/me').catch((err) => {
        if (err.response && err.response.status === 401) {
          throw new Error('Token expired or invalid');
        }
        throw err;
      });
      
      console.log('✅ Token is valid');
      return { success: true, message: 'Token is valid', data: response.data };
    } catch (error) {
      console.error('❌ Token validation failed:', error.message);
      return { 
        success: false, 
        message: 'Token is invalid or expired',
        error: error.message 
      };
    }
  },

  /**
   * Run all connection tests
   */
  runAllTests: async () => {
    console.log('🔍 Starting connection tests...\n');
    
    const results = {
      backend: await ConnectionTest.testBackendConnection(),
      cors: await ConnectionTest.testCORS(),
      auth: await ConnectionTest.testAuthEndpoints(),
      token: await ConnectionTest.testTokenValidity()
    };

    console.log('\n📊 Test Results Summary:');
    console.log('Backend Connection:', results.backend.success ? '✅' : '❌');
    console.log('CORS Configuration:', results.cors.success ? '✅' : '❌');
    console.log('Auth Endpoints:', results.auth.login && results.auth.register ? '✅' : '❌');
    console.log('Token Validity:', results.token.success ? '✅' : '❌');

    return results;
  },

  /**
   * Test specific role endpoints
   */
  testRoleEndpoint: async (role) => {
    const endpoints = {
      CANDIDAT: '/candidat/me',
      PROFESSEUR: '/professeur/sujets/mes-sujets/1',
      DIRECTEUR_LABO: '/directeur-labo/labo/1/candidats',
      DIRECTEUR_CED: '/directeur/ced/candidats',
      DIRECTEUR_POLE: '/directeur/pole/candidats',
      SCOLARITE: '/scolarite/dossiers'
    };

    const endpoint = endpoints[role];
    if (!endpoint) {
      console.error(`❌ Unknown role: ${role}`);
      return { success: false, message: 'Unknown role' };
    }

    try {
      await api.get(endpoint);
      console.log(`✅ ${role} endpoint is accessible`);
      return { success: true, message: `${role} endpoint working` };
    } catch (error) {
      if (error.response && error.response.status === 401) {
        console.log(`ℹ️ ${role} endpoint exists but requires authentication`);
        return { success: true, message: 'Endpoint exists (requires auth)' };
      }
      console.error(`❌ ${role} endpoint test failed:`, error.message);
      return { 
        success: false, 
        message: `${role} endpoint not accessible`,
        error: error.message 
      };
    }
  },

  /**
   * Get connection status info
   */
  getConnectionInfo: () => {
    const token = localStorage.getItem('user_token');
    const role = localStorage.getItem('user_role');
    const userInfo = localStorage.getItem('user_info');

    return {
      apiUrl: process.env.REACT_APP_API_URL || 'http://localhost:8085',
      hasToken: !!token,
      userRole: role,
      isAuthenticated: !!token,
      userInfo: userInfo ? JSON.parse(userInfo) : null,
      tokenPreview: token ? `${token.substring(0, 20)}...` : 'No token'
    };
  },

  /**
   * Clear all stored data
   */
  clearStoredData: () => {
    localStorage.removeItem('user_token');
    localStorage.removeItem('user_role');
    localStorage.removeItem('user_info');
    console.log('✅ All stored data cleared');
  }
};

// Export for use in browser console or components
export default ConnectionTest;

// Make it available in console for debugging
if (typeof window !== 'undefined') {
  window.ConnectionTest = ConnectionTest;
}
