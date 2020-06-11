import React, {Component} from 'react';
import './Counter.css';
import CounterButton from "./CounterButton";

class Counter extends Component {
    constructor() {
        super();
        this.state = {
            counter: 0
        };
    }

    render() {
        const css = {fontSize: "50px", padding: "15px 30px"};
        return (
            <div>
                <CounterButton by={1} incrementMethod={this.increment} decrementMethod={this.decrement}/>
                <CounterButton by={5} incrementMethod={this.increment} decrementMethod={this.decrement}/>
                <CounterButton by={10} incrementMethod={this.increment} decrementMethod={this.decrement}/>
                <button onClick={this.reset}>RESET</button>
                <br/>
                <span className="count" style={css}>{this.state.counter}</span>
            </div>
        );
    }

    increment = (by) => {
        this.setState( (prevState) =>{
            return {counter: prevState.counter + by}
        })
    };

    decrement = (by) => {
        this.setState( (prevState) =>{
            return {counter: prevState.counter - by}
        })
    };

    reset = () => {
        this.setState( () =>{
            return {counter: 0}
        })
    };
}

export default Counter;