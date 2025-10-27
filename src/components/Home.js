import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const Home = () => {
  const [videos, setVideos] = useState([]);
  const [categoryFilter, setCategoryFilter] = useState('');
  const [tagFilter, setTagFilter] = useState('');

  useEffect(() => {
    loadVideos();
  }, [categoryFilter, tagFilter]);

  const loadVideos = async () => {
    let url = `${process.env.REACT_APP_API_BASE_URL}/videos`;
    if (categoryFilter) url += `/category/${categoryFilter}`;
    else if (tagFilter) url += `/tag/${tagFilter}`;

    try {
      const response = await axios.get(url);
      setVideos(response.data);
    } catch (error) {
      console.error('Error loading videos:', error);
    }
  };

  return (
    <div>
      <h2>🎬 Video Library</h2>
      <Link to="/upload">➕ Upload New Video</Link>

      <div>
        <input type="text" placeholder="Filter by category" value={categoryFilter} onChange={e => setCategoryFilter(e.target.value)} />
        <input type="text" placeholder="Filter by tag" value={tagFilter} onChange={e => setTagFilter(e.target.value)} />
        <button onClick={loadVideos}>Apply Filters</button>
      </div>

      <ul>
        {videos.map(video => (
          <li key={video.id}>
            <h3>{video.title}</h3>
            <p>{video.description}</p>
            <p><strong>Category:</strong> {video.category}</p>
            <p><strong>Tags:</strong> {video.tags.map(t => t.tag).join(', ')}</p>
            <Link to={`/play/${video.id}`}>▶️ Play</Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Home;
