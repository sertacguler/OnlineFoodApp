import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import HeaderComponent from "./HeaderComponent";
import AuthenticatedRoute from "./AuthenticatedRoute";
import Login from "./Login";
import MealList from "./MealList";
import FooterComponent from "./FooterComponent";
import LogoutComponent from "./LogoutComponent";
import ErrorComponent from "./ErrorComponent";
import WelcomeComponent from "./WelcomeComponent";
import MealComponent from "./MealComponent";
import DeliveryComponent from "./DeliveryComponent";
import DeliveryDoneComponent from "./DeliveryDoneComponent";

class OnlineFoodApp extends Component {
    render() {
        return (
            <div className="onlineFoodApp">
                <Router>
                    <>
                        <HeaderComponent/>
                        <Switch>
                            <Route path="/" exact component={WelcomeComponent}/>
                            <Route path="/login" component={Login}/>
                            <AuthenticatedRoute path="/welcome/:name" component={WelcomeComponent}/>
                            <AuthenticatedRoute path="/meallist/:code" component={MealComponent}/>
                            <AuthenticatedRoute path="/meallist" component={MealList}/>
                            <AuthenticatedRoute path="/logout" component={LogoutComponent}/>
                            <AuthenticatedRoute path="/deliverydoing" component={DeliveryComponent}/>
                            <AuthenticatedRoute path="/deliverydone" component={DeliveryDoneComponent}/>
                            <Route component={ErrorComponent}/>
                        </Switch>
                        <FooterComponent/>
                    </>
                </Router>
            </div>
        )
    }
}

export default OnlineFoodApp;