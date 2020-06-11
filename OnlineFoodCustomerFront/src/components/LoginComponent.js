import React, {Component} from 'react';
import AuthenticationService from "./AuthenticationService";

import Cookies from 'universal-cookie';

class LoginComponent extends Component {
    constructor() {
        super();
        this.state = {
            username: "sertac",
            password: "123",
            remember: false,
            isLoggedIn: null
        };
    }

    componentDidMount() {
    };

    render() {

        return (
            <div className="container">
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <div className="row">
                    <div className="col-md-4"></div>
                    <div className="col-md-8">
                        <div className="col-md-4 col-md-offset-4">
                            <div className="panel panel-default">
                                <div className="panel-heading">
                                    <h3 className="panel-title">Please Sign In</h3>
                                </div>

                                <div className="panel-body">
                                    {this.state.isLoggedIn && <div>Başarılı giriş!</div>}
                                    {this.state.isLoggedIn != null && !this.state.isLoggedIn &&
                                    <div className="alert alert-warning">Kullanıcı veya şifre hatalı!</div>}
                                    <div className="form-group">
                                        <input type="text" name="username"
                                               className="form-control"
                                               placeholder="Username"
                                               value={this.state.username}
                                               onChange={this.handleChange}/>
                                    </div>
                                    <div className="form-group">
                                        <input type="text" name="password"
                                               className="form-control"
                                               placeholder="Password"
                                               value={this.state.password}
                                               onChange={this.handleChange}/>
                                    </div>
                                    <div className="form-group">
                                        <input type="checkbox" name="remember"
                                               value={this.state.remember}
                                               onChange={this.handleChanges}/> Beni
                                        Hatırla
                                    </div>
                                    <div className="form-group">
                                        <button className="btn btn-lg btn-block btn-success"
                                                onClick={this.loginClicked}>Login
                                        </button>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div className="col-md-4"></div>
                </div>
            </div>
        );
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };
    handleChanges = () => {
        this.setState({remember: true});
    };

    loginClicked = (event) => {
        AuthenticationService.executeJwtAuthentication(this.state.username, this.state.password)
            .then(response => {
                AuthenticationService.registerSuccessfullLoginJwt(this.state.username, response.data.token, this.state.remember);
                this.props.history.push(`/`);
            })
            .catch(error => {
                this.setState({isLoggedIn: false});
                console.log("FAILED");
            })
    }

}

export default LoginComponent;