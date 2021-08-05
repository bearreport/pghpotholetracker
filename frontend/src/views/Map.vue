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
  }
}
</script>

<style>
.map-content {
  flex-grow: 3;
}
</style>