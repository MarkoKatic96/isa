import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';


class FlightInfo extends Component {

    state = {
        let: "",
        aviokompanija: ""
    }

    componentDidMount() {
        console.log(this.props.match.params.flightid);
        axios.get('http://localhost:8221/flight/getone/' + this.props.match.params.flightid).then(
            res => {
                console.log(res);
                this.setState({
                    let: res.data
                })
            }
        )

        axios.get('http://localhost:8221/flight/getcompanyid/' + this.props.match.params.flightid).then(
            res => {
                console.log(res);
                this.setState({
                    aviokompanija: res.data
                })
            }
        )



    }

    showCompanyInfo = () => {
        this.props.history.push('/companyinfo/' + this.state.aviokompanija);
    }

    reserveTicket = (idLeta) =>
    {
        this.props.history.push('/reservation/' + idLeta);
    }

    render() {
        return (
            <div>
                <div className="center container">
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                    <span className="card-title"><b>Broj leta: {this.state.let.brojLeta}</b></span>
                                    <div className="divider white"></div>
                                    <br />
                                    <p>Vreme poletanja: {this.state.let.vremePoletanja}</p>
                                    <p>Vreme sletanja: {this.state.let.vremeSletanja}</p>
                                    <p>Broj slobodnih mesta: {this.state.let.brojMesta - this.state.let.brojOsoba}</p>
                                    <p>Broj presedanja: {this.state.let.brojPresedanja}</p>
                                    <p>Duzina putovanja: {this.state.let.duzinaPutovanja}</p>
                                    <p>Tip puta: {this.state.let.tipPuta}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                <button className="btn waves-effect waves-light green" id="avioinfo-btn" onClick={() => { this.showCompanyInfo() }}>Informacije o aviokompaniji</button>
                                    <button className="btn waves-effect waves-light red" id="rezervisi-btn" onClick={() => { this.reserveTicket(this.state.let.idLeta) }}>Rezervacija</button><br />
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




