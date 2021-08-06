<template>
  <div class=“home”>
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
      <!--<div class= “menuButton”>
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
       </button>
       </div>
       <div class=“grid-itemC”>
         <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. 
           Beatae tempore in fugit ratione temporibus voluptatibus quisquam suscipit aspernatur 
           magnam rem labore architecto nam atque saepe itaque neque id, accusamus exercitationem?</p>
     </div> 
    </div>-->
    </div>
</template>

<script>
export default {
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
