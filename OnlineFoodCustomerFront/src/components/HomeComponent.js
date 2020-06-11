import React, {Component} from 'react';
import Top from "./Top";
import MenuComponent from "./MenuComponent";

class HomeComponent extends Component {
    render() {
        return (

            <div className="container">
                <Top/>
                <MenuComponent/>
            </div>

        );
    }
}

export default HomeComponent;