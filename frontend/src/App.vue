<template>
  <div id="app">
    <div id="nav">
      <h3 id="page-title">PITTSBURGH POTHOLE TRACKER</h3>
      <div>
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      <span class="pipe">   |   </span>
      <router-link v-bind:to="{ name: 'map' }">Map</router-link>
      <span class="pipe" v-if="$store.state.token != ''">   |   </span>
      <router-link v-bind:to="{ name: 'list' }" v-if="checkRole">List View</router-link>
      <span class="pipe" v-if="checkRole" >   |  </span>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      </div>
      <span v-if="$store.state.token == ''">You are not currently logged in.</span>
      <span v-if="$store.state.token != ''">You are currently logged in as: {{ this.$store.state.user.authorities[0].name.substring(5) }}</span>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  computed: {
    checkRole() {
      if (this.$store.state.token != '' && (this.$store.state.user.authorities[0].name == 'ROLE_EMPLOYEE' || this.$store.state.user.authorities[0].name == 'ROLE_ADMIN')) {
        return true;
      } else {
        return false;
      }
    }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Work+Sans:wght@200&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Staatliches&display=swap');

#app {
  font-family: 'Work Sans', sans-serif;
  font-weight: bold;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 5px;
}
body {
  background-color: #ffffea;
}
#nav {
  display: flex;
  width: 98%;
  height: 50px;
  border-radius: 15px 0 0 15px;
  align-items: center;
  justify-content: space-around;
  background-color: #025252;
  color: white;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  font-size: 20px;
  font-weight: bold;
  padding: 0 20px;
  font-size: 1.2h;
}
#nav a.router-link-exact-active {
  color: #42b983;
}
#nav a {
  font-weight: bold;
  color: white;
}
.pipe {
  padding: 0 20px;
}
#page-title {
  font-weight: bolder;
  font-family: 'Staatliches', sans-serif;
  font-size: 2vw;
}


</style>



