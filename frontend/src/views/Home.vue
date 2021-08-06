<template>
  <div class=“home”>
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <dashboard/>
      <!-- <div class= “menuButton”>
       <button onclick=“myFunction()” class=“dropbtn”>
        <div class=“container” onclick=“menuButton(this)“>
        <div class=“bar1”></div>
        <div class="bar2"></div>
        <div class=“bar3”></div>
        <div id=“myDropdown” class=“dropdown-content”>
           <a href=“#”>Report Pothole</a>
           <a href=“#”>Pothole Repair ETA</a>
           <a href=“#”>Pothole Severity</a
       </div>
       </div>
       </button>
       </div>
       <div class=“grid-itemC”>
         <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. 
           Beatae tempore in fugit ratione temporibus voluptatibus quisquam suscipit aspernatur 
           magnam rem labore architecto nam atque saepe itaque neque id, accusamus exercitationem?</p>
     </div> -->
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
.home {

}
.container {
    display: inline-block;
    cursor: pointer;
}
.bar1, .bar2, .bar3 {
    width: 35px;
    height: 5px;
    background-color: #333;
    margin: 6px 0;
    transition: 0.4s;
}
.change .bar1 {
    -webkit-transform: rotate(-45deg) translate(-9px, 6px);
    transform: rotate(-45deg) translate(-9px, 6px)
}
.change .bar2 {
    opacity: 0;
}
.change .bar3 {
    -webkit-transform: rotate(45deg) translate(-8px, -8px);
    transform: rotate(45deg) translate(-8px, -8px);
}
.dropbtn {
    background-color: #3498DB;
    color: white;
    padding: 16px;
    font-size: 16px;
    border: none;
    cursor: pointer;
  }
  .dropbtn:hover, .dropbtn:focus {
    background-color: #2980B9;
  }
  .dropdown {
    position: relative;
    display: inline-block;
  }
  .dropdown-content {
    display: none;
    position: absolute;
    background-color: #F1F1F1;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
  }
  .dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
  }


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
