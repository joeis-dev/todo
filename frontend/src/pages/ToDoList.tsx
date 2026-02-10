import React, { useState, useEffect } from 'react';
import { api } from '../services/api';
import { useAuth } from '../hooks/useAuth';

interface Todo {
  id: number;
  title: string;
  completed: boolean;
}

export const ToDoList = () => {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [newTodo, setNewTodo] = useState('');
  const { logout, user } = useAuth();

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const data = await api.get('/todos');
      setTodos(data);
    } catch (err) {
      console.error(err);
    }
  };

  const addTodo = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTodo.trim()) return;
    try {
      const todo = await api.post('/todos', { title: newTodo, completed: false });
      setTodos([...todos, todo]);
      setNewTodo('');
    } catch (err) {
      console.error(err);
    }
  };

  const toggleTodo = async (todo: Todo) => {
    try {
      const updated = await api.put(`/todos/${todo.id}`, { ...todo, completed: !todo.completed });
      setTodos(todos.map(t => t.id === todo.id ? updated : t));
    } catch (err) {
      console.error(err);
    }
  };

  const deleteTodo = async (id: number) => {
    try {
      await api.delete(`/todos/${id}`);
      setTodos(todos.filter(t => t.id !== id));
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div style={{ padding: '2rem', maxWidth: '800px', margin: '0 auto' }}>
      <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h1>My Tasks</h1>
        <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
          <span>{user?.username}</span>
          <button onClick={logout} style={{ background: 'var(--danger)', color: 'white', padding: '0.5rem 1rem' }}>Logout</button>
        </div>
      </header>

      <form onSubmit={addTodo} style={{ display: 'flex', gap: '1rem', marginBottom: '2rem' }}>
        <input 
          style={{ flex: 1 }} 
          type="text" 
          placeholder="What needs to be done?" 
          value={newTodo} 
          onChange={(e) => setNewTodo(e.target.value)} 
        />
        <button type="submit" style={{ backgroundColor: 'var(--primary)', color: 'white', padding: '0.75rem 1.5rem' }}>Add Task</button>
      </form>

      <div className="todo-list" style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
        {todos.map(todo => (
          <div key={todo.id} className="glass-card" style={{ padding: '1rem', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
              <input 
                type="checkbox" 
                checked={todo.completed} 
                onChange={() => toggleTodo(todo)} 
                style={{ width: '20px', height: '20px' }}
              />
              <span style={{ textDecoration: todo.completed ? 'line-through' : 'none', color: todo.completed ? 'var(--text-muted)' : 'inherit' }}>
                {todo.title}
              </span>
            </div>
            <button onClick={() => deleteTodo(todo.id)} style={{ background: 'transparent', color: 'var(--danger)' }}>Delete</button>
          </div>
        ))}
        {todos.length === 0 && <div style={{ textAlign: 'center', color: 'var(--text-muted)', marginTop: '2rem' }}>No tasks yet. Enjoy your day!</div>}
      </div>
    </div>
  );
};
