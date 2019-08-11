import React, { Component } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import Select from 'react-select';

class AvioCompanies extends Component {

    state = {
        user: "",
        destinacije: [],
        company: "",
        naziv: "",
        adresa: "",
        opis: "",
        destinacijeNaKojimaPosluje: []
        
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

            axios.put("http://localhost:8221/aviocompany/adddefaultdest/" + idKompanije).then(ress =>
            {
                axios.get("http://localhost:8221/aviocompany/getone/" + idKompanije, { headers: { Authorization: `Bearer ${token}` } })
                .then(res=>{
                    this.setState({
                        company: res.data,
                        naziv: res.data.naziv,
                        adresa: res.data.adresa,
                        opis: res.data.opis,
                        destinacijeNaKojimaPosluje: res.data.destinacijeNaKojimaPosluje
                    })
                    console.log(res.data);
                    axios.get("http://localhost:8221/destination/getall").then(res=>{
                    console.log(res)
                    this.setState({
                        destinacije: res.data
                    })
                })
            })

           
            })
        })

        
    }

    changeInputField = (e) =>
    {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    changeDestinacijeNaKojimaPosluje = (destinacijeNaKojimaPosluje) => {
        this.setState({ destinacijeNaKojimaPosluje });
        console.log(this.state.destinacijeNaKojimaPosluje)
    }

    handleSubmit = (e) =>
    {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken');

        if(this.state.naziv !== "" && this.state.adresa !== "" && this.state.opis !== "")
        {
            console.log(this.state.destinacijeNaKojimaPosluje)
            axios.put("http://localhost:8221/aviocompany/update/" + this.state.company.idAvioKompanije, 
            {
                naziv: this.state.naziv, adresa: this.state.adresa, opis: this.state.opis, destinacijeNaKojimaPosluje: this.state.destinacijeNaKojimaPosluje
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

    render() {

        var { destinacijeNaKojimaPosluje } = this.state.destinacijeNaKojimaPosluje;

        var { destinacije } = this.state;
        var listaDestinacija = [];

        destinacije.map(dest => {
            let options = new Object();
            options.value = dest.idDestinacije;
            options.label = dest.naziv;
            listaDestinacija.push(options);
        })
        return(
            <div className="center container">
            {(localStorage.getItem('rola') !== 'ADMIN_AVIO_KOMPANIJE') ? (<h1>Nemate pristup ovoj stranici</h1>) : (<div><br/>
                <h3>Izmena aviokompanije:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="naziv">Naziv:</label>
                        <input type="text" id="naziv" value={this.state.naziv} onChange={(e) => { this.changeInputField(e) }}/>
                        <label className="left black-text" htmlFor="adresa">Adresa:</label>
                        <input type="text" id="adresa" value={this.state.adresa} onChange={(e) => { this.changeInputField(e) }}/>
                        <label className="left black-text" htmlFor="opis">Opis:</label>
                        <input type="text" id="opis" value={this.state.opis} onChange={(e) => { this.changeInputField(e) }}/>
                        <label htmlFor="destinacijeNaKojimaPosluje">Destinacije na kojima posluje</label>
                            <Select
                                value={destinacijeNaKojimaPosluje}
                                onChange={(destinacijeNaKojimaPosluje) => { this.changeDestinacijeNaKojimaPosluje(destinacijeNaKojimaPosluje) }}
                                options={listaDestinacija}
                                id="destinacijeNaKojimaPosluje" isMulti={true}/>
                        <button className="btn waves-effect waves-light green">Izmeni</button>
                        

                    </form>
                </div></div>)}
                
            </div>
        )
    }
}

export default withRouter(AvioCompanies);
