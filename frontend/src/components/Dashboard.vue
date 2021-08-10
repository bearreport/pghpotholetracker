<template>
<div>

  <div class="dashboard">
      <h1>Dashboard</h1>
      <ul id="dashboard-list">
        <li>Total Number of <br>Unrepaired Potholes: {{ numberOfPotholes }}</li>
        <br>
        <li>Potholes of low severity: {{ numberOfPotholesOfLowSeverity }}</li>
        <li>Potholes of medium severity: {{numberOfPotholesOfMediumSeverity}} </li>
        <li>Potholes of high severity: {{numberOfPotholesofHighSeverity}} </li>
        <li> Potholes of extreme severity: {{numberOfPotholesExtremeSeverity}}</li>
      </ul>
      <h2>Top 5 Pittsburgh Neighborhoods<br>by Unrepaired Pothole Occurence</h2>
      <ol id="neighborhoodList">
      <li v-for="neighborhoodWithPotholes in potholesPerNeighborhood" v-bind:key="neighborhoodWithPotholes.neighborhood" class="neighborhood"> {{neighborhoodWithPotholes.neighborhood}} : {{neighborhoodWithPotholes.occurrence}}</li>
      </ol>
      <h2>Total number of repaired potholes: {{ numberOfRepairedPotholes }}</h2>
  </div>
  </div>
  
</template>

<script>
import potholeService from '../services/PotholeService'

export default {
    name: "dashboard",
    components: {
      
    },
    computed: {
        numberOfPotholes() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.currentStatus != "repaired";
            }).length;
        },
        numberOfPotholesOfLowSeverity() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.severity === "low" && pothole.currentStatus != "repaired" // filters out to find potholes with low severity and puts them all in an array.
            }).length; // will actually return just the length of that array, showing how many it found in the data.
        },
        numberOfPotholesOfMediumSeverity() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.severity === "medium" && pothole.currentStatus != "repaired"
            }).length;
        },
         numberOfPotholesOfHighSeverity() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.severity === "high" && pothole.currentStatus != "repaired"
            }).length;
        },
        numberOfPotholesExtremeSeverity() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.severity === "extreme" && pothole.currentStatus != "repaired"
            }).length;
            
        },
        potholesPerNeighborhood() {
            let arr = this.$store.state.allPotholes.filter((pothole) => {
                return pothole.currentStatus != "repaired";
            });
            let key = "neighborhood";
            let arr2 = [];

            arr.forEach((x)=>{
       
            // Checking if there is any object in arr2
            // which contains the key value
            if(arr2.some((val)=>{ return val[key] == x[key] })){
                
            // If yes! then increase the occurrence by 1
            arr2.forEach((k)=>{
                if(k[key] === x[key]){ 
                k["occurrence"]++
                }
            })
                
            }else{
            //if not, add it and set occurence to 1
            let a = {}
            a[key] = x[key]
            a["occurrence"] = 1
            arr2.push(a);
            }
        })
        arr2.sort((a,b) => parseInt(b.occurrence) - parseInt(a.occurrence))   
        return arr2.slice(0, 4);
        },
        numberOfRepairedPotholes() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.currentStatus == "repaired";
            }).length;
        }
        },
    
    created() {
      potholeService.getAllPotholes().then((response) => {
          this.$store.state.allPotholes = response.data;
      })
    }
}

</script>

<style>
.dashboard {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #025252;
    border-radius: 15px;
    color: white;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    text-align: center;
    padding: 2px 25px;
    height: 100%;
}
#dashboard-list {
    text-align: left;
    font-weight: bold;
    font-size: 20px;
}
.neighborhood {
    text-align: left;
    padding-bottom: 15px;
}

</style>