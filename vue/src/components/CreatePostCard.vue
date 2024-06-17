<template>
  
    <div class="create-post-home"> Create A Post</div>
      <form class ="build-a-post" v-on:submit.prevent="addPost">
      
        <label for="post_name">Name of Post</label>
        <input type="text" id="post_name" name="post_name" v-model="postDto.title"/>

        <label for="post_message_details">Message of Post</label>
        <input type="text" id="post_message_details" name="post_message_details" v-model="postDto.message"/>

        <label for="post_location">Location of Post</label>
        <input type="text" id="post_location" name="post_location" v-model="postDto.location"/>
      
        <div class="submissions">
          <button class="submitBtn" type="submit">Submit</button>
          <button class="cancelBtn" type="button" v-on:click="cancelButton">Cancel</button>
        </div>

      </form>
  

</template>
  
<script>
import PostService from '../services/PostService';

export default {
      components: {

      },
      props: {
        post: {
        type: Object,
        required: false,
      }
      },

      data() {
      return {
        postDto: {
        //   id: 0,
        //   forum_user_id: 0,
          title: '',
          message: '',
          location: '',
        //   time_stamp: Date,
          
        },
        forumId: null

      }
    },

    created(){
      this.forumId = this.$route.params.forumId;
    },
  
    methods: {
      addPost() {
        PostService.addPost(this.forumId, this.postDto).then(
          (response) => {
            if(response.status === 201) {
            //   this.$router.push({ name: "forumview", params: {forumId: this.forumId}} )
            location.reload();
            
            }
          }
        )
      },
      cancelButton() {
        this.$router.push('/');
      }
    },
  
  };
  </script>
  
  <style scoped>
  .create-post-home {
      display: grid;
      align-content: center;
      border-radius: 15px;
      margin: 0 auto;   /* this keeps the card in the middle of the page */
      margin-bottom: 1%;
      font-size: 2.5rem;
      font-weight: bold;
      color: white;
      margin-top: .25%;
      /* height: 100%; */
      width: 100%;
      margin: 0;
      text-align: center;
  }

  .build-a-post {
      display: flex;
      align-items: center;
      align-content: center;
      color: white;
      text-align: center;
      justify-content: center;
      /* height: 100vh; */
      width: 100vh;
      box-sizing: border-box;
  }

  .build-a-post {
      display: grid;
      align-content: baseline;
      border-radius: 15px;
      justify-content: space-around;
      margin: 0 auto;
      margin-bottom: 1%;
      color: white;
      margin-top: 0;
      border-bottom-width: 0;
      text-align: center;
      padding: 10px;
  }
  
  /* Test 1 */
  </style>