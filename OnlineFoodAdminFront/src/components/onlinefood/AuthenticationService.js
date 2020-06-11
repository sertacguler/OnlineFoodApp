import axios from 'axios';
import Cookies from 'universal-cookie';

class AuthenticationService {

    executeBasicAuthentication(username, password) {
        return axios.get('http://localhost:8034/basicauthentication', {headers: {authorization: this.createBasicAuthentication(username, password)}});
    };

    executeJwtAuthentication(username, password) {
        return axios.post('http://localhost:8034/admin/authenticate', {username, password});
    };

    createBasicAuthentication(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password);
    };

    registerSuccessfullLogin(username, password) {
        sessionStorage.setItem('authenticatedUser', username);
        this.setupAxiosInterceptors(username, password);
    }

    refreshJwtAuthentication(token) {
        //this.setupAxiosInterceptorsJwt(token);
        //console.log(token);
        return axios.post('http://localhost:8034/admin/refresh', token);
    };

    registerSuccessfullLoginJwt(username, token, remember) {
        sessionStorage.setItem('authenticatedUser', username);
        localStorage.setItem('token', token);
        if (remember) {
            const cookies = new Cookies();
            cookies.set('userN', username, {path: '/'});
            cookies.set('rememberlogin', token, {path: '/'});
        }
        this.setupAxiosInterceptorsJwt(token);
    }

    logout() {
        localStorage.removeItem('token');
        sessionStorage.removeItem('authenticatedUser');
        sessionStorage.removeItem('role_Name');
        const cookies = new Cookies();
        cookies.remove('userN');
        cookies.remove('rememberlogin');
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem('authenticatedUser');
        if (user === null) return false;
        return true;
    }

    setupAxiosInterceptors(username, password) {
        axios.interceptors.request.use((config) => {
            if (this.isUserLoggedIn())
                config.headers.authorization = this.createBasicAuthentication(username, password);
            return config;
        })
    }

    setupAxiosInterceptorsJwt = (token) => {
        axios.interceptors.request.use((config) => {
            if (this.isUserLoggedIn())
                config.headers.authorization = this.createJwtAuthentication(token);
            return config;
        })
    }

    setupAxiosInterceptorsForSavedToken() {
        if (this.isUserLoggedIn())
            this.setupAxiosInterceptorsJwt(localStorage.getItem('token'));
    }

    createJwtAuthentication(token) {
        return 'Bearer ' + token;
    };

    Role(name) {
        console.log('execute started (Role)');
        return axios.get(`http://localhost:8034/initial/roleName/${name}`);
    };

    registerRoleJwt(roleNmae) {
        sessionStorage.setItem('role_Name', roleNmae);
    };

    isUser_Role() {
        console.log("service" + sessionStorage.getItem('role_Name'));
        return sessionStorage.getItem('role_Name');
    }

}

export default new AuthenticationService()