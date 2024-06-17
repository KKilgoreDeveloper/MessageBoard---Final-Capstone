import axios from 'axios';
const http = axios.create ({
    baseURL: "http://localhost:9000"
});

export default {

  getVotes(voteType) {
    //Get votes can be up votes or down votes.
    return axios.get(`/posts/${voteType}`)
  },

  addUpVote(vote) {
    return axios.post('add-up-vote', vote)
  },

  addDownVote(vote) {
    return axios.post('add-down-vote', vote)
  }

}