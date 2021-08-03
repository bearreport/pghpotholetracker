<template>
  <div>
      <HereMap ref="map" :center="center" />
  </div>  
</template>

<script>
import HereMap from '../components/HereMap'

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
    }
}
  
  },
  mounted() {
    let map = this.$refs.map;
    map.dropMarker({Latitude: 40.42387869, Longitude: -79.9779719});
    
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