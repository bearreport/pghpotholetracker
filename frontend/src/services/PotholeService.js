import axios from 'axios';

export default {

    getAllPotholes() {
        const string = "http://localhost:8080/potholes/"
        return axios.get(string);
    }

}