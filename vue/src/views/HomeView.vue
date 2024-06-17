<template>
    <section class="whole-page">
    <section class="left-panel">

      <!-- <h1 class="home-text">Home</h1> -->
    
      <!-- <router-link class="ResourcesViewLink" 
      v-bind:to="{name: 'resources'}">
        <h1 class="resources-link"> Resources</h1>
      </router-link> -->

      <select v-model="Selection" @change="changeView" class="dropdown">
        <option value="">Select</option>
        <option value="User">Profile</option>
        <!-- <router-link v-bind:to="{name: 'resources'}"> -->
          <option value="Create-Forum">Create Forum</option>
        <!-- </router-link> -->
        <!-- <router-link v-bind:to="{name: 'createforum'}"> -->
          <option value="Resources">Community Guidelines</option>
      <!-- </router-link> -->
      </select>

      <!-- <router-link class="CreateForumViewLink" 
      v-bind:to="{name: 'createforum'}">
        <h1 class="create-forum">Create Forum</h1>
      </router-link> -->

      <!-- <router-link class="CreatePostViewLink" v-bind:to="{name: 'createpost'}">
        <h1 class="create-post">Create A Post</h1>
      </router-link> -->
    </section>
    <section class="main-section">
      <section class ="top-section">
        <!-- <h1 class="linksandsearches"> -->
          <h1 class="welcome">Stellar Discussions</h1>
    <form class = "ForumSearch" @submit.prevent="searchForums(keyword)">
        <div class="searchbar">
          <input type="text" v-model="keyword"
          placeholder="Search here for your criteria!"/>
          <button type="submit">Search</button>
        </div>
      </form>
    <!-- </h1> -->
    </section>
    <section class="main-content">
      <h2 class="forums-description">Today's Exciting Forums</h2>
  <div class="forum-section">
    <ForumsCard class = "active-forums" v-for="forum in forums"
    v-bind:forum="forum" v-bind:key="forum.forum_id"/>
  </div>
  <h2 class="posts-description">Today's Trending Posts</h2>
  <div class="post-section">
    <PostCard class="trending-posts" v-for="post in posts"
    v-bind:post="post"
    v-bind:key="post.post_id"/>
  </div>
    </section>
    </section>
  </section>
  <div class="home">
  </div>
  
</template>
<script>
import ForumService from '../services/ForumService';
import ForumsCard from '../components/ForumsCard.vue';
import PostCard from '../components/PostCard.vue';
import PostService from '../services/PostService';
  export default {
    components: {
      ForumsCard,
      PostCard
    },
    data(){
        return {
             forums: [
             ],
             posts: [
             ],
             keyword: "",
             Selection: "",
        };
    },
    methods: {
      getTodaysActiveForums(){
          ForumService.getActiveForums().then((response)=>{
            this.forums = response.data;
          }). catch()
        },
        getTodaysPopularPosts(){
          PostService.get10MostPopularPosts().then((response) =>{
            this.posts = response.data;
          }).catch(error =>{
                
              })
        },
        getForums(){
            ForumService.getAllForums().then((response) =>{
                this.forums = response.data;
            })
            .catch(error =>{
            })
        },
        getPosts(){
          PostService.getAllPosts().then((response) => {
            this.posts = response.data;
          })
          .catch (error => {
          })
        },
        getForumsByKeyword(keyword){
          ForumService.getForumsByKeyword(keyword).then(
            (response) => {
              this.forums = response.data;
              this.$router.push({name: 'searchview', params: {keyword: keyword}})
            }
          )
        },
        searchForums(keyword) {
          this.getForumsByKeyword(keyword)
        },

        changeView(){
          if(this.Selection === 'Resources'){
            this.$router.push({name:'resources'});
          } else if (this.Selection === 'User'){
            this.$router.push({name:'userhome'});
          }else if (this.Selection === 'Create-Forum'){
            this.$router.push({name:'createforum'});

          }
        }
       
    },
    created(){
            // this.getForums();
            this.getTodaysActiveForums();
            // this.getPosts();
            this.getTodaysPopularPosts();
        }
    };
  </script>
<style scoped>


.whole-page{
  display: flex;
  /* height: 100%; */
  overflow-y: auto;
}
.home-text{
  padding-top: 20%;
  border-bottom: 5px solid rgb(250, 129, 240);
  margin-right: 10px;
  padding-bottom: 10%;
}

.dropdown{
  margin-top: 20%;
  margin-right: 10px;
  size: 30px;
  font-size: 23px;

}

.ResourcesViewLink{
  text-decoration: none;
  color: white;
}
.CreateForumViewLink{
  text-decoration: none;
  color: white;
}
.resources-link{
  border-bottom: 5px solid rgb(250, 129, 240);
  margin-right: 10px;
  padding-bottom: 10%;
}
.create-forum{
  border-bottom: 5px solid rgb(250, 129, 240);
  margin-right: 10px;
  padding-bottom: 10%;
}
.left-panel{
  position: fixed;
  display:flex;
  flex-direction: column;
  width: 6%;
  /* border-right: 5px solid rgb(250, 129, 240); */
  height: 100vh;
  /* padding-top: 2px; */
  padding-left: 3px;
  color: white;
  font-size: 1rem;
  z-index: 0;
}
.top-section{
  display: flex;
  flex-direction: column;
  align-items: center;
}
.main-section{
  flex-grow: 1;
  height: auto;
  overflow-y: auto;
}
.main-content{
  padding-top: 2%;
}
/* Test */
.forum-section{
  display: flex;
  justify-content: center;
  /* flex-direction: column; */
  flex-wrap: wrap;
  justify-content: space-evenly;
}
.post-section{
  display: flex;
  flex-direction: column;
  /* flex-wrap: wrap; */
}
.posts-description{
  text-align: center;
  margin-bottom: 15px;
  color: white;
  font-size: 2rem;
}
/* .home{
  margin-left: .85%;
  margin-bottom: 2%;
  color: white;
} */
.forums-description{
  /* margin-left: .85%; */
  color: white;
  font-size: 2rem;
  text-align: center;
}
.searchbar {
  /* justify-content: right; */
  grid-area: searchforums;
  /* top: 300px; */
  width: 250px;
  /* margin-left: 300px; */
}

form {
  /* width: 100%; */
}

.searchbar {
  width: 100%;
  display: flex;
  justify-content: center;
}
.searchbar input, .searchbar button {
  font-size: 20px;
}

.searchbar input {
  width: 400px;
}

.welcome {
  font-size: 3rem;
  color: white;
}
input {
  size: 100px;
}
/* .linksandsearches {
  display: grid;
  grid-template-columns: 3fr, 3fr, 1fr;
  grid: 0%;
  grid-template-areas:
  ". welcome ."
  ". searchforums ."
  ;
} */
/* Test 1 */
/* Test 2 */
</style>










