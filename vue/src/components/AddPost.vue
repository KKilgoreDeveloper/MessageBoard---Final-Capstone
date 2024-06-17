<template>
    <h1>Post Add</h1>
  </template>
  
  <script>
import PostService from '../services/PostService';
  
export default {
      data() {
          return {
              newPost: ''
          }
      },
  
      methods: {
          addPost() {
              PostService.addPost(this.newPost).then(
                  (response) => {
                      if(response.status === 201) {
                          window.alert('Post added!')
                          this.newPost = '';
                          this.$router.push({name: 'post'})
                      }
                  },
              )  .catch(
                (error) => {
                    if(error.response) {
                        this.errorNeedingAddressed(this.error.response, "adding");
                    } else if (error.request) {
                        this.errorNeedingAddressed(this.error.request, "adding");
                    }
                    }
                ) 
          },
          errorNeedingAddressed(error, toBeDone) {
            if(error.response) {
                if(error.response.status == 404) {
                    this.$router.push({name: 'Home'})
                    //Need a Something went wrong view
                } else {
                    `This ${toBeDone} has not occurred.  Server could not be reached.`
                }
            } else if (error.request) {
                `This ${toBeDone} has not occurred.  Server could not be reached.`
            }
        },
      },
  
}
  </script>
  
  <style scoped>
  
  </style>