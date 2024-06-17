<template>
    <div class="create-forum-home"> Create A Forum</div>
    <CreateForumCard v-bind:forum="forum"/>
</template>
  
<script>

import CreateForumCard from '../components/CreateForumCard.vue';


  export default {
    components: {
      CreateForumCard
    },
    data() {
      return {
        changeForum: {
          // id: 0,
          // forum_user_id: 0,
          forum_name: '',
          // time_stamp: Date,
        }
        
      }
    }

    
    
  };
</script>
  
<style scoped>

</style>