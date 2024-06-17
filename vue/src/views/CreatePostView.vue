<template>
    <div class="create-post-home"> Create A Forum</div>
    <CreatePostCard v-bind:post="post"/>

</template>
  
<script>

import CreatePostCard from '../components/CreatePostCard.vue';


  export default {
    components: {
      CreatePostCard,
    },
    data() {
      return {
        changePost: {
          // id: 0,
          // forum_user_id: 0,
          title: '',
          message: '',
          location: '',
          // time_stamp: Date,
        }
        
      }
    }

    
    
  };
</script>
  
<style scoped>

</style>