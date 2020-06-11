import React, {Component} from 'react';
import Modal from "react-modal";
import MealDataService from "../../api/MealDataService";
import AuthenticationService from "./AuthenticationService";

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

class DeliveryComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            deliveryList: [],
            deliveryFalse: [],
            deliveryTrue: [],
            modalIsOpen: false,
            carts: [],
            id: ""
        }
        AuthenticationService.setupAxiosInterceptorsForSavedToken();
    }

    componentDidMount() {
        this.doing();
    };

    shouldComponentUpdate(nextProps, nextState) {
        return true;
    }

    doing = () => {
        MealDataService.doing()
            .then(response => {
                this.setState({deliveryFalse: response.data});
            })
    };

    orderDone = (event) => {
        MealDataService.OrderDone(event)
            .then(response => {
                this.doing();
            })
    };

    openModal = (event) => {
        this.setState({modalIsOpen: true});
        this.state.deliveryFalse.map(meal => (meal.cart.cart_id === event) && this.setState({
                carts: meal.cart.meals,
                id: meal.id
            })
        );
    };

    afterOpenModal = () => {
        // references are now sync'd and can be accessed.
        //this.subtitle.style.color = '#f00';
    };

    closeModal = () => {
        this.setState({modalIsOpen: false});
    };

    render() {
        return (
            <div className="container" style={{marginTop: "5%"}}>
                <b>Bu sayfayı kim görüntülüyor admin mi aşçı mı admin ise buton disabled olsun </b>
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onAfterOpen={this.afterOpenModal}
                    onRequestClose={this.closeModal}
                    style={customStyles}
                    contentLabel="Example Modal">

                    <p style={{align: 'center'}}>Sipariş No <b> : {this.state.id}</b></p>
                    <table className="table table-bordered">
                        <thead>
                        <tr>
                            <th scope="col"><b>Meal Code</b></th>
                            <th scope="col"><b>Meal Name</b></th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.carts.map(meal => (
                            <tr key={meal.code}>
                                <th>{meal.code}</th>
                                <th>{meal.name}</th>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                </Modal>

                <div className="card">
                    <div className="card-body">
                        <h4 className="card-title text-danger"> Hazırlanan Sipariş Durumu </h4>
                        <table className="table">
                            <thead>
                            <tr>
                                <th scope="col">Sipariş</th>
                                <th scope="col">Sipariş Durumu</th>
                                <th scope="col">Sipariş Tarihi</th>
                                <th scope="col">Sipariş Detayları</th>
                                <th scope="col">Sipariş Hazır</th>
                            </tr>
                            </thead>
                            <tbody>

                            {this.state.deliveryFalse.map((answer, i) =>
                                <tr key={i}>
                                    <th scope="row" className="border-0">
                                        <h6>{answer.id}</h6>
                                    </th>
                                    <th scope="row" className="border-0">
                                        <h6 style={{color: 'red'}}> ÜRÜN HAZIRLANIYOR </h6>
                                    </th>
                                    <td className="border-0 align-middle">
                                        <strong>{answer.orderDate}</strong>
                                    </td>
                                    <th scope="row" className="border-0">
                                        <button className="btn btn-warning"
                                                onClick={() => this.openModal(answer.cart.cart_id)}>Detay
                                        </button>
                                    </th>
                                    <th scope="row" className="border-0">
                                        <button className="btn btn-success"
                                                onClick={() => this.orderDone(answer.id)}>Sipariş Hazır
                                        </button>
                                    </th>
                                </tr>
                            )
                            }

                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        );
    }
}

export default DeliveryComponent;