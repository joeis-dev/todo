import React from 'react'
import { AuthProvider } from './context/AuthContext'
import { ToDoList } from './pages/ToDoList'
import { Login } from './pages/Login'
import { useAuth } from './hooks/useAuth'

const AppContent = () => {
  const { user, loading } = useAuth()

  if (loading) return <div>Loading...</div>

  return (
    <div className="app-container">
      {user ? <ToDoList /> : <Login />}
    </div>
  )
}

function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  )
}

export default App
