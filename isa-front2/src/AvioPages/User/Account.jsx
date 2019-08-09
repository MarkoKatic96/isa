import React, { Component } from 'react';
import axios from 'axios'
import UserReservations from './UserReservations';
import UserFriends from './UserFriends';
import UserRequests from './UserRequests';

class Account extends Component {

    state = {
        user: "",
        email: "",
        ime: "",
        prezime: "",
        grad: "",
        telefon: "",
        prijateljiKorisnika: [],
        zahteviKorisnika: []
    }

    componentDidMount() {
        axios.get("http://localhost:8221/user/all/" + localStorage.getItem('email'))
            .then(res => {
                this.setState({
                    user: res.data,
                    email: res.data.email,
                    ime: res.data.ime,
                    prezime: res.data.prezime,
                    grad: res.data.grad,
                    telefon: res.data.telefon,
                    prijateljiKorisnika: res.data.prijateljiKorisnika,
                    zahteviKorisnika: res.data.zahteviKorisnika
                })
            })
    }

    changeInputField = (e) =>
    {

    }

    handleSubmit = (e) => {

    }

    render() {

        const user = this.state.user;

        return (
            <div>
                <form className="white" onSubmit={(e) => { this.handleSubmit(e) }}>
                    <h2 className="red-text lighten-1 center">Informacije o korisniku</h2>
                    <div className="container">

                        <label htmlFor="email">Email</label>
                        <div className="input-field">
                            <input type="text" id="email" defaultValue={this.state.email} className="browser-default" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="lozinka">Lozinka</label>
                        <div className="input-field">
                            <input type="password" id="lozinka" className="browser-default" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="ime">Ime</label>
                        <div className="input-field">
                            <input type="text" id="ime" defaultValue={this.state.ime} className="browser-default" name="ime" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="prezime">Prezime</label>
                        <div className="input-field">
                            <input type="text" id="prezime" defaultValue={this.state.prezime} className="browser-default" name="prezime" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="grad">Grad</label>
                        <div className="input-field">
                            <input type="text" id="grad" defaultValue={this.state.grad} className="browser-default" name="grad" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="telefon">Telefon</label>
                        <div className="input-field">
                            <input type="text" id="telefon" defaultValue={this.state.telefon} className="browser-default" name="telefon" onChange={(e) => { this.changeInputField(e) }} />
                        </div>
                        <div className="input-field">
                            <input type="submit" value="Sacuvaj" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                        </div>
                    </div>
                </form>
                <h3>Zahtevi za prijateljstvo:</h3>
                <UserRequests userid={this.state.user.id} />
                <h3>Vasi prijatelji:</h3>
                <UserFriends userid={this.state.user.id} />
                <h3>Vase rezervacije:</h3>
                <UserReservations userid={this.state.user.id} />
            </div>
        );
    }
}

export default Account;
