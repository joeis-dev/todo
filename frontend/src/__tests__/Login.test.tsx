import { render, screen, fireEvent } from '@testing-library/react';
import { Login } from '../pages/Login';
import { AuthProvider } from '../context/AuthContext';
import { describe, it, expect, vi } from 'vitest';

describe('Login Component', () => {
  it('renders login form by default', () => {
    render(
      <AuthProvider>
        <Login />
      </AuthProvider>
    );
    
    expect(screen.getByText(/Welcome Back/i)).toBeDefined();
    expect(screen.getByPlaceholderText(/Username/i)).toBeDefined();
    expect(screen.getByPlaceholderText(/Password/i)).toBeDefined();
  });

  it('toggles to register form when link is clicked', () => {
    render(
      <AuthProvider>
        <Login />
      </AuthProvider>
    );
    
    const toggleLink = screen.getByText(/Don't have an account\? Sign Up/i);
    fireEvent.click(toggleLink);
    
    expect(screen.getByText(/Create Account/i)).toBeDefined();
    expect(screen.getByPlaceholderText(/Email/i)).toBeDefined();
  });
});
