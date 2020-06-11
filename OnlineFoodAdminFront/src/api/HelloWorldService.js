import axios from "axios";

class HelloWorldService {
    executeHelloWorldService() {
        console.log('execute started');
        return axios.get('http://localhost:8034/initial/helloworld');
    }

    executeHelloWorldBeanService() {
        console.log('execute started');
        return axios.get('http://localhost:8034/initial/helloworldbean');
    }

    executeHelloWorldBeanServicePathVariable(name) {
        console.log('execute started');
        return axios.get(`http://localhost:8034/initial/helloworldbean/${name}`);
    }
}

export default new HelloWorldService()