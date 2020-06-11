import React, {Component} from 'react';
import moment from "moment";
import {ErrorMessage, Field, Form, Formik} from "formik";
import MealDataService from "../../api/MealDataService";

class MealComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            /* meal : {code: this.props.match.params.code, name: 'Mantı', price: 12, photo: 'text', detail: 'Süper bi yemek!',
                     creationDate: moment(new Date()).format('YYYY-MM-DD')}*/
            meal: {
                code: '',
                name: '',
                price: '',
                photo: '',
                detail: '',
                campaign: '',
                creationDate: moment(new Date()).format('YYYY-MM-DD')
            },
            action: 'update'
        }
    }

    validate(values) {
        let errors = {};

        if (!values.code)
            errors.code = 'Enter a meal code!';
        else if (values.code.length < 3)
            errors.code = 'Enter at least 3 characters into code!';

        if (!values.name)
            errors.name = 'Enter a meal name!';
        else if (values.name.length < 2)
            errors.name = 'Enter at least 2 characters into name!';

        const re = /^[0-9\b]+$/;
        if (!values.price)
            errors.price = 'Enter a meal price!';
        else if (values.price === '' || !re.test(values.price))
            errors.price = 'Enter only numbers into price!';

        if (!moment(values.creationDate).isValid())
            errors.creationDate = 'Enter a valid date!';
        return errors;
    }

    onSubmit = (values) => {
        console.log(values);
        if(this.state.action==='update'){
            MealDataService.updateMeal(this.state.meal.code, values)
                .then(response => {
                    this.props.history.push(`/meallist`);
                })
        }else{
            MealDataService.createMeal(values)
                .then(response => {
                    this.props.history.push(`/meallist`);
                })
        }
    }

    componentDidMount = () => {
        if (this.props.match.params.code === "new") {
            this.setState({action: 'create'});
            return;
        }

        MealDataService.retrieveMeal(this.props.match.params.code)
            .then(response => {
                console.log(response);
                this.setState({meal: response.data});
            });

    };

    render() {
        let {code, name, price, photo, detail, campaign, creationDate} = this.state.meal;

        function setFieldValue(checked) {
            campaign = checked;
        }

        return (
            <div>
                <h1>Meal</h1>
                <div className="container">
                    <Formik onSubmit={this.onSubmit}
                            validate={this.validate}
                            initialValues={{code, name, price, photo, detail, campaign, creationDate}}
                            enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="code" component="div" className="alert alert-warning"/>
                                    <ErrorMessage name="name" component="div" className="alert alert-warning"/>
                                    <ErrorMessage name="price" component="div" className="alert alert-warning"/>
                                    <ErrorMessage name="creationDate" component="div" className="alert alert-warning"/>
                                    <fieldset className="form-group">
                                        <label>Code</label>
                                        <Field className="form-control" type="text" name="code"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Name</label>
                                        <Field className="form-control" type="text" name="name"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Price</label>
                                        <Field className="form-control" type="text" name="price"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Photo</label>
                                        <Field className="form-control" type="text" name="photo"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Detail</label>
                                        <Field className="form-control" type="text" name="detail"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Creation Date</label>
                                        <Field className="form-control" type="date" name="creationDate"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Campaign?</label>
                                        <Field className="form-control" type="checkbox" name="campaign"
                                               onChange={event => setFieldValue(event.target.checked)} checked={campaign}/>
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>
                </div>
            </div>
        );
    }
}

export default MealComponent;