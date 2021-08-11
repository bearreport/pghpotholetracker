<template>
    <div class="container">
        <add-pothole />
        <div class='map-content'>
          <map-module />
        </div>
    </div>    
</template>

<script>
import MapModule from "../components/MapModule"
import AddPothole from "../components/AddPothole"

export default {
  components: { AddPothole,
  MapModule },
  created() {
    this.$store.state.mapHeight.height = "750px"
  }
   

}
</script>

<style>
#newPotholeContainer {
  display: flex;
}
</style>