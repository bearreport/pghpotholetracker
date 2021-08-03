import axios from 'axios';

export default {
    
    reverseGeocode(lat, long) {
        const string = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + lat + ',' + long + '&key=' + 'AIzaSyDy2ycpSCI4uawIkkmeqs4a3FeP0ut8Sf8';
        console.log(string);
        return axios.get(string ,{ transformRequest: [(data, headers) => {
          delete headers.common.Authorization;
       }] })
      
  }
}