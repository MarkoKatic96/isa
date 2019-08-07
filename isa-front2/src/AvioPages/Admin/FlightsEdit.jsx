import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import Select from 'react-select';

class FlightsEdit extends Component {
    state = {
        aviokompanijaPovucena: "",
        user: "",
        let: "",

        datumPoletanja1fetch: "",
        vremePoletanja1fetch: "",

        datumSletanja2fetch: "",
        vremeSletanja2fetch: "",
        destinacijaPoletanjaFetch: "",
        destinacijaSletanjaFetch: "",



        destinacije: [],
        klase: [],
        dodatneUsluge: [],
        clickAdd: false,

        brojLeta: "",
        vremePoletanja: "",
        vremeSletanja: "",
        destinacijaPoletanja: "",
        destinacijaSletanja: "",
        duzinaPutovanja: "",
        brojPresedanja: "",
        destinacijePresedanja: [],

        tipPuta: "",
        brojMesta: "",

        tipoviPrtljagaPoLetu: "", //au brt kako ovo.
        klaseKojeLetSadrzi: [],
        dodatneUslugeKojeLetSadrzi: [],


        //POMOCNE
        datumPoletanja1: "",
        datumPoletanja2: "",
        vremePoletanja1: "",
        vremePoletanja2: ""

    }

    componentDidMount() {
        var token = localStorage.getItem('jwtToken');
        var idKompanije = "";
        axios.get("http://localhost:8221/user/all/" + localStorage.getItem('email'))
            .then(res => {
                console.log(res)
                this.setState({
                    user: res.data
                })
                idKompanije = res.data.zaduzenZaId;
                axios.get('http://localhost:8221/flight/getone/' + this.props.match.params.flightid).then(
                    res => {
                        this.setState({
                            let: res.data,
                            vremePoletanja: res.data.vremePoletanja,
                            vremeSletanja: res.data.vremeSletanja,
                            destinacijaPoletanja: res.data.destinacijaPoletanja,
                            destinacijaSletanja: res.data.destinacijaSletanja,
                            brojLeta: res.data.brojLeta,
                            duzinaPutovanja: res.data.duzinaPutovanja,
                            brojPresedanja: res.data.brojPresedanja,
                            tipPuta: res.data.tipPuta,
                            brojMesta: res.data.brojMesta,
                        })

                        let dat1 = res.data.vremePoletanja;
                        let dat2 = res.data.vremeSletanja;

                        let destp = res.data.destinacijaPoletanja.naziv;
                        let dests = res.data.destinacijaSletanja.naziv;



                        this.setState({
                            

                        })
                    }


                ).then(axios.get('http://localhost:8221/aviocompany/getone/' + idKompanije, { headers: { Authorization: `Bearer ${token}` } }).then(res => {
                    this.setState({
                        aviokompanijaPovucena: res.data
                    })
                })

                )
            })

        axios.get('http://localhost:8221/destination/getall').then(
            res => {
                this.setState({
                    destinacije: res.data
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

        axios.get('http://localhost:8221/service/getall').then(
            res => {
                this.setState({
                    dodatneUsluge: res.data
                })
            }
        )
    }

    changeFlight = (idLeta) => {
        this.props.history.push('/adflightsedit/' + idLeta);
    }

    toggleAddBtn = () => {
        if (this.state.clickAdd) {
            this.setState({
                clickAdd: false
            })
        }
        else {
            this.setState({
                clickAdd: true
            })
        }
    }


    /*
    *   OPERACIJE ZA DODAVANJE NOVOG LETA
    */

    changeInputField = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    changeDatum1 = (e) => {
        this.setState({
            datumPoletanja1: e.target.value
        })
    }

    changeVreme1 = (e) => {
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

    changeDestinacijaPoletanja = (destinacijaPoletanja) => {
        this.setState({ destinacijaPoletanja });
        console.log(this.state.destinacijaPoletanja)
    }

    changeDestinacijaSletanja = (destinacijaSletanja) => {
        this.setState({ destinacijaSletanja });
        console.log(this.state.destinacijaSletanja)
    }

    changeDestinacijePresedanja = (destinacijePresedanja) => {
        this.setState({ destinacijePresedanja });
        console.log(this.state.destinacijePresedanja)
    }

    changeKlaseKojeLetSadrzi = (klaseKojeLetSadrzi) => {
        this.setState({ klaseKojeLetSadrzi });
        console.log(this.state.klaseKojeLetSadrzi)
    }

    changeDodatneUslugeKojeLetSadrzi = (dodatneUslugeKojeLetSadrzi) => {
        this.setState({ dodatneUslugeKojeLetSadrzi });
        console.log(this.state.dodatneUslugeKojeLetSadrzi)
    }


    handleSubmit = (e) => {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken');

        let idLeta = 9999;

        let brojLeta = this.state.brojLeta;

        let vremePoletanja = this.state.datumPoletanja1 + 'T' + this.state.vremePoletanja1 + ':00';

        let vremeSletanja = this.state.datumSletanja1 + 'T' + this.state.vremeSletanja1 + ':00';

            
        let duzinaPutovanja = this.state.duzinaPutovanja;
        let brojPresedanja = this.state.brojPresedanja;
        let tipPuta = this.state.tipPuta;
        let brojMesta = this.state.brojMesta;
        let aviokompanija = {
            idAvioKompanije: this.state.user.zaduzenZaId,
            naziv: this.state.aviokompanijaPovucena.naziv,
            opis: this.state.aviokompanijaPovucena.opis
        }

        let destinacijaPoletanja = {
            idDestinacije: this.state.destinacijaPoletanja.value,
            naziv: this.state.destinacijaPoletanja.label,
            informacije: ""
        }

        let destinacijaSletanja = {
            idDestinacije: this.state.destinacijaSletanja.value,
            naziv: this.state.destinacijaSletanja.label,
            informacije: ""
        }


        let destinacijePresedanja = [];
        let object = new Object();
        this.state.destinacijePresedanja.map(dest => {
            object.idDestinacije = dest.value;
            object.naziv = dest.label;
            object.informacije = "";
            destinacijePresedanja.push(object);
        })

        let klaseKojeLetSadrzi = []
        this.state.klaseKojeLetSadrzi.map(klasa => {
            object.idKlase = klasa.value;
            object.naziv = klasa.label;
            klaseKojeLetSadrzi.push(object);
        })

        let dodatneUslugeKojeLetSadrzi = []
        let temp3 = this.state.dodatneUslugeKojeLetSadrzi.map(usluga => {
            object.idDodatneUsluge = usluga.value;
            object.naziv = usluga.label;
            dodatneUslugeKojeLetSadrzi.push(object);
        })

        var LET_ID = this.state.let.idLeta;


        axios.put("http://localhost:8221/flight/update/" + LET_ID, {
            idLeta, brojLeta, vremePoletanja, vremeSletanja, duzinaPutovanja, brojPresedanja, tipPuta, brojMesta,
            aviokompanija, destinacijaPoletanja, destinacijaSletanja, destinacijePresedanja, klaseKojeLetSadrzi,
            dodatneUslugeKojeLetSadrzi, prosecnaOcena: null, brojOsoba: 0, ukupanPrihod: 0
        }, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Uspesno ste dodali novi let")
                this.props.history.push("/adflights");
            }).catch(error => {
                alert("GRESKA!?.");
            })
    }

    render() {
        //DESTINACIJE
        var { destinacijaPoletanja } = this.state;
        var { destinacijaSletanja } = this.state;
        var { destinacijePresedanja } = this.state;

        var { destinacije } = this.state;
        var listaDestinacija = [];

        destinacije.map(dest => {
            let options = new Object();
            options.value = dest.idDestinacije;
            options.label = dest.naziv;
            listaDestinacija.push(options);
        })

        //KLASE
        var { klaseKojeLetSadrzi } = this.state;
        var { klase } = this.state;

        var listaKlasa = [];

        klase.map(klasa => {
            let options = new Object();
            options.value = klasa.idKlase;
            options.label = klasa.naziv;
            listaKlasa.push(options);
        })

        //DODATNE USLUGE
        var { dodatneUslugeKojeLetSadrzi } = this.state;
        var { dodatneUsluge } = this.state;

        var listaDodatnihUsluga = [];

        dodatneUsluge.map(usl => {
            let options = new Object();
            options.value = usl.idDodatneUsluge;
            options.label = usl.naziv;
            listaDodatnihUsluga.push(options);
        })


        return (
            <div>
                <br />
                <div className="container">
                    <form className="white" onSubmit={(e) => { this.handleSubmit(e) }}>
                        <h2 className="red-text lighten-1 center">Izmeni let</h2>
                        <div className="container">

                            <label htmlFor="brojLeta">Broj leta</label>
                            <div className="input-field">
                                <input type="number" id="brojLeta" defaultValue={this.state.let.brojLeta} className="browser-default" name="brojLeta" onChange={(e) => { this.changeInputField(e) }} />
                            </div>

                            <label htmlFor="takeoff">Datum i vreme poletanja</label>
                            <div className="input-field">
                                <input type="date" defaultValue={this.state.vremePoletanja.split("T")[0]} className="datepicker" id="takeoff" onChange={(e) => { this.changeDatum1(e) }} />
                                <input type="time" defaultValue={this.state.vremePoletanja.split("T")[1]} className="timepicker" id="takeofftime" onChange={(e) => { this.changeVreme1(e) }} />
                            </div>
                            <label htmlFor="landing">Datum i vreme sletanja</label>
                            <div className="input-field">
                                <input type="date" defaultValue={this.state.vremeSletanja.split("T")[0]} className="datepicker" id="landing" onChange={(e) => { this.changeDatum2(e) }} />
                                <input type="time" defaultValue={this.state.vremeSletanja.split("T")[1]} className="timepicker" id="landingtime" onChange={(e) => { this.changeVreme2(e) }} />
                            </div>
                            <label htmlFor="destinacijaPoletanja">Mesto poletanja</label>
                            <Select
                                value={destinacijaPoletanja}
                                onChange={(destinacijaPoletanja) => { this.changeDestinacijaPoletanja(destinacijaPoletanja) }}
                                options={listaDestinacija}
                                id="destinacijaPoletanja" />


                            <label htmlFor="destinacijaSletanja">Mesto sletanja</label>
                            <Select
                                value={destinacijaSletanja}
                                onChange={(destinacijaSletanja) => { this.changeDestinacijaSletanja(destinacijaSletanja) }}
                                options={listaDestinacija}
                                id="destinacijaSletanja" />

                            <label htmlFor="duzinaPutovanja">Duzina leta [km]</label>
                            <input type="number" defaultValue={this.state.let.duzinaPutovanja} id="duzinaPutovanja" onChange={(e) => { this.changeInputField(e) }} />

                            <label htmlFor="brojPresedanja">Broj presedanja</label>
                            <input type="number" defaultValue={this.state.let.brojPresedanja} id="brojPresedanja" onChange={(e) => { this.changeInputField(e) }} />

                            <label htmlFor="destinacijePresedanja">Mesta u kojima se preseda</label>
                            <Select
                                value={destinacijePresedanja}
                                onChange={(destinacijePresedanja) => { this.changeDestinacijePresedanja(destinacijePresedanja) }}
                                options={listaDestinacija}
                                id="destinacijePresedanja" isMulti={true} />

                            <label htmlFor="tipPuta">Tip leta</label>
                            <input type="text" defaultValue={this.state.let.tipPuta} id="tipPuta" onChange={(e) => { this.changeInputField(e) }} />


                            <label htmlFor="brojMesta">Broj mesta za rezervaciju</label>
                            <input type="number" defaultValue={this.state.let.brojMesta} id="brojMesta" onChange={(e) => { this.changeInputField(e) }} />


                            <label htmlFor="klaseKojeLetSadrzi">Klase u avionu</label>
                            <Select
                                value={klaseKojeLetSadrzi}
                                onChange={(klaseKojeLetSadrzi) => { this.changeKlaseKojeLetSadrzi(klaseKojeLetSadrzi) }}
                                options={listaKlasa}
                                id="klaseKojeLetSadrzi" isMulti={true} />

                            <label htmlFor="dodatneUslugeKojeLetSadrzi">Dodatne usluge u avionu</label>
                            <Select
                                value={dodatneUslugeKojeLetSadrzi}
                                onChange={(dodatneUslugeKojeLetSadrzi) => { this.changeDodatneUslugeKojeLetSadrzi(dodatneUslugeKojeLetSadrzi) }}
                                options={listaDodatnihUsluga}
                                id="dodatneUslugeKojeLetSadrzi" isMulti={true} />

                            <div className="input-field">
                                <input type="submit" value="Sacuvaj" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}

export default withRouter(FlightsEdit);
