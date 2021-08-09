<template>
    <div>
<formulate-form>
    <h2>Add A Pothole</h2>
    <fieldset>
      <legend>Pothole Details</legend>
 
      <formulate-input
        name="address"
        label="Address"
        v-model="pothole.address"
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
       <formulate-input
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
      ></formulate-input>
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

    </fieldset>

    <formulate-input
      type="submit"
      label="Submit"
    ></formulate-input>
  </formulate-form>
    <button @click.prevent="getCoords">Add GPS Coordinates of your current position to your submission</button>
    <button @click.prevent="geocode">Add GPS Coordinates of address to your submission</button>

    </div>
</template>

<script>
import neighborhoodService from '../services/NeighborhoodService'
export default {
    data() {
        return {
            pothole: {
            submitterId: 0,
            lat: "",
            lon: "",
            address: "",
            neighborhood: "",
            dateCreated: new Date().toLocaleString(),
            currentStatus: "uninspected",
            dimensions: "",
            notes: "",
            dateInspected: null,
            dateRepaired: null,
            severity: ""
            },
            geocodeResponse: {},
        }

    },
    methods: {
    getCoords() {
      //collect coordinates
      navigator.geolocation.getCurrentPosition((loc) => {
        this.$store.commit("UPDATE_LAT", loc.coords.latitude);
        this.$store.commit("UPDATE_LNG",loc.coords.longitude);
        this.pothole.lat = loc.coords.latitude;
        this.pothole.lon = loc.coords.longitude;
      })  
    },
    reverseGeocode() {
        neighborhoodService.reverseGeocode(this.pothole.lat, this.pothole.long)
        .then((response => {
        this.pothole.neighborhood = response.data.results[0].address_components[2].long_name;
      }))   
    },
     geocode() {
        neighborhoodService.geocode(this.pothole.address)
        .then((response => {
        this.pothole.neighborhood = response.data.results[0].address_components[2].long_name;
        this.$store.commit("UPDATE_LAT", response.data.results[0].geometry.location.lat);
        this.$store.commit("UPDATE_LNG",response.data.results[0].geometry.location.lng);
        this.pothole.lat = response.data.results[0].geometry.location.lat;
        this.pothole.lon = response.data.results[0].geometry.location.lng;

      }))   
    },
    
    submitHandler() {
        //fire off object + credentials to API
        //handle necessary geocoding to grab coordinates from the address

    }
    }
}
</script>

<style>

</style>

