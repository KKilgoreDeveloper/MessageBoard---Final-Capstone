<template>
 <div class="card">
    <!-- icon to mark a forum as favorite 
    Aiming to have the color change to yellow when the icon is clicked
    commented-out code doesn't successfully change the color once clicked
    (ONLY LOGGED IN USERS CAN FAVORITE - add pop up asking you to log in when clicked?)

    <i v-bind:class="{'fas fa-star yellow-star': iconClick, 'fas fa-star white-star': !iconClick}" v-on:click="favorite"></i> -->
    
    <i class = "fas fa-star white-star"></i>
    <router-link class="ForumViewLink" v-bind:to="{ name: 'forumview', params:{forumId: forum.forumId} }">
    <header>
        <h1 class="forumName">{{ forum.forumName }}</h1>
    </header>
    </router-link>
    <div class="timeStamp">{{ forum.timeStamp }}</div>
    
    </div>
</template>

<script>

// import PostsComponent from '../components/PostsComponent'

export default{
    data(){
        return{
            iconClick: false
        }
    },
    components: {
        // PostsComponent
    },
    props: {
        forum: {
            type: Object,
            required: true
        }
        
    },
    computed: {

    },
    methods: {
        favorite(){
            this.iconClick = !this.iconClick
        }

    }

};
</script>

<style scoped>
.card{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    border: 5px solid rgb(250, 129, 240);
    border-radius: 10px;
    width: 650px;
    height: 250px;
    margin: 20px;
    margin: 0 auto;
    margin-bottom: 10px;
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
    margin-right: 10px;
}

.ForumViewLink{
    text-decoration: none;
}

.white-star{
    color: white;
    font-size: 35px;
    margin-top: 10px;
    margin-left: 550px;
}

.yellow-star{
    color: yellow;
    font-size: 35px;
    margin-top: 10px;
    margin-left: 550px;
}


/* Test */
</style>