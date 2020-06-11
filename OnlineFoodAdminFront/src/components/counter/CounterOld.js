import React,{Component} from 'react';
import './Counter.css';
import PropTypes from 'prop-types';

class CounterOld extends Component {
    constructor(){
        super();
        this.state = {
            counter : 0,
            secondCounter : 100
        }
    }

    render() {
        const css = {fontSize: "50px",padding: "15px 30px"};
        return (
            <div className="counter">
                <button onClick={this.increment}>+{this.props.by}</button>
                <span className="count" style={css}>{this.state.counter}</span>
            </div>
        );
    }

    increment = () => {
        this.setState({
            counter : this.state.counter + this.props.by,
        })

    }
}

CounterOld.defaultProps = {
    by: 1
};

CounterOld.propTypes = {
    by : PropTypes.number
};

export default CounterOld;