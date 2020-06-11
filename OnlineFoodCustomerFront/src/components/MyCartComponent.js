import React, {Component} from 'react';
import {Link} from "react-router-dom";
import DataService from "../api/DataService";

class MyCartComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cart: [],
            price: 0
        }
    }

    componentDidMount() {
        this.refreshMeals();

    }

    refreshMeals = () => {
        DataService.executeFindCartBeanService()
            .then(response => {
                this.setState({cart: response.data.meals.concat(), price: response.data.totalPrice})
            })
    };

    deleteMeal = (event) => {
        console.log(event);
        DataService.deleteFromCart(event)
            .then(response => {
                this.setState({cart: response.data.meals.concat(), price: response.data.totalPrice});
                this.refreshMeals();
            })
    };

    odeme = () => {
        console.log("Ödeme");
        DataService.odeme()
            .then(response => {
                this.props.history.push(`/durum`);
            })
    };

    sumPrice = () => {
        return (Number(this.state.price) + 10.00);
    };

    render() {
        return (
            <div className="container">
                <br/>
                <br/>
                <br/>
                <br/>
                <div className="px-4 px-lg-0">
                    <div className="pb-5">
                        <div className="container">
                            <div className="row">
                                <div className="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">

                                    <div className="table-responsive">
                                        <table className="table">
                                            <thead>
                                            <tr>
                                                <th scope="col" className="border-0 bg-light">
                                                    <div className="p-2 px-3 text-uppercase"></div>
                                                </th>
                                                <th scope="col" className="border-0 bg-light">
                                                    <div className="p-2 px-3 text-uppercase">Ürün</div>
                                                </th>
                                                <th scope="col" className="border-0 bg-light">
                                                    <div className="py-2 text-uppercase">Fiyat</div>
                                                </th>
                                                <th scope="col" className="border-0 bg-light">
                                                    <div className="py-2 text-uppercase">Sil</div>
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            {this.state.cart.map((meal, i) => <tr key={i}>
                                                <th scope="row" className="border-0">
                                                    <img src={meal.photo} alt="" width="70"
                                                         className="img-fluid rounded shadow-sm imgs_Cart"/>
                                                </th>
                                                <th scope="row" className="border-0">
                                                    <div className="p-2">
                                                        <div className="ml-3 d-inline-block align-middle">
                                                            <h5>{meal.name}</h5>
                                                        </div>
                                                    </div>
                                                </th>
                                                <td className="border-0 align-middle">
                                                    <strong>{meal.price.toString()} TL</strong></td>
                                                <td className="border-0 align-middle">
                                                    <button className="text-white btn btn-danger"
                                                            onClick={() => this.deleteMeal(meal.code)}>Sepetten Çıkar
                                                    </button>
                                                </td>
                                            </tr>)}

                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                            </div>

                            <Link className="btn btn-success" to="/"> Alışverişe Devam Et</Link>

                            <div className="row py-5 p-4 bg-white rounded shadow-sm">
                                <div className="col-lg-6">
                                    <div
                                        className="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">Satıcıya
                                        ileti bırakın
                                    </div>
                                    <div className="p-4">
                                        <p className="font-italic mb-4">Satıcı için bir bilginiz varsa, bunları
                                            aşağıdaki kutuya bırakabilirsiniz.</p>
                                        <textarea name="" cols="30" rows="2" className="form-control"></textarea>
                                    </div>
                                </div>
                                <div className="col-lg-6">
                                    <div
                                        className="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">Sipariş
                                        Detayı
                                    </div>
                                    <div className="p-4">
                                        <p className="font-italic mb-4">Nakliye ve ek masraflar girdiğiniz değerlere
                                            göre hesaplanır.</p>
                                        <ul className="list-unstyled mb-4">
                                            <li className="d-flex justify-content-between py-3 border-bottom">
                                                <strong
                                                    className="text-muted">Sipariş
                                                    Tutarı </strong><strong>{this.state.price === "0.0" || this.state.price === null ? "0" : this.state.price} TL</strong>
                                            </li>
                                            <li className="d-flex justify-content-between py-3 border-bottom">
                                                <strong
                                                    className="text-muted">Teslimat</strong><strong>{this.state.price === "0.0" || this.state.price === null ? "0" : "10.00"} TL</strong>
                                            </li>
                                            <li className="d-flex justify-content-between py-3 border-bottom">
                                                <strong
                                                    className="text-muted">Toplam</strong>
                                                <h5 className="font-weight-bold">{this.sumPrice() === 10 ? "0" : this.sumPrice()} TL</h5>
                                            </li>
                                        </ul>
                                        <button className="btn btn-dark rounded-pill py-2 btn-block"
                                                disabled={this.state.price === "0.0" || this.state.price === "0" || this.state.price === null}
                                                onClick={() => this.odeme()}>
                                            {this.state.price === "0.0" || this.state.price === "0" || this.state.price === null ? "Sepetiniz Boş" : "Ödeme işlemine geç"}
                                        </button>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

        );
    }
}

export default MyCartComponent;