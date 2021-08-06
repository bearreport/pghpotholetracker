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
          <tr v-for="pothole in filteredList" v-bind:key="pothole.potholeId" v-bind:class="{ active : selectedPotholeIDs.includes(pothole.potholeId) }">
           
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

            <!-- <td v-if="potholesBeingEdited.includes(pothole)"><input class="idField" type="number" step="any" v-model="potholesBeingEdited[0].potholeId"></td>
            <td v-if="potholesBeingEdited.includes(pothole)"><input class="idField" type="number" step="any" v-model="potholesBeingEdited[0].submitterId"></td> -->
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)"><input type="text" v-model="potholesBeingEdited[0].lat"></td>
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)"><input type="text" v-model="potholesBeingEdited[0].lon"></td>
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)"><input type="text" v-model="potholesBeingEdited[0].addr"></td>
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)"><input type="text" v-model="potholesBeingEdited[0].neighborhood"></td>
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)"><input type="date" v-model="potholesBeingEdited[0].dateCreated"></td>
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)"><input type="date" v-model="potholesBeingEdited[0].dateInspected"></td>
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)"><input type="date" v-model="potholesBeingEdited[0].dateRepaired"></td>
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)">
              <select type="text" v-model="potholesBeingEdited[0].currentStatus">
                <option value="uninspected">uninspected</option>
                <option value="under inspection">under inspection</option>
                <option value="inspected">inspected</option>
                <option value="under repair">under repair</option>
                <option value="repaired">repaired</option>
              </select>
            </td>
            <td v-if="potholesBeingEdited.includes(pothole)">
              <select type="text" v-model="potholesBeingEdited[0].severity">
                <option value="low">low</option>
                <option value="medium">medium</option>
                <option value="high">high</option>
                <option value="extreme">extreme</option>
              </select>
            </td>
            <td v-if="potholesBeingEdited.includes(pothole)">
              <select type="text" v-model="potholesBeingEdited[0].dimensions">
                <option value="0-1ft">0-1ft</option>
                <option value="1-2ft">1-2ft</option>
                <option value="2+ft">2+ft</option>
                <option value="sinkhole">sinkhole</option>
              </select>
            </td>
            <td class="cell" v-if="potholesBeingEdited.includes(pothole)"><textarea class="notes-cell" type="text" v-model="potholesBeingEdited[0].notes"></textarea></td>

            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.lat }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.lon }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.addr }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.neighborhood }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.dateCreated }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.dateInspected }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.dateRepaired }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.currentStatus }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)"><p class="severity" v-bind:class="pothole.severity">{{ pothole.severity }}</p></td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.dimensions }}</td>
            <td class="cell" v-if="!potholesBeingEdited.includes(pothole)"><textarea class="notes-cell" v-model="pothole.notes"></textarea></td>


            <td>
              <button v-if="potholesBeingEdited.length == 0" v-on:click="addPotholeToEdited(pothole)">Edit</button>
              <button class="updateButton xButton" v-if="potholesBeingEdited.includes(pothole)" v-on:click="clearEdited()">X</button>
              <button class="updateButton checkButton" v-if="potholesBeingEdited.includes(pothole)" v-on:click="updatePothole(potholesBeingEdited[0])">✓</button>
            </td>

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
        potholesBeingEdited: []
      }
    },
    created() {
      potholeService.getAllPotholes().then((response) => {
          this.$store.state.allPotholes = response.data;
      });
    },
    methods: {
      addPotholeToEdited(pothole) {
        if (!this.potholesBeingEdited.includes(pothole)) {
          this.potholesBeingEdited.push(pothole);
        }
      },
      clearEdited() {
        this.potholesBeingEdited = []
      },
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
        if (this.selectedPotholeIDs.length == 0) {
          return null;
        }
        if (confirm("You are about to delete the following potholes: \n" + this.selectedPotholeIDs + "\n\n Are you sure?")) {
          this.selectedPotholeIDs.forEach((potholeId) => {
            this.deletePothole(potholeId);
            this.filteredList = this.filteredList.filter((pothole) => {
              return pothole.potholeId !== potholeId;
             })
          });
          this.selectedPotholeIDs = [];
          this.$router.go();
        }
      }, 
      updatePothole(pothole) {
        potholeService
          .updatePotholeByIdFull(pothole)
          .then(response => {
          if (response.status === 200) {
            this.$router.go();
          }
        })
      }
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
  table-layout: fixed;
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
  height: 50px;
}
.active {
  box-shadow: inset 0 0 50px rgb(240, 240, 174);
}
tbody tr:nth-child(even){
  background-color: #c0c0c0;
}
tbody tr:nth-child(odd){
  background-color: #ffffff;
}
.severity {
  text-align: center;
  font-weight: bold;
  border-radius: 5px;
  text-transform: uppercase;
}
.low {
  background-color: greenyellow;
}
.medium {
  background-color: yellow;
}
.high {
  background-color: red;
  color: white;
}
.extreme {
  background-color: purple;
  color: white;
}
input[type=text] {
  width: 100px;
}
input[type=date] {
  width: 40px;
}
.updateButton {
  width: 30px;
}
.xButton {
  background-color: red;
}
.checkButton {
  background-color: greenyellow;
}
.idField {
  width: 50px;
}
.class {
  width: 50px;
}
</style>