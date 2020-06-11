import axios from "axios";

class DataService {

    executeContactBeanService() {
        console.log('execute started (contact)');
        return axios.get('http://localhost:8034/contact');
    }

    executeFindAllBeanService() {
        console.log('execute started (find all)');
        return axios.get('http://localhost:8034/meal/findAllByMeal');
    }

    executeFindCartBeanService() {
        console.log('execute started (find all id)');
        return axios.get('http://localhost:8034/meal/findMealByCustomer_Id');
    }

    addtoCart(mealCode) {
        console.log('execute started ' + mealCode + '(add to cart)');
        return axios.post(`http://localhost:8034/meal/add`, mealCode);
    };

    deleteFromCart(meal) {
        console.log('execute started');
        return axios.post(`http://localhost:8034/meal/DeleteByMealCodeId`, meal);
    };

    odeme() {
        console.log('execute started');
        return axios.get('http://localhost:8034/meal/odeme');
    };

    yolda() {
        console.log('execute started (Yolda)');
        return axios.get('http://localhost:8034/meal/findYolda');
    };

    geldi() {
        console.log('execute started (Yolda)');
        return axios.get('http://localhost:8034/meal/findGeldi');
    };

    campaigns() {
        console.log('execute started (campaigns)');
        return axios.get('http://localhost:8034/meal/campaigns');
    };

}

export default new DataService();