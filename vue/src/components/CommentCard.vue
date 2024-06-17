<template>
  <div class="CommentCard">

    <router-link class="CommentViewLink" v-bind:to="{name: 'commentview', params:{comment_id: comment.comment_id} }">
        <header>
            <h1 class="messageDetails">{{ comment.message }}</h1>
        </header>
    </router-link>
    <div class="timeStamp">{{ comment.time_stamp }}</div>
  </div>
</template>

<script>

export default{
    data(){
        return{
            // iconClick: false
        }
    },
    components: {
        // PostsComponent
    },
    props: {
        comment: {
            type: Object,
            required: true
        },
        
    },
    computed: {

    },

};
</script>

<style scoped>

.CommentCard {
    display: grid;
    align-items: center;
    align-content: baseline;
    justify-content: center;
    margin: 0 auto;
}

.CommentCard{
    display: flex;
    flex-direction: column;
    justify-content: center;
    border: 8px solid rgb(250, 129, 240);
    border-radius: 15px;
    width: 50%;
    min-width: 600px;
    height: 150px;
    /* margin: 20px; */
    /* margin: 0 auto; */
    margin-bottom: 5px;
    background-color: #34aae1;

}

.CommentCard .messageDetails{
    font-size: 2.0rem;
    color: white;
    text-align: center;
    /* margin-top: 1px; */
    /* text-align: justify; */
    padding-top: 20px;

}

.timeStamp{
    font-size: 1rem;
    color: white;
    align-self: flex-end;
    /* justify-self: flex-end; */
    padding-right: 5px;
    padding-top: 10px;
}

.CommentViewLink {
    text-decoration: none;
    color: white;
}

</style>