<template>
    <h1>Comments Add</h1>
</template>
  
<script>
import ForumService from '../services/ForumService.js'

  
  export default {
      data() {
          return {
              newForum: ''
          }
      },
  
      methods: {
          addForum() {
              ForumService.addForum(this.newForum).then(
                  (response) => {
                      if(response.status === 201) {
                          window.alert('Forum added!')
                          this.newComment = '';
                          this.$router.push({name: 'forum'})
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