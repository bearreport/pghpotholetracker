<template>
  <div class=“home”>
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <dashboard/>
</div>
</template>

<script>
import Dashboard from '../components/Dashboard.vue';

export default {
  components: { Dashboard },
  name: "home"
};
</script>

<style>



#login {
  
  background-color: rgba(2,82,82, .9);
  
  margin: auto;
  margin-top: 10%;
  padding-bottom: 50px;
  padding-top: 10px;
  border-radius: 25px;
  width: 20%;
  height: auto;
  align-content: center;
}
.form-signin{
  opacity: 100%;
  color: white;
 
}
#username{
font-size: 15px;
}
#password{
  font-size: 15px;
}
.sr-only{
  margin-top: 10px;
  font-size:x-large;
}
#form-control{
  opacity: 100%;
}

  .dropdown-content a:hover {background-color: #ddd}
  .show {display:block;}
</style>
