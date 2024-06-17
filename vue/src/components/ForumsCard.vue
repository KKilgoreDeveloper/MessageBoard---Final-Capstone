<template>
 <div class="card">
    <!-- icon to mark a forum as favorite 
    Aiming to have the color change to yellow when the icon is clicked
    first commented-out code doesn't successfully change the color once clicked
    (ONLY LOGGED IN USERS CAN FAVORITE - add pop up asking you to log in when clicked?)

     -->
     <!-- <button v-on:click="favorite" class="favorite-star"> -->
     <!-- <button v-bind:class="{'fas fa-star yellow-star': iconClick, 'fas fa-star white-star': !iconClick}" v-on:click="favorite"></button> -->
     
        <!-- <span v-if="iconClick" @click="favorite">
            <i class="fas fa-star yellow-star"></i>
        </span>
        <span v-else @click="favorite">
            <i class="fas fa-star white-star"></i>
        </span> -->
        <div class = "top-of-card">
            <span class = "username">{{ user }}</span>
        <span v-if="iconClick" @click="unfavoriteForum(forum.forum_id)">
            <i class="fas fa-star yellow-star"></i>
        </span>
        <span v-else @click="addFavoriteForum(forum.forum_id)">
            <i class="fas fa-star white-star"></i>
        </span>
        </div>
        

     <!-- </button> -->
    <!-- <i class = "fas fa-star white-star"></i> -->
    <router-link class="ForumViewLink" v-bind:to="{ name: 'forumview', params:{forumId: forum.forum_id} }">
    <div class="midcard">
        <h1 class="forumName">{{ forum.forum_name }}</h1>
    </div>
    </router-link>
    
    <div class="timeStamp">{{ forum.time_stamp }}</div>
    
    </div>
</template>

<script>

import ForumService from '../services/ForumService';
import UserService from '../services/UserService';
// import PostsComponent from '../components/PostsComponent'

export default{
    data(){
        return{
            // iconClick: false,
            // forumId: 0,
            iconClick: localStorage.getItem('forum_' + this.forum.forum_id) === 'true' ? true : false,
            forumId: 0,
            // user: this.$store.state.user
            user: ''
        }
    },

    components: {

    },

    props: {
        forum: {
            type: Object,
            required: true
        },
        post: {},
        
    },

    computed: {
        // user(){
        //     return this.$store.state.user
        // }
    },

    methods: {
        // addFavoriteForum(forumId) {

        //     ForumService.addFavoriteForum(forumId).then(
        //         (response) => {
        //             if(response.status === 201) {
        //                 this.iconClick =!this.iconClick;
        //             }
        //         }
        //     );
        // },

        // unfavoriteForum(forumId) {

        //     ForumService.unfavoriteForum(forumId).then(
        //         (response) => {
        //             if(response.status === 204) {
        //                 this.iconClick = !this.iconClick;
        //             }
        //         }
        //     );
        // },
        getUser(){
            UserService.getUsernameByForumId(this.forum.forum_id).then((response)=>{
                this.user = response.data;
            })
        },

        addFavoriteForum(forumId) {
            if(!this.$store.state.token){
                alert('Please log in or sign up to favorite this forum.')
                this.$router.push({name:'register'});
            } else {
                ForumService.addFavoriteForum(forumId).then(
                (response) => {
                    if(response.status === 201) {
                        this.iconClick = true;
                        localStorage.setItem('forum_' + forumId, 'true')
                    } 
                }
            );
            }
        },

        unfavoriteForum(forumId) {
            if(!this.$store.state.token){
                alert('Please log in or sign up to favorite this forum.')
                this.$router.push({name:'register'});
            } else {
                ForumService.unfavoriteForum(forumId).then(
                (response) => {
                    if(response.status === 204) {
                        this.iconClick = false;
                        localStorage.setItem('forum_' + forumId, 'false')
                    }
                }
            );
            }
            
        },

        favorite(){
            this.iconClick = !this.iconClick;
            console.log(this.iconClick);
        },

    },

    mounted(){
        this.getUser(this.forum.forum_id);
    }

};
</script>

<style scoped>
.card{
    /* display: flex; */
    display: grid;
    grid-template-columns: 1fr;
    grid-template-areas: "topcard"
    "midcard"
    "endcard";
    flex-direction: column;
    justify-content: center;
    /* justify-content: space-between; */
    border: 8px solid rgb(250, 129, 240);
    border-radius: 25px;
    min-width: 20%;
    height: 250px;
    max-width: 30%;
    /* margin: 20px; */
    /* margin: 0 auto; */
    margin-bottom: 10px;
    background-color: #34aae1;
    padding: 10px;
}

.username{
    font-size: 1.5rem;
    color: white; 
    /* padding-top: 2%; */
}
.card .forumName{
    font-size: 2.5rem;
    color: white;
    text-decoration: none;
    text-align: center;
    margin-top: 1px;

}

.card .timeStamp{
    font-size: 1rem;
    color: white;
    align-self: flex-end;
    justify-content: bottom;
    /* padding-top: 3%; */
    margin-right: 10px;
}

.ForumViewLink {
    text-decoration: none;
    color: white;
}

/* .favorite-star{
    cursor: pointer;
} */

.white-star{
    cursor: pointer;
    color: white;
    font-size: 35px;
    margin-top: 0;
    margin-bottom: 20px;
    /* margin-left: 85%; */
    /* margin-right: 10px; */
    
}

.yellow-star{
    cursor: pointer;
    color: gold;
    font-size: 35px;
    margin-top: 0;
    margin-bottom: 20px;

}

.top-of-card{
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-right: 10%;
    grid-area: topcard;

}

.forumName{
    margin: 5%;
    grid-area: midcard;
}

.timeStamp{

    margin-bottom: 2%;
    grid-area: endcard;
    display: flex;
    justify-content: flex-end;
}


/* Test 2.75 */
</style>