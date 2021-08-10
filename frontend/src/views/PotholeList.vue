<template>
<div id="list-container">

  <div id="list-map">
    <map-module />
  </div>

  <div id="list-list">
    <list/>
  </div>

</div>
</template>

<script>
import List from '../components/List.vue'
import MapModule from '../components/MapModule.vue'

export default {
    name: 'pothole-list',
    components: {
        List,
        MapModule
    },
  created() {
    this.$store.state.mapHeight.height = "360px"
  }
}
</script>

<style>
#list-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>