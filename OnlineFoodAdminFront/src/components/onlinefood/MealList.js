import React, {Component} from 'react';
import MealDataService from "../../api/MealDataService";
import AuthenticationService from "./AuthenticationService";

class MealList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            message: null,
            meals:
                [
                    /*{code: 'MNT1', name: 'Mantı', price: 12, photo: 'text', detail: 'Süper bi yemek!'},
                    {code: 'CRB1', name: 'Çorba', price: 6, photo: 'text', detail: 'Süper bi yemek!'},
                    {code: 'PD1', name: 'Pide', price: 19, photo: 'text', detail: 'Süper bi yemek!'}*/
                ]
        };
        AuthenticationService.setupAxiosInterceptorsForSavedToken();
    }

    componentWillUnmount() {
        console.log('componentwillunmount');
    }

    shouldComponentUpdate(nextProps, nextState) {
        console.log('componentwillunmount');
        console.log(nextProps)
        console.log(nextState);
        return true;
    }

    componentDidMount() {
        this.refreshMeals();
    };

    refreshMeals = () => {
        MealDataService.retrieveAllMeals()
            .then(response =>{
                this.setState({meals: response.data});
            })
    };

    render() {
        return (
            <div>
                <h1>Meal List</h1>
                {this.state.message!=null && <div className="alert alert-success">{this.state.message}</div>}
                <div className="container">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>CODE</th>
                            <th>NAME</th>
                            <th>PRICE</th>
                            <th>PHOTO</th>
                            <th>DETAIL</th>
                            <th>UPDATE</th>
                            <th>DELETE</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.meals.map(
                                meal =>
                                    <tr key={meal.code}>
                                        <td>{meal.code}</td>
                                        <td>{meal.name}</td>
                                        <td>{meal.price.toString()}</td>
                                        <td>{meal.photo}</td>
                                        <td>{meal.detail}</td>
                                        <td><button onClick={() => this.handleUpdateMealClicked(meal.code)} className="btn btn-success">UPDATE</button></td>
                                        <td><button onClick={() => this.handleDeleteMealClicked(meal.code)} className="btn btn-warning">DELETE</button></td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>

                    <div className="row">
                        <button className="btn btn-success" onClick={this.handleAddMealClicked}>ADD</button>
                    </div>
                </div>
            </div>
        );
    }

    handleDeleteMealClicked = (code) =>{
        MealDataService.deleteMeal(code).then(response=>{
            this.setState({message : `Delete successfull!`});
            this.refreshMeals();
        });
    };

    handleUpdateMealClicked = (code) =>{
        this.props.history.push(`/meallist/${code}`);
    };

    handleAddMealClicked = () => {
        this.props.history.push(`/meallist/${'new'}`);
    }
}

export default MealList;