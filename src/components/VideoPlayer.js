import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import '../VideoPlayer.css'; // ✅ correct
 // Add this for styling src\VideoPlayer.css

const VideoPlayer = () => {
  const { id } = useParams();
  const [video, setVideo] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchVideo = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/videos/${id}`);
        setVideo(response.data);
      } catch (error) {
        console.error('Error fetching video:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchVideo();
  }, [id]);

  if (loading) return <p>Loading video...</p>;
  if (!video) return <p>Video not found.</p>;

  return (
    <div className="video-container">
      <h2>{video.title}</h2>
      <div className="responsive-player">
        <div className="responsive-player">
  <video
    controls
    preload="auto"
    width="100%"
    height="auto"
    style={{ maxWidth: '100%', borderRadius: '8px' }}
  >
    <source src={video.videoUrl} type="video/mp4" />
    Your browser does not support the video tag.
  </video>
</div>

      </div>
      <p><strong>Description:</strong> {video.description}</p>
      <p><strong>Category:</strong> {video.category}</p>
      <p><strong>Tags:</strong> {Array.isArray(video.tags) ? video.tags.map(t => t.tag).join(', ') : ''}</p>
      <p><strong>Uploaded At:</strong> {new Date(video.uploadedAt).toLocaleString()}</p>
      <Link to="/">⬅️ Back to Home</Link>
    </div>
  );
};

export default VideoPlayer;
