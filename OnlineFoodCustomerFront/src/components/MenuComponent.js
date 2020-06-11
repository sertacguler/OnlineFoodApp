import React, {Component} from 'react';
import DataService from "../api/DataService";
import '../App.css';
import './xx.css';
import AuthenticationService from "./AuthenticationService";
import Modal from 'react-modal';
import Cookies from 'universal-cookie';
import {withRouter} from 'react-router-dom';

const customStyles = {
    content: {
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)'
    }
};

class MenuComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            meals: [],
            modalIsOpen: false,
            mealCode: "",
            mealName: "",
            mealDetail: "",
            mealPrice: "",
            mealPhoto: "",
            meal: []
        }
    }

    componentDidMount() {
        this.tokenCheck();
        this.refreshMeals();
    }

    tokenCheck = () => {

        const cookies = new Cookies();
        if (cookies.get('rememberlogin') != null) {
            AuthenticationService.refreshJwtAuthentication(cookies.get('rememberlogin')) // token geçerlimi kontrolu
                .then(response => {
                    AuthenticationService.registerSuccessfullLoginJwt(cookies.get('userN'), cookies.get('rememberlogin'))
                    this.props.history.push(`/`);
                })
                .catch(error => { // token geçerli değilse login sayfasına git
                    console.log("FAILED");
                    AuthenticationService.logout();
                    this.props.history.push(`/login`);
                });
            // this.props.history.push(`/`);
        }

    };

    refreshMeals = () => {
        DataService.executeFindAllBeanService()
            .then(response => {
                this.setState({meals: response.data})
            })
    };

    openModal = (event) => {
        console.log(event);
        this.setState({mealCode: event, modalIsOpen: true});
        this.state.meals.map(meal => (meal.code === event) && this.setState({
            mealName: meal.name,
            mealDetail: meal.detail,
            mealPrice: meal.price,
            mealPhoto: meal.photo
        }));
        console.log(this.state.mealName);
    };

    afterOpenModal = () => {
        // references are now sync'd and can be accessed.
        this.subtitle.style.color = '#f00';
    };

    closeModal = () => {
        this.setState({modalIsOpen: false});
    };

    render() {
        return (
            <div className="row text-center">

                <Modal
                    isOpen={this.state.modalIsOpen}
                    onAfterOpen={this.afterOpenModal}
                    onRequestClose={this.closeModal}
                    style={customStyles}
                    contentLabel="Example Modal">

                    <button style={{position: 'absolute', zIndex: '2', right: '10px'}}
                            onClick={() => this.closeModal()}>X
                    </button>
                    <img style={{zIndex: '-1'}} src={this.state.mealPhoto} alt=""
                         className="card-img-top imgss"/>
                    <div style={{float: 'left', position: 'absolute'}}>
                        <h2 style={{margin: '10px'}}
                            ref={subtitle => this.subtitle = subtitle}>{this.state.mealName}</h2>
                        <div style={{marginTop: '10px', marginLeft: '10px'}}>{this.state.mealDetail}</div>
                    </div>
                    <div style={{float: 'right'}}>
                        <h2 style={{
                            marginTop: '10px',
                            border: '2px solid orange',
                            borderRadius: '10px',
                            fontSize: '30px',
                            textAlign: 'center'
                        }}>{this.state.mealPrice} TL</h2>
                        <button className="btn btn-success btn-lg"
                                style={{float: 'right', marginTop: '10px'}}
                                onClick={() => this.handleAddToCart(this.state.mealCode)}>Sepete Ekle
                        </button>
                    </div>

                </Modal>

                {this.state.meals.map(meal =>

                    <div className="col-lg-4 col-md-6 mb-4" key={meal.code}>

                        <div className="card h-100">

                            <img alt="" className="card-img-top img-fluid rounded imgs" src={meal.photo}/>
                            <div className="card-body">
                                <h4 className="card-title"
                                    onClick={() => this.openModal(meal.code)}>{meal.name}</h4>
                                <p className="card-text">{meal.detail}</p>
                            </div>

                            <div className="card-footer">
                                <div className="row">
                                    <div className="col"><b className="h4">{meal.price.toString()}</b> <small
                                        className="h6">TL</small></div>
                                    <div className="col">
                                        <button className="btn btn-success btn-lg"
                                                onClick={() => this.handleAddToCart(meal.code)}>Sepete Ekle
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                )
                }
            </div>
        );
    }

    handleAddToCart = (event) => {
        if (AuthenticationService.isUserLoggedIn()) {
            DataService.addtoCart(event)
                .then(response => {
                    //this.props.history.push(`/cart`);
                })
        } else {
            //this.props.history.push('/cart');
        }
    }

}

export default withRouter(MenuComponent);