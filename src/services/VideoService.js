import axios from 'axios';

const API = process.env.REACT_APP_API_BASE_URL;

export const getVideos = () => axios.get(`${API}/videos`);
export const getVideoById = (id) => axios.get(`${API}/videos/${id}`);
