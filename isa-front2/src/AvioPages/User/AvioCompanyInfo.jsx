import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';


class FlightInfo extends Component {

    state = {
        kompanija: "",
        avgrating: ""
    }

    componentDidMount() {
        console.log(this.props.match.params.flightid);
        axios.get('http://localhost:8221/aviocompany/getone/' + this.props.match.params.flightid).then(
            res => {
                this.setState({
                    kompanija: res.data
                })
            }
        )
            //za prosecnu ocenu
        axios.get('http://localhost:8221/aviocompany/getavgrating/' + this.props.match.params.flightid).then(
            res => {
                console.log(res.data);
                this.setState({
                    avgrating: res.data
                })
            }
        )
    }

    render() {
        return (
            <div>
                <div className="center container">
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                    <span className="card-title"><b>Naziv aviokompanije: {this.state.kompanija.naziv}</b></span>
                                    <div className="divider white"></div>
                                    <br />
                                    <p>Adresa: {this.state.kompanija.adresa}</p>
                                    <p>Opis: {this.state.kompanija.opis}</p>
                                    <p>Prosecna ocena: {this.state.avgrating}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    {/* <button className="btn waves-effect waves-light green" id="avioinfo-btn" onClick={() => { this.showCompanyInfo(this.state.let.aviokompanija) }}>Informacije o aviokompaniji</button>
                                    <button className="btn waves-effect waves-light red" id="rezervisi-btn" onClick={() => { this.reserveTicket(this.state.let.idLeta) }}>Rezervacija</button> */}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(FlightInfo);




