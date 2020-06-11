import PropTypes from "prop-types";
import React, {Component} from "react";

class CounterButton extends Component {
    render() {
        return (
            <div className="counter">
                <button onClick={() => this.props.incrementMethod(this.props.by)} className="green">+{this.props.by}</button>
                <button onClick={() => this.props.decrementMethod(this.props.by)} className="red">-{this.props.by}</button>
            </div>
        );
    }
}

CounterButton.defaultProps = {
    by: 1
};

CounterButton.propTypes = {
    by: PropTypes.number
};

export default CounterButton;