<template>
  <div id="app">
    <div id="nav">
      <div id="page-title">Pittsburgh Pothole Tracker</div>
      <span></span>
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      <span>   |   </span>
      <router-link v-bind:to="{ name: 'map' }">Map</router-link>
      <span v-if="$store.state.token != ''">   |   </span>
      <router-link v-bind:to="{ name: 'list' }" v-if="$store.state.token != '' && this.$store.state.user.authorities[0].name == 'ROLE_EMPLOYEE'">List View</router-link>
      <span v-if="$store.state.token != '' && this.$store.state.user.authorities[0].name == 'ROLE_EMPLOYEE'">   |  </span>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <span v-if="$store.state.token == ''">You are not currently logged in.</span>
      <span v-if="$store.state.token != ''">You are currently logged in as: {{ this.$store.state.user.authorities[0].name.substring(5) }}</span>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {

}
</script>

<style>
#nav {
  display: flex;
  width: 100%;
  height: 50px;
  border-radius: 15px;
  align-items: center;
  justify-content: start;
  background-color: #025252;
  color: white;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  font-size: 20px;
  font-weight: bold;
  padding: 0 20px;
}
#nav a.router-link-exact-active {
  color: #42b983;
}
#nav a {
  font-weight: bold;
  color: white;
}
span {
  padding: 0 20px;
}
#page-title {
  font-weight: bolder;
}
</style>



