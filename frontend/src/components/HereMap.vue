<!-- find tutorial code here https://developer.here.com/tutorials/how-to-implement-a-web-map-using-vuejs/-->

<template>
  <div id="map">
  <!--In the following div the HERE Map will render-->
    <div id="mapContainer" style="height:600px;width:100%" ref="hereMap"></div>
  
    <button @click.prevent="getAllPotholes">Get All Potholes</button>
    <button @click.prevent="reverseGeocode">Reverse Geocode</button>
    
  </div>

</template>

<script>
import neighborhoodService from "../services/NeighborhoodService"
import potholeService from "../services/PotholeService"

export default {
  name: "HereMap",
  props: {
    center: Object
    // center object lives on the MAP vue
  },
  data() {
    return {
      platform: null,
      apikey: "gfjDkWIjpaACkQgvCKHvIcyEuH1rDgA41LDywM7Po4U",
      lat: 0,
      long: 0,
      reverseGeocodeResponse: {},
      neighborhoodName: "",
      address: "",
      potholes: []
      // You can get the API KEY from developer.here.com
    };
  },
  async mounted() {
    // Initialize the platform object:
    const platform = new window.H.service.Platform({
      apikey: this.apikey
    });
    this.platform = platform;
    this.initializeHereMap();
    this.getCoords();
  },
  methods: {
    initializeHereMap() { // rendering map

      const mapContainer = this.$refs.hereMap;
      const H = window.H;
      // Obtain the default map types from the platform object
      var maptypes = this.platform.createDefaultLayers();

      // Instantiate (and display) a map object:
      var map = new H.Map(mapContainer, maptypes.vector.normal.map, {
        zoom: 12,
        center: this.center
      });

      addEventListener("resize", () => map.getViewPort().resize());

      // add behavior control
      new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

      // add UI
      H.ui.UI.createDefault(map, maptypes);
      // End rendering the initial map
    },
     getCoords() {
      //collect coordinates
      navigator.geolocation.getCurrentPosition((loc) => {
        this.lat = loc.coords.latitude;
        this.long = loc.coords.longitude;
      })  
    },
    reverseGeocode() {
      neighborhoodService.reverseGeocode(this.lat, this.long).then((response) => {
      this.reverseGeocodeResponse = response.data;
      this.neighborhoodName = response.data.results[0].address_components[2].long_name;
      const fullAddress = response.data.results[0].formatted_address;
      this.address = fullAddress.substring(0, fullAddress.length - 5);
      })
      }  
    },
    getAllPotholes() {
      potholeService.getAllPotholes().then((response) => {
        this.potholes = response.data;
        console.log(this.potholes)
      })
    }
  };
</script>

<style scoped>
#map {
  width: 60vw;
  min-width: 360px;
  text-align: center;
  margin: 5% auto;
  background-color: #ccc;
}
</style>