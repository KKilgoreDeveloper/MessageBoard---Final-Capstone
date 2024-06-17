<template>
    <h1 class="classmeup">Here is what we found!</h1>
  <div class ='searchforum'>
    <SearchCard v-for="forum in foundForums" v-bind:forum="forum" v-bind:key="forum.forum_id"/>

  </div>
</template>

<script>

import ForumService from '../services/ForumService';
// import ForumsCard from '../components/ForumsCard.vue';
import SearchCard from '../components/SearchCard.vue';

export default {
    components: {
        // ForumsCard,
        SearchCard
    },

    data() {
        return {
            keyword: this.$route.params.keyword,
            foundForums:[],
        }
    },
    methods: {
        getForumsByKeyword(keyword){
            ForumService.getForumsByKeyword(keyword).then((response)=>{
                this.foundForums = response.data;
            })
            /*
            .catch((error) =>{
                this.handleErrorResponse(error);
            });
            */
        },
    },
    created(){
        this.getForumsByKeyword(this.keyword);
    }
}
</script>

<style scoped>

.classmeup{
    font-size: 2.5rem;
    color: white;
    text-decoration: none;
    text-align: center;
    margin-top: 1px;

}

.searchforum{
    display: grid;
    align-content: center;
    justify-content: space-around;
    margin: 0 auto;
    margin-bottom: 1%;
    margin-top: .25%;
    text-align: center;
    padding: 10px;
    height: 100vh;
    width: 100vh;

}

</style>