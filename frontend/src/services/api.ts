const API_BASE_URL = '/api/v1';

export const api = {
  get: async (url: string) => {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_BASE_URL}${url}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });
    if (!response.ok) throw new Error('API Error');
    return response.json();
  },
  post: async (url: string, data: any) => {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_BASE_URL}${url}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error('API Error');
    return response.json();
  },
  put: async (url: string, data: any) => {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_BASE_URL}${url}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error('API Error');
    return response.json();
  },
  delete: async (url: string) => {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_BASE_URL}${url}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });
    if (!response.ok) throw new Error('API Error');
    return true;
  },
};
