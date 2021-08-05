<template>
  <div>
    <div id="table-container">
    <table id="tblPotholes">
      <thead>
        <tr>
            <th>Pothole ID</th>
            <th>Submitter ID</th>
            <th>Latitude</th>
            <th>Longitude</th>
            <th>Address</th>
            <th>Neighborhood</th>
            <th>Create Date</th>
            <th>Inspect Date</th>
            <th>Repair Date</th>
            <th>Status</th>
            <th>Severity</th>
            <th>Dimensions</th>
            <th>Notes</th>
          </tr>
      </thead>
      <tbody>
          <tr v-for="pothole in potholes" v-bind:key="pothole.potholeId">
            <td>{{ pothole.potholeId }}</td>
            <td>{{ pothole.submitterId }}</td>
            <td>{{ pothole.lat }}</td>
            <td>{{ pothole.lon }}</td>
            <td>{{ pothole.addr }}</td>
            <td>{{ pothole.neighborhood }}</td>
            <td>{{ pothole.dateCreated }}</td>
            <td>{{ pothole.dateInspected }}</td>
            <td>{{ pothole.dateRepaired }}</td>
            <td>{{ pothole.currentStatus }}</td>
            <td>{{ pothole.severity }}</td>
            <td>{{ pothole.dimensions }}</td>
            <td><textarea class="notes-cell" v-model="pothole.notes"></textarea></td>
          </tr>
      </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import potholeService from '../services/PotholeService'

export default {
    name: "list",
    components: {
      
    },
    data() {
      return {
        potholes: []
      }
    },
    mounted() {
      potholeService.getAllPotholes().then((response) => {
          this.potholes = response.data;
      });   
    }
}
</script>

<style>
.notes-cell {
  resize: none;
  font-family: sans-serif;
}
#table-container {
  overflow-y: scroll;
  height: 500px;
  max-height: 500px;
  margin-top: 20px;

}
#tblPotholes {
  font-family: sans-serif;
  border-collapse: collapse;
  position: relative;
  margin: auto;
}
th {
  position: sticky;
  top: 0;
  background-color: #FFF;
  text-transform: uppercase;
  text-align: center;
  color: white;
  padding: 10px;
  background-color: rgb(51, 51, 51);
}
td {
  padding: 10px;
  font-weight: normal;
  text-align: left;
}
tbody tr:nth-child(even){
  background-color: #c0c0c0;
}
tbody tr:nth-child(odd){
  background-color: #ffffff;
}
</style>