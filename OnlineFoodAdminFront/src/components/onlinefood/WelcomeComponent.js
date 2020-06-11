import {Link, withRouter} from "react-router-dom";
import React, {Component} from 'react';
import HelloWorldService from "../../api/HelloWorldService";
import AuthenticationService from "./AuthenticationService";
import Cookies from 'universal-cookie';

class WelcomeComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            welcomeMessage: null,
            username: ""
        }
    }

    componentDidMount() {
        this.tokenCheck();
    }

    tokenCheck = () => {

        const cookies = new Cookies();
        if (cookies.get('rememberlogin') != null) {
            AuthenticationService.refreshJwtAuthentication(cookies.get('rememberlogin')) // token geçerlimi kontrolu
                .then(response => {
                    this.setState({username: cookies.get('userN')});
                    AuthenticationService.registerSuccessfullLoginJwt(cookies.get('userN'), cookies.get('rememberlogin'));
                    AuthenticationService.Role(this.state.username)
                        .then(response2 => {
                            AuthenticationService.registerRoleJwt(response2.data)
                        }).catch(error => {
                        this.setState({isLoggedIn: false});
                        console.log("FAILED");
                    });
                    this.props.history.push(`/welcome/${this.state.username}`);
                })
                .catch(error => { // token geçerli değilse login sayfasına git
                    console.log("FAILED");
                    AuthenticationService.logout();
                    this.props.history.push(`/login`);
                });
        }

    };

    render() {
        return (
            <>
                <h1>Welcome</h1>
                <div className="container">
                    Welcome {this.props.match.params.name} to Online Food App , restaurant meal list is <Link
                    to="/meallist">here</Link>
                </div>

                <div className="container">
                    Click here to get a customized message from backend!
                    <button onClick={this.retrieveWelcomeMessage} className="btn btn-success">Get Welcome
                        Message</button>
                </div>

                <div className="container">
                    {this.state.welcomeMessage}
                </div>
            </>
        );
    }

    retrieveWelcomeMessage = () => {
        //HelloWorldService.executeHelloWorldService().then(response => this.handleSuccessfullResponse(response));
        //HelloWorldService.executeHelloWorldBeanService().then(response => this.handleSuccessfullResponse(response));
        HelloWorldService.executeHelloWorldBeanServicePathVariable(this.props.match.params.name)
            .then(response => this.handleSuccessfullResponse(response))
            .catch(error => this.handleError(error));
    };

    handleSuccessfullResponse = (response) => {
        console.log(response);
        //this.setState({welcomeMessage: response.data});
        this.setState({welcomeMessage: response.data.message});
    };

    handleError = (error) => {
        console.log(error.response);

        let errorMessage = '';
        if (error.message)
            errorMessage += error.message;

        if (error.response && error.response.data)
            errorMessage += error.response.data.message;

        this.setState({welcomeMessage: errorMessage})
    };
}

export default withRouter(WelcomeComponent);