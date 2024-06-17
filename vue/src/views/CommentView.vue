<template>
    <div class="comment">
      Welcome to Comments
    </div>
  </template>
  
  <script>
  import CommentService from '../services/CommentService';
  
  export default {
      data() {
          return {
              comment: {},
              comment_id: this.$route.params.comment_id,
              post_id: this.$route.params.post_id,
          };
      },
      methods: {
          getComments() {
              CommentService.getAllComments(this.post_id).then(
                  (response) => {
                      this.comment = response.data;
                  }
              )
          },
          getComment() {
              CommentService.getComment(this.comment_id).then(
                  (response) => {
                      this.comment.response.data;
                  }
              )
          },
      }
  
  }
  </script>
  
  <style>
  
  .comment {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      width: 650px;
      height: 250px;
      margin: 20px;
      margin: 0 auto;
      margin-bottom: 10px;
      border: 5px solid rgb(250, 129, 240);
      font-size: 2.5rem;
      color: white;
      margin-top: 1px;
      text-align: center;
  }
  
  
  </style>