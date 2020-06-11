import React, {Component} from 'react';
import DataService from "../api/DataService";

class DeliveryComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            deliveryList: [],
            deliveryFalse: [],
            deliveryTrue: []
        }
    }

    componentDidMount() {
        this.yolda();
        this.geldi();
    };

    yolda = () => {
        DataService.yolda()
            .then(response => {
                this.setState({deliveryFalse: response.data});
            })
    };
    geldi = () => {
        DataService.geldi()
            .then(response => {
                this.setState({deliveryTrue: response.data});
            })
    };

    render() {
        return (
            <div className="container" style={{marginTop: "5%"}}>

                <div className="card">
                    <div className="card-body">
                        <h4 className="card-title text-danger">Sipariş Durumu</h4>
                        <table className="table">
                            <thead>
                            <tr>
                                <th scope="col">Sipariş Durumu</th>
                                <th scope="col">Sipariş Tarihi</th>
                                <th scope="col">Ödenen Fiyat</th>
                            </tr>
                            </thead>
                            <tbody>

                            {this.state.deliveryFalse.map((answer, i) =>
                                <tr key={i}>
                                    <th scope="row" className="border-0">
                                        <h5> Ürün Hazırlanıyor </h5>
                                    </th>
                                    <td className="border-0 align-middle">
                                        <strong>{answer.orderDate}</strong>
                                    </td>
                                    <th scope="row" className="border-0">
                                        <span>{answer.cart.totalPrice} TL</span>
                                    </th>
                                </tr>
                            )
                            }

                            </tbody>
                        </table>
                    </div>
                </div>


                <div className="card" style={{marginTop: "8%"}}>
                    <div className="card-body">
                        <h5 className="card-title text-success">Önceki Siparişler</h5>
                        <table className="table table-sm" style={{marginTop: "2%"}}>
                            <thead>
                            <tr>
                                <th scope="col">Sipariş Durumu</th>
                                <th scope="col">Sipariş Tarihi</th>
                                <th scope="col">Sipariş Teslim Tarihi</th>
                                <th scope="col">Bahşiş</th>
                                <th scope="col">Ödenen Fiyat</th>
                            </tr>
                            </thead>
                            <tbody>

                            {this.state.deliveryTrue.map((answer, i) =>
                                <tr key={i}>
                                    <th scope="row" className="border-0">
                                        <h6> Teslim Edildi </h6>
                                    </th>
                                    <td className="border-0 align-middle">
                                        <strong>{answer.orderDate}</strong>
                                    </td>
                                    <td className="border-0 align-middle">
                                        <strong>{answer.deliveredDate}</strong>
                                    </td>
                                    <th scope="row" className="border-0">
                                        <h6> {answer.tip === null ? "Belirtilmedi" : answer.tip + "TL"} </h6>
                                    </th>
                                    <th scope="row" className="border-0">
                                        <span>{answer.cart.totalPrice} TL</span>
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