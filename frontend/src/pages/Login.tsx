import React, { useState } from 'react';
import { useAuth } from '../hooks/useAuth';

export const Login = () => {
  const [isRegister, setIsRegister] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const url = isRegister ? '/api/v1/auth/register' : '/api/v1/auth/login';
      const body = isRegister ? { username, password, email } : { username, password };
      
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      });

      const data = await response.json();
      if (data.token) {
        login(data.token, { username: data.username, email: data.email });
      } else if (isRegister) {
        setIsRegister(false);
        alert('Registered successfully! Please login.');
      }
    } catch (err) {
      alert('Authentication failed');
    }
  };

  return (
    <div className="login-container" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <div className="glass-card" style={{ padding: '2.5rem', width: '100%', maxWidth: '400px' }}>
        <h1 style={{ marginBottom: '1.5rem', textAlign: 'center' }}>{isRegister ? 'Create Account' : 'Welcome Back'}</h1>
        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
          <input 
            type="text" 
            placeholder="Username" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            required 
          />
          {isRegister && (
            <input 
              type="email" 
              placeholder="Email" 
              value={email} 
              onChange={(e) => setEmail(e.target.value)} 
              required 
            />
          )}
          <input 
            type="password" 
            placeholder="Password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
          />
          <button type="submit" style={{ backgroundColor: 'var(--primary)', color: 'white', padding: '0.8rem', marginTop: '1rem' }}>
            {isRegister ? 'Sign Up' : 'Login'}
          </button>
        </form>
        <div style={{ marginTop: '1.5rem', textAlign: 'center', color: 'var(--text-muted)' }}>
          <span onClick={() => setIsRegister(!isRegister)} style={{ cursor: 'pointer', color: 'var(--primary)' }}>
            {isRegister ? 'Already have an account? Login' : "Don't have an account? Sign Up"}
          </span>
        </div>
        <div style={{ marginTop: '1rem', borderTop: '1px solid var(--border)', paddingTop: '1rem', textAlign: 'center' }}>
           <button style={{ background: 'var(--glass)', width: '100%', color: 'white', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '0.5rem' }}>
             Continue with OAuth (Demo)
           </button>
        </div>
      </div>
    </div>
  );
};
