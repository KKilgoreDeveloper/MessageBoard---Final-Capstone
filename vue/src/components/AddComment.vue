<template>
  <h1>Comments Add</h1>
</template>

<script>
import CommentService from '../services/CommentService.js';

export default {
    data() {
        return {
            newComment: ''
        }
    },

    methods: {
        addComment() {
            CommentService.addComment(this.newComment).then(
                (response) => {
                    if(response.status === 201) {
                        window.alert('Comment added!')
                        this.newComment = '';
                        this.$router.push({name: 'comment'})
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
                    this.$router.push({name: 'home'})
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