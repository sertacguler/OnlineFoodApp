import React, {Component} from 'react';
import {Slide} from 'react-slideshow-image';
import DataService from "../api/DataService";
import Modal from "react-modal";

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

class Top extends Component {
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
        this.refreshMeals();
    }

    refreshMeals = () => {
        DataService.campaigns()
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
        const properties = {
            duration: 5000,
            transitionDuration: 500,
            infinite: true,
            indicators: true,
            arrows: true,
            onChange: (oldIndex, newIndex) => {
                console.log(`slide transition from ${oldIndex} to ${newIndex}`);
            }
        };

        return (

            <div className="slide-container">
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

                <br/>
                <br/>
                <br/>
                <Slide {...properties}>

                    {this.state.meals.map(meal =>
                        <div className="each-slide" key={meal.code}>
                            <div style={{'backgroundImage': `url(${meal.photo})`}}
                                 onClick={() => this.openModal(meal.code)}>
                                <span> Sadece Bugüne Özel {meal.name} <b>{meal.price} TL</b></span>
                            </div>
                        </div>
                    )}

                </Slide>
                <br/>
            </div>

        );
    }
}

export default Top;
