<template>
  <div>
    <div id="table-container">
    <table id="tblPotholes">
      <thead>
        <tr>
          <!-- Header Cells -->
            <th class="cell-id">✓</th>
            <th class="cell-id">ID<br><button class="sortButton" v-on:click="setSortField('ID')">▼</button></th>
            <th class="cell-id">USR<br><button class="sortButton" v-on:click="setSortField('USERID')">▼</button></th>
            <th class="cell-ll">Lat<br><button class="sortButton" v-on:click="setSortField('LAT')">▼</button></th>
            <th class="cell-ll">Lon<br><button class="sortButton" v-on:click="setSortField('LON')">▼</button></th>
            <th class="cell-loc">Addr.<br><button class="sortButton" v-on:click="setSortField('ADDR')">▼</button></th>
            <th class="cell-loc">Nbhd.<br><button class="sortButton" v-on:click="setSortField('NEIGHBORHOOD')">▼</button></th>
            <th class="cell-date">Create Date<br><button class="sortButton" v-on:click="setSortField('CREATE')">▼</button></th>
            <th class="cell-date">Inspect Date<br><button class="sortButton" v-on:click="setSortField('INSPECT')">▼</button></th>
            <th class="cell-date">Repair Date<br><button class="sortButton" v-on:click="setSortField('REPAIR')">▼</button></th>
            <th class="cell-stat">Status<br><button class="sortButton" v-on:click="setSortField('STATUS')">▼</button></th>
            <th class="cell-stat">Severity<br><button class="sortButton" v-on:click="setSortField('SEVERITY')">▼</button></th>
            <th class="cell-dim">Size<br><button class="sortButton" v-on:click="setSortField('SIZE')">▼</button></th>
            <th class="cell-notes">Notes</th>
            <th class="cell-edit">Edit?</th>
          </tr>
      </thead>
      <tbody>
          <tr 
            v-for="pothole in filteredList" 
            v-bind:key="pothole.potholeId" 
            v-bind:class="{ active : selectedPotholeIDs.includes(pothole.potholeId), 
                            reviewNeeded : pothole.currentStatus == 'needs review',
                            close : tooClose(pothole),
                            clickedPin : clickedPin(pothole)
                          }"
          >
           
           <!-- Cells with checkboxes -->
            <td class="cell-id">
              <input
                type="checkbox"
                v-bind:id="pothole.potholeId"
                v-bind:value="pothole.potholeId"
                v-on:change="selectPothole($event)"
                v-bind:checked="selectedPotholeIDs.includes(pothole.potholeId)"
              />
            </td>

            <!-- Cells with data for ids (must be separate since they do not get edited.) -->
            <td class="cell-id">{{ pothole.potholeId }}</td>
            <td class="cell-id">{{ pothole.submitterId }}</td>

            <!-- Cells with inputs -->
            <td class="cell-ll" v-if="potholesBeingEdited.includes(pothole)"><input type="text" v-model="potholesBeingEdited[0].lat"></td>
            <td class="cell-ll" v-if="potholesBeingEdited.includes(pothole)"><input type="text" v-model="potholesBeingEdited[0].lon"></td>
            <td class="cell-loc" v-if="potholesBeingEdited.includes(pothole)"><input type="text" v-model="potholesBeingEdited[0].addr"></td>
            <td class="cell-loc" v-if="potholesBeingEdited.includes(pothole)"><input type="text" v-model="potholesBeingEdited[0].neighborhood"></td>
            <td class="cell-date" v-if="potholesBeingEdited.includes(pothole)"><input type="date" v-model="potholesBeingEdited[0].dateCreated"></td>
            <td class="cell-date" v-if="potholesBeingEdited.includes(pothole)"><input type="date" v-model="potholesBeingEdited[0].dateInspected"></td>
            <td class="cell-date" v-if="potholesBeingEdited.includes(pothole)"><input type="date" v-model="potholesBeingEdited[0].dateRepaired"></td>
            <td class="cell-date" v-if="potholesBeingEdited.includes(pothole)">
              <select type="text" v-model="potholesBeingEdited[0].currentStatus">
                <option value="needs review">needs review</option>
                <option value="uninspected">uninspected</option>
                <option value="under inspection">under inspection</option>
                <option value="inspected">inspected</option>
                <option value="under repair">under repair</option>
                <option value="repaired">repaired</option>
              </select>
            </td>
            <td class="cell-stat" v-if="potholesBeingEdited.includes(pothole)">
              <select type="text" v-model="potholesBeingEdited[0].severity">
                <option value="low">low</option>
                <option value="medium">medium</option>
                <option value="high">high</option>
                <option value="extreme">extreme</option>
              </select>
            </td>
            <td class="cell-dim" v-if="potholesBeingEdited.includes(pothole)">
              <select type="text" v-model="potholesBeingEdited[0].dimensions">
                <option value="0-1ft">0-1ft</option>
                <option value="1-2ft">1-2ft</option>
                <option value="2+ft">2+ft</option>
                <option value="sinkhole">sinkhole</option>
              </select>
            </td>
            <td class="cell-notes" v-if="potholesBeingEdited.includes(pothole)"><textarea class="notes-cell" type="text" v-model="potholesBeingEdited[0].notes"></textarea></td>

            <!-- Cells with data -->
            <td class="cell-ll" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.lat }}</td>
            <td class="cell-ll" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.lon }}</td>
            <td class="cell-loc" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.addr }}</td>
            <td class="cell-loc" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.neighborhood }}</td>
            <td class="cell-date" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.dateCreated }}</td>
            <td class="cell-date" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.dateInspected }}</td>
            <td class="cell-date" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.dateRepaired }}</td>
            <td class="cell-stat" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.currentStatus }}</td>
            <td class="cell-stat" v-if="!potholesBeingEdited.includes(pothole)"><p class="severity" v-bind:class="pothole.severity">{{ pothole.severity }}</p></td>
            <td class="cell-dim" v-if="!potholesBeingEdited.includes(pothole)">{{ pothole.dimensions }}</td>
            <td class="cell-notes" v-if="!potholesBeingEdited.includes(pothole)"><textarea class="notes-cell" v-model="pothole.notes"></textarea></td>


            <td class="cell-edit">
              <button v-if="potholesBeingEdited.length == 0" v-on:click="addPotholeToEdited(pothole)">Edit</button>
              <button class="updateButton xButton" v-if="potholesBeingEdited.includes(pothole)" v-on:click="clearEdited()">X</button>
              <button class="updateButton checkButton" v-if="potholesBeingEdited.includes(pothole)" v-on:click="updatePothole(potholesBeingEdited[0])">✓</button>
            </td>

          </tr>
      </tbody>
      </table>
    </div>
    <div class="button-container">
    <button v-on:click="deleteSelectedPotholes()">Delete Selected Potholes</button>
    <div>
      <label for="searchRadiusInput">Search Radius (in dec. degrees): </label>
      <input type="number" id="searchRadiusInput" v-model.number="searchRadius" step=".0001">
    </div>
    </div>
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
        if (this.sortByField == "") {
          let idA = 0;
          let idB = 0;
          filteredPotholes.sort((a, b) => {
            if (a.currentStatus == "needs review") {
              idA = -500
            } else {
              idA = a.potholeId;
            }
            if (b.currentStatus == "needs review") {
              idB = -500
            } else {
              idB = b.potholeId;
            }
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "ID") {
          filteredPotholes.sort((a, b) => {
            let idA = a.potholeId;
            let idB = b.potholeId;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        }  else if (this.sortByField == "USERID") {
          filteredPotholes.sort((a, b) => {
            let idA = a.submitterId;
            let idB = b.submitterId;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "LAT") {
          filteredPotholes.sort((a, b) => {
            let idA = a.lat;
            let idB = b.lat;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "LON") {
          filteredPotholes.sort((a, b) => {
            let idA = a.lon;
            let idB = b.lon;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "ADDR") {
          filteredPotholes.sort((a, b) => {
            let idA = a.addr;
            let idB = b.addr;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "NEIGHBORHOOD") {
          filteredPotholes.sort((a, b) => {
            let idA = a.neighborhood;
            let idB = b.neighborhood;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "CREATE") {
          filteredPotholes.sort((a, b) => {
            let idA = a.dateCreated;
            let idB = b.dateCreated;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "INSPECT") {
          filteredPotholes.sort((a, b) => {
            let idA = (a.dateInspected) ? "" : "" + a.dateInspected;
            let idB = (b.dateInspected) ? "" : "" + b.dateInspected;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "REPAIR") {
          filteredPotholes.sort((a, b) => {
            let idA = (a.dateRepaired) ? "" : "" + a.dateRepaired;
            let idB = (b.dateRepaired) ? "" : "" + b.dateRepaired;
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "STATUS") {
          let idA = 0;
          let idB = 0;
          filteredPotholes.sort((a, b) => {
            if (a.currentStatus == "needs review") {
              idA = 0;
            } else if (a.currentStatus == "uninspected") {
              idA = 1;
            } else if (a.currentStatus == "under inspection") {
              idA = 2;
            } else if (a.currentStatus == "inspected") {
              idA = 3;
            } else if (a.currentStatus == "under repair") {
              idA = 4;
            } else if (a.currentStatus == "repaired") {
              idA = 5;
            }
            if (b.currentStatus == "needs review") {
              idB = 0;
            } else if (b.currentStatus == "uninspected") {
              idB = 1;
            } else if (b.currentStatus == "under inspection") {
              idB = 2;
            } else if (b.currentStatus == "inspected") {
              idB = 3;
            } else if (b.currentStatus == "under repair") {
              idB = 4;
            } else if (b.currentStatus == "repaired") {
              idB = 5;
            }
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "SEVERITY") {
          let idA = 0;
          let idB = 0;
          filteredPotholes.sort((a, b) => {
            if (a.severity == "low") {
              idA = 3;
            } else if (a.severity == "medium") {
              idA = 2;
            } else if (a.severity == "high") {
              idA = 1;
            } else if (a.severity == "extreme") {
              idA = 0;
            }
            if (b.severity == "low") {
              idB = 3;
            } else if (b.severity == "medium") {
              idB = 2;
            } else if (b.severity == "high") {
              idB = 1;
            } else if (b.severity == "extreme") {
              idB = 0;
            }
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        } else if (this.sortByField == "SIZE") {
          let idA = 0;
          let idB = 0;
          filteredPotholes.sort((a, b) => {
            if (a.dimensions == "0-1ft") {
              idA = 3;
            } else if (a.dimensions == "1-2ft") {
              idA = 2;
            } else if (a.dimensions == "2+ft") {
              idA = 1;
            } else if (a.dimensions == "sinkhole") {
              idA = 0;
            }
            if (b.dimensions == "0-1ft") {
              idB = 3;
            } else if (b.dimensions == "1-2ft") {
              idB = 2;
            } else if (b.dimensions == "2+ft") {
              idB = 1;
            } else if (b.dimensions == "sinkhole") {
              idB = 0;
            }
            if (idA < idB) {
              return -1;
            } else if (idA > idB) {
              return 1
            } else {
              return 0
            }
          });
        }
        return filteredPotholes;
      }
    },
    data() {
      return {
        selectedPotholeIDs: [],
        errorMsg: "",
        potholesBeingEdited: [],
        sortByField: "",
        searchRadius: .0003
      }
    },
    created() {
      potholeService.getAllPotholes().then((response) => {
          this.$store.commit('GET_POTHOLES', response.data)
      });
    },
    methods: {
      clickedPin(pothole) {
        return this.$store.state.currentMarker != {} && this.$store.state.currentMarker.potholeId == pothole.potholeId
      },
      tooClose(potholeToCheck) {
        let potholeToCheckLat = parseFloat(potholeToCheck.lat);
        let potholeToCheckLon = parseFloat(potholeToCheck.lon);
        let checkBool = false;
        this.filteredList.forEach((pothole) => {
          let potholeLat = parseFloat(pothole.lat);
          let potholeLon = parseFloat(pothole.lon);
          if (
            (potholeToCheck.potholeId != pothole.potholeId && potholeToCheckLat >= (potholeLat - this.searchRadius) && potholeToCheckLat <= (potholeLat + this.searchRadius))
            &&
            (potholeToCheck.potholeId != pothole.potholeId && potholeToCheckLon >= (potholeLon - this.searchRadius) && potholeToCheckLon <= (potholeLon + this.searchRadius))
            ) {
            checkBool = true;
          }
        })
        return checkBool;
      },
      setSortField(string) {
        this.sortByField = string;
      },
      addPotholeToEdited(pothole) {
        if (!this.potholesBeingEdited.includes(pothole)) {
          this.potholesBeingEdited.push(pothole);
        }
      },
      clearEdited() {
        potholeService.getAllPotholes().then((response) => {
          this.$store.commit('GET_POTHOLES', response.data)
        });
        this.potholesBeingEdited = [];
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
            potholeService
              .deletePotholeByIdFull(potholeId)
              .then((response) => {
                if (response.status !== 200) {
                  alert("Error deleting potholes..." + this.errorMsg)
                } else {
                  this.$store.commit("REMOVE_POTHOLE_FROM_STORE", potholeId);
                }
              })
              .catch((error) => {
                if (error.response) {
                  this.errorMsg = "Error deleting pothole. Response received was '" + error.response.statusText + "'.";
                } else if (error.request) {
                  this.errorMsg = "Error deleting pothole. Server could not be reached.";
                } else {
                  this.errorMsh = "Error deleting pothole. Request could not be created.";
                }
              })
              this.selectedPotholeIDs = [];
              // this.$router.go();
          })
        }
      },
      updatePothole(pothole) {
        potholeService
          .updatePotholeByIdFull(pothole)
          .then(response => {
          if (response.status === 200) {
            this.$store.commit("UPDATE_POTHOLE_IN_STORE", pothole);
            this.potholesBeingEdited = [];
          }
        })
      }
    }
}
</script>

<style>
.notes-cell {
  width: 100%;
  height: 100%;
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
  width: 100%;
}
th {
  position: sticky;
  top: 0;
  text-transform: uppercase;
  text-align: center;
  vertical-align: bottom;
  color: white;
  padding: 10px;
  background-color: rgb(51, 51, 51);
  overflow: hidden;
}
td {
  padding: 5px;
  font-weight: normal;
  text-align: left;
  height: 50px;
  overflow: hidden;
}
.active {
  box-shadow: inset 0 0 50px rgb(240, 240, 174);
}
.reviewNeeded {
  box-shadow: inset 0 0 100px rgb(212, 50, 50);
}
.active.reviewNeeded {
  box-shadow: inset 0 0 50px rgb(240, 240, 174), inset 0 0 100px rgb(212, 50, 50);
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
  overflow: hidden;
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
input, select {
  width: 90%;
  height: 70%;
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
.cell-id {
  width: 2%;
  text-align: center;
}
.cell-ll {
  width: 3%;
}
.cell-loc {
  width: 8%;
}
.cell-date {
  width: 6%
}
.cell-stat {
  width: 6%
}
.cell-dim {
  width: 4%;
}
.cell-notes{
  width: 10%;
  padding: 8px 8px 10px 0;
}
.cell-edit {
  width: 3%;
  text-align: center;
}
.button-container {
  margin-top: 5px;
  display: flex;
  justify-content: start;
  align-items: center;
  padding: 0 30px;
  column-gap: 30px;
}
.sortButton {
  padding: 0;
  border: none;
  background: none;
  color: white;
}
.close {
  background: rgb(205,0,0);
  background: linear-gradient(90deg, rgba(205,0,0,1) 0%, rgba(255,255,255,0) 40%); 
}
.clickedPin {
  background: rgb(0, 216, 144);
  background: linear-gradient(90deg, rgb(0, 216, 144) 0%, rgba(255,255,255,0) 40%); 
}
#searchRadiusInput {
  width: 55px;
  margin: 0 20px;
}
</style>