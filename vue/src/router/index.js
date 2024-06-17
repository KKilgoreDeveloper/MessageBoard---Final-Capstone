import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import UnauthHomeView from '../views/UnauthHomeView.vue';
import ForumView from '../views/ForumView.vue';
import PostView from '../views/PostView.vue';
import CommentView from '../views/CommentView.vue';
import SearchView from '../views/SearchView.vue';
import ResourcesView from '../views/ResourcesView.vue';
import CreateForumView from '../views/CreateForumtView.vue';
import CreatePostView from '../views/CreatePostView.vue';
import UserHomeView from '../views/UserHomeView.vue';


/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/guest-homeview",
    name: "GuestHomeView",
    component: UnauthHomeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums/:forumId",
    name: "forumview",
    component: ForumView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/posts/:postId",
    name: "postview",
    component: PostView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/comments/:comment_id",
    name: "commentview",
    component: CommentView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forum/keyword/:keyword",
    name: "searchview",
    component: SearchView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/resources",
    name: "resources",
    component: ResourcesView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/createforum",
    name: "createforum",
    component: CreateForumView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/createpost",
    name: "createpost",
    component: CreatePostView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/userHome",
    name: "userhome",
    component: UserHomeView,
    meta: {
      requiresAuth: true
    }
  }


  // {
  //   path: "/posts",
  //   name: "AllPostsView",
  //   component: AllPostsView,
  //   meta: {
  //     requiresAuth: false
  //   }
  // },
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
