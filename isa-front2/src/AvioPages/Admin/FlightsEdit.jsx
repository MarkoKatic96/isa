import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import Select from 'react-select';

class FlightsEdit extends Component {
    state = {
        letovi: [],
        destinacije: [],
        klase: [],

        mestoPolaska: "",
        mestoDolaska: "",
        vremePolaska: "",
        vremeDolaska: "",
        tipLeta: "",
        brojOsoba: "",
        klaseLeta: ""
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

    handleChange = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleChangeMestoPolaska = (mestoPolaska) => {
        console.log(mestoPolaska);
        this.setState({ mestoPolaska });
    };

    handleChangeMestoDolaska = (mestoDolaska) => {
        console.log(mestoDolaska);
        this.setState({ mestoDolaska });
    };

    handleSubmit = (event) => {
        event.preventDefault();
        var token = localStorage.getItem('jwtToken');
        // var tipZaSlanje = "";
        // var {tipoviSobe} = this.state;
        // var i;
        // for(i=0; i<tipoviSobe.length; i++){
        //     if(this.state.selectedTip.value===tipoviSobe[i].id){
        //         tipZaSlanje = tipoviSobe[i];
        //     }
        // }
        // if(this.state.sprat!=="" && this.state.brojSobe!=="" && this.state.brojKreveta!=="" && this.state.selectedTip!=="" && this.state.originalnaCena!==""){
        //     axios.put("http://localhost:8080/sobe/" + this.props.match.params.sobaId, {sprat: this.state.sprat, brojSobe: this.state.brojSobe, brojKreveta: this.state.brojKreveta, tipSobe: tipZaSlanje, hotel: this.state.soba.hotel, originalnaCena: this.state.originalnaCena}, { headers: { Authorization: `Bearer ${token}` } })
        //     .then(res => {
        //         alert("Uspesno izmenjena soba.")
        //         this.props.history.push("/adflights");
        //     }).catch(error=>{
        //         alert("Soba je rezervisana, ne mozete je trenutno izmeniti.");
        //     })
        // }else{
        //     alert("Sva polja moraju biti ispravno popunjena.")
        // }
    }


    render() {

        var { destinacije } = this.state;
        var { letovi } = this.state;
        var { klase } = this.state;

        var { mestoPolaska } = this.state;
        var { mestoDolaska } = this.state;
        var { vremePolaska } = this.state;
        var { vremePolaska } = this.state;
        var { tipPuta } = this.state;


        var listaDestinacija = [];

        destinacije.map(dest => {
            var options = new Object();
            options.value = dest.id;
            options.label = dest.naziv;
            listaDestinacija.push(options);
        })

        return (
            <div>
                <br />
                {(localStorage.getItem('rola') === 'ADMIN_AVIO_KOMPANIJE') ? (
                    <div className="container">
                        <form onSubmit={(e) => { this.handleSubmit(e) }}>
                            <label className="left black-text" htmlFor="mestoPolaska">Mesto polaska:</label>
                            <Select
                                value={mestoPolaska}
                                onChange={this.handleChangeMestoPolaska}
                                options={listaDestinacija}
                                id="mestoPolaska" />
                            <label className="left black-text" htmlFor="mestoDolaska">Mesto dolaska:</label>
                            <Select
                                value={mestoDolaska}
                                onChange={this.handleChangeMestoDolaska}
                                options={listaDestinacija}
                                id="mestoDolaska" />
                            <label className="left black-text" htmlFor="vremePolaska">Datum i vreme poletanja</label>
                            <input type="date" className="datepicker" id="vremePolaska" onChange={(e) => { this.changeDatum1(e) }} />
                            <input type="time" className="timepicker" id="takeofftime" onChange={(e) => { this.changeVreme1(e) }} />

                            <label className="left black-text" htmlFor="vremeDolaska">Datum i vreme sletanja</label>
                            <input type="date" className="datepicker" id="vremeDolaska" onChange={(e) => { this.changeDatum2(e) }} />
                            <input type="time" className="timepicker" id="landingtime" onChange={(e) => { this.changeVreme2(e) }} />

                            <label className="left black-text" htmlFor="vremePolaska">Vreme polaska:</label>
                            <input type="number" id="brojKreveta" value={this.state.brojKreveta} onChange={this.handleChange} />
                            <label className="left black-text" htmlFor="originalnaCena">Originalna cena:</label>
                            <input type="number" id="originalnaCena" value={this.state.originalnaCena} onChange={this.handleChange} />
                            {/* <Select 
                                    value={selectedTip}
                                    onChange={this.handleChangeTip}
                                    options={ listaTipova } 
                                    id="selectTip"/> */}
                            <button className="btn waves-effect waves-light green" id="dodajSobaClick">Izmeni</button>
                        </form>




                        {/* <form className="white" onSubmit={(e) => { this.handleSubmit(e) }}>
                        <h2 className="red-text lighten-1 center">Izmeni let</h2>
                        <div className="container">
                            <label htmlFor="destinacijaPoletanja">Mesto polaska</label>
                            <div className="input-field">
                            <Select 
                                    value={destinacije}
                                    onChange={this.handleChangeTip}
                                    options={ this.state.destinacije } 
                                    id="destinacijaPoletanja"/>
                            </div>
                            <label htmlFor="landingdest">Mesto dolaska</label>
                            <div className="input-field">
                                <select id="landingdest" className="browser-default" name="destinationLanding" onChange={(e) => { this.changeMestoDolaska(e) }}>
                                    {this.state.destinacije.map(dest =>
                                        <option>{dest.naziv}</option>
                                    )}
                                </select>
                            </div>
                            <label htmlFor="takeoff">Datum i vreme poletanja</label>
                            <div className="input-field">
                                <input type="date" className="datepicker" id="takeoff" onChange={(e) => { this.changeDatum1(e) }} />
                                <input type="time" className="timepicker" id="takeofftime" onChange={(e) => { this.changeVreme1(e) }} />
                            </div>
                            <label htmlFor="landing">Datum i vreme sletanja</label>
                            <div className="input-field">
                                <input type="date" className="datepicker" id="landing" onChange={(e) => { this.changeDatum2(e) }} />
                                <input type="time" className="timepicker" id="landingtime" onChange={(e) => { this.changeVreme2(e) }} />
                            </div>
                            <label htmlFor="fltype">Tip leta</label>
                            <div className="input-field">
                                <select id="fltype" className="browser-default" name="travelType" onChange={(e) => { this.changeTipLeta(e) }}>
                                    {this.state.letovi.map(lett =>
                                        <option>{lett.tipPuta}</option>
                                    )}
                                </select>
                            </div>

                            <label htmlFor="takeoffdest">Broj osoba</label>
                            <div className="input-field">
                                <input type="number" id="takeoffdest" className="browser-default" name="takenNumber" onChange={(e) => { this.changeBrojOsoba(e) }} />

                            </div>

                            <label htmlFor="flclass">Klase leta</label>
                            <div className="input-field">
                                <select id="flclass" className="browser-default" name="travelClass" onChange={(e) => { this.changeKlaseLeta(e) }}>
                                    {this.state.klase.map(klasa =>
                                        <option>{klasa.naziv}</option>
                                    )}
                                </select>
                            </div>

                            <div className="input-field">
                                <input type="submit" value="Sacuvaj" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                            </div>
                        </div>
                    </form>  */}
                    </div>
                ) : (<div></div>)}
            </div>
        );
    }
}

export default withRouter(FlightsEdit);
