import axios from 'axios';

class MealDataService {
    retrieveAllMeals() {
        console.log('execute started');
        return axios.get('http://localhost:8034/meals/all');
    };

    deleteMeal(code) {
        console.log('execute started');
        return axios.delete(`http://localhost:8034/meals/${code}`);
    };

    retrieveMeal(code) {
        console.log('execute started');
        return axios.get(`http://localhost:8034/meals/${code}`);
    };

    updateMeal(code, meal) {
        console.log('execute started');
        return axios.put(`http://localhost:8034/meals/${code}`, meal);
    };

    createMeal(meal) {
        console.log('execute started');
        return axios.post(`http://localhost:8034/meals/create`, meal);
    };


    doing() {
        console.log('execute started (doing)');
        return axios.get('http://localhost:8034/meals/doing');
    };

    done() {
        console.log('execute started (done)');
        return axios.get('http://localhost:8034/meals/done');
    };

    OrderDone(id) {
        console.log('execute started (Order Done)');
        return axios.get(`http://localhost:8034/meals/orderDone/${id}`);
    };

    DeliveryDone(id, tip) {
        console.log('execute started (Delivery Done)');
        return axios.get(`http://localhost:8034/meals/deliveryDone/${id}/${tip}`);
    };
}

export default new MealDataService();