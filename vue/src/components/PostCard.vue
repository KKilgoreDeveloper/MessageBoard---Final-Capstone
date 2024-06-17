<template>

  <div class="PostCard">
    <div class="top-row">
    <span id="increase" @:click="upVoteScore">
        <i class="fa-solid fa-meteor vote-up"></i>
    </span>
    <!-- <span class = "username">{{ user }}</span> -->

    <span>{{ postScore }}</span>
    <span id="decrease" @:click="downVoteScore">
        <i class="fa-solid fa-meteor vote-down" style=""></i>
    </span>
    
    </div>
    
    <router-link class="PostViewLink" v-bind:to="{ name: 'postview', params:{postId: post.post_id} }">
        <header>
        <h1 class="postName">{{ post.title }}</h1>
        
        </header>
    </router-link>
        <h3 class="messageDetails">{{ post.message }}</h3>
    
    <p class="timeStamp">{{ post.time_stamp }}</p>

  </div>
</template>

<script>

import PostService from '../services/PostService.js'
import UserService from '../services/UserService';


export default {
    data(){
        return{
            postId: this.post.post_id,
            hasUpVoted: false,
            hasDownVoted: false,
            postScore: 0,
            editPost: {
                post_id: this.post_id,
                user_id: this.user_id,
                forum_id: this.forum_id,
                title: this.title,
                message: this.message,
                up_votes: this.up_votes,
                down_votes: this.down_votes,
                time_stamp: this.time_stamp,
                location: this.location

            },
            // user: ''

        }
    },
    components: {

    },
    props: {
        post: {
        },
        posts: [],
    },

    methods: {
        // getUser(){
        //     UserService.getUsernameByForumId(this.forum.forum_id).then((response)=>{
        //         this.user = response.data;
        //     })
        // },

        getPost(postId) {
            PostService.getPost(this.postId).then(
                (response) => {
                    if(response.status === 200) {
                        this.$router.push('posts');
                    }
                }
            ).catch(
                (error) => {
                    if(error.response) {
                        this.errorNeedingAddressed(this.error.response, "Post not found");
                    } else if (error.request) {
                        this.errorNeedingAddressed(this.error.request, "Post not found");
                    }
                }
            )
        },
        getAllPostsForForum(forumId) {
            PostService.getPostsbyForumId(this.posts.forumId).then(
                (response) => {
                    if(response.status === 200) {
                        this.$router.push('posts');
                    }
                }
            ).catch(
                (error) => {
                    if(error.response) {
                        this.errorNeedingAddressed(this.error.response, "Post not found");
                    } else if (error.request) {
                        this.errorNeedingAddressed(this.error.request, "Post not found");
                    }
                }
            )
        },
        errorNeedingAddressed(error, toBeDone) {
            if(error.response) {
                if(error.response.status == 404) {
                    this.$router.push({name: '/'})
                    //Need a Something went wrong view
                } else {
                    this.store.commit('SET_NOTIFICATION',
                    `This ${toBeDone} has not occurred.  Server response was "${error.response.statusText}".`)
                }
            } else if (error.request) {
                this.$store.commit('SET_NOTIFICATION',
                `This ${toBeDone} has not occurred.  Server could not be reached.`)
            } else {
                `This ${toBeDone} has not occurred.  Server could not be created.`
            }
        },
        getScore(postId){
            if (!postId) {
                postId = this.$route.params.postId;
            }
            PostService.getPostScore(postId).then((response) =>{
                this.postScore = response.data;
            })
        },

        upVoteScore(){
            if(!this.$store.state.token){
                alert('Please log in or sign up to vote on this post')
                this.$router.push({name:'register'});
            } else {
                if (this.hasUpVoted === false){
                PostService.upVotePost(this.post.post_id).then((response)=>{
                if(response.status === 200){
                    this.getScore(this.post.post_id);
                    this.hasUpVoted = true;
                    this.hasDownVoted = false;

                }
            })
            }
            }
            
            console.log('up')
        },

        downVoteScore(){
            if(!this.$store.state.token){
                alert('Please log in or sign up to vote on this post')
                this.$router.push({name:'register'});
            } else {
                if(this.hasDownVoted === false){
                PostService.downVotePost(this.post.post_id).then((response)=>{
                if(response.status === 200){
                    this.getScore(this.post.post_id);
                    this.hasDownVoted = true;
                    this.hasUpVoted = false;
                }
            })
            }
            }
            

            console.log('down')
        }



    },
    mounted(){
        this.getScore(this.post.post_id);
        // this.getUser(this.post.post_id);

    }

};
</script>

<style scoped>
.PostCard {
    display: grid;
    grid-template-columns: auto 1fr;
    grid-template-areas: 
    "vote-icons post-name"
    "message message"
    "time time";
    /* flex-direction: column;
    justify-content: space-between; */
    border-radius: 15px;
    width: 50%;
    /* max-width: 750px; */
    min-width: 600px;
    /* height: 250px; */
    min-height: 500px;  
    /* margin: 20px; */
    margin: 0 auto;   /* this keeps the card in the middle of the page */
    margin-bottom: 1%;
    border: 8px solid rgb(250, 129, 240);
    /* font-size: 2.5rem; */
    color: white;
    margin-top: .5%;
    text-align: center;
    padding: 10px;
    /* background-image: ; */
    background-color: #34aae1;
;
}

.username{
    font-size: 1.5rem;
    color: white; 
    padding-top: 2%;
}
.messageDetails {
    grid-area: message;
    font-size: 1.5rem;
    color: white;
    align-self: center;
    margin-right: 10px;
    padding-top: 3% 
}

.timeStamp {
    grid-area: time;
    font-size: 1rem;
    color: white;
    align-self: flex-end;
    justify-self: flex-end;
    margin-bottom: 2%;
    margin-right: 2%;
    padding-top: 3%
}

.postName{
    grid-area: post-name;
    font-size: 1.5rem;
    margin-top: 20px;
    margin-right: 12%;
}

.PostViewLink {
    text-decoration: none;
    color: white;
}

.vote-up{
color: gold;
font-size: 35px;
align-self: flex-start;
margin-right: 10px;
margin-left: 20px;
margin-top: 20px;
rotate: 140deg;
cursor: pointer;
}

.vote-down{
color: red;
font-size: 35px;
align-self: flex-start;
margin-left: 10px;
margin-top: 20px;
rotate: -40deg;
cursor: pointer;
}

.top-row{
    grid-area: vote-icons;

}

#increase, #decrease{
    size: 2px;
}
/* Test 2.8 */
</style>