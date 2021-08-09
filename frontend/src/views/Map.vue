<template>
<div>
  <div class='container'>
    <map-sidebar />
    <div class='map-content'>
      <map-module />
    </div>
  </div>   
</div>
</template>

<script>
import MapSidebar from '@/components/MapSidebar.vue'
import MapModule from '../components/MapModule.vue'


export default {
  name: 'Map',
  components: {
    MapSidebar,
    MapModule
  },
  created() {
    this.$store.state.mapHeight.height = "750px"
  }
}
</script>

<style>
.map-content {
  flex-grow: 3;
  padding-left: 10px;
}

</style>