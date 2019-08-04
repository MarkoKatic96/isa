import React, { Component } from 'react';
import axios from 'axios';
import ChooseBar from '../../ChooseBar';

class UserHome extends Component {
    state = {
        flights: []
    }

    componentDidMount() {
        axios.get("http://localhost:8221/flight/getall").then(
            res => {
                this.setState({
                    flights: res.data
                })
            }
        )
    }


    render() {

        var {flights} = this.state;
        const flightsList = flights.length ? (flights.map(flight => {
            return (
                <div className="center container" key={flight.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>Broj leta: {flight.brojLeta}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Vreme poletanja: {flight.vremePoletanja}</p>
                                <p>Vreme sletanja: {flight.vremeSletanja}</p>
                                <p>Broj slobodnih mesta: {flight.brojMesta - flight.brojOsoba}</p>
                                <p>Broj presedanja: {flight.brojPresedanja}</p>
                                <p>Duzina putovanja: {flight.duzinaPutovanja}</p>
                                <p>Tip puta: {flight.tipPuta}</p>
                                {/* <p>Ocena: {hotel.ocena}</p> */}
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    {/* <button className="btn waves-effect waves-light green" id="sobeBtn" onClick={()=>{this.sobeClick(hotel.id)}}>Sobe</button>
                                    <button className="btn waves-effect waves-light green" id="uslugeBtn" onClick={()=>{this.uslugeClick(hotel.id)}}>Dodatne usluge</button> */}
                                    {this.props.loggedIn ? (
                                        {/* <button className="btn waves-effect waves-light green" id="rezervisiBtn" onClick={()=>{this.uslugeClick(hotel.id)}}>Rezrvisi</button> */}
                                    ):(
                                        <p/>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
            <div className="center">Nije pronadjena nijedna avio kompanija.</div>
        )

        return(
            <div className="center container">
                <br/>
                <ChooseBar/>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="datumOd">Datum od:</label>
                        <input type="date" id="datumOd" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumDo">Datum do:</label>
                        <input type="date" id="datumDo" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="brojOsoba">Broj osoba:</label>
                        <input type="number" id="brojSoba" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Pretrazi</button>
                    </form>
                </div>
                <br/>
                <h3 className="left-align container">Letovi:</h3>
                <br/>
                {flightsList}
            </div>
        )
        
    }

}
export default UserHome;
