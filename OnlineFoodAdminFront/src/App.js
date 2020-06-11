import React, {Component} from 'react';
import './App.css';
import OnlineFoodApp from "./components/onlinefood/OnlineFoodApp";
import './bootstrap.css';

class App extends Component{
  render() {
    return (
        <div className="App">
            <OnlineFoodApp/>
        </div>
    );
  }
}

export default App;
