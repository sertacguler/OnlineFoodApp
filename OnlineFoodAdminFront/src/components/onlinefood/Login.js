import React, {Component} from 'react';
import AuthenticationService from "./AuthenticationService";

class Login extends Component {
    constructor() {
        super();
        this.state = {
            username: "mesutcan",
            password: "123",
            remember: false,
            isLoggedIn: null
        };
    }

    componentDidMount() {
    };

    render() {
        return (
            <div className="login">
                <h1>Login</h1>
                <div className="container">
                    {this.state.isLoggedIn && <div>Başarılı giriş!</div>}
                    {this.state.isLoggedIn != null && !this.state.isLoggedIn &&
                    <div className="alert alert-warning">Kullanıcı veya şifre hatalı!</div>}
                    User Name : <input type="text" name="username" value={this.state.username}
                                       onChange={this.handleChange}/>
                    Password : <input type="text" name="password" value={this.state.password}
                                      onChange={this.handleChange}/>
                    <div className="form-group">
                        <input type="checkbox" name="remember"
                               value={this.state.remember}
                               onChange={this.handleChanges}/> Beni
                        Hatırla
                    </div>
                    <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                </div>
            </div>
        )
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
                AuthenticationService.Role(this.state.username)
                    .then(response2 => {
                        AuthenticationService.registerRoleJwt(response2.data)
                    }).catch(error => {
                    this.setState({isLoggedIn: false});
                    console.log("FAILED");
                })
                this.props.history.push(`/welcome/${this.state.username}`);
            })
            .catch(error => {
                this.setState({isLoggedIn: false});
                console.log("FAILED");
            })
    }
}

export default Login;