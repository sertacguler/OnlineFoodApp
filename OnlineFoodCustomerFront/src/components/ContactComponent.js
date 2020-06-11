import React, {Component} from 'react';
import DataService from "../api/DataService";

class ContactComponent extends Component {

    constructor() {
        super();
        this.state = {
            contact: []
        }
    }

    componentDidMount() {
        this.contacts();
    };

    contacts = () => {
        DataService.executeContactBeanService()
            .then(response => {
                this.setState({contact: response.data})
            })
    };


    render() {
        return (
            <div className="container   ">
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>

                <div className="row text-center col-lg-12">
                    <div className="card col-md-6">

                        <div className="card-header pt-3">
                            <div className="row d-flex justify-content-center">
                                <h3 className="white-text mb-3 pt-3 font-weight-bold">Contact Us</h3>
                            </div>
                        </div>

                        <div className="card-body mx-4 mt-4">

                            <table className="table table-borderless">
                                <thead>
                                <tr>
                                    <th><b>Address</b></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>{this.state.contact.address}
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Lets Talk</b></td>
                                </tr>
                                <tr>
                                    <td>{this.state.contact.phone}</td>
                                </tr>
                                <tr>
                                    <td><b>General Support</b></td>
                                </tr>
                                <tr>
                                    <td>{this.state.contact.email}</td>
                                </tr>
                                </tbody>
                            </table>

                        </div>

                    </div>

                    <div className="card col-md-6">

                        <div className="card-header pt-3">
                            <div className="row d-flex justify-content-center">
                                <h3 className="white-text mb-3 pt-3 font-weight-bold">Contact Us</h3>
                            </div>
                        </div>

                        <div className="card-body mx-4 mt-4">
                            <form className="text-center border border-light p-5" action="#!">
                                <input type="text" id="defaultContactFormName" className="form-control mb-4"
                                       placeholder="Name"/>
                                <input type="email" id="defaultContactFormEmail" className="form-control mb-4"
                                       placeholder="E-mail"/>
                                <div className="form-group">
                                            <textarea className="form-control rounded-0"
                                                      id="exampleFormControlTextarea2" rows="3"
                                                      placeholder="Message"></textarea>
                                </div>
                                <button className="btn btn-info btn-block" type="submit">Send</button>
                            </form>
                        </div>
                    </div>
                </div>

                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
            </div>
        );
    }
}

export default ContactComponent;