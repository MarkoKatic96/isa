import React, { Component } from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";
import { withRouter} from 'react-router-dom';

class AllFlights extends Component {

    state = {
        letovi: [],
        showFlightInfo: false
    }

    componentDidMount() {

        axios('http://localhost:8221/flight/getall').then(
            res => {
                this.setState({
                    letovi: res.data
                })
            }
        )
    }

    showFlightInfo = (idLeta) =>
    {
        this.props.history.push('/flinfo/' + idLeta);
    }

    render() {
        const flightsList = this.state.letovi.length ? (this.state.letovi.map(flight => {
            return (
                <div>

                    <div className="center container" key={flight.idLeta}>

                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Broj leta: {flight.brojLeta}</b></span>
                                        <div className="divider white"></div>
                                        <br />
                                        <p>Destinacija poletanja: {flight.destinacijaPoletanja.naziv}</p> <button className="btn waves-effect waves-light blue" id="destsinfo-btn" onClick={() => { this.showDestinationsInfo() }}>Informacije o destinaciji</button>
                                        <p>Destinacija sletanja: {flight.destinacijaSletanja.naziv}</p> <button className="btn waves-effect waves-light blue" id="destsinfo-btn" onClick={() => { this.showDestinationsInfo() }}>Informacije o destinaciji</button> <br /><br />
                                        <p>Vreme poletanja: {flight.vremePoletanja}</p><br />
                                        <p>Vreme sletanja: {flight.vremeSletanja}</p><br />
                                        <p>Broj slobodnih mesta: {flight.brojMesta - flight.brojOsoba}</p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                        <button className="btn waves-effect waves-light red" id="letinfo-btn" onClick={() => { this.showFlightInfo(flight.idLeta) }}>Informacije o letu</button>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
                <h3>Nema letova</h3>
            )

        return (
            <div>
                <br />
                <Link to="/flsearch"><button className="btn green center lighten-1 z-depth-0">Pretrazi letove</button></Link>
                <div className="center container">
                    <h3 className="left-align container">Spisak letova:</h3>
                </div>
                {flightsList}
            </div>
        )

    }
}

export default withRouter(AllFlights);
