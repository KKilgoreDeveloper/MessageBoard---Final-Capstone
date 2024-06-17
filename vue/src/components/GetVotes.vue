<template>
  <h1>Votes</h1>
</template>

<script>

import VoteServices from '../services/VoteServices.js'

export default {
    data() {
        return {
            newVote: ''
        }
    },

    methods: {
        addUpVote() {
            VoteServices.addUpVote(this.newVote).then(
                (response) => {
                    if(response.status === 201) {
                        window.alert('Vote added!')
                        this.newComment = '';
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
        addDownVote() {
            VoteServices.addDownVote(this.newVote).then(
                (response) => {
                    if(response.status === 201) {
                        window.alert('Vote added!')
                        this.newComment = '';
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