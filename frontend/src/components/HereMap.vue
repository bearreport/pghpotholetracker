<!-- find tutorial code here https://developer.here.com/tutorials/how-to-implement-a-web-map-using-vuejs/-->

<template>
  <div id="map">
  <!--In the following div the HERE Map will render-->
    <div id="mapContainer" style="height:600px;width:100%" ref="hereMap"></div>
  </div>
</template>

<script>
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

  },
  methods: {
    initializeHereMap() { // rendering map

      const mapContainer = this.$refs.hereMap;
      const H = window.H;
      // Obtain the default map types from the platform object
      let maptypes = this.platform.createDefaultLayers();

      // Instantiate (and display) a map object:
      let map = new H.Map(mapContainer, maptypes.vector.normal.map, {
        zoom: 12,
        center: this.center
      });
      //add event listeners for reshaping the viewport and marker dragging
      addEventListener("resize", () => map.getViewPort().resize());
      

      // add behavior control
      new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

      // add UI
      H.ui.UI.createDefault(map, maptypes, H);
      // End rendering the initial map

      let behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));
      console.log("adding draggable marker")
      this.addDraggableMarker(map, behavior)
      console.log("marker added")
    },
    addDraggableMarker(map, behavior, here){
      let marker = new here.map.Marker({lat: 40.42387869, lng: -79.9779719});
      // Ensure that the marker can receive drag events
      marker.draggable = true;
      map.addObject(marker);
      console.log(marker);
      // disable the default draggability of the underlying map
      // when starting to drag a marker object:
      map.addEventListener('dragstart', function(ev) {
        let target = ev.target;
        if (target instanceof here.map.Marker) {
          behavior.disable();
        }
      }, false);


      // re-enable the default draggability of the underlying map
      // when dragging has completed
      map.addEventListener('dragend', function(ev) {
        let target = ev.target;
        if (target instanceof here.map.Marker) {
          behavior.enable();
        }
      }, false);

      // Listen to the drag event and move the position of the marker
      // as necessary
      map.addEventListener('drag', function(ev) {
        let target = ev.target,
            pointer = ev.currentPointer;
        if (target instanceof here.map.Marker) {
          target.setPosition(map.screenToGeo(pointer.viewportX, pointer.viewportY));
        }
      }, false);
    }
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