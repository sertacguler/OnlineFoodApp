import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import AuthenticationService from "./AuthenticationService";
import {withRouter} from 'react-router';

class HeaderComponent extends Component {
    render() {
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
        return (
            <>
                <nav className="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
                    <div className="container">
                        <b className="navbar-brand">Online Food App</b>
                        <button className="navbar-toggler" type="button" data-toggle="collapse"
                                data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarResponsive">
                            <ul className="navbar-nav ml-auto">
                                <li className="nav-item active">
                                    <Link className="nav-link" to="/">AnaSayfa
                                        <span className="sr-only">(current)</span>
                                    </Link>
                                </li>
                                {isUserLoggedIn && <li className="nav-item">
                                    <Link className="nav-link" to="/cart">Sepetim</Link>
                                </li>}
                                {isUserLoggedIn && <li className="nav-item">
                                    <Link className="nav-link" to="/durum">Sipariş Durumu</Link>
                                </li>}
                                <li className="nav-item">
                                    <Link className="nav-link" to="/contact">İletişim</Link>
                                </li>
                                {!isUserLoggedIn && <li className="nav-item">
                                    <Link className="nav-link" to="/login">Login</Link>
                                </li>}
                                {isUserLoggedIn && <li className="nav-item">
                                    <Link className="nav-link" to="/logout"
                                          onClick={AuthenticationService.logout}>Çıkış</Link>
                                </li>}
                            </ul>
                        </div>
                    </div>
                </nav>
            </>
        );
    }
}

export default withRouter(HeaderComponent);