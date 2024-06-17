<template>
    <h1>Post Delete</h1>
  </template>
  
  <script>
  import PostService from '../services/PostService.js';
  
  export default {
      methods: {
          deletePost() {
              if(confirm("Are you sure you want to delete your comment?  This cannot be undone.")) {
  
                  PostService.deletePost(this.postId).then(
                      (response) => {
                          if(response.status === 200) {
                              this.$router.push({name: 'GuestHomeView'})
                          }
                      }
                  ) .catch(
                  (error) => {
                      if(error.response) {
                          this.errorNeedingAddressed(this.error.response, "deleting");
                      } else if (error.request) {
                          this.errorNeedingAddressed(this.error.request, "deleting");
                      }
                      }
                  )
              }
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
      }
  
  }
  </script>
  
  <style scoped>
  
  </style>