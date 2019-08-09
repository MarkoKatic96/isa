import React, { Component } from 'react';
import axios from 'axios';

class UserReservations extends Component {
    state = {  
        user: "",
        userTickets: []
    }

    componentDidMount() {
        axios.get("http://localhost:8221/user/all/" + localStorage.getItem('email'))
            .then(res => {
                console.log(res)
                this.setState({
                    user: res.data
                })

                axios.get('http://localhost:8221/user/getallreservations/' + res.data.id).then(res =>
                {
                    this.setState({
                        userTickets: res.data
                    })
                    console.log(res);
                })
            })
    }

    deleteReservation = (idKarte) =>
    {
        let userid = this.state.user.id;
        axios.post('http://localhost:8221/ticket/deletereservation/' + userid + '/' + idKarte).then(res =>
                {
                    // alert(res.data)
                    if(res.data)
                    {
                        alert("Rezervacija uspesno otkazana")
                        // this.props.history.push('/account');  
                    }
                    else
                    {
                        alert("Vreme za otkazivanje rezervacije je isteklo")   
                    }
                    
                }).catch(err => {
                    alert("Vreme za otkazivanje rezervacije je isteklo")
                })
    }

    render() {
        const reservationList = this.state.userTickets.length ? (this.state.userTickets.map(ticket => {
            return (
                <div key={ticket.idKarte}>
                    <div className="center container">
                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Broj leta: {ticket.let.brojLeta}</b></span>
                                        <div className="divider white"></div>
                                        <br />
                                        <p>Destinacija poletanja: {ticket.let.destinacijaPoletanja.naziv}</p>
                                        <p>Destinacija sletanja: {ticket.let.destinacijaSletanja.naziv}</p>
                                        <p>Vreme poletanja: {ticket.let.vremePoletanja}</p>
                                        <p>Vreme sletanja: {ticket.let.vremeSletanja}</p>
                                        <p>Vreme rezervisanja: {ticket.vremeRezervisanja}</p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                        <button className="btn waves-effect waves-light red" id="deletebtn" onClick={() => { this.deleteReservation(ticket.idKarte) }}>Ponisti</button>
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

            return(
                <div>
                    {reservationList}
                </div>
            )

    }
}

export default UserReservations;
