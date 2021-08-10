
<template>

  <div class="backgroundcontainer">
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
     <div class="grid-container">
    <div class="grid-itemA"> <h1>Pothole Tracker</h1> </div>
    <div class="grid-itemB"><h3>We got what you need so you can speed!</h3></div>
     </div>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <label for="username" class="sr-only">Username</label><br>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      /><br>
      <label for="password" class="sr-only">Password</label><br>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      /><br>
      <router-link class="need-account" :to="{ name: 'register' }">Need an account?</router-link><br>
      <button class="sign-in-button" type="submit">Sign in</button>
    </form> 
    </div>
  </div>
  
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style>
.grid-itemB{
  width: 250px;
}
#username{
  width: 175px;
}
#password{
  width: 175px;
}
.need-account{
color: mintcream;

}
.need-account:hover{
  color: blue
}

.sign-in-button{
height: 40px;
width: 80px;
}


div.backgroundcontainer{
  margin: 0;
  padding-top: 30px;
  padding-bottom: 300px;
  height: 100%;
  width: 100%;
   
 background-image: url("https://images.unsplash.com/photo-1447357704087-5de405e80f38?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1617&q=80");
 background-size:cover;
 background-repeat: no-repeat;
 background-color: #000000;
 width: 100%;
 height:auto;
}
.backgroundcontainer{
  
  height: 100vh;
  width: 100%;
  background-image: url("https://images.unsplash.com/photo-1447357704087-5de405e80f38?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1617&q=80") ;
  background-size:cover;
  background-position: center;
  background-repeat: no-repeat;
  transition: 5s;

  animation-name: animate;
  
  
  animation-direction: alternate-reverse;
  animation-duration: 20s;
  animation-fill-mode:backwards;
  animation-iteration-count: infinite;
  animation-play-state: running;
  animation-timing-function: ease-in-out;

  
}

@keyframes animate{
  0%{
    background-image: url("https://images.unsplash.com/photo-1614111603415-62097d5606ce?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1373&q=80");
  } 20%{
    background-image: url("https://images.unsplash.com/photo-1593436878321-2cadf6021994?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1349&q=80");
  } 40% {
    background-image: url("https://images.unsplash.com/photo-1563229603-eca60c81d02f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80");
  } 
}
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

</style>