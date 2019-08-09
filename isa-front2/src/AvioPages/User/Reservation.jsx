import React, { Component, Fragment } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import './user.css';
import Rectangle from 'react-rectangle';


class Reservation extends Component {
    state = {
        toggle: false,
        message: "",
        karte: [],
        idKarte: "",
        user: "",
        idLeta: ""
    }

    componentDidMount() {
        let letid = this.props.match.params.flightid;
        this.setState({
            idLeta: letid
        })
        axios.get('http://localhost:8221/flight/getone/' + letid).then(res => {
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

    sendMessage = (e) => {
        this.setState({
            message: e.target.value
        })
    }

    //ovo ce za sad da bude za slanje zahteva prijatelju
    handleMessageSubmit = (e) => {
        e.preventDefault();

        let email = this.state.message; //ne salje dobro, treba promeniti na serveru

        axios.post('http://localhost:8221/user/invitefriend/1', { email }, { headers: { 'Content-Type': 'text/plain' } }).then(res => {
            console.log(res);
        }).catch(error => {
            console.log(error);
        })
    }

    getTicket = (e) => {
        this.setState({
            idKarte: e.target.value
        })
    }

    confirmReservation = () => {
        let userid = this.state.user.id;
        let ticketid = this.state.idKarte;
        let flightid = this.state.idLeta;
        axios.post('http://localhost:8221/ticket/reserveone/' + userid + '/' + ticketid).then(res => {
            this.props.history.push('/flinfo/' + flightid);    
            alert("Karta uspesno rezervisana");
        }).catch(error => {
            alert("Rezervacija nije uspela")
        })
    }

    render() {
        let i = 1;
        const raspored = this.state.karte.length ? (this.state.karte.map(karta => {
            
            return (
                <div id="planelayout" key={karta.idKarte}>
                    <Fragment>
                    <button  className="btn waves-effect waves-light blue" value={karta.idKarte} id="ticketbtn" onClick={(e) => { this.getTicket(e) }}>{i++}</button><br />
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
                <button className="btn waves-effect waves-light red" id="reservationbtn" onClick={() => { this.confirmReservation() }}>Potvrdi rezervaciju</button><br />
                {
                    (this.state.toggle) ? (
                        <div>
                            <form onSubmit={(e) => { this.handleMessageSubmit(e) }}>
                                <div className="container">
                                    <label htmlFor="friend" className="black">Unesite ime prijatelja:</label>
                                    <div className="input-field">
                                        <input type="text" id="friend" className="browser-default" name="friend" onChange={(e) => { this.sendMessage(e) }} /><br />
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
