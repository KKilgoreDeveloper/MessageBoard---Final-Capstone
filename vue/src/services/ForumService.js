import axios from 'axios';

const http = axios.create ({
    baseURL: "http://localhost:9000"
});

export default {

  getAllForums() {
    return axios.get('/forums')
  },

  getForum(forumId) {
    return axios.get(`/forums/${forumId}`)
  },

  getActiveForums(){
    return axios.get('/forums/active')
  },

  updateForum(forumId) {
    return axios.put(`/update-forum`, forumId);
  },

  addForum(changeForum) {

    return axios.post('/forums', changeForum)
  },

  deleteForum(forumId) {
    return axios.delete(`/forums/${forumId}`)
  },

  getForumsByKeyword(keyword) {
    return axios.get(`/forums/keyword/${keyword}`)
  },

  addFavoriteForum(forumId) {
    return axios.post(`/forums/favorite/${forumId}`)
  },

  unfavoriteForum(forumId) {
    return axios.delete(`/forums/favorite/${forumId}`)
  },

  getUserFavorites(){
    return axios.get('/forums/favorite')
  },



  //Why is this happening?


}