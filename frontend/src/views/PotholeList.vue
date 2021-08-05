<template>
<div>
  <list/>
  <map-module />
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
}
</script>

<style>

</style>