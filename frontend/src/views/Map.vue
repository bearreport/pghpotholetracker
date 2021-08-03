<template>
  <div>
      <HereMap ref="map" :center="center" />
      <button @click.prevent="mapPotholeArray">map all potholes </button>
  </div>  
</template>

<script>
import HereMap from '../components/HereMap'
import potholeService from '../services/PotholeService'

export default {
  name: 'app',
  components: {
    HereMap
    // Remove the HelloWorld.vue 
  },
  data() {
  return {
    // we are this as prop to the HereMap component 
  center:{ 
    lat: 40.42387869, 
    lng: -79.9779719
    },
    potholes: []
}
  
  },
  mounted() {
    let map = this.$refs.map;
    map.dropMarker({Latitude: 40.42387869, Longitude: -79.9779719});
    potholeService.getAllPotholes().then((response) => {
        this.potholes = response.data;
    });   
  },
  methods: {
    mapPotholeArray() {
      let map = this.$refs.map;

      for (let i = 0; i < this.potholes.length; i++) {
        let lat = this.potholes[i].lat;
        let lon = this.potholes[i].lon;
        let data = {};
        data.potholeId = this.potholes[i].potholeId;
        data.neighborhood = this.potholes[i].neighborhood
        data.status = this.potholes[i].currentStatus;
        data.dateCreated = this.potholes[i].dateCreated;
        data.severity = this.potholes[i].severity;
        map.dropMarker({Latitude: lat, Longitude: lon}, data);
      }

    }

  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>