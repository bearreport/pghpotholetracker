<template>
    <div class="map-sidebar">
      <h1>Pothole Information</h1>
      <p>Click on a pothole marker to view information.</p>
      <ul class="pothole-info">
        <li>Pothole Id: <span class="info-text">{{ this.$store.state.currentMarker.potholeId }}</span></li>
        <li>Submitter Id: <span class="info-text">{{ this.$store.state.currentMarker.submitterId }}</span></li>
        <li>Latitude: <span class="info-text">{{ this.$store.state.currentMarker.lat }}</span></li>
        <li>Longitude: <span class="info-text">{{ this.$store.state.currentMarker.lon }}</span></li>
        <li>Address: <span class="info-text">{{ this.$store.state.currentMarker.addr }}</span></li>
        <li>Neighborhood: <span class="info-text">{{ this.$store.state.currentMarker.neighborhood }}</span></li>
        <li>Date Created: <span class="info-text">{{ this.$store.state.currentMarker.dateCreated }}</span></li>
        <li>Date Inspected: <span class="info-text">{{ this.$store.state.currentMarker.dateInspected }}</span></li>
        <li>Date Repaired: <span class="info-text">{{ this.$store.state.currentMarker.dateRepaired }}</span></li>
        <li>Current Status: <span class="info-text">{{ this.$store.state.currentMarker.status }}</span></li>
        <li>Severity: <span class="info-text">{{ this.$store.state.currentMarker.severity }}</span></li>
        <li>Dimensions: <span class="info-text">{{ this.$store.state.currentMarker.dimensions }}</span></li>
        <li>Notes: </li>
        <textarea class="notes-field" readonly v-model="this.$store.state.currentMarker.notes"></textarea>
      </ul>
    </div>
</template>

<script>
export default {
  name: "map-sidebar"

}
</script>

<style>
.map-sidebar {
  background-color: #025252;
  flex-grow: 2;
  max-width: 350px;
  border-radius: 15px;
  color: white;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  text-align: center;
  padding: 0 12px;
}
.pothole-info {
  text-align: start;
  font-size: 20px;
  font-family: sans-serif;
}
.notes-field {
  width: 87%;
  height: 250px;
  resize: none;
  margin: 20px 0 0 0;
  font-family: sans-serif;
  font-size: 20px;
}
.info-text {
  font-weight: normal;
}

</style>