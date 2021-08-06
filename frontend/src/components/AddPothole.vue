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
    <button @click.prevent="getCoords">Add Precise GPS Coordinates to your submission</button>
    </div>
</template>

<script>
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
            }
        }

    },
    methods: {
    getCoords() {
      //collect coordinates
      navigator.geolocation.getCurrentPosition((loc) => {
        this.pothole.lat = loc.coords.latitude;
        this.pothole.lon = loc.coords.longitude;
      })  
    }
    }
}
</script>

<style>

</style>

