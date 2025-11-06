import React, { useState, useEffect } from 'react';
import './style.css';

const API_URL = `${import.meta.env.VITE_API_URL}/recipeapi`;

export default function RecipeApp() {
  const [recipes, setRecipes] = useState([]);
  const [form, setForm] = useState({
    id: null,
    name: '',
    description: '',
    ingredients: '',
    steps: ''
  });
  const [isEditing, setIsEditing] = useState(false);
  const [message, setMessage] = useState('');

  // Fetch recipes
  useEffect(() => {
    fetch(`${API_BASE}/all`)
      .then(res => res.json())
      .then(data => setRecipes(data))
      .catch(err => console.error('Error fetching recipes:', err));
  }, []);

  // Handle input
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  // Add recipe
  const handleAdd = () => {
    if (!form.name || !form.description) {
      setMessage('Please fill all fields!');
      return;
    }

    fetch(`${API_BASE}/add`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form)
    })
      .then(res => res.json())
      .then(() => {
        setMessage('Recipe added successfully!');
        setForm({ id: null, name: '', description: '', ingredients: '', steps: '' });
        refreshList();
      })
      .catch(() => setMessage('Failed to add recipe'));
  };

  // Update recipe
  const handleUpdate = () => {
    fetch(`${API_BASE}/update/${form.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form)
    })
      .then(res => res.json())
      .then(() => {
        setMessage('Recipe updated successfully!');
        setIsEditing(false);
        setForm({ id: null, name: '', description: '', ingredients: '', steps: '' });
        refreshList();
      })
      .catch(() => setMessage('Failed to update recipe'));
  };

  // Delete recipe
  const handleDelete = (id) => {
    fetch(`${API_BASE}/delete/${id}`, { method: 'DELETE' })
      .then(() => {
        setMessage('Recipe deleted!');
        refreshList();
      })
      .catch(() => setMessage('Error deleting recipe'));
  };

  // Edit mode
  const handleEdit = (recipe) => {
    setForm(recipe);
    setIsEditing(true);
  };

  // Refresh list
  const refreshList = () => {
    fetch(`${API_BASE}/all`)
      .then(res => res.json())
      .then(data => setRecipes(data))
      .catch(err => console.error(err));
  };

  return (
    <div>
      {/* === NAVBAR === */}
      <nav className="navbar">
        <div className="nav-logo">🍴 <span>RecipeHub</span></div>
        <div className="nav-links">
          <a href="#">Home</a>
          <a href="#">Add</a>
          <a href="#">Favourites</a>
          <a href="#">About</a>
        </div>
      </nav>

      <div className="container">
        {message && <div className="message">{message}</div>}

        {/* === ADD / EDIT FORM === */}
        <section className="form-section">
          <h2>{isEditing ? 'Edit Recipe' : 'Add New Recipe'}</h2>
          <input
            className="input"
            type="text"
            name="name"
            placeholder="Recipe Name"
            value={form.name}
            onChange={handleChange}
          />
          <textarea
            className="textarea"
            name="description"
            placeholder="Short Description"
            value={form.description}
            onChange={handleChange}
          />
          <textarea
            className="textarea"
            name="ingredients"
            placeholder="Ingredients"
            value={form.ingredients}
            onChange={handleChange}
          />
          <textarea
            className="textarea"
            name="steps"
            placeholder="Steps to Prepare"
            value={form.steps}
            onChange={handleChange}
          />

          <div className="button-group">
            {isEditing ? (
              <>
                <button className="update-btn" onClick={handleUpdate}>Update</button>
                <button className="cancel-btn" onClick={() => {
                  setIsEditing(false);
                  setForm({ id: null, name: '', description: '', ingredients: '', steps: '' });
                }}>Cancel</button>
              </>
            ) : (
              <button className="add-btn" onClick={handleAdd}>Add</button>
            )}
          </div>
        </section>

        {/* === ALL RECIPES === */}
        <section className="list-section">
          <h2>All Recipes</h2>
          {recipes.length === 0 ? (
            <p className="empty">No recipes yet! 🍰 Add your first one!</p>
          ) : (
            <div className="card-grid">
              {recipes.map((recipe) => (
                <div key={recipe.id} className="recipe-card">
                  <h3>{recipe.name}</h3>
                  <p><strong>Description:</strong> {recipe.description}</p>
                  <p><strong>Ingredients:</strong> {recipe.ingredients}</p>
                  <p><strong>Steps:</strong> {recipe.steps}</p>

                  <div className="card-actions">
                    <button className="edit-btn" onClick={() => handleEdit(recipe)}>Edit</button>
                    <button className="delete-btn" onClick={() => handleDelete(recipe.id)}>Delete</button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </section>
      </div>
    </div>
  );
}
