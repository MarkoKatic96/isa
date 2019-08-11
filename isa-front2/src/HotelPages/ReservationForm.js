    
import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';


class ReservationForm extends Component{

    state = {
        datumOd:"",
        datumDo:""
    }

    handleChange = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit = (e) => {
        e.preventDefault();

        
    }

    render(){
        return(
            //ne zaboravi da dodas dodtne usluge i racunanje ukupne cene
            <div className = "center container">
                <h4 className="center">Rezervacija smestaja:</h4>
                <div className = "center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="datumOd">Datum od:</label>
                        <input type="date" id="datumOd" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumDo">Datum do:</label>
                        <input type="date" id="datumDo" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Rezervisi</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(ReservationForm)