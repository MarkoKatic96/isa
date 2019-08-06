import React, { Component } from 'react';
import axios from 'axios';
import { withRouter} from 'react-router-dom';

class Flights extends Component {
    state = {
        letovi: [],
        destinacije: [],
        klase: [],
        clickAdd: false
    }

    componentDidMount() {

        axios('http://localhost:8221/flight/getall').then(
            res => {
                this.setState({
                    letovi: res.data
                })
            }
        )

        axios('http://localhost:8221/destination/getall').then(
            res => {
                this.setState({
                    destinacije: res.data
                })
            }
        )

        axios('http://localhost:8221/class/getall').then(
            res => {
                this.setState({
                    klase: res.data
                })
            }
        )
    }

    changeFlight = (idLeta) =>
    {
        this.props.history.push('/adflightsedit/' + idLeta);
    }

    toggleAddBtn = () =>
    {
        if(this.state.clickAdd)
        {
            this.setState({
                clickAdd: false
            })
        }
        else
        {
            this.setState({
                clickAdd: true
            })
        }
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
                                        <p>Destinacija poletanja: {flight.destinacijaPoletanja.naziv}</p> 
                                        <p>Destinacija sletanja: {flight.destinacijaSletanja.naziv}</p> 
                                        <p>Vreme poletanja: {flight.vremePoletanja}</p><br />
                                        <p>Vreme sletanja: {flight.vremeSletanja}</p><br />
                                        <p>Broj slobodnih mesta: {flight.brojMesta - flight.brojOsoba}</p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                        <button className="btn waves-effect waves-light red" id="changebtn" onClick={() => { this.changeFlight(flight.idLeta) }}>Izmeni</button>
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
                <button className="btn waves-effect waves-light red" id="changebtn" onClick={() => { this.toggleAddBtn() }}>Dodaj novi let</button>
                {(this.state.clickAdd) ? (<div className="container">
                    <form className="white" onSubmit={(e) => { this.handleSubmit(e) }}>
                        <h2 className="red-text lighten-1 center">Dodaj novi let</h2>
                        <div className="container">
                            <label htmlFor="takeoffdest">Mesto polaska</label>
                            <div className="input-field">
                                <select id="takeoffdest" className="browser-default" name="destinationTakeOff" onChange = {(e) => {this.changeMestoPolaska(e)}}>
                                    {this.state.destinacije.map(dest =>
                                        <option>{dest.naziv}</option>
                                    )}
                                </select>
                            </div>
                            <label htmlFor="landingdest">Mesto dolaska</label>
                            <div className="input-field">
                                <select id="landingdest" className="browser-default" name="destinationLanding" onChange={(e) => {this.changeMestoDolaska(e)}}>
                                    {this.state.destinacije.map(dest =>
                                        <option>{dest.naziv}</option>
                                    )}
                                </select>
                            </div>
                            <label htmlFor="takeoff">Datum i vreme poletanja</label>
                            <div className="input-field">
                                <input type="date" className="datepicker" id="takeoff" onChange={(e) => {this.changeDatum1(e)}} />
                                <input type="time" className="timepicker" id="takeofftime" onChange={(e) => {this.changeVreme1(e)}} />
                            </div>
                            <label htmlFor="landing">Datum i vreme sletanja</label>
                            <div className="input-field">
                                <input type="date" className="datepicker" id="landing" onChange={(e) => {this.changeDatum2(e)}} />
                                <input type="time" className="timepicker" id="landingtime" onChange={(e) => {this.changeVreme2(e)}} />
                            </div>
                            <label htmlFor="fltype">Tip leta</label>
                            <div className="input-field">
                                <select id="fltype" className="browser-default" name="travelType" onChange={(e) => {this.changeTipLeta(e)}}>
                                    {this.state.letovi.map(lett =>
                                        <option>{lett.tipPuta}</option>
                                    )}
                                </select>
                            </div>

                            <label htmlFor="takeoffdest">Broj osoba</label>
                            <div className="input-field">
                                <input type="number" id="takeoffdest" className="browser-default" name="takenNumber" onChange={(e) => {this.changeBrojOsoba(e)}}/>

                            </div>

                            <label htmlFor="flclass">Klase leta</label>
                            <div className="input-field">
                                <select id="flclass" className="browser-default" name="travelClass" onChange={(e) => {this.changeKlaseLeta(e)}}>
                                    {this.state.klase.map(klasa =>
                                        <option>{klasa.naziv}</option>
                                    )}
                                </select>
                            </div>

                            <div className="input-field">
                                <input type="submit" value="Pretrazi" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                            </div>
                        </div>
                    </form>
                </div>
            ) : (<div></div>)}
                <div className="center container">
                    <h3 className="left-align container">Spisak letova:</h3>
                </div>
                {flightsList}
            </div>
        )

    }
}

export default withRouter(Flights);
