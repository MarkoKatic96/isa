import React, { Component } from 'react'
import axios from 'axios';
import FlightInfo from './FlightInfo';
import { Link } from 'react-router-dom';
import Select from 'react-select';

class FlightsSearch extends Component {

    state = {
        destinacije: [],
            letovi: [],
            classes: [],

            takeOffDestination: "",
            landingDestination: "",
            type: "",
            number: "",
            klase: [],
            klaseKojeLetSadrzi: [],

            datumPoletanja1: "",
            vremePoletanja1: "",
            datumPoletanja2: "",
            vremePoletanja2: "",

            showFlightInfo: true,

            flightsRes: []
    }


    componentDidMount() {
        axios.get('http://localhost:8221/destination/getall').then(
            res => {
                this.setState({
                    destinacije: res.data
                })
            }
        )

        axios.get('http://localhost:8221/flight/getall').then(
            res => {
                this.setState({
                    letovi: res.data
                })
            }
        )

        axios.get('http://localhost:8221/class/getall').then(
            res => {
                this.setState({
                    klase: res.data
                })
            }
        )

    }

    changeMestoPolaska = (e) => {

        let mestoPolaska = "";

        this.state.destinacije.map(dest =>
            {
                if(e.target.value == dest.naziv)
                {
                    mestoPolaska = dest.idDestinacije
                }
            })

        this.setState({
            takeOffDestination: mestoPolaska
        })
        console.log(e.target.value);
    }

    changeMestoDolaska = (e) => {

        let mestoDolaska = "";

        this.state.destinacije.map(dest =>
            {
                if(e.target.value == dest.naziv)
                {
                    mestoDolaska = dest.idDestinacije
                }
            })

        this.setState({
             landingDestination: mestoDolaska
        })
        console.log(e.target.value);
    }

    changeDatum1 = (e) => {
        this.setState({
            datumPoletanja1: e.target.value 
       })
    }

    changeVreme1 = (e) => {
        console.log(e.target.value)
        this.setState({
            vremePoletanja1: e.target.value 
       })
    }

    changeDatum2 = (e) => {
        this.setState({
            datumPoletanja2: e.target.value 
       })
    }

    changeVreme2 = (e) => {
        this.setState({
            vremePoletanja2: e.target.value 
       })
    }

    changeTipLeta = (e) => {
        this.setState({
            type: e.target.value
        })
        console.log(e.target.value);
    }


    changeBrojOsoba = (e) => {
        this.setState({
            number: e.target.value
        })
    }

    changeKlaseKojeLetSadrzi = (klaseKojeLetSadrzi) => {
        this.setState({ klaseKojeLetSadrzi });
        console.log(this.state.klaseKojeLetSadrzi)
    }

    handleSubmit = (e) => {
        e.preventDefault();

        let takeOffDestination = this.state.takeOffDestination;
        let landingDestination = this.state.landingDestination;
        let time1 = this.state.datumPoletanja1 + 'T' + this.state.vremePoletanja1 + ':00';
        let time2 = this.state.datumPoletanja2 + 'T' + this.state.vremePoletanja2 + ':00';
        let type = this.state.type;
        let number = this.state.number;
        // let klase = this.state.klase; 
        let klaseKojeLetSadrzi = [];
        let object = new Object();
        for(let i = 0; i<this.state.klaseKojeLetSadrzi.length; i++)
        {
            object.idKlase = this.state.klaseKojeLetSadrzi[i].value;
            object.naziv = this.state.klaseKojeLetSadrzi[i].label;
            klaseKojeLetSadrzi.push(object)
            object = {}
        }

        axios.post('http://localhost:8221/flight/searchflights', {
           time1, time2, takeOffDestination, landingDestination, type, number, klaseKojeLetSadrzi
        }).then(res => {
            console.log(res)
                if(res.status == 200)
                {
                    this.setState({
                        flightsRes: res.data
                    })
                }
                else
                {
                    this.setState({
                        flightsRes: []
                    }) 
                }
            }
        ).catch(error => {
            this.setState({
                flightsRes: []
            }) 
        })


    }

    showFlightInfo = () =>
    {
        this.setState({
            showFlightInfo: true
        })
    }


    render() {

        //LISTE ZA SELECT
         //KLASE
         var { klaseKojeLetSadrzi } = this.state.klaseKojeLetSadrzi;
         var { klase } = this.state.klase;
 
         var listaKlasa = [];
 
         this.state.klase.map(klasa => {
             let options = new Object();
             options.value = klasa.idKlase;
             options.label = klasa.naziv;
             listaKlasa.push(options);
         })




        const flightsList = this.state.flightsRes.length ? (this.state.flightsRes.map(flight => {
            return (
                <div className="center container" key={flight.idLeta}>
                <h3 className="left-align container">Rezultati pretrage:</h3>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>Broj leta: {flight.brojLeta}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Vreme poletanja: {flight.vremePoletanja}</p>
                                <p>Vreme sletanja: {flight.vremeSletanja}</p>
                                <p>Destinacija poletanja: {flight.destinacijaPoletanja.naziv}</p>
                                <p>Destinacija sletanja: {flight.destinacijaSletanja.naziv}</p>
                                <p>Broj slobodnih mesta: {flight.brojMesta - flight.brojOsoba}</p>
                                <p>Broj presedanja: {flight.brojPresedanja}</p>
                                <p>Duzina putovanja: {flight.duzinaPutovanja}</p>
                                <p>Tip puta: {flight.tipPuta}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                <button className="btn waves-effect waves-light blue" id="letinfo-btn" onClick={() => {this.showFlightInfo()}}>Informacije o letu</button>
                                {(this.state.showFlightInfo) ? (<FlightInfo idLeta={flight.brojLeta}/>) : null}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
            <h3>Nema rezultata pretrage</h3>
        )

        return(
            <div><br />
            <Link to="/companies"><button className="btn red center lighten-1 z-depth-0">Nazad</button></Link>
            <div className="center container">
            <div>
                {/* <button onClick={this.handleSearchButton} className="btn blue center lighten-1 z-depth-0">Pretraga</button> */}
                <div className="container">
                    <form className="white" onSubmit={(e) => { this.handleSubmit(e) }}>
                        <h2 className="red-text lighten-1 center">Pretraga letova</h2>
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
                            <label htmlFor="takeoff">Datum i vreme poletanja OD:</label>
                            <div className="input-field">
                                <input type="date" className="datepicker" id="takeoff" onChange={(e) => {this.changeDatum1(e)}} />
                                <input type="time" className="timepicker" id="takeofftime" onChange={(e) => {this.changeVreme1(e)}} />
                            </div>
                            <label htmlFor="landing">Datum i vreme poletanja DO:</label>
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

                            <label htmlFor="klaseKojeLetSadrzi">Klase u avionu</label>
                            <Select
                                value={klaseKojeLetSadrzi}
                                onChange={(klaseKojeLetSadrzi) => { this.changeKlaseKojeLetSadrzi(klaseKojeLetSadrzi) }}
                                options={listaKlasa}
                                id="klaseKojeLetSadrzi" isMulti={true} />

                            <div className="input-field">
                                <input type="submit" value="Pretrazi" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            
                <br/>
                {flightsList}
            </div>
            </div>
        )
        
    }

};

export default FlightsSearch