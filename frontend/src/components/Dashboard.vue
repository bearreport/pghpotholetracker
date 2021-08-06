<template>
  <div class="dashboard">
      <h1>Dashboard</h1>
      <ul id="dashboard-list">
        <li>Total Number of Potholes: {{ numberOfPotholes }}</li>
        <li>Potholes of low severity: {{ numberOfPotholesOfLowSeverity }}</li>
        <li>Potholes of medium severity: {{numberOfPotholesOfMediumSeverity}} </li>
        <li>Potholes of high severity: {{numberOfPotholesofHighSeverity}} </li>
        <li> Potholes of extreme severity: {{numberOfPotholesExtremeSeverity}}</li>
      </ul>
      <h2>Neighborhoods with most number of potholes</h2>

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
            return this.$store.state.allPotholes.length
        },
        numberOfPotholesOfLowSeverity() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.severity === "low" // filters out to find potholes with low severity and puts them all in an array.
            }).length; // will actually return just the length of that array, showing how many it found in the data.
        },
        numberOfPotholesOfMediumSeverity() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.severity === "medium"
            }).length;
        },
        //add computed data methods for high severity, extreme severity, and find other things like the neighborhood with the most potholes, or any other useful info you can think of.
        numberOfPotholesOfHighSeverity() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.severity === "high"
            }).length;
        },
        numberOfPotholesExtremeSeverity() {
            return this.$store.state.allPotholes.filter((pothole) => {
                return pothole.severity === "extreme"
            }).length;
            
        }},
    
    created() {
      potholeService.getAllPotholes().then((response) => {
          this.$store.state.allPotholes = response.data;
      })
    }
}

</script>

<style>
.dashboard {
  background-color: #025252;
  max-width: 350px;
  border-radius: 15px;
  color: white;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  text-align: center;
  padding: 0 12px;
}
#dashboard-list {
    text-align: left;
    font-weight: bold;
}
</style>