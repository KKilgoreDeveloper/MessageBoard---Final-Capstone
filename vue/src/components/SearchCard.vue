<template>
  <div class="searchResults">
    
    <div class="forum-section">
        <forumsearch class = "searched-forums" v-for="forum in forums" v-bind:forum="forum" v-bind:key="forum.keyword"/>
        <router-link class="ForumViewLink" v-bind:to="{ name: 'forumview', params:{forumId: forum.forum_id} }">
        <header>
        <h1 class="forumName">{{ forum.forum_name }}</h1>
        </header>
        </router-link>
    </div>
    <div class="timeStamp">{{ forum.time_stamp }}</div>

  </div>
</template>

<script>

import ForumService from '../services/ForumService';
import ForumsCard from '../components/ForumsCard.vue';

export default {
    components: {
        
    },

    props: {
        forum: {
            type: Object,
            required: false
        },
        
    },

    data() {
        return {
            
        };
    },

    methods: {
    }
};
</script>

<style>

.searchResults .forumName .forum-section{
    font-size: 2.5rem;
    color: white;
    text-decoration: none;
    text-align: center;
    margin-top: 1px;

}

.searchResults{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    border: 8px solid rgb(250, 129, 240);
    border-radius: 25px;
    min-width: 450px;
    height: 250px;
    max-width: 650px;
    margin-bottom: 10px;
    background-color: #34aae1;;
}

.searchResults .forumName{
    font-size: 2.5rem;
    color: white;
    text-decoration: none;
    text-align: center;
    margin-top: 1px;

}

.searchResults .timeStamp{
    font-size: 1rem;
    color: white;
    align-self: flex-end;
    margin-right: 10px;
}

.ForumViewLink {
    text-decoration: none;
    color: white;
}

.white-star{
    color: white;
    font-size: 35px;
    margin-top: 5px;
    margin-left: 85%;
    margin-right: 10px;
}

.yellow-star{
    color: white;
    font-size: 35px;
    margin-top: 5px;
    margin-left: 85%;
    margin-right: 10px;
}

.forumName{
    margin: 5%;
}

.timeStamp{
    margin-bottom: 2%;
}

</style>