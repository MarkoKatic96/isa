import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import './user.css';
import Rectangle from 'react-rectangle';


class Reservation extends Component {
    state = {
        toggle: false,
        message: "",
        brojMesta: ""
    }

    componentDidMount() {
        let letid = this.props.match.params.flightid;
        axios.get('http://localhost:8221/flight/getone/' + letid).then(res =>
        {
            this.setState({
                brojMesta: res.data.brojMesta
            })
            console.log("BROJ MESTA" + this.state.brojMesta)
        }).catch(error =>
            {
                console.log(error);
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

        axios.post('http://localhost:8221/user/invitefriend/1', {email}, {headers: { 'Content-Type': 'text/plain' }}).then(res =>
        {
            console.log(res);
        }).catch(error =>
            {
                console.log(error);
            })
    }

    confirmReservation = () =>
    {
        let list = [1, 1]; //ne salje dobro
        axios.post('http://localhost:8221/ticket/reserveone/1/1').then(res =>
        {
            alert("Karta uspesno rezervisana")
        }).catch(error =>
            {
                alert("Rezervacija nije uspela")
            })
    }

    render() {
        const brojMesta = this.state.brojMesta;
        return (
            <div>
    
    <Rectangle aspectRatio={[5, 3]} id="1" value="5">
      <div value="ej bre" style={{ background: 'white', width: '50px', height: '50px' }} />
    </Rectangle>

    <Rectangle  id="1" value="5">
      <div value="ej bre" style={{ background: 'white', width: '50px', height: '50px' }} />
    </Rectangle>
    

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
