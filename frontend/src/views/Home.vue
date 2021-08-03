<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
      <div class= "menuButton">
       <button onclick="myFunction()" class="dropbtn">
         <div class="container" onclick="menuButton(this)">
    
        <div class="bar1"></div>
        <div class="bar2"></div>
        <div class="bar3"></div>
   
       <div id="myDropdown" class="dropdown-content">
           <a href="#">Report Pothole</a>
           <a href="#">Pothole Repair ETA</a>
           <a href="#">Pothole Severity</a>
       </div>
       </div>
       </button>
       </div>
       <div class="grid-itemC"><p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Beatae tempore in fugit ratione temporibus voluptatibus quisquam suscipit aspernatur magnam rem labore architecto nam atque saepe itaque neque id, accusamus exercitationem?</p></div>
<div class ="grid-itemD">
        <img src="placeholder.jpg" alt="map of pittsburgh" class= "center"> 
        </div>
</div>
  

</template>

<script>
export default {
  name: "home"
};
</script>
