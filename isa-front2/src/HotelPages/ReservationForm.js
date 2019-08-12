    
import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';


class ReservationForm extends Component{

    state = {
        datumOd:"",
        datumDo:"",
        soba:"",
        korisnik:"",
        services:[],
        selectedUsluge:[],
        ukupnaCena:""
    }

    componentDidMount(){
        axios.get("http://localhost:8080/usluga/all/" + this.props.match.params.hotelId)
            .then(res => {
                this.setState({
                    services: res.data
                })
            })

            axios.get("http://localhost:8080/sobe/" + this.props.match.params.sobaId)
            .then(res => {
                console.log(res.data);
                this.setState({
                    soba: res.data
                })
            })

            axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {
                console.log(res.data);
                this.setState({
                    korisnik: res.data
                })
            })
    }

    handleChange = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
        if(e.target.id=="datumOd" && this.state.datumDo!=="")
        {
            const startDate = e.target.value;
            const endDate   = this.state.datumDo;
            const timeDiff  = ((new Date(endDate) - new Date(startDate)));
            const days      = timeDiff / (1000 * 60 * 60 * 24)
            this.setState({
                ukupnaCena: +days * +this.state.soba.originalnaCena
            })
        }else if(e.target.id=="datumDo" && this.state.datumOd!==""){
            const startDate = this.state.datumOd;
            const endDate   = e.target.value;
            const timeDiff  = ((new Date(endDate) - new Date(startDate)));
            const days      = timeDiff / (1000 * 60 * 60 * 24)
            this.setState({
                ukupnaCena: +days * +this.state.soba.originalnaCena
            })
        }
    }

    handleSubmit = (e) => {
        e.preventDefault();
    }

    handleCBChange = (e) =>{
        var lista = this.state.selectedUsluge;
        var dodat = 0;
        var i;
        for(i=0; i<lista.length; i++){
            if(lista[i]===e.target.value){ //remove
                lista.splice(i, 1);
                dodat=1;
            }
        }
        if(dodat==0){ //add
            lista.push(e.target.value);
            this.setState({
                ukupnaCena: +this.state.ukupnaCena
            })
            console.log(lista);
        } 
    }

    render(){
        var {services} = this.state;
        var {ukupnaCena} = this.state;
        return(
            //racunanje ukupne cene na osnovu onoga sto se dobije u infoDTO
            <div className = "center container">
                <h4 className="center">Rezervacija smestaja:</h4>
                <div className = "center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="datumOd">Datum od:</label>
                        <input type="date" id="datumOd" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumDo">Datum do:</label>
                        <input type="date" id="datumDo" onChange={this.handleChange}/>
                        { services.map(service => {
                            return(
                                <p key = {service.id}>
                                    <label>
                                        <input className="black" type="checkbox" id={service.id} value={service.id} onChange={this.handleCBChange}/>
                                        <span className="black-text">{service.naziv}........{service.cena}din</span>
                                    </label>
                                </p>
                            )
                        })}
                        <h5 id="ukupnaCena">Ukupna cena: {ukupnaCena}</h5>
                        <button className="btn waves-effect waves-light green">Rezervisi</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(ReservationForm)