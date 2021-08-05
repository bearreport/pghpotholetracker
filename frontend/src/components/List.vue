<template>
  <div>
    <div id="table-container">
    <table id="tblPotholes">
      <thead>
        <tr>
            <th>*</th>
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
            <th>Edit?</th>
          </tr>
      </thead>
      <tbody>
          <tr v-for="pothole in filteredList" v-bind:key="pothole.potholeId">
            <td>
              <input
                type="checkbox"
                v-bind:id="pothole.potholeId"
                v-bind:value="pothole.potholeId"
                v-on:change="selectPothole($event)"
                v-bind:checked="selectedPotholeIDs.includes(pothole.potholeId)"
              />
            </td>
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
            <td><button >Edit</button></td>
          </tr>
      </tbody>
      </table>
    </div>
    <button v-on:click="deleteSelectedPotholes()">Delete Selected Potholes</button>
  </div>
</template>

<script>
import potholeService from '../services/PotholeService'

export default {
    name: "list",
    components: {
      
    },
    computed: {
      filteredList() {
        let filteredPotholes = this.$store.state.allPotholes;
        return filteredPotholes;
      }
    },
    data() {
      return {
        selectedPotholeIDs: [],
        errorMsg: "",
        potholeTableKey: 0
      }
    },
    created() {
      potholeService.getAllPotholes().then((response) => {
          this.$store.state.allPotholes = response.data;
      });
    },
    methods: {
      forceGetAllPotholes() {
        potholeService.getAllPotholes().then((response) => {
        this.$store.state.allPotholes = response.data;
        });
      },
      selectPothole(event) {
        if (event.target.checked) {
          this.selectedPotholeIDs.push(parseInt(event.target.id));
        } else {
          this.selectedPotholeIDs = this.selectedPotholeIDs.filter((pothole) => {
            return pothole !== parseInt(event.target.id);
          });
        }
      },
      deletePothole(potholeId){
        potholeService
          .deletePotholeByIdFull(potholeId)
          .then(response => {
            if (response.status !== 200) {
              alert("Error deleting potholes... " + this.errorMsg)
            }
          })
          .catch(error => {
            if (error.response) {
              this.errorMsg =
                "Error deleting pothole. Response received was '" + 
                error.response.statusText + "'.";
            } else if (error.request) {
              this.errorMsg =
                "Error deleting pothole. Server could not be reached.";
            } else {
              this.errorMsg =
                "Error deleting pothole. Request could not be created.";
            }
          })
      },
      deleteSelectedPotholes(){
        if (confirm("You are about to delete the following potholes: " + this.selectedPotholeIDs + " Are you sure?")) {
          this.selectedPotholeIDs.forEach((potholeId) => {
            this.deletePothole(potholeId);
            this.filteredList = this.filteredList.filter((pothole) => {
              return pothole.potholeId !== potholeId;
             })
          });
          this.selectedPotholeIDs = [];
          this.$router.go();
        }
      } 
      // updatePothole() {
      //   const potholeToUpdate = {
          
      //   }
      // }
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