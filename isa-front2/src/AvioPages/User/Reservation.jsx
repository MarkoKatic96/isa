import React, { Component, Fragment } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import './user.css';
import Rectangle from 'react-rectangle';


class Reservation extends Component {
    state = {
        toggle: false,
        podaciOLetu: "",
        
        karte: [],
        idKarte: "",
        user: "",
        idLeta: "",

        listaRezervisanihMesta: [],

        sviKorisnici: [], //lista svih povucenih korisnika
        friend: "", //sadrzi samo email korisnika (ili ime ce vidimo)
        listaPrijatelja: [] //ovo saljemo - lista objekata korisnika
    }

    componentDidMount() {
        let letid = this.props.match.params.flightid;
        this.setState({
            idLeta: letid
        })
        axios.get('http://localhost:8221/flight/getone/' + letid).then(res => {
            this.setState({
                podaciOLetu: res.data
            })
        }).catch(error => {
            console.log(error);
        }).then(
            axios.get('http://localhost:8221/ticket/getfree/' + letid).then(res =>
            {
                this.setState({
                    karte: res.data
                })
            })
        )

        //uzmi korisnika
        axios.get("http://localhost:8221/user/all/" + localStorage.getItem('email'))
            .then(res => {
                console.log(res)
                this.setState({
                    user: res.data
                })
            })

            axios.get("http://localhost:8221/user/all")
            .then(res => {
                console.log(res.data)
                this.setState({
                    sviKorisnici: res.data
                })
            })  

    }

    componentDidUpdate(){
        // let letid = this.props.match.params.flightid;
        // axios.get('http://localhost:8221/ticket/getfree/' + letid).then(res =>
        //     {
        //         this.setState({
        //             karte: res.data
        //         })
        //     })
    }

    toggleFriends = () => {
        if (this.state.toggle) {
            this.setState({
                toggle: false
            })
        }
        else {
            this.setState({
                toggle: true
            })
        }
    }

    sendInvitationFieldChange = (e) => {
        this.setState({
            friend: e.target.value
        })
    }

    handleInvitation = (e) => {
        e.preventDefault();

        let listaZaSlanje = this.state.listaPrijatelja;
        let friendEmail = this.state.friend;
        let sviKorisnici = this.state.sviKorisnici;
        console.log(sviKorisnici)
        sviKorisnici.map(user => {
            if(friendEmail === user.email)
            {
                listaZaSlanje.push(user);
            }
        }) 

        console.log(listaZaSlanje)
        this.setState({
            listaPrijatelja: listaZaSlanje
        })

    }

    getTicket = (karta) => {

        let lista = this.state.listaRezervisanihMesta;
        lista.push(karta)

       

        this.setState({
            listaRezervisanihMesta: lista
        })

        console.log(lista);
    }

    confirmMoreTicketsReservation = () =>
    {
        let userid = this.state.user.id;
        let listaKarata = this.state.listaRezervisanihMesta;
        let listaPrijatelja = this.state.listaPrijatelja;

        console.log("ZA SLANJE: ")
        console.log(listaKarata)
        console.log(listaPrijatelja)

        axios.post('http://localhost:8221/ticket/reservemore/' + userid, {listaKarata, listaPrijatelja}).then(res => { 
            if(res.data === "REZERVISANE")
            {
                alert("Karte uspesno rezervisane");
                this.setState({
                    listaRezervisanihMesta: []
                })
                this.componentDidMount();
            }
            else if(res.data === "NOT_FRIEND_ERR")
                alert("Osoba koju pozivate nije u prijateljima")
            else
                alert("Rezervacija nije uspela")
            this.componentDidUpdate();
        }).catch(error => {
            console.log(error);
            alert("Osoba koju pozivate nije u prijateljima")
        })
    }

    render() {
        let i = 1;
        const raspored = this.state.karte.length ? (this.state.karte.map(karta => {
            
            return (
                <div id="planelayout" key={karta.idKarte}>
                    <Fragment>
                            <button  className="btn waves-effect waves-light blue" id="ticketbtn" onClick={() => { this.getTicket(karta) }}>{i++}</button>
                    </Fragment>
                </div>
            );
        })) : (
                <div></div>
            )

        return (
            <div>

                {raspored}

                <button className="btn waves-effect waves-light red" id="friendsbtn" onClick={() => { this.toggleFriends() }}>Pozovi prijatelje</button><br />
                <button className="btn waves-effect waves-light red" id="reservationbtn" onClick={() => { this.confirmMoreTicketsReservation() }}>Potvrdi rezervaciju</button><br />
                {
                    (this.state.toggle) ? (
                        <div>
                            <form onSubmit={(e) => { this.handleInvitation(e) }}>
                                <div className="container">
                                    <div className="input-field">
                                        <input type="text" id="friend" placeholder="Ime prijatelja" className="browser-default" name="friend" onChange={(e) => { this.sendInvitationFieldChange(e) }} /><br />
                                        <button className="btn waves-effect waves-light red" type="submit">Potvrdi</button><br />
                                    </div>
                                </div>
                            </form>
                        </div>) : (<p></p>)
                }

            </div>
        );
    }
}

export default withRouter(Reservation);
