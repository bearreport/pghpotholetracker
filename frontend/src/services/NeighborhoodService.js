import axios from 'axios';

export default {
    
    reverseGeocode(lat, long) {
        const string = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + lat + ',' + long + '&key=' + '//your api key here//';
        console.log(string);
        return axios.get(string ,{ transformRequest: [(data, headers) => {
          delete headers.common.Authorization;
       }] })
      
  },

    geocode(address) {
      const fixedAddress = address.replaceAll(' ', '+');
      const string = 'https://maps.googleapis.com/maps/api/geocode/json?address=' + fixedAddress + '&key=' + '//your api key here//';
      console.log(string)
      return axios.get(string ,{ transformRequest: [(data, headers) => {
        delete headers.common.Authorization;
     }] })
    }
}
