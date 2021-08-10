import axios from 'axios';

export default {

    getAllPotholes() {
        const string = "/potholes/"
        return axios.get(string);
    },

    updatePotholeByIdFull(pothole) {
        const string = `/potholes/employee/${pothole.potholeId}`;
        return axios.put(string, pothole);
    },

    deletePotholeByIdFull(potholeId) {
        const string = `/potholes/employee/${potholeId}`;
        return axios.delete(string);
    },

    addPothole(pothole) {
        const string = "/potholes";
        return axios.post(string, pothole);
    }

}