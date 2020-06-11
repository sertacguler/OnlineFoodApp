import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import HeaderComponent from "./HeaderComponent";
import HomeComponent from "./HomeComponent";
import LoginComponent from "./LoginComponent";
import LogoutComponent from "./LogoutComponent";
import ContactComponent from "./ContactComponent";
import MyCartComponent from "./MyCartComponent";
import AuthenticatedRoute from "./AuthenticatedRoute";
import DeliveryComponent from "./DeliveryComponent";

class OnlineFoodHomePage extends Component {

    render() {
        return (
            <>
                <Router>
                    <>
                        <HeaderComponent/>
                        <Switch>
                            <Route path="/" exact component={HomeComponent}/>
                            <Route path="/contact" component={ContactComponent}/>
                            <AuthenticatedRoute path="/cart" component={MyCartComponent}/>
                            <AuthenticatedRoute path="/durum" component={DeliveryComponent}/>
                            <Route path="/login" component={LoginComponent}/>
                            <AuthenticatedRoute path="/logout" component={LogoutComponent}/>
                        </Switch>
                    </>
                </Router>
            </>
        );
    }
}

export default OnlineFoodHomePage;