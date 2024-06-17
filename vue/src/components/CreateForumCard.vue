<template>
  
    <div class="create-forum-home"> Create A Forum</div>
      <form class="build-a-forum" v-on:submit.prevent="addForum" >
        <label for="forum_name">Name of Forum</label>
          <input type="text" id="forum_name" name="forum_name" v-model="changeForum.forum_name" />
      
        <div class="submissions">
          <button class="submitBtn" type="submit">Submit</button>
          <button class="cancelBtn" type="button" v-on:click="cancelButton">Cancel</button>
        </div>
      </form>
  

</template>
  
<script>

import ForumService from '../services/ForumService';

  
export default {
    props: {
      forum: {
        type: Object,
        required: true,
      }
    },

    data() {
      return {
        changeForum: {
        //   id: 0,
        //   forum_user_id: 0,
          forum_name: ''
        //   time_stamp: Date,
          
        }

      }
    },

    methods: {
      addForum() {
        ForumService.addForum(this.changeForum).then(
          (response) => {
            if(response.status === 201) {
              this.$router.push('/')
            }
          }
        ).catch(
                (error) => {
                    if(error.response) {
                        this.errorNeedingAddressed(this.error.response, "Forum not created");
                    } else if (error.request) {
                        this.errorNeedingAddressed(this.error.request, "Forum not created");
                    }
                }
          )
      },
      cancelButton() {
        this.$router.push("/");
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
      
    },
  
  };

  </script>
  
  <style scoped>
  .create-forum-home {
      display: grid;
      align-items: center;
      align-content: center;
      text-align: center;
      /* flex-direction: column;
      justify-content: space-between; */
      border-radius: 15px;
      margin: 0 auto;
      margin-bottom: 1%;
      font-size: 2.5rem;
      font-weight: bold;
      color: white;
      margin-top: .25%;
      height: 100%;
      width: 100%;
      margin: 0;
  }
  .build-a-forum {
      display: flex;
      align-items: center;
      align-content: center;
      color: white;
      text-align: center;
      justify-content: center;
      height: 100vh;
      width: 100vh;
  }

  .build-a-forum {
      display: grid;
      align-content: baseline;
      border-radius: 15px;
      justify-content: space-around;
      margin: 0 auto;
      margin-bottom: 1%;
      color: white;
      margin-top: .25%;
      text-align: center;
      padding: 10px;
  }
  </style>