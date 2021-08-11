<template>
    <div id="addFormContainer">
<formulate-form>
    <h2>Add A Pothole</h2>
    <fieldset>
      <legend>Pothole Details</legend>
 
      <formulate-input
        name="address"
        label="Address"
        v-model="pothole.addr"
        validation="required"
      ></formulate-input>
      <formulate-input
        type="select"
        name="dimensions"
        label="Pothole Dimensions"
        validation="required"
        v-model="pothole.dimensions"
        :options="[
            { value: '0-1ft', label: '0-1ft'},
            { value: '1-2ft', label: '1-2ft'},
            { value: '2+ft', label: '2+ft'},
            { value: 'sinkhole', label: 'sinkhole'}
        ]"
      ></formulate-input>
     <!--  <formulate-input
        type="select"
        name="severity"
        label="Pothole Severity"
        validation="required"
        v-model="pothole.severity"
        :options="[
            { value: 'low', label: 'low'},
            { value: 'medium', label: 'medium'},
            { value: 'high', label: 'high'},
            { value: 'extreme', label: 'extreme'}
        ]" 
      ></formulate-input> -->
      <formulate-input
        type="text"
        name="lat"
        label= latitude
        v-model="pothole.lat" readonly>
      </formulate-input>
        <formulate-input
        type="text"
        name="lng"
        label= longitude
        v-model="pothole.lon" readonly>
      </formulate-input>
      <formulate-input 
        type="textarea"
        label="Notes"
        validation="max:250,length"
        help="Keep it under 250 characters."
        v-model="pothole.notes"
      ></formulate-input>
      <button class="addButton" @click="submitHandler()"
      type="submit"
      label="Submit"
    >Submit!</button>
    </fieldset>


  </formulate-form>

    <br>
    <div id="addButtonContainer">
       <button class="addButton" @click.prevent="getCoords">Add GPS Coordinates of current position</button>
       <button class="addButton" @click.prevent="geocode">Extract coordinates from address</button>
    </div>
    </div>
</template>

<script>
import neighborhoodService from '../services/NeighborhoodService'
import potholeService from '../services/PotholeService'

export default {
    data() {
        return {
            pothole: {
            lat: "",
            lon: "",
            addr: "",
            neighborhood: "",
            dimensions: "",
            notes: "",
            },
            geocodeResponse: {},
        }

    },
    methods: {
    getCoords() {
      //collect coordinates
      navigator.geolocation.getCurrentPosition((loc) => {
        this.pothole.lat = loc.coords.latitude;
        this.pothole.lon = loc.coords.longitude;
        this.reverseGeocode(this.pothole.lat, this.pothole.lon);
      })  
    },
    reverseGeocode() {
        neighborhoodService.reverseGeocode(this.pothole.lat, this.pothole.lon)
        .then((response => {
        this.pothole.neighborhood = response.data.results[0].address_components[2].long_name;
      }))   
    },
     geocode() {
        neighborhoodService.geocode(this.pothole.address)
        .then((response => {
        this.pothole.neighborhood = response.data.results[0].address_components[2].long_name;
        this.pothole.lat = response.data.results[0].geometry.location.lat;
        this.pothole.lon = response.data.results[0].geometry.location.lng;
        }))   
    },
    
    submitHandler() {
        potholeService.addPothole(this.pothole)
        .then((response => {
          if (response.status != 200) {
            alert ("Error adding pothole!")
          } else {
            alert("Pothole added!");
          }
        }))

    }
    }
}
</script>

<style>
#addButtonContainer {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-content: center;
}
.addButton {
  /* height: 40px; */
  padding: 5px;
  margin: 5px;
  width: 50%;
  border: none;
  background-color: #e7b600;
  border-radius: 5px;
  font-weight: bold;
  white-space: normal;
  font-size: 15px;
}
#addFormContainer {
  background-color: #025252;
  flex-grow: 2;
  max-width: 350px;
  border-radius: 15px;
  color: white;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  text-align: center;
  padding: 0 12px;
  font-size: 20px;
}
</style>

