<template>
<div class="bodyDiv">
  <div class="home">
    <div id="para">
      <img id="ppt-logo-home" src="..\img\PPT-Logo.png" alt="Pittsburgh Pothole Tracker Logo">
      <h3>Welcome to the Pittsburgh Pothole Tracker!</h3>
      <p class="lorem">Giving you the info you need so you can speed!<br><br>From this page you can get most information about the amount of potholes, which neighborhood has the most potholes and severity in your area!</p>
    </div>
    <div id="dashboard-div">
      <dashboard/>
    </div>
  </div>
</div>
</template>

<script>
import Dashboard from '../components/Dashboard.vue';

export default {
  components: {Dashboard},
  name: "home"
};
</script>

<style>
#login {
  background-color: rgba(2,82,82, .9);
  margin: auto;
  margin-top: 100px;
  padding-bottom: 50px;
  padding-top: 10px;
  border-radius: 25px;
  width: 300px;
  height: 525px;
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
.dropdown-content a:hover {
  background-color: #ddd
}
.show {
  display:block;
}
.bodyDiv {
  display: flex;
  justify-content: center;
  padding: 20px;
  border-radius: 15px;
  background: linear-gradient(rgba(0, 0, 0, 0.226), rgba(0, 0, 0, 0.521)), url("https://images.unsplash.com/photo-1515162816999-a0c47dc192f7?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80");
  background-size: cover;
}
.home {
  display: grid;
  width: 100%;
  text-align: center;
  justify-content: space-around;
  align-items: center;
  grid-template-columns: 1fr 1fr;
}
.lorem {
  width: 80%;
  font-size: 30px;
}
#potholePic {
  height: fit-content;
  border-radius: 10px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
#para {
  display: flex;
  color: white;
  font-size: 30px;
  flex-direction: column;
  align-items: center;
  width: 80%;
  padding: 10px;
  text-shadow: 2px 2px 5px black;
  font-weight: bolder;
}
@media only screen and (max-width: 1456px) {
  .home {
    grid-template-columns: 1fr;
    justify-content: space-evenly;
    justify-items: center;
    align-content: space-evenly;
    align-items: center;
  }
}
#ppt-logo-home {
  width: 300px
}
</style>
