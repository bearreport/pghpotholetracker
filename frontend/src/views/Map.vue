<template>
  <div>
    <div v-if="globalview">
      <HereMap ref="map" :center="center" />
      <button @click.prevent="mapPotholeArray">map all potholes </button>
    </div>
    <div v-if="specificview">
      <PotholeMap ref="potholeMap" :center="center" />
    </div>  
  </div>  
</template>

<script>
import HereMap from '../components/HereMap'
import PotholeMap from '../components/PotholeMap'
import potholeService from '../services/PotholeService'


export default {
  name: 'Map',
  components: {
    HereMap,
    PotholeMap
    // Remove the HelloWorld.vue 
  },
  data() {
  return {
    // we are this as prop to the HereMap component 
  center:{ 
    lat: 40.42387869, 
    lng: -79.9779719
    },
    potholes: [],
    globalview: true,
    specificview: false
}
  
  },
  mounted() {
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
        let data = this.potholes[i];
        map.dropMarker({Latitude: lat, Longitude: lon}, data);
      }

    },

    dropMarker(position, data) {
      const H = window.H;
      let marker = new H.map.Marker({lat: position.Latitude, lng: position.Longitude});
      //add listener to every marker
      marker.addEventListener("tap", () => {
        console.log(data);
        this.globalView = false;
        this.specificView = true;
        });
        this.map.addObject(marker);
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