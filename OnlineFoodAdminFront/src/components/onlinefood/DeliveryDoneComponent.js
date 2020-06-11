import React, {Component} from 'react';
import MealDataService from "../../api/MealDataService";
import AuthenticationService from "./AuthenticationService";

class DeliveryComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            deliveryList: [],
            deliveryFalse: [],
            deliveryTrue: [],
            carts: [],
            tip: 0,
            id: ""
        };
        AuthenticationService.setupAxiosInterceptorsForSavedToken();
    }

    componentDidMount() {
        this.done();
    };

    done = () => {
        MealDataService.done()
            .then(response => {
                this.setState({deliveryTrue: response.data});
                console.log(response.data)
            })
    };

    handleChange = (event) => {
        //console.log(this.state);
        this.setState({[event.target.name]: event.target.value});
    };

    deliveryDone = (event, tip) => {
        MealDataService.DeliveryDone(event, tip)
            .then(response => {
                this.doing();
            })
    };

    doing = () => {
        MealDataService.done()
            .then(response => {
                this.setState({deliveryTrue: response.data});
                console.log(response.data)
            })
    };


    render() {
        return (
            <div className="container" style={{marginTop: "1%"}}>
                <b>Bu sayfayı kim görüntülüyor admin mi kargocu mu admin ise buton ve input disabled olsun </b>

                <div className="card">
                    <div className="card-body">
                        <h5 className="card-title text-success">Teslimata Hazır Siparişler</h5>
                        <table className="table table-sm">
                            <thead>
                            <tr>
                                <th scope="col">Sipariş Durumu</th>
                                <th scope="col">Sipariş Veren</th>
                                <th scope="col">Sipariş Adresi</th>
                                <th scope="col">Sipariş Tarihi</th>
                                <th scope="col">Sipariş Fiyatı</th>
                                <th scope="col">Bahşiş</th>
                                <th scope="col">Teslim Edildi</th>
                            </tr>
                            </thead>
                            <tbody>

                            {this.state.deliveryTrue.map((answer, i) => (answer.deliveredDate === null) ?
                                <tr key={i}>
                                    <th scope="row" className="border-0">
                                        <h6 style={{color: "red"}}>TESLİMATA HAZIR</h6>
                                    </th>
                                    <td className="border-0 align-middle">
                                        <strong>{answer.cart.customer.name}</strong>
                                    </td>
                                    <td className="border-0 align-middle">
                                        <strong>{answer.cart.customer.address}</strong>
                                    </td>
                                    <td className="border-0 align-middle">
                                        <strong>{answer.orderDate}</strong>
                                    </td>
                                    <th scope="row" className="border-0">
                                        <span>{answer.cart.totalPrice} TL</span>
                                    </th>
                                    <th scope="row" className="border-0">
                                        <input type="text" name="tip" value={this.state.tip}
                                               style={{textAlign: 'center'}}
                                               onChange={this.handleChange}/>
                                    </th>
                                    <th scope="row" className="border-0">
                                        <button className="btn btn-success"
                                                onClick={() => this.deliveryDone(answer.id, this.state.tip)}>Sipariş
                                            Teslim Edildi
                                        </button>
                                    </th>
                                </tr> : <tr></tr>
                            )
                            }

                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="card" style={{marginTop: "3%"}}>
                    <h5 className="card-title text-success">Teslimata Hazır Siparişler</h5>
                    <table className="table table-sm" style={{marginTop: "1%"}}>
                        <thead>
                        <tr>
                            <th scope="col">Sipariş Durumu</th>
                            <th scope="col">Sipariş Veren</th>
                            <th scope="col">Sipariş Adresi</th>
                            <th scope="col">Sipariş Tarihi</th>
                            <th scope="col">Sipariş Teslim Tarihi</th>
                            <th scope="col">Bahşiş</th>
                            <th scope="col">Ödenen Fiyat</th>
                        </tr>
                        </thead>
                        <tbody>

                        {this.state.deliveryTrue.map((answer, i) => (answer.deliveredDate != null) ?
                            <tr key={i}>
                                <th scope="row" className="border-0">
                                    <h6 style={{color: (answer.deliveredDate != null) ? "green" : "red"}}> {(answer.deliveredDate != null) ? "Teslim Edildi" : "TESLİMATA HAZIR"}</h6>
                                </th>
                                <th scope="row" className="border-0">
                                    <h6>{answer.cart.customer.name}</h6>
                                </th>
                                <td className="border-0 align-middle">
                                    <strong>{answer.cart.customer.address}</strong>
                                </td>
                                <td className="border-0 align-middle">
                                    <strong>{answer.orderDate}</strong>
                                </td>
                                <td className="border-0 align-middle">
                                    <strong>{(answer.deliveredDate != null) ? answer.deliveredDate : " - - "}</strong>
                                </td>
                                <th scope="row" className="border-0">
                                    <h6> {answer.tip === null ? "Belirtilmedi" : answer.tip + "TL"} </h6>
                                </th>
                                <th scope="row" className="border-0">
                                    <span>{answer.cart.totalPrice} TL</span>
                                </th>
                            </tr> : <tr></tr>
                        )
                        }

                        </tbody>
                    </table>
                </div>


            </div>
        );
    }
}

export default DeliveryComponent;