import React, { Component } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';

class AvioCompanies extends Component {

    state = {
        user: "",
        company: "",
        name: "",
        address: "",
        description: "",
        destinations: ""
        
    }

    componentDidMount(){
        var token = localStorage.getItem('jwtToken');
        let idKompanije = "";
        axios.get("http://localhost:8221/user/all/" + localStorage.getItem('email'))
        .then(res=>{
            console.log(res)
            this.setState({
                user: res.data
            })
            idKompanije = res.data.zaduzenZaId;
            axios.get("http://localhost:8221/aviocompany/getone/" + idKompanije, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                this.setState({
                    company: res.data,
                    name: res.data.naziv,
                    address: res.data.adresa,
                    description: res.data.opis,
                    destinations: res.data.destinacijeNaKojimaPosluje
                })
                console.log(res.data);
            })
        })
    }

    handleChange = (e) =>
    {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit = (e) =>
    {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken');

        if(this.state.name !== "" && this.state.address !== "" && this.state.description !== "")
        {
            console.log(localStorage.getItem('rola'))
            axios.put("http://localhost:8221/aviocompany/update/" + this.state.company.idAvioKompanije, 
            {
                naziv: this.state.name, adresa: this.state.address, opis: this.state.description
            },  { headers: { Authorization: `Bearer ${token}` } }).then(res =>
                {
                    alert("Informacije aviokompanije su uspesno izmenjene.")
                    this.props.history.push("/");
                }).catch(error=>{
                    console.log(error)
                })
        }
        else
        {
            alert("Sva polja moraju biti ispravno popunjena.")
        }

    }

    render()
    {
        return(
            <div className="center container">
            {(localStorage.getItem('rola') !== 'ADMIN_AVIO_KOMPANIJE') ? (<h1>Nemate pristup ovoj stranici</h1>) : (<div><br/>
                <h3>Izmena aviokompanije:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="name">Naziv:</label>
                        <input type="text" id="name" value={this.state.name} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="address">Adresa:</label>
                        <input type="text" id="address" value={this.state.address} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="description">Opis:</label>
                        <input type="text" id="description" value={this.state.description} onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Izmeni</button>
                    </form>
                </div></div>)}
                
            </div>
        )
    }
}

export default withRouter(AvioCompanies);
